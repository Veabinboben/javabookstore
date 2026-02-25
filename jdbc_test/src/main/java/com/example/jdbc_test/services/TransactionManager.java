package com.example.jdbc_test.services;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    
    private Connection connection;

    public TransactionManager (Connection connection){
        this.connection = connection;
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void abortTransaction() throws SQLException {
        connection.rollback();
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
    }
}
