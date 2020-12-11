/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceptanceTests;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Node;
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
public class DeleteBooksAcceptanceTest extends ApplicationTest {
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
        clickOn("#searchsplit");
        clickOn("#searchsplitbook");
    }
    
    @After
    public void tearDown() {
        sovellus.getLogic().getDao().clearDatabase();
    }
    
    @Test
    public void whenDeleteIsClickedTheSelectedBookIsdeleted() {
        deleteRow(0);
        
        assertThat((TableView<Book>)lookup("#list").query(), not(TableViewMatchers.hasTableCell("Book with all data")));
    }
    
    @Test
    public void whenDeleteIsClickedAndNoBookIsSelectedThenNoBookIsDeleted() {
        clickOn("#deleteButton");
        
        assertThat((TableView<Book>)lookup("#list").query(), TableViewMatchers.hasTableCell("Book with all data"));
    }
    
    private void deleteRow(int row) {
        clickOn(lookup("#authorCol").nth(1 + row).queryAs(Node.class));
        clickOn("#deleteButton");
    }
    
    private void moveToSearchView() {
        clickOn("#search");
    }
    
    private void addSomeBooks() {
        Logic l = sovellus.getLogic();
        
        someBooks = new ArrayList();
        
        someBooks.add( new Book("Book with all data", "b-1", "A. AuthorI", "2000", "100", "978-951-98548-9-2", false));
        
        someBooks.forEach((b) -> {
            l.addBook(b.getTitle(), b.getAuthor(), b.getYear(), b.getPages(), b.getISBN());
        });
    }
    
}
