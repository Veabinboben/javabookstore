package com.example.jdbc_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static Connection connect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/javadb";
        String user = "postgres";
        String password = "admin";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL database successfully.");
            return connection;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e; 
        }
    }
    public static void main(String[] args) {
        try (Connection conn = connect()) {
            DBService service = new DBService(conn);
            ConsoleBookMarket bookMarket = new ConsoleBookMarket(service);
            bookMarket.mainMenu();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}