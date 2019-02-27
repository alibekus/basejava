package kz.akbar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecution<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
