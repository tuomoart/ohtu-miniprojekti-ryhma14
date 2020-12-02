/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.ArrayList;
import java.util.List;
import library.domain.Book;

/**
 *
 * @author tuomoart
 */
public class StubDao implements BookDao {
    private ArrayList<List<String>> database;

    public StubDao()  {
        format();
    }

    @Override
    public boolean create(String title, String author, String year,
        String pages, String isbn) {
        ArrayList<String> bookStrings = new ArrayList();
        bookStrings.add(title);
        bookStrings.add(author);
        bookStrings.add(year);
        bookStrings.add(pages);
        bookStrings.add(isbn);
        database.add(bookStrings);
        return true;
    }

    @Override
    public List<List<String>> getBooks() {
        return database;
    }

    public void format() {
        this.database = new ArrayList();
    }

    @Override
    public boolean clearDatabase() {
        format();
        return true;
    }
}
