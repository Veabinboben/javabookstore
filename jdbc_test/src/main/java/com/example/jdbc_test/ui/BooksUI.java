package com.example.jdbc_test.ui;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.example.jdbc_test.services.AuthorsDBService;
import com.example.jdbc_test.services.BooksDBService;
import com.example.jdbc_test.services.GenresDBService;
import com.example.jdbc_test.services.PublishersDBService;
import com.example.jdbc_test.services.TransactionManager;
import com.example.jdbc_test.utils.InputUtils;
import com.example.jdbc_test.utils.QuerryResult;

public class BooksUI {
    
    private BooksDBService booksDBService;
    private AuthorsDBService authorsDBService;
    private GenresDBService genresDBService;
    private PublishersDBService publishersDBService;
    private TransactionManager transactionManager;


    public BooksUI(BooksDBService booksDBService, AuthorsDBService authorsDBService, 
        GenresDBService genresDBService, PublishersDBService publishersDBService, TransactionManager transactionManager){
        this.booksDBService = booksDBService;    
        this.authorsDBService = authorsDBService;    
        this.genresDBService = genresDBService;
        this.publishersDBService = publishersDBService;
        this.transactionManager = transactionManager;
    }

    private long insertAuthor() throws SQLException{
        System.out.println("Input full name (name, middle name and surname, separated by spaces)");
        String[] fullName = InputUtils.inputString().split(" ");

        System.out.println("Input author's birthday");
        Date birthday = InputUtils.inputDate();

        System.out.println("Input author's bio");
        String bio = InputUtils.inputString();

        return authorsDBService.insertAuthor(fullName[0], fullName[1], fullName[2], birthday, bio, null);
    }

    public long chooseAuthor() throws SQLException{
        System.out.println("Show authors list?");
        boolean answ = InputUtils.inputYN();
        if (answ) System.out.println(authorsDBService.getAuthors());

        System.out.println("Input author's name");
        String name = InputUtils.inputString();

        List<QuerryResult> authors = authorsDBService.getAuthorsWithName(name);

        if (authors.size() > 1){
            System.out.println("Choose one");
            int count = authors.size();
            for (int i = 0 ; i < count; i++){
                System.out.println(String.format("%d : %s", i+1, authors.get(i).view()));
            }
            int rowNum;
            while (true) {
                rowNum = InputUtils.inputInt(); 
                if (rowNum < 1 || rowNum > count) System.out.println("Wrong index, please retry");
                else break;              
            }
            return authors.get(rowNum-1).id();
        }
        else if (authors.size() == 1){
            System.out.println(authors.get(0).view());
            return authors.get(0).id();
        }
        else{
            System.out.println("No author with this name");
            return -1;
        }
    }


    private long insertPublisher() throws SQLException{
        System.out.println("Input publisher name");
        String name = InputUtils.inputString();

        System.out.println("Input publisher description");
        String description = InputUtils.inputString();

        return publishersDBService.insertPublisher(name, description);
    }

    private long choosePublisher() throws SQLException{
        System.out.println("Show publishers list?");
        boolean answ = InputUtils.inputYN();
        if (answ) System.out.println(publishersDBService.getPublishers());

        System.out.println("Input publishers's name");
        String name = InputUtils.inputString();

        List<QuerryResult> publishres = publishersDBService.getPublishersWithName(name);

        if (publishres.size() > 1){
            System.out.println("Choose one");
            int count = publishres.size();
            for (int i = 0 ; i < count; i++){
                System.out.println(String.format("%d : %s", i+1, publishres.get(i).view()));
            }
            int rowNum;
            while (true) {
                rowNum = InputUtils.inputInt(); 
                if (rowNum < 1 || rowNum > count) System.out.println("Wrong index, please retry");
                else break;              
            }
            return publishres.get(rowNum-1).id();
        }
        else if (publishres.size() == 1){
            System.out.println(publishres.get(0).view());
            return publishres.get(0).id();
        }
        else{
            System.out.println("No publisher with this name");
            return -1;
        }
    }

    private long insertGenre() throws SQLException{
        System.out.println("Input genre name");
        String name = InputUtils.inputString();
        return genresDBService.insertGenre(name);
    }

    private long chooseGenre() throws SQLException{
        System.out.println("Show genres list?");
        boolean answ = InputUtils.inputYN();
        if (answ) System.out.println(genresDBService.getGenres());

        System.out.println("Input genres's name");
        String name = InputUtils.inputString();

        List<QuerryResult> genres = genresDBService.getGenresWithName(name);

        if (genres.size() > 1){
            System.out.println("Choose one");
            int count = genres.size();
            for (int i = 0 ; i < count; i++){
                System.out.println(String.format("%d : %s", i+1, genres.get(i).view()));
            }
            int rowNum;
            while (true) {
                rowNum = InputUtils.inputInt(); 
                if (rowNum < 1 || rowNum > count) System.out.println("Wrong index, please retry");
                else break;              
            }
            return genres.get(rowNum-1).id();
        }
        else if (genres.size() == 1){
            System.out.println(genres.get(0).view());
            return genres.get(0).id();
        }
        else{
            System.out.println("No genre with this name");
            return -1;
        }
    }

