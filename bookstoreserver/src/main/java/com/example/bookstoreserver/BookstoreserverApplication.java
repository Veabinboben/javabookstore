package com.example.bookstoreserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO 
// CRUD on books (create done)
// batch get for publishers genres and authors?
// probably search for authors
// search for books by filters and paginated
// simple create api for other models
// add logger

@SpringBootApplication
public class BookstoreserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreserverApplication.class, args);
	}

}
