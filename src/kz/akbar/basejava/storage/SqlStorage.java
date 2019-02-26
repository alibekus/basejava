package kz.akbar.basejava.storage;

import kz.akbar.basejava.exception.ExistStorageException;
import kz.akbar.basejava.exception.NotExistStorageException;
import kz.akbar.basejava.exception.StorageException;
import kz.akbar.basejava.model.Resume;
import kz.akbar.basejava.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public int size() {
        int size = 0;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                size++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("username"));
        } catch (SQLException e) {
            throw new StorageException("Database's record reading error! ",e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resumes")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                String name = rs.getString("username");
                resumes.add(new Resume(uuid,name));
            }
        } catch (SQLException e) {
            throw new StorageException("Database's records reading error!",e);
        }
        return resumes;
    }

    @Override
    public void save(Resume resume) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resumes (uuid, username) VALUES (?,?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void update(Resume resume) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE resumes SET username = ? WHERE uuid = ?")) {
            ps.setString(1,resume.getFullName());
            ps.setString(2,resume.getUuid());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resumes WHERE uuid = ?")) {
            ps.setString(1,uuid);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resumes")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException("Database clear table error!",e);
        }
    }
}
