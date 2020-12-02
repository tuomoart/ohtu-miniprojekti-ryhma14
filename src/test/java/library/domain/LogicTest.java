/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

import java.util.ArrayList;
import java.util.List;
import library.dao.BookDao;
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
    public class StubDao implements BookDao {
        private ArrayList<List<String>> database;
        
        public StubDao() {
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
        someBooks.add(new Book("Book with all data", "A. AuthorI", "2000", "100", "978-951-98548-9-2"));
        someBooks.add(new Book("Book with minimal data", "", "", "", ""));
        someBooks.add(new Book("Book with incorrect author", "Author1","","",""));
        someBooks.add(new Book("Book with incorrect year", "Author I", "123S", "", ""));
        someBooks.add(new Book("Book with incorrect pages", "AuthorII", "", "I0", ""));
        someBooks.add(new Book("Book with letters in ISBN", "AuthorII", "", "", "ABC-951-98548-9-2"));
        someBooks.add(new Book("Book with too few hyphens in ISBN", "AuthorII", "", "", "978951-98548-92"));
        someBooks.add(new Book("Book with too few numbers in ISBN", "AuthorII", "", "", "9-951-985-92"));
        someBooks.add(new Book("","","","",""));
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getBooksTest() throws Throwable {
        addSomeBooksDirectly();
        List<List<String>> books = new ArrayList();
        for (Book book : logic.getBooks()) {
            books.add(book.toStringList(book));
        }
        
        assertEquals(dao.getBooks(), books);
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
        
        checkThatDoesNotGetAdded(book, "Vääränmallinen ISBN");
    }
    
    @Test
    public void dontAddBookWithTooFewHyphensInISBN() throws Throwable {
        Book book = someBooks.get(6);
        
        checkThatDoesNotGetAdded(book, "Vääränmallinen ISBN");
    }
    
    @Test
    public void dontAddBookWithTooFewNumbers() throws Throwable {
        Book book = someBooks.get(7);
        
        checkThatDoesNotGetAdded(book, "Vääränmallinen ISBN");
    }
    
    public void checkThatDoesNotGetAdded(Book book, String errMsg) throws Throwable {
        assertEquals(errMsg, tryToAdd(book));
        assertEquals(0, dao.getBooks().size());
    }
    
    public void checkThatGetsAdded(Book book) throws Throwable{
        assertEquals("Kirja '" + book.getTitle() + "' lisätty", tryToAdd(book));
        assertEquals(1, dao.getBooks().size());
        assertEquals(book.toStringList(book), dao.getBooks().get(0));
    }
    
    public String tryToAdd(Book book) {
        return logic.addBook(book.getTitle(), book.getAuthor(), book.getYear(),
                book.getPages(), book.getISBN()).get(0);
    }
    
    public void addSomeBooksDirectly() throws Throwable {
        for (Book book: someBooks) {
            dao.create(book.getTitle(), book.getAuthor(), book.getYear(), book.getPages(), book.getISBN());
        }
    }

    @Test
    public void noBookDataAfterClearDatabase() throws Throwable {
        tryToAdd(someBooks.get(0));
        logic.clearDatabase();
        assertTrue(dao.getBooks().isEmpty());
    }
}
