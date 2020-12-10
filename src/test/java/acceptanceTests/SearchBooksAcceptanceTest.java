/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceptanceTests;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import library.dao.SQLBookDao;
import library.domain.Book;
import library.domain.Logic;
import library.ui.gui.Gui;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TableViewMatchers;

/**
 *
 * @author tuomoart
 */
public class SearchBooksAcceptanceTest extends ApplicationTest {
    private Gui sovellus;
    private ArrayList<Book> someBooks;
    
    @Override
    public void start(Stage stage) throws Exception {
        sovellus = new Gui(new Logic(new SQLBookDao("jdbc:sqlite:testdatabase.db")));
        
        Application app = Application.class.cast(sovellus);
        app.start(stage);
    }
    
    @BeforeClass
    public static void setupSpec() throws Exception {
        //Set to false if you want to see the gui tests get excecuted
        Boolean headless = true;
        if (headless) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }
    
    @Before
    public void setUp() {
        sovellus.getLogic().getDao().clearDatabase();
        addSomeBooks();
        moveToSearchView();
    }
    
    @After
    public void tearDown() {
        sovellus.getLogic().getDao().clearDatabase();
    }
    
    @Test
    public void allBooksCanBeViewed() {
        someBooks.forEach((book) -> {
            checkThatBookIsFound(book);
        });        
    }
    
    @Test
    public void bookCanBeSearchedByName() {
        enterValueInGui("#searchBox", someBooks.get(1).getTitle());
        
        checkThatBookIsFound(someBooks.get(1));
    }
    
    @Test
    public void bookCanBeSearchedByAuthor() {
        enterValueInGui("#searchBox", someBooks.get(2).getAuthor());
        
        checkThatBookIsFound(someBooks.get(2));
    }
    
    @Test
    public void bookCanBeSearchedByYear() {
        enterValueInGui("#searchBox", someBooks.get(3).getYear());
        
        checkThatBookIsFound(someBooks.get(3));
        checkThatBookIsFound(someBooks.get(4));
    }
    
    @Test
    public void bookCanBeSearchedByISBN() {
        enterValueInGui("#searchBox", someBooks.get(0).getISBN());
        
        checkThatBookIsFound(someBooks.get(0));
    }
    
    @Test
    public void whenSearchedBySomethingThenOthersAreNotVisible() {
        enterValueInGui("#searchBox", someBooks.get(1).getTitle());
        
        checkThatBookIsNotFound(someBooks.get(0));
    }
    
    private void enterValueInGui(String id, String value) {
        clickOn(id).write(value);
    }
    
    private void checkThatBookIsFound(Book book) {
        assertThat((TableView<Book>)lookup("#list").query(), TableViewMatchers.hasTableCell(book.getTitle()));
        assertThat((TableView<Book>)lookup("#list").query(), TableViewMatchers.hasTableCell(book.getAuthor()));
        assertThat((TableView<Book>)lookup("#list").query(), TableViewMatchers.hasTableCell(book.getYear()));
        assertThat((TableView<Book>)lookup("#list").query(), TableViewMatchers.hasTableCell(book.getPages()));
        assertThat((TableView<Book>)lookup("#list").query(), TableViewMatchers.hasTableCell(book.getISBN()));
    }
    
    private void checkThatBookIsNotFound(Book book) {
        assertThat((TableView<Book>)lookup("#list").query(), not(TableViewMatchers.hasTableCell(book.getTitle())));
    }
    
    private void moveToSearchView() {
        clickOn("#search");
    }
    
    private void addSomeBooks() {
        Logic l = sovellus.getLogic();
        
        someBooks = new ArrayList();
        
        someBooks.add( new Book("Book with all data", "b-1", "A. AuthorI", "2000", "100", "978-951-98548-9-2", false));
        someBooks.add( new Book("Find By Name", "b-2", "", "", "", "", false));
        someBooks.add( new Book("Some Name", "b-3", "Find by author", "", "", "", false));
        someBooks.add( new Book("Some Other Name", "b-4", "", "2001", "", "", false));
        someBooks.add( new Book("Some Third Name", "b-5", "", "2001", "", "", false));
        
        someBooks.forEach((b) -> {
            l.addBook(b.getTitle(), b.getAuthor(), b.getYear(), b.getPages(), b.getISBN());
        });
    }
    
}
