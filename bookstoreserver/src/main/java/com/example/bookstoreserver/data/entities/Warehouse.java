package com.example.bookstoreserver.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "adress")
    @NotNull
    private String adress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    public Long getId() {
        return id;
    }

    public String getAdress() {
        return this.adress;
    }

    public City getCity() {
        return this.city;
    }

}
