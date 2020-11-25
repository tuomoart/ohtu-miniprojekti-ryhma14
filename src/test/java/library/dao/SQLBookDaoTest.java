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
        boolean created = sqlBookDao.create(new Book("How to write tests", "Some Person", "2020", "100", "012-0123456789"));
        assertTrue(created);
    }

    @Test
    public void createdBookHasCorrectData() throws SQLException {
        Book book = new Book("Test Book", "Author", "2020", "20", "123-0123456789");
        sqlBookDao.create(book);
        List<Book> books = sqlBookDao.getBooks();
        Book createdBook = books.get(books.size()-1);
        assertEquals("Test Book", createdBook.getTitle());
        assertEquals("Author", createdBook.getAuthor());
        assertEquals("2020", createdBook.getYear());
        assertEquals("20", createdBook.getPages());
        assertEquals("123-0123456789", createdBook.getISBN());
    }

}
