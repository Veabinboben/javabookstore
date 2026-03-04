package com.example.bookstoreserver.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Publisher;
import com.example.bookstoreserver.data.repositories.PublishersRepository;

@Service
public class PublishersService {

    private final PublishersRepository publishersRepository;

    public PublishersService(PublishersRepository publishersRepository) {
        this.publishersRepository = publishersRepository;
    }

    public Publisher getPublisherById(long id) {
        return publishersRepository.findById(id).orElseThrow();
    }

    public List<Publisher> getPublishers(String name) {
        return publishersRepository.findByNameContainingIgnoringCase(name);
    }

    public void savePublisher(Publisher publisher) {
        publishersRepository.save(publisher);
    }

}
