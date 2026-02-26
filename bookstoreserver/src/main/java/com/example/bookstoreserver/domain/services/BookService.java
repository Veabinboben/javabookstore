package com.example.bookstoreserver.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.repositories.BookRepository;

//TODO maybe add interface
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> books(){
        return bookRepository.findAll();
    }
}
