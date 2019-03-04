package kz.akbar.basejava.storage;

import kz.akbar.basejava.exception.NotExistStorageException;
import kz.akbar.basejava.model.*;
import kz.akbar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return helper.executeSql("SELECT count(*) FROM resumes", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeSql("SELECT * FROM resumes r " +
                "LEFT JOIN contacts c " +
                "ON r.uuid = c.resume_uuid " +
                "WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(rs.getString("uuid"), rs.getString("username"));
            do {
                addContact(rs, resume);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.executeSql("SELECT * FROM resumes r " +
                        "LEFT JOIN contacts c ON r.uuid = c.resume_uuid " +
                        "ORDER BY username,uuid",
                preparedStatement -> {
                    Map<String, Resume> uuidResumes = new LinkedHashMap<>();
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid").trim();
                        String username = rs.getString("username");
                        Resume resume = uuidResumes.computeIfAbsent(uuid, uuid1 -> new Resume(uuid1, username));
                        addContact(rs, resume);
                    }
                    return new ArrayList<>(uuidResumes.values());
                });
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecuteSql(conn -> {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO resumes (uuid, username) VALUES (?,?)")) {
                Map<SectionType, Section> sections = resume.getSections();
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            writeContacts(conn, resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecuteSql(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE resumes SET username = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(conn, resume);
            writeContacts(conn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.executeSql("DELETE FROM resumes WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void clear() {
        helper.executeSql("DELETE FROM resumes");
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String name = rs.getString("contact_type");
        if (name != null) {
            ContactType type = ContactType.valueOf(name);
            Contact contact = new Contact(type.getTitle(), rs.getString("contact_value"));
            resume.addContact(type, contact);
        }
    }

    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM contacts WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }

    private void writeContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contacts (resume_uuid, contact_type, contact_value) VALUES (?,?,?)")) {
            Map<ContactType, Contact> contacts = resume.getContacts();
            if (contacts.size() > 0) {
                for (Map.Entry<ContactType, Contact> contactEntry : contacts.entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, contactEntry.getKey().name());
                    ps.setString(3, contactEntry.getValue().getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }
}
