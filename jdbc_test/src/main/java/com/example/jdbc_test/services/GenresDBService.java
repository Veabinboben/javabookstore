package com.example.jdbc_test.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbc_test.utils.QuerryResult;


public class GenresDBService{
    
    private Connection connection;

    public GenresDBService (Connection connection){
        this.connection = connection;
    }

    public long insertGenre(String name) throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            "INSERT INTO genres (name) VALUES (?)",
            PreparedStatement.RETURN_GENERATED_KEYS
        );
        insert.setString(1, name);
        insert.executeUpdate();
        ResultSet result = insert.getGeneratedKeys();
        long ind = -1;
        if (result.next()){
            ind = result.getLong(1);
        }
        result.close();
        return ind;
    }
                
    public void updateGenreById(String name, int id) throws SQLException{
        PreparedStatement update = connection.prepareStatement(
            "UPDATE genres SET name = ? where id = ?"
        );
        update.setString(1, name);
        update.setInt(2, id);
        update.executeUpdate();
        update.close();
    }

    public void connectGenreBook(int bookId, int genreId)throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            "INSERT INTO book_genres (book_id, genre_id) VALUES (?, ?)"
        );
        insert.setInt(1, bookId);
        insert.setInt(2, genreId);
        insert.executeUpdate();
        insert.close();
    }

    public void disconnectGenreBook (int bookId, int genreId)throws SQLException{
        PreparedStatement delete = connection.prepareStatement(
            "DELETE FROM book_genres where book_id = ? AND genre_id = ?"
        );
        delete.setInt(1, bookId);
        delete.setInt(2, genreId);
        delete.executeUpdate();
        delete.close();
    }

    public List<QuerryResult> getGenresWithName (String name)throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                id, name
            FROM
                genres
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

    public String getGenres() throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            
            SELECT
                id, name
            FROM
                genres;
            
            """
        );
        ResultSet result = select.executeQuery();
        String strRes = "";
        while (result.next()) {
            strRes +=  String.format("Name : %s \n ", 
                result.getString("name")
            ) ;
        }
        result.close();
        return strRes;
    }
}