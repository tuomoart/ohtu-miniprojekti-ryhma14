/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.dao;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tuomoart
 */
public class StubDao implements BookDao {
    private ArrayList<List<String>> database;
    private int id;

    public StubDao()  {
        format();
    }

    @Override
    public boolean addBookToDatabase(String title, String author, String year,
        String pages, String isbn, Boolean read) {
        ArrayList<String> bookStrings = new ArrayList();
        bookStrings.add(title);
        bookStrings.add(author);
        bookStrings.add(year);
        bookStrings.add(pages);
        bookStrings.add(isbn);
        bookStrings.add("" + String.valueOf(this.id));
        this.id++;
        bookStrings.add(read.toString());
        database.add(bookStrings);
        return true;
    }

    @Override
    public List<List<String>> getBooks() {
        return database;
    }
    
    public void format() {
        this.database = new ArrayList();
        this.id = 0;
    }

    @Override
    public boolean clearDatabase() {
        format();
        return true;
    }
    
    @Override
    public boolean remove(int id) {
        String idString = String.valueOf(id);
        for (List<String> l : database) {
            if (l.get(5).equals(idString)) {
                database.remove(l);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean toggleRead(int id) {
        String idString = String.valueOf(id);
        for (List<String> l : database) {
            if (l.get(5).equals(idString)) {
                if (l.get(6).equals("false")) {
                    l.set(6, "true");
                } else {
                    l.set(6, "false");
                }
            }
        }
        return true;
    }
}
