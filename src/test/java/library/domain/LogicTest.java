/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

import java.util.ArrayList;
import java.util.List;
import library.dao.BookDao;
import library.dao.StubDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author tuomoart
 */
public class LogicTest {   
    
    private BookDao dao;
    private Logic logic;
    private ArrayList<Book> someBooks;
    
    public LogicTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Throwable {
        dao = new StubDao();
        logic = new Logic(dao);
        
        someBooks = new ArrayList();
        someBooks.add(new Book("Book with all data", "b-10", "A. AuthorI", "2000", "100", "978-951-98548-9-2", false));
        someBooks.add(new Book("Book with minimal data", "b-2", "", "", "", "", false));
        someBooks.add(new Book("Book with incorrect author", "b-3", "Author1","","","", false));
        someBooks.add(new Book("Book with incorrect year", "b-4", "Author I", "123S", "", "", false));
        someBooks.add(new Book("Book with incorrect pages", "b-5", "AuthorII", "", "I0", "", false));
        someBooks.add(new Book("Book with letters in ISBN", "b-6", "AuthorII", "", "", "ABC-951-98548-9-2", false));
        someBooks.add(new Book("Book with too few hyphens in ISBN", "b-7", "AuthorII", "", "", "978951-98548-92", false));
        someBooks.add(new Book("Book with too few numbers in ISBN", "b-8", "AuthorII", "", "", "9-951-985-92", false));
        someBooks.add(new Book("","","","","","", false));
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getBooksTest() throws Throwable {
        addSomeBooksDirectly();
        List<List<String>> books = new ArrayList();
        for (Book book : logic.getBooks()) {
            books.add(book.toStringList());
        }
        
        List<List<String>> correct = dao.getBooks();
        
        for (List<String> listBook : correct) {
            listBook.set(5, "b-" + listBook.get(5));
        }
        
        assertEquals(correct, books);
    }
    
    @Test
    public void getBooksWhenThereAreNone() throws Throwable {
        assertEquals(new ArrayList(), logic.getBooks());
    }
    
    @Test
    public void addBookWithAllDataCorrect() throws Throwable {
        Book book = someBooks.get(0);
        
        checkThatGetsAdded(book);
    }
    
    @Test
    public void addBookWithMinimalData() throws Throwable {
        Book book = someBooks.get(1);
        
        checkThatGetsAdded(book);
    }
    
    @Test
    public void dontAddBookWithNoTitle() throws Throwable {
        Book book = someBooks.get(8);
        
        checkThatDoesNotGetAdded(book, "Otsikko ei saa olla tyhjä");
    }
    
    @Test
    public void dontAddBookWithIncorrectAuthor() throws Throwable {
        Book book = someBooks.get(2);
        
        checkThatDoesNotGetAdded(book, "Kirjailijan nimi ei saa sisältää numeroita");
    }
    
    @Test
    public void dontAddBookWithIncorrectYear() throws Throwable {
        Book book = someBooks.get(3);
        
        checkThatDoesNotGetAdded(book, "Vääränmallinen vuosiluku");
    }
    
    @Test
    public void dontAddBookWithIncorrectPages() throws Throwable {
        Book book = someBooks.get(4);
        
        checkThatDoesNotGetAdded(book, "Vääränmallinen sivumäärä");
    }
    
    @Test
    public void dontAddBookWithLettersInISBN() throws Throwable {
        Book book = someBooks.get(5);
        
        checkThatDoesNotGetAdded(book, "ISBN tunnus täytyy olla muotoa 'xxxx-xxx-xx-x', jossa x:t ovat numeroita");
    }
    
    @Test
    public void dontAddBookWithTooFewHyphensInISBN() throws Throwable {
        Book book = someBooks.get(6);
        
        checkThatDoesNotGetAdded(book, "ISBN tunnus täytyy olla muotoa 'xxxx-xxx-xx-x', jossa x:t ovat numeroita");
    }
    
    @Test
    public void dontAddBookWithTooFewNumbers() throws Throwable {
        Book book = someBooks.get(7);
        
        checkThatDoesNotGetAdded(book, "ISBN tunnus täytyy olla muotoa 'xxxx-xxx-xx-x', jossa x:t ovat numeroita");
    }
    
    public void checkThatDoesNotGetAdded(Book book, String errMsg) throws Throwable {
        assertEquals(errMsg, tryToAdd(book));
        assertEquals(0, dao.getBooks().size());
    }
    
    public void checkThatGetsAdded(Book book) throws Throwable{
        assertEquals("Kirja '" + book.getTitle() + "' lisätty", tryToAdd(book));
        assertEquals(1, dao.getBooks().size());
        assertEquals(book.toStringList(), dao.getBooks().get(0));
    }
    
    public String tryToAdd(Book book) {
        return logic.addBook(book.getTitle(), book.getAuthor(), book.getYear(),
                book.getPages(), book.getISBN()).get(0);
    }
    
    public void addSomeBooksDirectly() throws Throwable {
        for (Book book: someBooks) {
            dao.addBookToDatabase(book.getTitle(), book.getAuthor(), book.getYear(), book.getPages(), book.getISBN(), book.isRead());
        }
    }

    @Test
    public void noBookDataAfterClearDatabase() throws Throwable {
        tryToAdd(someBooks.get(0));
        logic.clearDatabase();
        assertTrue(dao.getBooks().isEmpty());
    }
}
