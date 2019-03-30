package kz.akbar.basejava.sql;

import kz.akbar.basejava.exception.SqlExceptionConvertor;
import kz.akbar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory factory) {
        connectionFactory = factory;
    }

    public void executeSql(String sql) {
        executeSql(sql, PreparedStatement::execute);
    }

    public <T> T executeSql(String sql, SqlExecution<T> executor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw SqlExceptionConvertor.convertException(e);
        }
    }

    public <T> T transactionalExecuteSql(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw SqlExceptionConvertor.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
