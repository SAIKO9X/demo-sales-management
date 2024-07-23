package org.sales.management.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static volatile Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DB.class) {
                if (connection == null) {
                    try {
                        Properties props = loadProperties();
                        String dbUrl = props.getProperty("dburl");
                        String useSsl = props.getProperty("useSSL");
                        String dbUser = resolveEnvVar(props.getProperty("dbuser"));
                        String dbPassword = resolveEnvVar(props.getProperty("dbpassword"));
                        
                        String jdbcUrl = dbUrl + (useSsl != null ? "?useSSL=" + useSsl + "&allowPublicKeyRetrieval=true" : "?allowPublicKeyRetrieval=true");

                        connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new DbException("Error connecting to the database: " + e.getMessage());
                    }
                }
            }
        }
        return connection;
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("src/main/resources/db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
            throw new DbException("Error loading properties file: " + e.getMessage());
        }
    }

    private static String resolveEnvVar(String value) {
        if (value != null && value.startsWith("${") && value.endsWith("}")) {
            String envVarName = value.substring(2, value.length() - 1);
            return System.getenv(envVarName);
        }
        return value;
    }

    public static void closeConnection() {
        if (connection != null) {
            synchronized (DB.class) {
                if (connection != null) {
                    try {
                        connection.close();
                        connection = null;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new DbException("Error closing the database connection: " + e.getMessage());
                    }
                }
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DbException("Error closing statement: " + e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet set) {
        if (set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DbException("Error closing result set: " + e.getMessage());
            }
        }
    }
}
