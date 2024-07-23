package org.sales.management.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = loadProperties();

                String dbUser = props.getProperty("user");
                String dbPassword = props.getProperty("password");
                String dbUrl = props.getProperty("dburl");
                String useSsl = props.getProperty("useSSL");
                String jdbcUrl = dbUrl + (useSsl != null ? "?useSSL=" + useSsl : "");

                connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("src/main/resources/db.properties")) {
            Properties props = new Properties();
            props.load(fs);

            String user = System.getenv("MYSQL_USER");
            String password = System.getenv("MYSQL_PASSWORD");
            props.setProperty("user", user != null ? user : props.getProperty("user"));
            props.setProperty("password", password != null ? password : props.getProperty("password"));

            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet set) {
        if (set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
