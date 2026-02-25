package com.example.jdbc_test.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class WarehouseDBService{

    private Connection connection;

    public WarehouseDBService (Connection connection){
        this.connection = connection;
    }

    public long insertWarehouse(String adress, int cityId) throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            "INSERT INTO warehouses (adress, city_id) VALUES (?,  ?)",
            PreparedStatement.RETURN_GENERATED_KEYS
        );
        insert.setString(1, adress);
        insert.setInt(2, cityId);
        insert.executeUpdate();
        ResultSet result = insert.getGeneratedKeys();
        long ind = -1;
        if (result.next()){
            ind = result.getLong(1);
        }
        result.close();
        return ind;
    }
                
    public void updateWarehouseById(String adress, int cityId, int id) throws SQLException{
        PreparedStatement update = connection.prepareStatement(
            "UPDATE warehouses SET adress = ?, city_id = ? where id = ?"
        );
        update.setString(1, adress);
        update.setInt(2, cityId);
        update.setInt(3, id);
        update.executeUpdate();
        update.close();
    }

    public void stockWarehouseWithBooks(int bookId, int warehoseId, int stock)throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            """
            INSERT INTO stocks (book_id, warehouse_id, stock) VALUES (?, ?, ?) 
            ON CONFLICT (book_id, warehouse_id) DO UPDATE SET stock = excluded.stock
            """
        );
        insert.setInt(1, bookId);
        insert.setInt(2, warehoseId);
        insert.setInt(3, stock);
        insert.executeUpdate();
        insert.close();
    }

    public String getStocks() throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                b.title AS book_title,
                STRING_AGG(CONCAT(w.adress, ' ', c.name, ' ', s.stock), ', ') AS stocks
            FROM
                books b
            LEFT JOIN
                stocks s ON b.id = s.book_id
            LEFT JOIN
                warehouses w ON s.warehouse_id = w.id
            LEFT JOIN
                cities c ON c.id = w.city_id
            GROUP BY
                b.id;
            """
        );
        ResultSet result = select.executeQuery();
        String strRes = "";
        while (result.next()) {
            strRes +=  result.getString("book_title") + 
            "\t|" + result.getString("stocks") + 
            '\n' ;
        }
        result.close();
        return strRes;
    }

}