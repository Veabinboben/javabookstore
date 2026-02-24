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

    public void insertBook() throws SQLException {
        try{            
                _service.startTransaction();

            System.out.println("Input title");
            String title = _scanner.nextLine();

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
        String[] fullName = _scanner.nextLine().split(" ");

        System.out.println("Input author's birthday");
        Date birthday = inputDate();

        System.out.println("Input author's bio");
        String bio = _scanner.nextLine();

        //TODO photo

        return _authorsService.insertAuthor(fullName[0], fullName[1], fullName[2], birthday, bio, null);
    }
    private long insertPublisher() throws SQLException{
        System.out.println("Input publisher name");
        String name = _scanner.nextLine();

        System.out.println("Input publisher description");
        String description = _scanner.nextLine();

        return _publishersService.insertPublisher(name, description);
    }
    private long insertGenre() throws SQLException{
        System.out.println("Input genre name");
        String name = _scanner.nextLine();
        return _genresService.insertGenre(name);
    }


    public void changeStock() throws SQLException{
        
    }

}
