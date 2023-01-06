package com.pluralsight;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;

public class DBConnection {
    private Connection jdbcConnection;

    public DBConnection() {
        connect();
    }

    public Connection getConnection() {
        return jdbcConnection;
    }

    public void connect()  {
        try {
            Class.forName("org.sqlite.JDBC");
            jdbcConnection = DriverManager.getConnection("jdbc:sqlite:todolist.db");
            System.out.println("Opened database successfully");

            createTableIfNotExists();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private void createTableIfNotExists() {
        try {
            DatabaseMetaData meta = jdbcConnection.getMetaData();
            ResultSet res = meta.getTables(null, null, null, new String[] {"TABLE"});
            Statement stmt = jdbcConnection.createStatement();
            if (!res.next()) {
                // Create table

                String sql = "CREATE TABLE todo " +
                        "(id INTEGER PRIMARY KEY NOT NULL," +
                        " item TEXT NOT NULL, " +
                        " date TEXT)";
                stmt.executeUpdate(sql);

                sql = "INSERT INTO todo (item, date) VALUES (\"Workout\", \"" +
                        LocalDate.now().toString()  + "\")";
                stmt.executeUpdate(sql);

                stmt.close();
            }
            else {
                System.out.print(res);
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    public void disconnect() {
        try {
            if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                jdbcConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
