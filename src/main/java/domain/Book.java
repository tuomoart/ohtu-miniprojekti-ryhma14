/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author hiira
 */
public class Book extends Tip {
    private String author;
    private int year;
    private int pages;
    private String ISBN;
    
    public Book(String title, String author, int year, int pages, String ISBN) {
        super(title);
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    
    @Override
    public String toString() {
        String result = "Otsikko:" + super.getTitle() + "\n";
        result += "Kirjailija: " + author + "\n";
        result += "Julkaisuvuosi: " + year + "\n";
        result += "Sivuja: " + pages + "\n";
        result += "ISBN: " + pages;
        
        return result;
    }
}
