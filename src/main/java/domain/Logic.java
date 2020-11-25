/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.*;
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
            database = new Database();
            dao = new SQLBookDao(database);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void addBook(String title, String author, int year, int pages,
            String ISBN) {
        try {
            Book book = new Book(title, author, year, pages, ISBN);
            dao.create(book);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void printBooks() {
        try {
            List<Book> books = dao.getBooks();
            
            for (Book book : books) {
                System.out.println(book.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
