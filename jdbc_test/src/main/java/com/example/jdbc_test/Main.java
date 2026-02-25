package com.example.jdbc_test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.crypto.Data;

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

    // public static void getTables(Connection conn) throws  SQLException{
    //         String[] typs = {"TABLE"};
    //         DatabaseMetaData meta = conn.getMetaData();
    //         ResultSet tables = meta.getTables(null, null, null, typs);
    //         while (tables.next()){
    //             ResultSet columns = meta.getColumns(null, null, tables.getString("TABLE_NAME"), "%");
    //             System.out.println(" <-> " + tables.getString("TABLE_NAME"));
    //             while (columns.next()) {
    //                 String columnName = columns.getString("COLUMN_NAME");
    //                 String columnType = columns.getString("TYPE_NAME");
    //                 int columnSize = columns.getInt("COLUMN_SIZE");
    //                 System.out.println("  - " + columnName + " (" + columnType + ", Size: " + columnSize + ")");
    //             }
    //         }
    // }

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