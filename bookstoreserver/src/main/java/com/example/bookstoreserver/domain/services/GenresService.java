package com.example.bookstoreserver.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Genre;
import com.example.bookstoreserver.data.repositories.GenresRepository;

@Service
public class GenresService {
    
    @Autowired
    private GenresRepository genresRepository;

    public Genre getGenreById(long id){
        return genresRepository.findById(id);
    }

}
