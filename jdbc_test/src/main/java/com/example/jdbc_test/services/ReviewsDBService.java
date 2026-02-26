package com.example.jdbc_test.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReviewsDBService{

    private Connection connection;

    public ReviewsDBService(Connection connection){
        this.connection = connection;
    }

    public long insertReview(String contents, int rating, int bookId, int authorId) throws SQLException{
        PreparedStatement insert = connection.prepareStatement(
            "INSERT INTO reviews (contents, rating, book_id, author_id) VALUES (?, ?, ?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS
        );
        insert.setString(1, contents);
        insert.setInt(2, rating);
        insert.setInt(3, bookId);
        insert.setInt(4, authorId);
        insert.executeUpdate();
        ResultSet result = insert.getGeneratedKeys();
        long ind = -1;
        if (result.next()){
            ind = result.getLong(1);
        }
        result.close();
        return ind;
    }
                
    public void updateReviewById(String contents, int rating, int id) throws SQLException{
        PreparedStatement update = connection.prepareStatement(
            "UPDATE reviews SET contents = ?, rating = ? where id = ?"
        );
        update.setString(1, contents);
        update.setInt(2, rating);
        update.setInt(3, id);
        update.executeUpdate();
        update.close();
    }

    public void deleteReviewById(int id) throws SQLException{
        PreparedStatement delete = connection.prepareStatement(
            "DELETE FROM reviews where id = ?"
        );
        delete.setInt(1, id);
        delete.executeUpdate();
        delete.close();
    }

        public String getReviews() throws SQLException{
        PreparedStatement select = connection.prepareStatement(
            """
            SELECT
                r.id as id,
                r.contents AS cont,
                r.rating AS rating,
                b.title AS book_name,
                CONCAT(a.name, ' ', a.middle_name) AS authors
            FROM
                reviews r
            LEFT JOIN
                books b ON r.book_id = b.id
            LEFT JOIN
                authors a ON a.id = r.author_id;

            """
        );
        ResultSet result = select.executeQuery();
        String strRes = "";
        while (result.next()) {
            strRes +=  String.format("Contents : %s\t Rating: %s\t | Book title: %s\t Authors: %s  \n ", 
                result.getString("cont"),
                result.getString("rating"),
                result.getString("book_name"),
                result.getString("authors")
            );
        }
        result.close();
        return strRes;
    }
}