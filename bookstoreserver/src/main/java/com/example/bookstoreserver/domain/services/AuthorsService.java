package com.example.bookstoreserver.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.repositories.AuthorsRepository;

@Service
public class AuthorsService {

    private final AuthorsRepository authorsRepository;

    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

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
