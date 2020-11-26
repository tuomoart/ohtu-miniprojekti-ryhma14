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
    private BookDao dao;
    
    public Logic() {
        try {
            database = new Database("jdbc:sqlite:database.db");
            dao = new SQLBookDao(database);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Logic(BookDao dao) {
        this.dao = dao;
    }
    
    public String addBook(String title, String author, String year, String pages,
            String ISBN) {
        try {
            if (!textIsValidAuthorName(author)) {
                return "Kirjailijan nimi ei saa sisältää numeroita";
            }
            
            if (!textIsValidInteger(year)) {
                return "Vääränmallinen vuosiluku";
            }
            
            if (!textIsValidInteger(pages)) {
                return "Vääränmallinen sivumäärä";
            }
            
            if (!textIsValidISBN(ISBN)) {
                return "Vääränmallinen ISBN";
            }
            
            Book book = new Book(title, author, year, pages, ISBN);
            dao.create(book);
            return "Kirja lisätty";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    private boolean textIsValidAuthorName(String text) {
        if (text.isEmpty()) return true;
        return text.matches("^[ .A-Öa-ö]+$");
    }
    
    private boolean textIsValidInteger(String text) {
        if (text.isEmpty()) return true;
        return text.matches("[0-9]+");
    }
    
    
    /* metodi testaa seuraavat asiat: teksti koostuu vähintään neljästä 
    väliviivan erottamasta osasta, kaikki merkit viivoja lukuunottamatta
    ovat numeroita, viivoja ei ole kahta peräkkäin, numeroita on yhteensä
    väh. kymmenen.
    */
    private boolean textIsValidISBN(String text) {
        if (text.isEmpty()) return true;
        
        String[] split = text.split("-");
        
        if (split.length < 4) return false;
        
        int numOfDigits = 0;
        for (String s : split) {
            if (!textIsValidInteger(s)) return false;
            
            numOfDigits += s.length();
        }
        
        if (numOfDigits < 10) return false;
        
        return true;
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
