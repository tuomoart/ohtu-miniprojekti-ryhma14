/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.sql.SQLException;
import library.domain.Book;
import java.util.List;

/**
 *
 * @author hiski
 */
public interface BookDao {
    
    boolean create(Book book) throws SQLException;
    
    List<List<String>> getBooks() throws SQLException;
    
    boolean clearDatabase();
    
}
