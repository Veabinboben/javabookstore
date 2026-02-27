package com.example.bookstoreserver.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.repositories.AuthorsRepository;
import com.example.bookstoreserver.data.repositories.BooksRepository;

@Service
public class AuthorsService {
    @Autowired
    private AuthorsRepository authorsRepository;
    
    public Author getAuthorById(long id){
        return authorsRepository.findById(id);
    }

}
