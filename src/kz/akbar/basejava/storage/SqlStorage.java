package kz.akbar.basejava.storage;

import kz.akbar.basejava.exception.NotExistStorageException;
import kz.akbar.basejava.model.Contact;
import kz.akbar.basejava.model.ContactType;
import kz.akbar.basejava.model.Resume;
import kz.akbar.basejava.sql.SqlExecution;
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
                "WHERE r.uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("username"));
            do {
                addContact(resume, rs);
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
                        Resume resume = uuidResumes.get(uuid);
                        if (resume == null) {
                            resume = new Resume(uuid, rs.getString("username"));
                            uuidResumes.put(uuid, resume);
                        }
                        addContact(resume, rs);
                    }
                    return new ArrayList<>(uuidResumes.values());
                });
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecuteSql(conn -> {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO resumes (username, uuid) VALUES (?,?)")) {
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.execute();
            }
            writeContacts(conn, resume);
            return true;
        });
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecuteSql(conn -> {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE resumes SET username = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(resume);
            writeContacts(conn, resume);
            return true;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.executeSql("DELETE FROM resumes WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return 1;
        });
    }

    @Override
    public void clear() {
        helper.executeSql("DELETE FROM resumes");
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String title = rs.getString("type");
        if (title != null) {
            ContactType type = ContactType.valueOf(title);
            Contact contact = new Contact(type.getTitle(), rs.getString("value"));
            resume.addContact(type, contact);
        }
    }

    private void deleteContacts(Resume resume) {
        helper.executeSql("DELETE FROM contacts WHERE resume_uuid=?", (SqlExecution<Resume>) ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void writeContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contacts (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, Contact> contactEntry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contactEntry.getKey().name());
                ps.setString(3, contactEntry.getValue().getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
