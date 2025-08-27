package org.foodordering.common;

import org.foodordering.domain.Store;

import java.sql.*;
import java.util.List;

public abstract class AbstractService {

    public Connection getConnection() throws SQLException {
        final String DB_URL = System.getenv("DB_URL");
        final String DB_USER = System.getenv("DB_USER");
        final String DB_PASSWORD = System.getenv("DB_PASSWORD");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void close(ResultSet rs, PreparedStatement ps, Connection connection) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        close(ps, connection);
    }

    public void close(PreparedStatement ps, Connection connection) throws SQLException {
        if (ps != null) {
            ps.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

}
