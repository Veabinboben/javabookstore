package com.example.jdbc_test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class DBService {
    
    Connection conn;

    //TODO add transaction funcs

    //TODO Maybe cringe -_-
    //TODO unique pair field in m2m
    public DBService (Connection _conn) throws SQLException{
        conn = _conn;
    }
    
    class BooksDBService{

        public void InsertBook(String title, Date publish_date, double price) throws SQLException{
            PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO books (title, publish_date, price) VALUES (?, ?, ?)"
            );
            insert.setString(1, title);
            insert.setDate(2, publish_date);
            insert.setDouble(3, price);
            insert.executeUpdate();
        }

        public void DeleteBookById(int id) throws SQLException{
            PreparedStatement delete = conn.prepareStatement(
                "DELETE FROM books where id = ?"
            );
            delete.setInt(1, id);
            delete.executeUpdate();
        }

        public void UpdateBookById(String title, Date publish_date, double price, int id) throws SQLException{
            PreparedStatement update = conn.prepareStatement(
                "UPDATE FROM books where title = ?, publish_date = ?, price = ? where id = ?"
            );
            update.setString(1, title);
            update.setDate(2, publish_date);
            update.setDouble(3, price);
            update.setInt(4, id);
            update.executeUpdate();
        }
        //TODO
    }

    class AuthorsDBService{
        //TODO
    }

    class GenresDBService{
        //TODO
    }
    
    class WarehouseDBService{
        //TODO
    }
}
