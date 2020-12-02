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
    
    public SQLBookDao(Database database) throws SQLException {
        this.database = database;
    }
    
    @Override
    public boolean create(String title, String author, String year, String pages,
            String isbn) throws SQLException {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Books(title, author, year, pages, isbn) " +
                     "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, year);
            ps.setString(4, pages);
            ps.setString(5, isbn);      
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    @Override
    public List<List<String>> getBooks() throws SQLException {
        List<List<String>> books = new ArrayList<>();
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
            
            books.add(book);
        }
        connection.close();
        return books;
    }
    
    @Override
    public boolean clearDatabase() {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Books");
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
