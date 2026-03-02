package com.example.bookstoreserver.data.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity(name= "stocks")
public class Stock {
        
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "stock")
    private int stock;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "warehouse_id", nullable = false) 
    private Warehouse warehouse;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "book_id", nullable = false) 
    @JsonIgnore
    private Book book;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
