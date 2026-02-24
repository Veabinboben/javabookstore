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

    //TODO check what scanner can throw

    public ConsoleBookMarket(DBService service) {
        _service = service;
        _booksService = _service.new BooksDBService();
        _authorsService = _service.new AuthorsDBService();
        _warehouseService = _service.new WarehouseDBService();
        _reviewsService = _service.new ReviewsDBService();
        _publishersService = _service.new PublishersDBService();
        _genresService = _service.new GenresDBService();
    }

    private Date inputDate(Scanner scanner){
        while (true) {
            try{
                String dateString = scanner.nextLine();
                Date date = Date.valueOf(dateString);
                return date;
            }
            catch (IllegalArgumentException e){
                System.out.println("Date format should be \"yyyy-[m]m-[d]d\", retry input");
            }
        }
    }

    private boolean inputYN(Scanner scanner){
        while (true) {
            String answ = scanner.nextLine();
            answ = answ.toLowerCase();
            if (answ.equals('y')) return true;
            else if (answ.equals('n')) return false;
            else System.out.println("Wrong answer format");
        }
    }

    public void test()throws SQLException{
        //_booksService.DeleteBookById(3);
        //System.out.println(_booksService.GetBookById(4));
        //_booksService.UpdateBookById("name", Date.valueOf("1988-11-11"), 0.0, 3);
    }

    public void insertBook() throws SQLException {
        try{
            Scanner scanner = new Scanner(System.in);
            
                _service.startTransaction();

            System.out.println("Input title");
            String title = scanner.nextLine();

            System.out.println("Input publishing date");
            Date date = inputDate(scanner);

            System.out.println("Input price");
            double price = scanner.nextDouble();

            _booksService.insertBook(title,date,price);

            System.out.println("Add existing author to the book?");
            boolean answ = inputYN(scanner);
            if (answ){
                
            }


                _service.commitTransaction();
        }
        catch (SQLException e){
            _service.abortTransaction();
            throw e;
        }
        
    }
    public void insertAuthor(){throw new UnsupportedOperationException();}
    public void insertWarehouse(){throw new UnsupportedOperationException();}
    public void insertGenre(){throw new UnsupportedOperationException();}
    public void changeStock(){throw new UnsupportedOperationException();}

}
