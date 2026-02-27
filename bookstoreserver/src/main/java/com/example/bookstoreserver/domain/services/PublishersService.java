package com.example.bookstoreserver.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.entities.Publisher;
import com.example.bookstoreserver.data.repositories.PublishersRepository;

@Service
public class PublishersService {

    @Autowired
    private PublishersRepository publishersRepository;
    
    public Publisher getPublisherById(long id){
        return publishersRepository.findById(id);
    }

}
