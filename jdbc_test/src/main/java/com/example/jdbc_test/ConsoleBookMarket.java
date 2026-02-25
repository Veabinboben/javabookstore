package com.example.jdbc_test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleBookMarket{
    
    private DBService _service;
    private DBService.BooksDBService _booksService;
    private DBService.AuthorsDBService _authorsService;
    private DBService.WarehouseDBService _warehouseService;
    private DBService.ReviewsDBService _reviewsService;
    private DBService.PublishersDBService _publishersService;
    private DBService.GenresDBService _genresService;
    private DBService.CitiesDBService _cityService;
    private Scanner _scanner;


    public ConsoleBookMarket(DBService service) {
        _service = service;
        _scanner = new Scanner(System.in);
        _booksService = _service.new BooksDBService();
        _authorsService = _service.new AuthorsDBService();
        _warehouseService = _service.new WarehouseDBService();
        _reviewsService = _service.new ReviewsDBService();
        _publishersService = _service.new PublishersDBService();
        _genresService = _service.new GenresDBService();
        _cityService = _service.new CitiesDBService();
    }

    public void mainMenu() throws SQLException{
        while (true){
            System.out.println("""
                    Input menu item:
                    1. Add book.
                    2. Update book
                    3. Delete book
                    4. Stock warehouse.
                    5. Publish review
                    6. Show all books
                    7. Show all stocks
                    other. Exit
                    """);
            int menuItem = inputInt();
            switch (menuItem) {
                case 1:
                    insertBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    addStock();
                    break;
                case 5:
                    publishReview();
                    break;
                case 6:
                    getBooks();
                    break;
                case 7:
                    getStocks();
                    break;
                default:
                    return;
            }
        }
    }

    private Date inputDate(){
        while (true) {
            try{
                String dateString = _scanner.nextLine();
                Date date = Date.valueOf(dateString);
                return date;
            }
            catch (IllegalArgumentException e){
                System.out.println("Date format should be \"yyyy-[m]m-[d]d\", retry input");
            }
        }
    }

    private String inputString(){
        while (true) {
            try{
                String string = _scanner.nextLine();
                return string;
            }
            catch (Exception e){
                System.out.println("Invalid format, try again");
            }
        }
    }

    private int inputInt(){
        while (true) {
            try{
                int number = _scanner.nextInt();
                _scanner.nextLine();
                return number;
            }
            catch (Exception e){
                System.out.println("Invalid format, try again");
            }
        }
    }
    private double inputDouble(){
        while (true) {
             try{
                double number = _scanner.nextDouble();
                _scanner.nextLine();
                return number;
            }
            catch (Exception e){
                System.out.println("Invalid format, try again");
            }
        }
    }



    private boolean inputYN(){
        System.out.println("[y\\n]");
        while (true) {
            String answ = _scanner.nextLine();
            answ = answ.toLowerCase();
            if (answ.equals("y")) return true;
            else if (answ.equals("n")) return false;
            else System.out.println("Wrong answer format");
        }
    }

    private void insertBook() throws SQLException {
        try{            
                _service.startTransaction();

            System.out.println("Input title");
            String title = inputString();

            System.out.println("Input publishing date");
            Date date = inputDate();

            System.out.println("Input price");
            double price = inputDouble();

            long bookId = _booksService.insertBook(title,date,price);

            //Authors
            while (true){
                System.out.println("Add another author to the book?");
                boolean answ = inputYN();
                if (!answ) break;
                System.out.println("Add new author to the book?");
                boolean answ1 = inputYN();
                if (answ1){
                    long authorid = insertAuthor();
                    _authorsService.connectAuthorBook((int)bookId, (int)authorid);
                }
                else { 
                    System.out.println("Add existing author to the book?");
                    boolean answ2 = inputYN();
                    if (answ2)
                    {
                        System.out.println("Input author id");
                        int authorid = inputInt();
                        _authorsService.connectAuthorBook((int)bookId, authorid);
                    }
                }
            }

            //Genres
            while (true){
                System.out.println("Add another genre to the book?");
                boolean answ = inputYN();
                if (!answ) break;
                System.out.println("Add new genre to the book?");
                boolean answ1 = inputYN();
                if (answ1){
                    long genreId = insertGenre();
                    _genresService.connectGenreBook((int)bookId,(int)genreId);
                }
                else { 
                    System.out.println("Add existing author to the book?");
                    boolean answ2 = inputYN();
                    if (answ2)
                    {
                        System.out.println("Input genre id");
                        int genreId = inputInt();
                        _genresService.connectGenreBook((int)bookId,(int)genreId);
                    }
                }
            }

            //Publishers
            while (true){
                System.out.println("Add another publisher to the book?");
                boolean answ = inputYN();
                if (!answ) break;
                System.out.println("Add new publisher to the book?");
                boolean answ1 = inputYN();
                if (answ1){
                    long publisherId = insertPublisher();
                    _publishersService.connectPublisherBook((int)bookId,(int)publisherId);
                }
                else { 
                    System.out.println("Add existing publisher to the book?");
                    boolean answ2 = inputYN();
                    if (answ2)
                    {
                        System.out.println("Input publisher id");
                        int publisherId = inputInt();
                        _publishersService.connectPublisherBook((int)bookId,(int)publisherId);
                    }
                }
            }

                _service.commitTransaction();
        }
        catch (SQLException e){
            _service.abortTransaction();
            throw e;
        }
        
    }

    private void updateBook() throws SQLException {
        try{            
                _service.startTransaction();

            System.out.println("Input book id");
            int bookId = inputInt();
            String curBook = _booksService.getBookById(bookId);
            if (curBook == null) {
                System.out.println("This book does not exist");
                return;
            }
            System.out.println(curBook);

            System.out.println("Input new title");
            String title = inputString();

            System.out.println("Input new publishing date");
            Date date = inputDate();

            System.out.println("Input new price");
            double price = inputDouble();

            _booksService.updateBookById(title,date,price,bookId);

            //Authors
            System.out.println("Edit authors?");
            boolean authChangeAnsw = inputYN();
            while (authChangeAnsw){
                System.out.println("Add authors to the book?");
                boolean answ = inputYN();
                if (answ){
                    System.out.println("Add new author to the book?");
                    boolean answ1 = inputYN();
                    if (answ1){
                        long authorid = insertAuthor();
                        _authorsService.connectAuthorBook((int)bookId, (int)authorid);
                    }
                    else { 
                        System.out.println("Add existing author to the book?");
                        boolean answ2 = inputYN();
                        if (answ2)
                        {
                            System.out.println("Input author id");
                            int authorid = inputInt();
                            _authorsService.connectAuthorBook((int)bookId, authorid);
                        }
                    }
                }
                System.out.println("Remove authors from the book?");
                boolean answ1 = inputYN();
                while (answ1){
                    System.out.println("Input author id");
                    int authorid = inputInt();
                    _authorsService.disconnectAuthorBook((int)bookId, authorid);
                    System.out.println("Remove another author from the book?");
                    boolean answ2 = inputYN();
                    if(!answ2) break;
                }
                System.out.println("Continue editing authors?");
                boolean answbr = inputYN();
                if(!answbr) break;
            }

            //Genres
            System.out.println("Edit Genres?");
            boolean genreChangeAnsw = inputYN();
            while (genreChangeAnsw){
                System.out.println("Add genres to the book?");
                boolean answ = inputYN();
                if (answ){
                    System.out.println("Add new genre to the book?");
                    boolean answ1 = inputYN();
                    if (answ1){
                        long genreId = insertGenre();
                        _genresService.connectGenreBook(bookId, (int)genreId);
                    }
                    else { 
                        System.out.println("Add existing genre to the book?");
                        boolean answ2 = inputYN();
                        if (answ2)
                        {
                            System.out.println("Input genre id");
                            int genreId = inputInt();
                            _genresService.connectGenreBook(bookId, (int)genreId);
                        }
                    }
                }
                System.out.println("Remove genres from the book?");
                boolean answ1 = inputYN();
                while (answ1){
                    System.out.println("Input genre id");
                    int genreId = inputInt();
                    _genresService.disconnectGenreBook(bookId, (int)genreId);
                    System.out.println("Remove another genre from the book?");
                    boolean answ2 = inputYN();
                    if(!answ2) break;
                }
                System.out.println("Continue editing genres?");
                boolean answbr = inputYN();
                if(!answbr) break;
            }


            //Publishers
            System.out.println("Edit Publishers?");
            boolean publisherChangeAnsw = inputYN();
            while (publisherChangeAnsw){
                System.out.println("Add publishers to the book?");
                boolean answ = inputYN();
                if (answ){
                    System.out.println("Add new publisher to the book?");
                    boolean answ1 = inputYN();
                    if (answ1){
                        long publisherId = insertPublisher();
                        _publishersService.connectPublisherBook(bookId, (int)publisherId);
                    }
                    else { 
                        System.out.println("Add existing publisher to the book?");
                        boolean answ2 = inputYN();
                        if (answ2)
                        {
                            System.out.println("Input publisher id");
                            int publisherId = inputInt();
                            _publishersService.connectPublisherBook(bookId, (int)publisherId);
                        }
                    }
                }
                System.out.println("Remove publishers from the book?");
                boolean answ1 = inputYN();
                while (answ1){
                    System.out.println("Input publisher id");
                    int publisherId = inputInt();
                    _publishersService.disconnectPublisherBook(bookId, (int)publisherId);
                    System.out.println("Remove another publisher from the book?");
                    boolean answ2 = inputYN();
                    if(!answ2) break;
                }
                System.out.println("Continue editing publishers?");
                boolean answbr = inputYN();
                if(!answbr) break;
            }


                _service.commitTransaction();
        }
        catch (SQLException e){
            _service.abortTransaction();
            throw e;
        }
        
    }

    
    private void deleteBook() throws SQLException {
        try{            
                _service.startTransaction();

            System.out.println("Input book id");
            int bookId = inputInt();
            String curBook = _booksService.getBookById(bookId);
            if (curBook == null) {
                System.out.println("This book does not exist");
                return;
            }
            System.out.println(curBook);

            System.out.println("Confirm deletion");
            boolean answ = inputYN();
            if(answ) _booksService.deleteBookById(bookId);
                _service.commitTransaction();
        }
        catch (SQLException e){
            _service.abortTransaction();
            throw e;
        }
        
    }

    private long insertAuthor() throws SQLException{
        System.out.println("Input full name (name, middle name and surname, separated by spaces)");
        String[] fullName = inputString().split(" ");

        System.out.println("Input author's birthday");
        Date birthday = inputDate();

        System.out.println("Input author's bio");
        String bio = inputString();

        return _authorsService.insertAuthor(fullName[0], fullName[1], fullName[2], birthday, bio, null);
    }
    private long insertPublisher() throws SQLException{
        System.out.println("Input publisher name");
        String name = inputString();

        System.out.println("Input publisher description");
        String description = inputString();

        return _publishersService.insertPublisher(name, description);
    }
    private long insertGenre() throws SQLException{
        System.out.println("Input genre name");
        String name = inputString();
        return _genresService.insertGenre(name);
    }

    private void addStock() throws SQLException{
        try{
            _service.startTransaction();
        
        System.out.println("Input book id");
        int bookId = inputInt();

        //Warehouses
        while (true){
                System.out.println("Stock another warehouse?");
                boolean answ = inputYN();
                if (!answ) break;
                System.out.println("Stock new warehouse?");
                boolean answ1 = inputYN();
                if (answ1){
                    long warehouseId = insertWarehouse();
                    System.out.println("Input stock ammount");
                    int stock = inputInt();
                    _warehouseService.stockWarehouseWithBooks((int)bookId, (int)warehouseId, stock);
                }
                else { 
                    System.out.println("Change stock in existing warehouse?");
                    boolean answ2 = inputYN();
                    if (answ2)
                    {
                        System.out.println("Input warehouse id");
                        int warehouseId = inputInt();
                        System.out.println("Input stock ammount");
                        int stock = inputInt();
                        _warehouseService.stockWarehouseWithBooks((int)bookId, (int)warehouseId, stock);
                    }
                }
            }    


        }
        catch (SQLException e){
            _service.abortTransaction();
            throw e;
        }
          
    }

    private long insertCity() throws SQLException{
        System.out.println("Input city name");
        String name = inputString();

        return _cityService.insertCity(name);
    }

    private long insertWarehouse() throws SQLException{
        System.out.println("Input warehouse adress");
        String adress = inputString();

        int cityId;

        //City
        System.out.println("Warehouse in a new city (choose existing otherwise)");
        boolean answ1 = inputYN();
        if (answ1){
            cityId = (int)insertCity();
        }
        else { 
            System.out.println("Input city id");
            cityId = inputInt();
        } 

        return _warehouseService.insertWarehouse(adress,cityId);
    }

    private void getBooks() throws SQLException{
        String books = _booksService.getBooks();
        System.out.println(books);
    }
    private void getStocks() throws SQLException{
        String books = _warehouseService.getStocks();
        System.out.println(books);
    }

    private void publishReview() throws SQLException {
        try{
                _service.startTransaction();

            System.out.println("Input book id");
            int bookId = inputInt();
            String curBook = _booksService.getBookById(bookId);
            if (curBook == null) {
                System.out.println("This book does not exist");
                return;
            }
            System.out.println(curBook);

            System.out.println("Input author id");
            int authorId = inputInt();
            String curAuthor = _authorsService.getAuthorById(authorId);
            if (curAuthor == null) {
                System.out.println("This author does not exist");
                return;
            }
            System.out.println(curAuthor);

            System.out.println("Input review text");
            String review = inputString();
            System.out.println("Input review score");
            int score;

            while (true) {
                score = inputInt();
                if (score < 0 || score > 10){
                    System.out.println("Score cant be less than 0 or higher than 10");
                }
                else {
                    break;
                }
            }

            _reviewsService.insertReview(review, score, bookId, authorId);

                _service.commitTransaction();
        }
        catch (SQLException e){
            _service.abortTransaction();
            throw e;
        }
        
    }

}
