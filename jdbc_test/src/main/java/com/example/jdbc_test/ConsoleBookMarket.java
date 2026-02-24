package com.example.jdbc_test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
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

    //TODO check what scanner can throw

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
                    3. Stock warehouse.
                    4. Show all books
                    other. Exit
                    """);
            int menuItem = _scanner.nextInt();
            switch (menuItem) {
                case 1:
                    insertBook();
                    break;
                case 3:
                    addStock();
                    break;
                case 4:
                    getBooks();
                    break;
                default:
                    return;
            }
        }
    }

    private Date inputDate(){
        while (true) {
            try{
                String dateString = _scanner.next();
                Date date = Date.valueOf(dateString);
                return date;
            }
            catch (IllegalArgumentException e){
                System.out.println("Date format should be \"yyyy-[m]m-[d]d\", retry input");
            }
        }
    }

    private boolean inputYN(){
        while (true) {
            String answ = _scanner.next();
            answ = answ.toLowerCase();
            if (answ.equals("y")) return true;
            else if (answ.equals("n")) return false;
            else System.out.println("Wrong answer format");
        }
    }

    //HACK
    public void test()throws SQLException{
        //_booksService.DeleteBookById(3);
        //System.out.println(_booksService.GetBookById(4));
        //_booksService.UpdateBookById("name", Date.valueOf("1988-11-11"), 0.0, 3);
    }

    private void insertBook() throws SQLException {
        try{            
                _service.startTransaction();

            System.out.println("Input title");
            String title = _scanner.next();

            System.out.println("Input publishing date");
            Date date = inputDate();

            System.out.println("Input price");
            double price = _scanner.nextDouble();

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
                        int authorid = _scanner.nextInt();
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
                        int genreId = _scanner.nextInt();
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
                        int publisherId = _scanner.nextInt();
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

    private long insertAuthor() throws SQLException{
        System.out.println("Input full name (name, middle name and surname, separated by spaces)");
        String[] fullName = _scanner.next().split(" ");

        System.out.println("Input author's birthday");
        Date birthday = inputDate();

        System.out.println("Input author's bio");
        String bio = _scanner.next();

        //TODO photo

        return _authorsService.insertAuthor(fullName[0], fullName[1], fullName[2], birthday, bio, null);
    }
    private long insertPublisher() throws SQLException{
        System.out.println("Input publisher name");
        String name = _scanner.next();

        System.out.println("Input publisher description");
        String description = _scanner.next();

        return _publishersService.insertPublisher(name, description);
    }
    private long insertGenre() throws SQLException{
        System.out.println("Input genre name");
        String name = _scanner.next();
        return _genresService.insertGenre(name);
    }

    private void addStock() throws SQLException{
        try{
            _service.startTransaction();
        
        System.out.println("Input book id");
        int bookId = _scanner.nextInt();

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
                    int stock = _scanner.nextInt();
                    _warehouseService.stockWarehouseWithBooks((int)bookId, (int)warehouseId, stock);
                }
                else { 
                    System.out.println("Change stock in existing warehouse?");
                    boolean answ2 = inputYN();
                    if (answ2)
                    {
                        System.out.println("Input warehouse id");
                        int warehouseId = _scanner.nextInt();
                        System.out.println("Input stock ammount");
                        int stock = _scanner.nextInt();
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
        String name = _scanner.next();

        return _cityService.insertCity(name);
    }

    private long insertWarehouse() throws SQLException{
        System.out.println("Input warehouse adress");
        String adress = _scanner.next();

        int cityId;

        //City
        System.out.println("Warehouse in a new city (chose existing otherwise)");
        boolean answ1 = inputYN();
        if (answ1){
            cityId = (int)insertCity();
        }
        else { 
            System.out.println("Input city id");
            cityId = _scanner.nextInt();
        } 

        return _warehouseService.insertWarehouse(adress,cityId);
    }

    private void getBooks() throws SQLException{
        String books = _booksService.getBooks();
        System.out.println(books);
    }

}
