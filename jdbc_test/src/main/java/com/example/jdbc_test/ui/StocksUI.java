package com.example.jdbc_test.ui;

import java.sql.SQLException;
import java.util.List;

import com.example.jdbc_test.services.CitiesDBService;
import com.example.jdbc_test.services.TransactionManager;
import com.example.jdbc_test.services.WarehouseDBService;
import com.example.jdbc_test.utils.InputUtils;
import com.example.jdbc_test.utils.QuerryResult;

public class StocksUI {

    private WarehouseDBService warehouseDBService;
    private CitiesDBService citiesDBService;
    private TransactionManager transactionManager;
    private BooksUI booksUI;

    public StocksUI(WarehouseDBService warehouseDBService, CitiesDBService citiesDBService, TransactionManager transactionManager, BooksUI booksUI){
        this.warehouseDBService = warehouseDBService;
        this.citiesDBService = citiesDBService;
        this.transactionManager = transactionManager;
        this.booksUI = booksUI;
    }
    
    public void addStock() throws SQLException{
        try{
            transactionManager.startTransaction();
            
            int bookId = (int)booksUI.chooseBook();
            if (bookId == -1) {
                System.out.println("This book does not exist");
                return;
            }
    
        //Warehouses
            while (true){
                System.out.println("Stock another warehouse?");
                boolean answ = InputUtils.inputYN();
                if (!answ) break;
                System.out.println("Stock new warehouse?");
                boolean answ1 = InputUtils.inputYN();
                if (answ1){
                    long warehouseId = insertWarehouse();
                    if (warehouseId != -1){
                        System.out.println("Input stock ammount");
                        int stock = InputUtils.inputInt();
                        warehouseDBService.stockWarehouseWithBooks((int)bookId, (int)warehouseId, stock);
                    }
                    else{
                        System.out.println("Could not insert new warehouse");
                    }
                }
                else { 
                    System.out.println("Change stock in existing warehouse?");
                    boolean answ2 = InputUtils.inputYN();
                    if (answ2)
                    {
                        int warehouseId = (int)chooseWarehouse();
                        if (warehouseId != -1){
                            System.out.println("Input stock ammount");
                            int stock = InputUtils.inputInt();
                            warehouseDBService.stockWarehouseWithBooks((int)bookId, (int)warehouseId, stock);
                        }
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

    public long choseCity() throws SQLException{
        System.out.println("Show cities list?");
        boolean answ = InputUtils.inputYN();
        if (answ) System.out.println(citiesDBService.getCities());

        System.out.println("Input city's name");
        String name = InputUtils.inputString();

        List<QuerryResult> cities = citiesDBService.getCitiesWithName(name);

        if (cities.size() > 1){
            System.out.println("Choose one");
            int count = cities.size();
            for (int i = 0 ; i < count; i++){
                System.out.println(String.format("%d : %s", i+1, cities.get(i).view()));
            }
            int rowNum;
            while (true) {
                rowNum = InputUtils.inputInt(); 
                if (rowNum < 1 || rowNum > count) System.out.println("Wrong index, please retry");
                else break;              
            }
            return cities.get(rowNum-1).id();
        }
        else if (cities.size() == 1){
            System.out.println(cities.get(0).view());
            return cities.get(0).id();
        }
        else{
            System.out.println("No city with this name");
            return -1;
        }
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
            cityId = (int)choseCity();
        } 
        if (cityId == -1) return -1;
        return warehouseDBService.insertWarehouse(adress,cityId);
    }

    public long chooseWarehouse() throws SQLException{
        System.out.println("Show warehouse list?");
        boolean answ = InputUtils.inputYN();
        if (answ) System.out.println(warehouseDBService.getWarehouses());

        System.out.println("Input warehouse's name");
        String name = InputUtils.inputString();

        List<QuerryResult> warehouses = warehouseDBService.getWarehousesWithName(name);

        if (warehouses.size() > 1){
            System.out.println("Choose one");
            int count = warehouses.size();
            for (int i = 0 ; i < count; i++){
                System.out.println(String.format("%d : %s", i+1, warehouses.get(i).view()));
            }
            int rowNum;
            while (true) {
                rowNum = InputUtils.inputInt(); 
                if (rowNum < 1 || rowNum > count) System.out.println("Wrong index, please retry");
                else break;              
            }
            return warehouses.get(rowNum-1).id();
        }
        else if (warehouses.size() == 1){
            System.out.println(warehouses.get(0).view());
            return warehouses.get(0).id();
        }
        else{
            System.out.println("No warehouse with this name");
            return -1;
        }
    }


    public void getStocks() throws SQLException{
        String books = warehouseDBService.getStocks();
        System.out.println(books);
    }    
}
