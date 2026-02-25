package com.example.jdbc_test.ui;

import java.sql.SQLException;

import com.example.jdbc_test.services.CitiesDBService;
import com.example.jdbc_test.services.TransactionManager;
import com.example.jdbc_test.services.WarehouseDBService;
import com.example.jdbc_test.utils.InputUtils;

public class StocksUI {

    private WarehouseDBService warehouseDBService;
    private CitiesDBService citiesDBService;
    private TransactionManager transactionManager;

    public StocksUI(WarehouseDBService warehouseDBService, CitiesDBService citiesDBService, TransactionManager transactionManager){
        this.warehouseDBService = warehouseDBService;
        this.citiesDBService = citiesDBService;
        this. transactionManager = transactionManager;
    }
    
    public void addStock() throws SQLException{
        try{
            transactionManager.startTransaction();
            
            System.out.println("Input book id");
            int bookId = InputUtils.inputInt();

        //Warehouses
            while (true){
                System.out.println("Stock another warehouse?");
                boolean answ = InputUtils.inputYN();
                if (!answ) break;
                System.out.println("Stock new warehouse?");
                boolean answ1 = InputUtils.inputYN();
                if (answ1){
                    long warehouseId = insertWarehouse();
                    System.out.println("Input stock ammount");
                    int stock = InputUtils.inputInt();
                    warehouseDBService.stockWarehouseWithBooks((int)bookId, (int)warehouseId, stock);
                }
                else { 
                    System.out.println("Change stock in existing warehouse?");
                    boolean answ2 = InputUtils.inputYN();
                    if (answ2)
                    {
                        System.out.println("Input warehouse id");
                        int warehouseId = InputUtils.inputInt();
                        System.out.println("Input stock ammount");
                        int stock = InputUtils.inputInt();
                        warehouseDBService.stockWarehouseWithBooks((int)bookId, (int)warehouseId, stock);
                    }
                }
            }    

            transactionManager.commitTransaction();
        }
        catch (SQLException e){
            transactionManager.abortTransaction();
            throw e;
        }
          
    }

    private long insertCity() throws SQLException{
        System.out.println("Input city name");
        String name = InputUtils.inputString();

        return citiesDBService.insertCity(name);
    }

    private long insertWarehouse() throws SQLException{
        System.out.println("Input warehouse adress");
        String adress = InputUtils.inputString();

        int cityId;

        //City
        System.out.println("Warehouse in a new city (choose existing otherwise)");
        boolean answ1 = InputUtils.inputYN();
        if (answ1){
            cityId = (int)insertCity();
        }
        else { 
            System.out.println("Input city id");
            cityId = InputUtils.inputInt();
        } 

        return warehouseDBService.insertWarehouse(adress,cityId);
    }


    public void getStocks() throws SQLException{
        String books = warehouseDBService.getStocks();
        System.out.println(books);
    }    
}
