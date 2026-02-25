package com.example.jdbc_test.ui;

import java.sql.SQLException;

import com.example.jdbc_test.services.AuthorsDBService;

public class AuthorsUI {
 
    private AuthorsDBService authorsDBService;

    public AuthorsUI(AuthorsDBService authorsDBService){
        this.authorsDBService = authorsDBService;
    }

    public void getAuthors() throws SQLException{
        String books = authorsDBService.getAuthors();
        System.out.println(books);
    }
}
