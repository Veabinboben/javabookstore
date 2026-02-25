package com.example.jdbc_test.ui;

import java.sql.SQLException;

import com.example.jdbc_test.services.AuthorsDBService;
import com.example.jdbc_test.services.BooksDBService;
import com.example.jdbc_test.services.ReviewsDBService;
import com.example.jdbc_test.services.TransactionManager;
import com.example.jdbc_test.utils.InputUtils;

public class ReviewsUI {

    private BooksDBService booksDBService;
    private AuthorsDBService authorsDBService;
    private ReviewsDBService reviewsDBService;
    private TransactionManager transactionManager;


    public ReviewsUI(BooksDBService booksDBService, AuthorsDBService authorsDBService, 
        ReviewsDBService reviewsDBService,
        TransactionManager transactionManager){
        this.booksDBService = booksDBService;    
        this.authorsDBService = authorsDBService;    
        this.reviewsDBService = reviewsDBService;    
        this.transactionManager = transactionManager;
    }

    public void publishReview() throws SQLException {
        try{
            transactionManager.startTransaction();

            System.out.println("Input book id");
            int bookId = InputUtils.inputInt();
            String curBook = booksDBService.getBookById(bookId);
            if (curBook == null) {
                System.out.println("This book does not exist");
                return;
            }
            System.out.println(curBook);

            System.out.println("Input author id");
            int authorId = InputUtils.inputInt();
            String curAuthor = authorsDBService.getAuthorById(authorId);
            if (curAuthor == null) {
                System.out.println("This author does not exist");
                return;
            }
            System.out.println(curAuthor);

            System.out.println("Input review text");
            String review = InputUtils.inputString();
            System.out.println("Input review score");
            int score;

            while (true) {
                score = InputUtils.inputInt();
                if (score < 0 || score > 10){
                    System.out.println("Score cant be less than 0 or higher than 10");
                }
                else {
                    break;
                }
            }

            reviewsDBService.insertReview(review, score, bookId, authorId);

            transactionManager.commitTransaction();
        }
        catch (SQLException e){
            transactionManager.abortTransaction();
            throw e;
        }
        
    }

    public void getReviews() throws SQLException{
        String books = reviewsDBService.getReviews();
        System.out.println(books);
    }
}
