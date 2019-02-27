package kz.akbar.basejava.storage;

import kz.akbar.basejava.exception.NotExistStorageException;
import kz.akbar.basejava.model.Resume;
import kz.akbar.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        return helper.executeSql("SELECT * FROM resumes r WHERE r.uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("username"));
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.executeSql("SELECT * FROM resumes r ORDER BY username,uuid", preparedStatement -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                String name = rs.getString("username");
                resumes.add(new Resume(uuid, name));
            }
            return resumes;
        });
    }

    @Override
    public void save(Resume resume) {
        helper.executeSql("INSERT INTO resumes (uuid, username) VALUES (?,?)", preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, resume.getFullName());
            return preparedStatement.execute();
        });
    }

    @Override
    public void update(Resume resume) {
        helper.executeSql("UPDATE resumes SET username = ? WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            int updateResult = preparedStatement.executeUpdate();
            if (updateResult == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return updateResult;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.executeSql("DELETE FROM resumes WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            int updateResult = preparedStatement.executeUpdate();
            if (updateResult == 0) {
                throw new NotExistStorageException(uuid);
            }
            return updateResult;
        });
    }

    @Override
    public void clear() {
        helper.executeSql("DELETE FROM resumes");
    }
}
