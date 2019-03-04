package kz.akbar.basejava.storage;

import kz.akbar.basejava.exception.NotExistStorageException;
import kz.akbar.basejava.model.*;
import kz.akbar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
        Resume resume = helper.executeSql("SELECT * FROM resumes WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("username"));
        });

        helper.executeSql("SELECT * FROM contacts WHERE resume_uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addContact(rs, resume);
            }
            return null;
        });

        return helper.executeSql("SELECT * FROM opaq_sections WHERE resume_uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addSection(rs, resume);
            }
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> uuidResumes = helper.executeSql("SELECT * FROM resumes",
                preparedStatement -> {
                    Map<String, Resume> resumesMap = new LinkedHashMap<>();
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        resumesMap.put(uuid, new Resume(uuid, rs.getString("username")));
                    }
                    return resumesMap;
                });

        helper.executeSql("SELECT * FROM contacts",
                preparedStatement -> {
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("resume_uuid");
                        ContactType type = ContactType.valueOf(rs.getString("contact_type"));
                        Contact contact = new Contact(type.getTitle(), rs.getString("contact_value"));
                        uuidResumes.get(uuid).addContact(type, contact);
                    }
                    return null;
                });
        return helper.executeSql("SELECT * FROM opaq_sections",
                preparedStatement -> {
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("resume_uuid");
                        uuidResumes.put(uuid, addSection(rs, uuidResumes.get(uuid)));
                    }

                    ArrayList<Resume> resumes = new ArrayList<>(uuidResumes.values());
                    Collections.sort(resumes);
                    return resumes;
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
            writeSections(conn, resume);
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
            deleteSections(conn, resume);
            writeSections(conn, resume);
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

    private Resume addSection(ResultSet rs, Resume resume) throws SQLException {
        String name = rs.getString("section_type");
        if (name != null) {
            SectionType type = SectionType.valueOf(name);
            Section section = null;
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    section = new TextSection(rs.getString("section_value"));
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    String itemsString = rs.getString("section_value");
                    List<String> itemsList = Arrays.asList(itemsString.split("\n"));
                    section = new ListSection(itemsList);
                    break;
            }
            resume.addSection(type, section);
        }
        return resume;
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

    private void deleteSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM opaq_sections WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }

    private void writeSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO opaq_sections (resume_uuid, section_type, section_value) VALUES (?,?,?)")) {
            Map<SectionType, Section> sections = resume.getSections();
            if (sections.size() > 0) {
                for (Map.Entry<SectionType, Section> sectionEntry : sections.entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, sectionEntry.getKey().name());
                    ps.setString(3, sectionEntry.getValue().toString());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }
}
