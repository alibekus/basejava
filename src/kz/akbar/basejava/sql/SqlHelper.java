package kz.akbar.basejava.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory factory) {
        connectionFactory = factory;
    }

    public void executeSql(String sql) {
        executeSql(sql, preparedStatement -> preparedStatement.execute());
    }

    public <T> T executeSql(String sql, SqlExecution<T> executor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            throw SqlExceptionConvertor.convertException(e);
        }
    }
}
