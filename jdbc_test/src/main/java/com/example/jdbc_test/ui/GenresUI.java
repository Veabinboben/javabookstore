package com.example.jdbc_test.ui;

import java.sql.SQLException;

import com.example.jdbc_test.services.GenresDBService;

public class GenresUI {
    
    private GenresDBService genresDBService;

    public GenresUI(GenresDBService genresDBService){
        this.genresDBService = genresDBService;
    }

    public void getGenres() throws SQLException{
        String books = genresDBService.getGenres();
        System.out.println(books);
    }
}
