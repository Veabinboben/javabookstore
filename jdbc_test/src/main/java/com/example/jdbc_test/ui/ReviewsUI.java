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
    private BooksUI booksUI;


    public ReviewsUI(BooksDBService booksDBService, AuthorsDBService authorsDBService, 
        ReviewsDBService reviewsDBService,
        TransactionManager transactionManager,
        BooksUI booksUI){
        this.booksDBService = booksDBService;    
        this.authorsDBService = authorsDBService;    
        this.reviewsDBService = reviewsDBService;    
        this.transactionManager = transactionManager;
        this.booksUI = booksUI;
    }

    public void publishReview() throws SQLException {
        try{
            transactionManager.startTransaction();

        
            int bookId = (int)booksUI.chooseBook();
            if (bookId == -1) {
                System.out.println("This book does not exist");
                return;
            }
            String curBook = booksDBService.getBookById(bookId);
            System.out.println(curBook);

            int authorId = (int) booksUI.chooseAuthor();
            if (authorId == -1) {
                System.out.println("This author does not exist");
                return;
            }
            String curAuthor = authorsDBService.getAuthorById(authorId);
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
