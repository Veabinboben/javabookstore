package com.example.jdbc_test.services;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbc_test.utils.QuerryResult;


public class BooksDBService{

    private Connection connection;

    public BooksDBService(Connection connection){
        this.connection = connection;
    }

    public long insertBook(String title, Date publishDate, double price) throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            "INSERT INTO books (title, publish_date, price) VALUES (?, ?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS
        );
        insert.setString(1, title);
        insert.setDate(2, publishDate);
        insert.setDouble(3, price);
        insert.executeUpdate();
        ResultSet result = insert.getGeneratedKeys();
        long ind = -1;
        if (result.next()){
            ind = result.getLong(1);
        }
        result.close();
        return ind;
    }

    public void deleteBookById(int id) throws SQLException{
        PreparedStatement delete = connection.prepareStatement(
            "DELETE FROM books where id = ?"
        );
        delete.setInt(1, id);
        delete.executeUpdate();
        delete.close();
    }

    public void updateBookById(String title, Date publishDate, double price, int id) throws SQLException{
        PreparedStatement update = connection.prepareStatement(
            "UPDATE books SET title = ?, publish_date = ?, price = ? where id = ?"
        );
        update.setString(1, title);
        update.setDate(2, publishDate);
        update.setDouble(3, price);
        update.setInt(4, id);
        update.executeUpdate();
        update.close();
    }

    public String getBookById(int id) throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                b.id as id,
                b.title AS book_title,
                b.publish_date AS date,
                STRING_AGG(g.name, ', ') AS genres,
                STRING_AGG(CONCAT(a.name, ' ', a.middle_name), ', ') AS authors
            FROM
                books b
            LEFT JOIN
                book_genres bg ON b.id = bg.book_id
            LEFT JOIN
                genres g ON bg.genre_id = g.id
            LEFT JOIN
                book_authors ba ON ba.book_id = b.id
            LEFT JOIN
                authors a ON a.id = ba.author_id
            WHERE
                b.id = ?
            GROUP BY
                b.id;

            """
        );
        select.setInt(1, id);
        ResultSet result = select.executeQuery();
        String strRes = null;
        if (result.next()) {
            
            strRes  = String.format("Name : %s \nPublish Date: %s \nGenres: %s \nAuthors: %s", 
                result.getString("book_title"),
                result.getString("date"),
                result.getString("genres"),
                result.getString("authors")
            );
        }
        result.close();
        return strRes;
    }

    public List<QuerryResult> getBooksWithName (String name)throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                b.id as id,
                b.title AS book_title,
                STRING_AGG(g.name, ', ') AS genres,
                STRING_AGG(CONCAT(a.name, ' ', a.middle_name), ', ') AS authors
            FROM
                books b
            LEFT JOIN
                book_genres bg ON b.id = bg.book_id
            LEFT JOIN
                genres g ON bg.genre_id = g.id
            LEFT JOIN
                book_authors ba ON ba.book_id = b.id
            LEFT JOIN
                authors a ON a.id = ba.author_id
            WHERE 
                b.title LIKE ? 
            GROUP BY
                b.id;
            """
        );
        select.setString(1, '%' + name + '%');
        ResultSet result = select.executeQuery();
        List<QuerryResult> strRes = new ArrayList<QuerryResult>();
        while (result.next()) {
    
            strRes.add(new QuerryResult( result.getInt("id"),
                String.format("Title : %s \tGenres: %s \tAuthors: %s \n ", 
                result.getString("book_title"),
                result.getString("genres"),
                result.getString("authors")
            ))); 
        }
        result.close();
        return strRes;
    }

    public String getBooks() throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                b.id as id,
                b.title AS book_title,
                STRING_AGG(g.name, ', ') AS genres,
                STRING_AGG(CONCAT(a.name, ' ', a.middle_name), ', ') AS authors
            FROM
                books b
            LEFT JOIN
                book_genres bg ON b.id = bg.book_id
            LEFT JOIN
                genres g ON bg.genre_id = g.id
            LEFT JOIN
                book_authors ba ON ba.book_id = b.id
            LEFT JOIN
                authors a ON a.id = ba.author_id
            GROUP BY
                b.id;

            """
        );
        ResultSet result = select.executeQuery();
        String strRes = "";
        while (result.next()) {
            strRes += String.format("Title : %s \tGenres: %s \tAuthors: %s \n ", 
                result.getString("book_title"),
                result.getString("genres"),
                result.getString("authors")
            ) ;
        }
        result.close();
        return strRes;
    }
}