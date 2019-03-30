package kz.akbar.basejava.storage;

import kz.akbar.basejava.exception.NotExistStorageException;
import kz.akbar.basejava.model.*;
import kz.akbar.basejava.sql.SqlHelper;
import kz.akbar.basejava.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
        System.out.println("SqlStorage constructed!");
        try {
            System.out.println("Postgres driver loading...");
            Class.forName("org.postgresql.Driver");
            System.out.println("Postgres driver has been loaded successfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Postgres driver class loading error: " + e.getCause());
        }
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
        return helper.transactionalExecuteSql(conn -> {
            Resume resume;
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM resumes " +
                    "WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                String uuid1 = resultSet.getString("uuid");
                String name = resultSet.getString("username");
                resume = new Resume(uuid1, name);
            }
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM contacts " +
                    "WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addContact(resultSet, resume);
                }
            }
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM sections " +
                    "WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addSection(resultSet, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.transactionalExecuteSql(conn -> {
            Map<String, Resume> uuidResumes = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM resumes " +
                    "ORDER BY username, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    String username = resultSet.getString("username");
                    uuidResumes.put(uuid, new Resume(uuid, username));
                }
            }
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM contacts")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("resume_uuid");
                    addContact(resultSet, uuidResumes.get(uuid));
                }
            }
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM sections")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("resume_uuid");
                    addSection(resultSet, uuidResumes.get(uuid));
                }
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
            writeSections(conn, resume);
            return null;
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
            deleteContacts(conn, resume);
            writeContacts(conn, resume);
            deleteSections(conn, resume);
            writeSections(conn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.executeSql("DELETE FROM resumes WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
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

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("section_value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("section_type"));
            Section section = JsonParser.read(value, Section.class);
            resume.addSection(type, section);
        }
    }

    private void writeContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO contacts (resume_uuid, contact_type, contact_value) VALUES (?,?,?)")) {
            Map<ContactType, Contact> contacts = resume.getContacts();
            if (contacts.size() > 0) {
                for (Map.Entry<ContactType, Contact> contactEntry : contacts.entrySet()) {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, contactEntry.getKey().name());
                    preparedStatement.setString(3, contactEntry.getValue().getValue());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        }
    }

    private void writeSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO sections (resume_uuid, section_type, section_value) VALUES (?,?,?)")) {
            Map<SectionType, Section> sections = resume.getSections();
            if (sections.size() > 0) {
                for (Map.Entry<SectionType, Section> sectionEntry : sections.entrySet()) {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, sectionEntry.getKey().name());
                    Section section = sectionEntry.getValue();
                    preparedStatement.setString(3, JsonParser.write(section, Section.class));
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        }
    }

    private void deleteResumeData(Connection conn, Resume resume, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.executeUpdate();
        }
    }

    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        deleteResumeData(conn, resume, "DELETE FROM contacts WHERE resume_uuid = ?");
    }

    private void deleteSections(Connection conn, Resume resume) throws SQLException {
        deleteResumeData(conn, resume, "DELETE FROM sections WHERE resume_uuid = ?");
    }
}
