package com.example.bookstoreserver.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity(name = "cities")
public class City {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    @NotNull
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    public String getName() {
        return this.name;
    }
}
