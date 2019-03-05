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
        return helper.transactionalExecuteSql(conn -> {
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM resumes r " +
                    "LEFT JOIN contacts c ON r.uuid = c.resume_uuid " +
                    "LEFT JOIN sections s ON r.uuid = s.resume_uuid " +
                    "WHERE r.uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                String uuid1 = resultSet.getString("uuid");
                String name = resultSet.getString("username");
                Resume resume = new Resume(uuid1, name);
                do {
                    addContact(resultSet, resume);
                    addSection(resultSet, resume);
                } while (resultSet.next());
                return resume;
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.transactionalExecuteSql(conn -> {
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM resumes r " +
                    "LEFT JOIN contacts c ON r.uuid = c.resume_uuid " +
                    "LEFT JOIN sections s on r.uuid = s.resume_uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                Map<String, Resume> uuidResumes = new LinkedHashMap<>();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    String username = resultSet.getString("username");
                    Resume resume = uuidResumes.computeIfAbsent(uuid, uuid1 -> new Resume(uuid1, username));
                    addContact(resultSet, resume);
                    addSection(resultSet, resume);
                }
                List<Resume> resumes = new ArrayList<>(uuidResumes.values());
                Collections.sort(resumes);
                return resumes;
            }
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
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "DELETE FROM contacts WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.executeUpdate();
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

    private void deleteSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM sections WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.executeUpdate();
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
                    preparedStatement.setString(3, sectionEntry.getValue().toString());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        }
    }
}