    public long chooseBook() throws SQLException{
        System.out.println("Show books list?");
        boolean answ = InputUtils.inputYN();
        if (answ) System.out.println(booksDBService.getBooks());

        System.out.println("Input book's name");
        String name = InputUtils.inputString();

        List<QuerryResult> books = booksDBService.getBooksWithName(name);

        if (books.size() > 1){
            System.out.println("Choose one");
            int count = books.size();
            for (int i = 0 ; i < count; i++){
                System.out.println(String.format("%d : %s", i+1, books.get(i).view()));
            }
            int rowNum;
            while (true) {
                rowNum = InputUtils.inputInt(); 
                if (rowNum < 1 || rowNum > count) System.out.println("Wrong index, please retry");
                else break;              
            }
            return books.get(rowNum-1).id();
        }
        else if (books.size() == 1){
            System.out.println(books.get(0).view());
            return books.get(0).id();
        }
        else{
            System.out.println("No book with this name");
            return -1;
        }
    }

    public void insertBook() throws SQLException {
        try{            
            transactionManager.startTransaction();

            System.out.println("Input title");
            String title = InputUtils.inputString();

            System.out.println("Input publishing date");
            Date date = InputUtils.inputDate();

            System.out.println("Input price");
            double price = InputUtils.inputDouble();

            long bookId = booksDBService.insertBook(title,date,price);

            //Authors
            while (true){
                System.out.println("Add another author to the book?");
                boolean answ = InputUtils.inputYN();
                if (!answ) break;
                System.out.println("Add new author to the book?");
                boolean answ1 = InputUtils.inputYN();
                if (answ1){
                    long authorid = insertAuthor();
                    authorsDBService.connectAuthorBook((int)bookId, (int)authorid);
                }
                else { 
                    System.out.println("Add existing author to the book?");
                    boolean answ2 = InputUtils.inputYN();
                    if (answ2)
                    {
                        int authorid = (int)chooseAuthor();
                        if (authorid != -1) authorsDBService.connectAuthorBook((int)bookId, authorid);
                    }
                }
            }

            //Genres
            while (true){
                System.out.println("Add another genre to the book?");
                boolean answ = InputUtils.inputYN();
                if (!answ) break;
                System.out.println("Add new genre to the book?");
                boolean answ1 = InputUtils.inputYN();
                if (answ1){
                    long genreId = insertGenre();
                    genresDBService.connectGenreBook((int)bookId,(int)genreId);
                }
                else { 
                    System.out.println("Add existing author to the book?");
                    boolean answ2 = InputUtils.inputYN();
                    if (answ2)
                    {
                        int genreId = (int)chooseGenre();
                        if (genreId != -1) genresDBService.connectGenreBook((int)bookId,(int)genreId);
                    }
                }
            }

            //Publishers
            while (true){
                System.out.println("Add another publisher to the book?");
                boolean answ = InputUtils.inputYN();
                if (!answ) break;
                System.out.println("Add new publisher to the book?");
                boolean answ1 = InputUtils.inputYN();
                if (answ1){
                    long publisherId = insertPublisher();
                    publishersDBService.connectPublisherBook((int)bookId,(int)publisherId);
                }
                else { 
                    System.out.println("Add existing publisher to the book?");
                    boolean answ2 = InputUtils.inputYN();
                    if (answ2)
                    {
                        int publisherId = (int)choosePublisher();
                        if (publisherId != -1) publishersDBService.connectPublisherBook((int)bookId,(int)publisherId);
                    }
                }
            }

            transactionManager.commitTransaction();
        }
        catch (SQLException e){
            transactionManager.abortTransaction();
            throw e;
        }
        
    }

