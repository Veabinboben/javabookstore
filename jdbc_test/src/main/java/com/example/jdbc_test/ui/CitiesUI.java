package com.example.jdbc_test.ui;

import java.sql.SQLException;

import com.example.jdbc_test.services.CitiesDBService;

public class CitiesUI {
    
    private CitiesDBService citiesDBService;

    public CitiesUI( CitiesDBService citiesDBService){
        this.citiesDBService = citiesDBService;
    }

    public void getCities() throws SQLException{
        String books = citiesDBService.getCities();
        System.out.println(books);
    }
}
