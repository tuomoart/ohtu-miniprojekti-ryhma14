/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.sql.*;
import java.util.*;

/**
 *
 * @author hiski
 */
public class SQLBookDao implements BookDao {

    final private Database database;

    public SQLBookDao(String address) throws SQLException {
        this.database = new Database(address);
    }

    @Override
    public boolean addBookToDatabase(String title, String author, String year, String pages,
            String isbn, Boolean read) throws SQLException {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Books(title, author, year, pages, isbn, read) "
                    + "VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, year);
            ps.setString(4, pages);
            ps.setString(5, isbn);
            
            int r;
            if (read) {
                r = 1;
            } else {
                r = 0;
            }
            
            ps.setInt(6, r);
            
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<List<String>> getBooks() throws SQLException {
        List<List<String>> books = new ArrayList<>();
        
        
        try {
            Connection connection = database.getConnection();
            
            
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Books");
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                List<String> book = new ArrayList<>();
                

                
                book.add(results.getString("title"));
                book.add(results.getString("author"));
                book.add(results.getString("year"));
                book.add(results.getString("pages"));
                book.add(results.getString("isbn"));
                book.add(results.getString("id"));
                book.add(Integer.toString(results.getInt("read")));
                
                System.out.println(books);
                
                books.add(book);
            }
            connection.close();
            return books;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return books;
        }
    }

    @Override
    public boolean clearDatabase() {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Books");
            ps.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
