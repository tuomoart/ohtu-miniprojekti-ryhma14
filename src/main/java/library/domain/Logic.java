/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

import library.dao.*;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    
    public List<String> addBook(String title, String author, String year, String pages,
            String ISBN) {
        
        List<String> messages = new ArrayList<>();
        
        try {
            
            
            if (title.isBlank()) {
                messages.add("Otsikko ei saa olla tyhjä");
            }
            
            if (!textIsValidAuthorName(author)) {
                messages.add("Kirjailijan nimi ei saa sisältää numeroita");
            }
            
            if (!textIsValidInteger(year)) {
                messages.add("Vääränmallinen vuosiluku");
            }
            
            if (!textIsValidInteger(pages)) {
                messages.add("Vääränmallinen sivumäärä");
            }
            
            if (!textIsValidISBN(ISBN)) {
                messages.add("Vääränmallinen ISBN");
            }
            
            if (!messages.isEmpty()) {
                return messages;
            }
            
            Book book = new Book(title, author, year, pages, ISBN);
            if (dao.create(book)) {
                messages.add("Kirja '" + title + "' lisätty");
            } else {
                messages.add("Ongelma kirjan lisäämisessä");
            }
            
            return messages;
            
        } catch (Exception e) {
            messages.add(e.getMessage());
            return messages;
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
    
    public boolean clearDatabase() {
        return dao.clearDatabase();
    }
    
    public List<Book> filteredList(String string) {
        List<Book> books = getBooks();
        
        books.stream()
                .filter(b -> b.getAuthor().contains(string) 
                        || b.getISBN().contains(string) 
                        || b.getYear().contains(string) 
                        || b.getPages().contains(string));
        
        return books;
    }
}
