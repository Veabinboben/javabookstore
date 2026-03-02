package com.example.bookstoreserver.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.City;
import com.example.bookstoreserver.data.repositories.CitiesRepository;

@Service
public class CitiesService {

    private final CitiesRepository citiesRepository;

    public CitiesService(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    public List<City> getCities(String name) {
        return citiesRepository.findByNameContainingIgnoringCase(name);
    }

    public void saveCity(City city) {
        citiesRepository.save(city);
    }

}
