/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

import java.util.Objects;

/**
 *
 * @author hiira
 */
public class Book extends Tip {
    private String author;
    private String year;
    private String pages;
    private String ISBN;
    
    public Book(String title, String author, String year, String pages, String ISBN) {
        super(title);
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public String getPages() {
        return pages;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    
    @Override
    public String toString() {
        String result = "Otsikko: " + super.getTitle() + "\n";
        result += "Kirjailija: " + author + "\n";
        result += "Julkaisuvuosi: " + year + "\n";
        result += "Sivuja: " + pages + "\n";
        result += "ISBN: " + ISBN;
        
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.year, other.year)) {
            return false;
        }
        if (!Objects.equals(this.pages, other.pages)) {
            return false;
        }
        if (!Objects.equals(this.ISBN, other.ISBN)) {
            return false;
        }
        return true;
    }
    
    
}
