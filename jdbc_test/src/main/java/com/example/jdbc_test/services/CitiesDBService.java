package com.example.jdbc_test.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CitiesDBService{

    private Connection connection;

    public CitiesDBService(Connection connection){
        this.connection = connection;
    }

    public long insertCity(String name) throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            "INSERT INTO cities (name) VALUES (?)",
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
                
    public void updateCityById(String name, int id) throws SQLException{
        PreparedStatement update = connection.prepareStatement(
            "UPDATE cities SET name = ? where id = ?"
        );
        update.setString(1, name);
        update.setInt(2, id);
        update.executeUpdate();
        update.close();
    }

    public String getCities() throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            
            SELECT
                id, name
            FROM
                cities;
            
            """
        );
        ResultSet result = select.executeQuery();
        String strRes = "";
        while (result.next()) {
            strRes +=  result.getString("id") + 
            ' ' + result.getString("name") + 
            '\n' ;
        }
        result.close();
        return strRes;
    }
}