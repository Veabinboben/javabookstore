package com.example.jdbc_test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class DBService {
    
    private Connection _conn;

    //TODO add transaction funcs

    //TODO Maybe cringe -_-
    //TODO unique pair field in m2m
    public DBService (Connection conn) throws SQLException{
        _conn = conn;
    }

    public void startTransaction() throws SQLException {
        _conn.setAutoCommit(false);
    }

    public void abortTransaction() throws SQLException {
        _conn.rollback();
    }

    public void commitTransaction() throws SQLException {
        _conn.commit();
    }
    
    class BooksDBService{

        public void insertBook(String title, Date publishDate, double price) throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO books (title, publish_date, price) VALUES (?, ?, ?)"
            );
            insert.setString(1, title);
            insert.setDate(2, publishDate);
            insert.setDouble(3, price);
            insert.executeUpdate();
        }

        public void deleteBookById(int id) throws SQLException{
            PreparedStatement delete = _conn.prepareStatement(
                "DELETE FROM books where id = ?"
            );
            delete.setInt(1, id);
            delete.executeUpdate();
        }

        public void updateBookById(String title, Date publishDate, double price, int id) throws SQLException{
            PreparedStatement update = _conn.prepareStatement(
                "UPDATE books SET title = ?, publish_date = ?, price = ? where id = ?"
            );
            update.setString(1, title);
            update.setDate(2, publishDate);
            update.setDouble(3, price);
            update.setInt(4, id);
            update.executeUpdate();
        }

        public String getBookById(int id) throws SQLException{
            PreparedStatement select = _conn.prepareStatement(
                "SELECT * FROM books where id = ?"
            );
            select.setInt(1, id);
            ResultSet result = select.executeQuery();
            String strRes = "";
            while (result.next()) {
                strRes += result.getString("title");
            }
            return strRes;
        }


    }

    class AuthorsDBService{
        public void insertAuthor(String name, String middleName, String surname, Date birthday, String bio, String photolink) throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO authors (name, middle_name, surname, birthday, bio, photo_link) VALUES (?, ?, ?, ?, ?, ?)"
            );
            insert.setString(1, name);
            insert.setString(2, middleName);
            insert.setString(3, surname);
            insert.setDate(4, birthday);
            insert.setString(5, bio);
            insert.setString(6, photolink);
            insert.executeUpdate();
        }

        public void updateAuthorById(String name, String middleName, String surname, Date birthday, String bio, String photolink, int id) throws SQLException{
            PreparedStatement update = _conn.prepareStatement(
                "UPDATE authors SET name = ? , middle_name = ?, surname = ? , birthday = ? , bio = ?, photo_link = ? where id = ?"
            );
            update.setString(1, name);
            update.setString(2, middleName);
            update.setString(3, surname);
            update.setDate(4, birthday);
            update.setString(5, bio);
            update.setString(6, photolink);
            update.setInt(7, id);
            update.executeUpdate();
        }

        public void connectAuthorBook (int bookId, int authorId)throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)"
            );
            insert.setInt(1, bookId);
            insert.setInt(2, authorId);
            insert.executeUpdate();
        }
    }

    class GenresDBService{
        public void insertGenre(String name) throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO genres (name) VALUES (?)"
            );
            insert.setString(1, name);
            insert.executeUpdate();
        }
                 
        public void updateGenreById(String name, int id) throws SQLException{
            PreparedStatement update = _conn.prepareStatement(
                "UPDATE genres SET name = ? where id = ?"
            );
            update.setString(1, name);
            update.setInt(2, id);
            update.executeUpdate();
        }

        public void connectGenreBook(int bookId, int genreId)throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO book_genres (book_id, genre_id) VALUES (?, ?)"
            );
            insert.setInt(1, bookId);
            insert.setInt(2, genreId);
            insert.executeUpdate();
        }
    }
    
    class WarehouseDBService{
        public void insertWarehouse(String adress, int cityId) throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO warehouses (adress, city_id) VALUES (?,  ?)"
            );
            insert.setString(1, adress);
            insert.setInt(2, cityId);
            insert.executeUpdate();
        }
                 
        public void updateWarehouseById(String adress, int cityId, int id) throws SQLException{
            PreparedStatement update = _conn.prepareStatement(
                "UPDATE warehouses SET adress = ?, city_id = ? where id = ?"
            );
            update.setString(1, adress);
            update.setInt(2, cityId);
            update.setInt(3, id);
            update.executeUpdate();
        }

        public void stockWarehouseWithBooks(int bookId, int warehoseId, int stock)throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO stocks (book_id, warehouse_id, stock) VALUES (?, ?, ?)"
            );
            insert.setInt(1, bookId);
            insert.setInt(2, warehoseId);
            insert.setInt(3, stock);
            insert.executeUpdate();
        }
    }

    class CitiesDBService{
        public void insertCity(String name) throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO cities (name) VALUES (?)"
            );
            insert.setString(1, name);
            insert.executeUpdate();
        }
                 
        public void updateCityById(String name, int id) throws SQLException{
            PreparedStatement update = _conn.prepareStatement(
                "UPDATE cities SET name = ? where id = ?"
            );
            update.setString(1, name);
            update.setInt(2, id);
            update.executeUpdate();
        }
    }

    class PublishersDBService{
        public void insertPublisher(String name, String description) throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO publishers (name, description) VALUES (?,  ?)"
            );
            insert.setString(1, name);
            insert.setString(2, description);
            insert.executeUpdate();
        }
                 
        public void updatePublisherById(String name, String description, int id) throws SQLException{
            PreparedStatement update = _conn.prepareStatement(
                "UPDATE publishers SET name = ?, description = ? where id = ?"
            );
            update.setString(1, name);
            update.setString(2, description);
            update.setInt(3, id);
            update.executeUpdate();
        }

        public void connectPublisherBook(int bookId, int publisherId)throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO book_publishers (book_id, publisher_id) VALUES (?, ?)"
            );
            insert.setInt(1, bookId);
            insert.setInt(2, publisherId);
            insert.executeUpdate();
        }
    }

    class ReviewsDBService{
         public void insertReview(String contents, int rating, int bookId, int authorId) throws SQLException{
            PreparedStatement insert = _conn.prepareStatement(
                "INSERT INTO reviews (contents, rating, book_id, author_id) VALUES (?, ?, ?, ?)"
            );
            insert.setString(1, contents);
            insert.setInt(2, rating);
            insert.setInt(3, bookId);
            insert.setInt(4, authorId);
            insert.executeUpdate();
        }
                 
        public void updateReviewById(String contents, int rating, int id) throws SQLException{
            PreparedStatement update = _conn.prepareStatement(
                "UPDATE reviews SET contents = ?, rating = ? where id = ?"
            );
            update.setString(1, contents);
            update.setInt(2, rating);
            update.setInt(3, id);
            update.executeUpdate();
        }

        public void deleteReviewById(int id) throws SQLException{
            PreparedStatement delete = _conn.prepareStatement(
                "DELETE FROM reviews where id = ?"
            );
            delete.setInt(1, id);
            delete.executeUpdate();
        }

    }
}
