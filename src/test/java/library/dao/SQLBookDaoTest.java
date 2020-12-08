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
        boolean created = sqlBookDao.addBookToDatabase("How to write tests", "Some Person", "2020", "100", "012-0123456789");
        assertTrue(created);
    }

    @Test
    public void createdBookHasCorrectData() throws SQLException {
        sqlBookDao.addBookToDatabase("Test Book", "Author", "2020", "20", "123-0123456789");
        List<List<String>> books = sqlBookDao.getBooks();
        List<String> createdBook = books.get(books.size()-1);
        assertEquals("Test Book", createdBook.get(0));
        assertEquals("Author", createdBook.get(1));
        assertEquals("2020", createdBook.get(2));
        assertEquals("20", createdBook.get(3));
        assertEquals("123-0123456789", createdBook.get(4));
    }

    @Test
    public void canClearDatabase() throws SQLException {
        sqlBookDao.addBookToDatabase("Book", "Author", "1970", "420", "");
        assertTrue(sqlBookDao.clearDatabase());
        assertTrue(sqlBookDao.getBooks().isEmpty());
    }
}
