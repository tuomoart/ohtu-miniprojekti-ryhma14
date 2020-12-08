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
    private BookDao dao;
    
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
                messages.add("ISBN tunnus täytyy olla muotoa 'xxxx-xxx-xx-x', jossa x:t ovat numeroita");
            }
            
            if (!messages.isEmpty()) {
                return messages;
            }
            
            if (dao.addBookToDatabase(title, author, year, pages, ISBN)) {
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
    
    public boolean textIsValidAuthorName(String text) {
        if (text.isEmpty()) return true;
        return text.matches("^[ .A-Öa-ö]+$");
    }
    
    public boolean textIsValidInteger(String text) {
        if (text.isEmpty()) return true;
        return text.matches("[0-9]+");
    }
    
    
    /* metodi testaa seuraavat asiat: teksti koostuu vähintään neljästä 
    väliviivan erottamasta osasta, kaikki merkit viivoja lukuunottamatta
    ovat numeroita, viivoja ei ole kahta peräkkäin, numeroita on yhteensä
    väh. kymmenen.
    */
    public boolean textIsValidISBN(String text) {
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
        List<Book> books = new ArrayList<>();
        
        try {
            
            List<List<String>> data = dao.getBooks();
            
            int i = 1;
            for (List<String> b : data) {
                String title = b.get(0);
                String author = b.get(1);
                String year = b.get(2);
                String pages = b.get(3);
                String isbn = b.get(4);
                String id = "b-" + Integer.toString(i);
                
                Book book = new Book(title, id, author, year, pages, isbn);
                books.add(book);
                
                i++;
            }
            
            return books;
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return books;
        }  
    }

    public BookDao getDao() {
        return dao;
    }
    
    public boolean clearDatabase() {
        return dao.clearDatabase();
    }
    
    public List<Book> filteredList(String string) {
        String haku = string.toLowerCase();
        List<Book> books = getBooks();
        List<Book> result = new ArrayList<>();
        
        books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(haku)
                        || b.getAuthor().toLowerCase().contains(haku) 
                        || b.getISBN().toLowerCase().contains(haku) 
                        || b.getYear().toLowerCase().contains(haku) 
                        || b.getPages().toLowerCase().contains(haku))
                .forEach(b -> result.add(b));
        
        return result;
    }
}
