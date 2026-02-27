package com.example.bookstoreserver.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.repositories.BooksRepository;

//TODO maybe add interface
@Service
public class BooksService {
    @Autowired
    private BooksRepository booksRepository;

    //TODO check visibility 
    public Page<Book> allBooksPaginated(int pageNum, int pagesize, String titleFilter){
        Pageable pageable = PageRequest.of(pageNum, pagesize);
        return booksRepository.findByTitleContainingIgnoreCase(titleFilter,pageable);
    }


}
