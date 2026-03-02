package com.example.bookstoreserver.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.repositories.AuthorsRepository;

@Service
public class AuthorsService {
    @Autowired
    private AuthorsRepository authorsRepository;

    public Author getAuthorById(long id) {
        return authorsRepository.findById(id).orElseThrow();
    }

    public List<Author> getAuthors(String nameFilter) {
        return authorsRepository.findByName(nameFilter);
    }

    public void saveAuthor(Author author) {
        authorsRepository.save(author);
    }

}