    public void updateBook() throws SQLException {
        try{            
            transactionManager.startTransaction();

            int bookId = (int)chooseBook();
            
            if (bookId == -1) {
                System.out.println("This book does not exist");
                return;
            }
            String curBook = booksDBService.getBookById(bookId);

            System.out.println(curBook);

            System.out.println("Input new title");
            String title = InputUtils.inputString();

            System.out.println("Input new publishing date");
            Date date = InputUtils.inputDate();

            System.out.println("Input new price");
            double price = InputUtils.inputDouble();

            booksDBService.updateBookById(title,date,price,bookId);

            //Authors
            System.out.println("Edit authors?");
            boolean authChangeAnsw = InputUtils.inputYN();
            while (authChangeAnsw){
                System.out.println("Add authors to the book?");
                boolean answ = InputUtils.inputYN();
                if (answ){
                    System.out.println("Add new author to the book?");
                    boolean answ1 = InputUtils.inputYN();
                    if (answ1){
                        long authorid = insertAuthor();
                        authorsDBService.connectAuthorBook((int)bookId, (int)authorid);
                    }
                    else { 
                        System.out.println("Add existing author to the book?");
                        boolean answ2 = InputUtils.inputYN();
                        if (answ2)
                        {
                            int authorid = (int)chooseAuthor();
                            if (authorid != -1) authorsDBService.connectAuthorBook((int)bookId, authorid);
                        }
                    }
                }
                System.out.println("Remove authors from the book?");
                boolean answ1 = InputUtils.inputYN();
                while (answ1){
                    int authorid = (int) chooseAuthor();
                    if (authorid != -1) authorsDBService.disconnectAuthorBook((int)bookId, authorid);
                    System.out.println("Remove another author from the book?");
                    boolean answ2 = InputUtils.inputYN();
                    if(!answ2) break;
                }
                System.out.println("Continue editing authors?");
                boolean answbr = InputUtils.inputYN();
                if(!answbr) break;
            }

            //Genres
            System.out.println("Edit Genres?");
            boolean genreChangeAnsw = InputUtils.inputYN();
            while (genreChangeAnsw){
                System.out.println("Add genres to the book?");
                boolean answ = InputUtils.inputYN();
                if (answ){
                    System.out.println("Add new genre to the book?");
                    boolean answ1 = InputUtils.inputYN();
                    if (answ1){
                        long genreId = insertGenre();
                        genresDBService.connectGenreBook(bookId, (int)genreId);
                    }
                    else { 
                        System.out.println("Add existing genre to the book?");
                        boolean answ2 = InputUtils.inputYN();
                        if (answ2)
                        {
                            int genreId = (int)chooseGenre();
                            if (genreId != -1) genresDBService.connectGenreBook(bookId, (int)genreId);
                        }
                    }
                }
                System.out.println("Remove genres from the book?");
                boolean answ1 = InputUtils.inputYN();
                while (answ1){
                    int genreId = (int) chooseGenre();
                    if (genreId != -1) genresDBService.disconnectGenreBook(bookId, (int)genreId);
                    System.out.println("Remove another genre from the book?");
                    boolean answ2 = InputUtils.inputYN();
                    if(!answ2) break;
                }
                System.out.println("Continue editing genres?");
                boolean answbr = InputUtils.inputYN();
                if(!answbr) break;
            }


            //Publishers
            System.out.println("Edit Publishers?");
            boolean publisherChangeAnsw = InputUtils.inputYN();
            while (publisherChangeAnsw){
                System.out.println("Add publishers to the book?");
                boolean answ = InputUtils.inputYN();
                if (answ){
                    System.out.println("Add new publisher to the book?");
                    boolean answ1 = InputUtils.inputYN();
                    if (answ1){
                        long publisherId = insertPublisher();
                        publishersDBService.connectPublisherBook(bookId, (int)publisherId);
                    }
                    else { 
                        System.out.println("Add existing publisher to the book?");
                        boolean answ2 = InputUtils.inputYN();
                        if (answ2)
                        {
                            int publisherId =  (int) choosePublisher();
                            if (publisherId != -1)publishersDBService.connectPublisherBook(bookId, (int)publisherId);
                        }
                    }
                }
                System.out.println("Remove publishers from the book?");
                boolean answ1 = InputUtils.inputYN();
                while (answ1){
                    int publisherId = (int)choosePublisher();
                     if (publisherId != -1) publishersDBService.disconnectPublisherBook(bookId, (int)publisherId);
                    System.out.println("Remove another publisher from the book?");
                    boolean answ2 = InputUtils.inputYN();
                    if(!answ2) break;
                }
                System.out.println("Continue editing publishers?");
                boolean answbr = InputUtils.inputYN();
                if(!answbr) break;
            }


            transactionManager.commitTransaction();
        }
        catch (SQLException e){
            transactionManager.abortTransaction();
            throw e;
        }
        
    }

    
    public void deleteBook() throws SQLException {
        try{            
            transactionManager.startTransaction();

            int bookId = (int)chooseBook();
            if (bookId == -1) {
                System.out.println("This book does not exist");
                return;
            }
           
            String curBook = booksDBService.getBookById(bookId);
        
            System.out.println(curBook);

            System.out.println("Confirm deletion");
            boolean answ = InputUtils.inputYN();
            if(answ) booksDBService.deleteBookById(bookId);
            transactionManager.commitTransaction();
        }
        catch (SQLException e){
            transactionManager.abortTransaction();
            throw e;
        }
        
    }


    public void getBooks() throws SQLException{
        String books = booksDBService.getBooks();
        System.out.println(books);
    }
}
