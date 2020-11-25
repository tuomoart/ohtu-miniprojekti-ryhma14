package library.dao;


import java.sql.SQLException;
import java.util.List;
import library.domain.Book;
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
        sqlBookDao = new SQLBookDao(new Database("jdbc:sqlite:testdatabase.db"));
    }
    
    @Test
    public void canAddBook() throws SQLException {
        Book book = new Book("Test Book", "Author", "2020", "20", "123-0123456789");
        assertTrue(sqlBookDao.create(book));
    }

    @Test
    public void createdBookHasCorrectData() throws SQLException {
        List<Book> books = sqlBookDao.getBooks();
        Book book = books.get(books.size()-1);
        assertEquals("Test Book", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals("2020", book.getYear());
        assertEquals("20", book.getPages());
        assertEquals("123-0123456789", book.getISBN());
    }

}
