package com.example.jdbc_test.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbc_test.utils.QuerryResult;

public class PublishersDBService{

    private Connection connection;

    public PublishersDBService (Connection connection){
        this.connection = connection;
    }

    public long insertPublisher(String name, String description) throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            "INSERT INTO publishers (name, description) VALUES (?,  ?)"
        );
        insert.setString(1, name);
        insert.setString(2, description);
        insert.executeUpdate();
        ResultSet result = insert.getGeneratedKeys();
        long ind = -1;
        if (result.next()){
            ind = result.getLong(1);
        }
        result.close();
        return ind; 
    }
                
    public void updatePublisherById(String name, String description, int id) throws SQLException{
        PreparedStatement update = connection.prepareStatement(
            "UPDATE publishers SET name = ?, description = ? where id = ?"
        );
        update.setString(1, name);
        update.setString(2, description);
        update.setInt(3, id);
        update.executeUpdate();
        update.close();
    }

    public void connectPublisherBook(int bookId, int publisherId)throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            "INSERT INTO book_publishers (book_id, publisher_id) VALUES (?, ?)"
        );
        insert.setInt(1, bookId);
        insert.setInt(2, publisherId);
        insert.executeUpdate();
        insert.close();
    }

    public void disconnectPublisherBook (int bookId, int publisherId)throws SQLException{
        PreparedStatement delete = connection.prepareStatement(
            "DELETE FROM book_publishers where book_id = ? AND publisher_id = ?"
        );
        delete.setInt(1, bookId);
        delete.setInt(2, publisherId);
        delete.executeUpdate();
        delete.close();
    }

    public List<QuerryResult> getPublishersWithName (String name)throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                id, name
            FROM
                publishers
            where 
                name LIKE ?;
            """
        );
        select.setString(1, '%' + name + '%');
        ResultSet result = select.executeQuery();
        List<QuerryResult> strRes = new ArrayList<QuerryResult>();
        while (result.next()) {
                strRes.add(new QuerryResult( result.getInt("id"),String.format("Name : %s \n", 
                result.getString("name")
            ))); 
        }
        result.close();
        return strRes;
    }

    public String getPublishers() throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            
            SELECT
                id, name
            FROM
                publishers;
            
            """
        );
        ResultSet result = select.executeQuery();
        String strRes = "";
        while (result.next()) {
            strRes +=  String.format("Name : %s \n", 
                result.getString("name")
            ) ;
        }
        result.close();
        return strRes;
    }
}