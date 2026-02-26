package com.example.jdbc_test.services;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbc_test.utils.QuerryResult;

public class AuthorsDBService{

    private Connection connection;

    public AuthorsDBService(Connection connection){
        this.connection = connection;
    }

    public long insertAuthor(String name, String middleName, String surname, Date birthday, String bio, String photolink) throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
        "INSERT INTO authors (name, middle_name, surname, birthday, bio, photo_link) VALUES (?, ?, ?, ?, ?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS
        );
        insert.setString(1, name);
        insert.setString(2, middleName);
        insert.setString(3, surname);
        insert.setDate(4, birthday);
        insert.setString(5, bio);
        insert.setString(6, photolink);
        insert.executeUpdate();
        ResultSet result = insert.getGeneratedKeys();
        long ind = -1;
        if (result.next()){
            ind = result.getLong(1);
        }
        result.close();
        return ind; 
    }

    public void updateAuthorById(String name, String middleName, String surname, Date birthday, String bio, String photolink, int id) throws SQLException{
        PreparedStatement update = connection.prepareStatement(
            "UPDATE authors SET name = ? , middle_name = ?, surname = ? , birthday = ? , bio = ?, photo_link = ? where id = ?"
        );
        update.setString(1, name);
        update.setString(2, middleName);
        update.setString(3, surname);
        update.setDate(4, birthday);
        update.setString(5, bio);
        update.setString(6, photolink);
        update.setInt(7, id);
        update.executeUpdate();
        update.close();
    }

    public void connectAuthorBook (int bookId, int authorId)throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            "INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)"
        );
        insert.setInt(1, bookId);
        insert.setInt(2, authorId);
        insert.executeUpdate();
        insert.close();
    }

    public void disconnectAuthorBook (int bookId, int authorId)throws SQLException{
        PreparedStatement delete = connection.prepareStatement(
            "DELETE FROM book_authors where book_id = ? AND author_id = ?"
        );
        delete.setInt(1, bookId);
        delete.setInt(2, authorId);
        delete.executeUpdate();
        delete.close();
    }

    public List<QuerryResult> getAuthorsWithName (String name)throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                id, name, middle_name, surname 
            FROM
                authors
            where 
                name LIKE ?;
            """
        );
        select.setString(1, '%' + name + '%');
        ResultSet result = select.executeQuery();
        List<QuerryResult> strRes = new ArrayList<QuerryResult>();
        while (result.next()) {
    
            strRes.add(new QuerryResult( result.getInt("id"),String.format("Name : %s \tMiddle name: %s \tSurname: %s \n ", 
                result.getString("name"),
                result.getString("middle_name"),
                result.getString("surname")
            ))); 
        }
        result.close();
        return strRes;
    }

    public String getAuthorById(int id) throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                name, middle_name, surname 
            FROM
                authors
            where 
                id = ?;
            """
        );
        select.setInt(1, id);
        ResultSet result = select.executeQuery();
        String strRes = null;
        if (result.next()) {
            
            strRes  = String.format("Name : %s \nMiddle name: %s \nSurname: %s ", 
                result.getString("name"),
                result.getString("middle_name"),
                result.getString("surname")
            );
        }
        result.close();
        return strRes;
    }

    public String getAuthors() throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                name, middle_name, surname 
            FROM
                authors;
            """
        );
        ResultSet result = select.executeQuery();
        String strRes = "";
        while (result.next()) {
            strRes +=  String.format("Name : %s \tMiddle name: %s \tSurname: %s \n", 
                result.getString("name"),
                result.getString("middle_name"),
                result.getString("surname")
            ) ;
        }
        result.close();
        return strRes;
    }
}