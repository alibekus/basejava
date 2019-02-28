package kz.akbar.basejava.storage;

import kz.akbar.basejava.exception.NotExistStorageException;
import kz.akbar.basejava.model.Contact;
import kz.akbar.basejava.model.ContactType;
import kz.akbar.basejava.model.Resume;
import kz.akbar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
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
                ContactType type = ContactType.valueOf(rs.getString("type"));
                Contact contact = new Contact(type.getTitle(), rs.getString("value"));
                resume.addContact(type, contact);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.executeSql("SELECT * FROM resumes r LEFT JOIN contacts c ON r.uuid = c.resume_uuid " +
                        "ORDER BY username,uuid",
                preparedStatement -> {
                    List<Resume> resumes = new ArrayList<>();
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid").trim();
                        String name = rs.getString("username");
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        Contact contact = new Contact(type.getTitle(), rs.getString("value"));
                        Resume resume = new Resume(uuid, name);
                        resume.addContact(type, contact);
                        resumes.add(resume);
                    }
                    return resumes;
                });
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecuteSql(conn -> {
            tryWriteResume("INSERT INTO resumes (uuid, username) VALUES (?,?)", conn, resume);
            tryWriteContact("INSERT INTO contacts (resume_uuid, type, value) VALUES (?, ?,?)", conn, resume);
            return true;
        });
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecuteSql(conn -> {
            tryWriteResume("UPDATE resumes SET username = ? WHERE uuid = ?", conn, resume);
            tryWriteContact("UPDATE contacts SET type = ?, value=? WHERE resume_uuid = ?", conn, resume);
            return true;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.executeSql("DELETE FROM resumes WHERE uuid = ?", prepStat -> {
            prepStat.setString(1, uuid);
            if (prepStat.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return 1;
        });
    }

    @Override
    public void clear() {
        helper.executeSql("DELETE FROM resumes");
    }

    private void tryWriteResume(String sql, Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, resume.getFullName());
            if (sql.startsWith("UPDATE")) {
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            } else {
                preparedStatement.execute();
            }
        }
    }

    private void tryWriteContact(String sql, Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement prepStat = conn.prepareStatement(sql)) {
            for (Map.Entry<ContactType, Contact> contactEntry : resume.getContacts().entrySet()) {
                if (sql.startsWith("INSERT")) {
                    writeContactData(prepStat, resume, contactEntry, new int[]{1, 2, 3});
                } else {
                    writeContactData(prepStat, resume, contactEntry, new int[]{3, 1, 2});
                }
            }
            prepStat.executeBatch();
        }
    }

    private void writeContactData(PreparedStatement preparedStatement, Resume resume, Map.Entry<ContactType, Contact> contactEntry,
                                  int[] order) throws SQLException {
        preparedStatement.setString(order[0], resume.getUuid());
        preparedStatement.setString(order[1], contactEntry.getKey().name());
        preparedStatement.setString(order[2], contactEntry.getValue().getValue());
        preparedStatement.addBatch();
    }
}
