/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author hiski
 */
public interface BookDao {
    
    boolean addBookToDatabase(String title, String author, String year,
            String pages, String isbn, Boolean read) throws SQLException;
    
    List<List<String>> getBooks() throws SQLException;
    
    boolean clearDatabase();
    
    boolean remove(int id);
    
    boolean toggleRead(int id);
    
}
