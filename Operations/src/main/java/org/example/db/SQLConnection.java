package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private final String jdbcUrl = "";
    private final String user = "";
    private final String password = "";
    private static SQLConnection instance;
    private SQLConnection () {}
    public static SQLConnection getInstance() {
        if (instance == null) {
            instance = new SQLConnection();
        }
        return instance;
    }

    public Connection getConnection () throws ClassNotFoundException, SQLException {
        // Carga el controlador JDBC
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(this.jdbcUrl, this.user, this.password);
    }

}