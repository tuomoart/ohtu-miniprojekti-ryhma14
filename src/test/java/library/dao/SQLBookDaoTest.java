package library.dao;

import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emma
 */
public class SQLBookDaoTest {

    private SQLBookDao sqlBookDao;

    @Before
    public void setUp() throws SQLException {
        sqlBookDao = new SQLBookDao("jdbc:sqlite:testdatabase.db");
    }

    @After
    public void tearDown() {
        sqlBookDao.clearDatabase();
    }

    @Test
    public void canAddBook() throws SQLException {
        boolean created = sqlBookDao.addBookToDatabase("How to write tests", "Some Person", "2020", "100", "012-0123456789", false);
        assertTrue(created);
    }
    
    @Test
    public void canAddReadBook() throws SQLException {
        sqlBookDao.addBookToDatabase("How to write tests", "Some Person", "2020", "100", "012-0123456789", true);
        assertEquals("1", sqlBookDao.getBooks().get(0).get(6));
    }

    @Test
    public void createdBookHasCorrectData() throws SQLException {
        sqlBookDao.addBookToDatabase("Test Book", "Author", "2020", "20", "123-0123456789", false);
        List<List<String>> books = sqlBookDao.getBooks();
        List<String> createdBook = books.get(books.size() - 1);
        assertEquals("Test Book", createdBook.get(0));
        assertEquals("Author", createdBook.get(1));
        assertEquals("2020", createdBook.get(2));
        assertEquals("20", createdBook.get(3));
        assertEquals("123-0123456789", createdBook.get(4));
    }

    @Test
    public void canClearDatabase() throws SQLException {
        sqlBookDao.addBookToDatabase("Book", "Author", "1970", "420", "", false);
        assertTrue(sqlBookDao.clearDatabase());
        assertTrue(sqlBookDao.getBooks().isEmpty());
    }

    @Test
    public void canRemoveBook() throws SQLException {
        sqlBookDao.addBookToDatabase("Book", "Author", "1970", "420", "", false);
        sqlBookDao.addBookToDatabase("Test Book", "Author", "2020", "20", "123-0123456789", false);
        sqlBookDao.addBookToDatabase("How to write tests", "Some Person", "2020", "100", "012-0123456789", false);
        List<List<String>> books = sqlBookDao.getBooks();
        List<String> createdBook = books.get(1);
        sqlBookDao.remove(2);
        books = sqlBookDao.getBooks();
        assertFalse(books.contains(createdBook));
    }

    @Test
    public void canToggelRead() throws SQLException {
        sqlBookDao.addBookToDatabase("Book", "Author", "1970", "420", "", false);
        List<String> toggled = sqlBookDao.getBooks().get(0);
        assertEquals("0", toggled.get(6));
        sqlBookDao.toggleRead(1);
        toggled = sqlBookDao.getBooks().get(0);
        assertEquals("1", toggled.get(6));
    }
    
    @Test
    public void addBookReturnsFalseInCaseOfSqlException() throws SQLException {
        Database db = new Database("jdbc:sqlite:testdatabase.db");
        db.getConnection().createStatement().execute("DROP TABLE Books");
        assertFalse(sqlBookDao.addBookToDatabase("Book", "Author", "1970", "420", "", false));
    }
    
    @Test
    public void clearDatabaseReturnsFalseInCaseOfSqlException() throws SQLException {
        Database db = new Database("jdbc:sqlite:testdatabase.db");
        db.getConnection().createStatement().execute("DROP TABLE Books");
        assertFalse(sqlBookDao.clearDatabase());
    }
    
    @Test
    public void toggleReadReturnsFalseInCaseOfSqlException() throws SQLException {
        Database db = new Database("jdbc:sqlite:testdatabase.db");
        db.getConnection().createStatement().execute("DROP TABLE Books");
        assertFalse(sqlBookDao.toggleRead(1));
    }
    
    @Test
    public void removeReturnsFalseInCaseOfSqlException() throws SQLException {
        Database db = new Database("jdbc:sqlite:testdatabase.db");
        db.getConnection().createStatement().execute("DROP TABLE Books");
        assertFalse(sqlBookDao.remove(1));
    }
    
    @Test
    public void getBooksReturnsAnAmptyListInCaseOfSqlException() throws SQLException {
        sqlBookDao.addBookToDatabase("", "", "", "", "", false);
        Database db = new Database("jdbc:sqlite:testdatabase.db");
        db.getConnection().createStatement().execute("DROP TABLE Books");
        assertTrue(sqlBookDao.getBooks().isEmpty());
    }
    
}
