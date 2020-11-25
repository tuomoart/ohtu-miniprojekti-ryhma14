/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

import library.dao.*;
import java.util.*;
/**
 *
 * @author hiira
 */
public class Logic {
    private Database database;
    private SQLBookDao dao;
    
    public Logic() {
        try {
            database = new Database("jdbc:sqlite:database.db");
            dao = new SQLBookDao(database);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String addBook(String title, String author, String year, String pages,
            String ISBN) {
        try {
            Book book = new Book(title, author, year, pages, ISBN);
            dao.create(book);
            return "Kirja lisätty";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public List<Book> getBooks() {
        try {
            List<Book> books = dao.getBooks();
            return books;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            List<Book> books = new ArrayList<>();
            return books;
        }
    }
}
