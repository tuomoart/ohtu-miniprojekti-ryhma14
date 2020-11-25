/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import domain.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public boolean create(Book book) throws SQLException {
        try {
            Connection connection = database.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Books(title, author, year, pages, isbn) " +
                     "VALUES (?, ?, ?, ?, ?");
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getYear());
            ps.setString(4, book.getPages());
            ps.setString(5, book.getISBN());
            
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    @Override
    public List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        Connection connection = database.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Books");
        ResultSet results = ps.executeQuery();
        
        while (results.next()) {
            String title = results.getString("title");
            String author = results.getString("author");
            String year = results.getString("year");
            String pages = results.getString("pages");
            String isbn = results.getString("isbn");
            
            Book book = new Book(title, author, year, pages, isbn);
            books.add(book);
        }
        
        return books;

    }
    
}
