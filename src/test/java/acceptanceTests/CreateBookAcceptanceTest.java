/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acceptanceTests;

import javafx.application.Application;
import javafx.stage.Stage;
import library.dao.SQLBookDao;
import library.domain.Logic;
import library.ui.gui.Gui;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxAssert.verifyThatIter;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 *
 * @author tuomoart
 */
public class CreateBookAcceptanceTest extends ApplicationTest {
    private Gui sovellus;
    
    @Override
    public void start(Stage stage) throws Exception {
        sovellus = new Gui(new Logic(new SQLBookDao("jdbc:sqlite:testdatabase.db")));
        
        Application app = Application.class.cast(sovellus);
        app.start(stage);
    }
    
    @BeforeClass
    public static void setupSpec() throws Exception {
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
        selectBook();
    }
    
    @After
    public void tearDown() {
        sovellus.getLogic().getDao().clearDatabase();
    }
    
    @Test
    public void canAddBookWithValidName() {
        checkThatBookGetsAdded("kirjannimi", "", "", "", "");
    }
    
    @Test
    public void canAddBookWithAllValidData() {
        checkThatBookGetsAdded("kirjannimi", "kirjailija", "1999", "100", "978-952-264-438-1");
    }
    
    @Test
    public void bookWithInvalidTitleIsNotAdded() {
        String error = "Otsikko ei saa olla tyhjä";
        checkThatBookIsNotAdded(error, "", "", "", "", "");
    }
    
    @Test
    public void bookWithInvalidWriterIsNotAdded() {
        String error = "Kirjailijan nimi ei saa sisältää numeroita";
        checkThatBookIsNotAdded(error, "kirjannimi", "k1rj41l1j4", "", "", "");
    }
    
    @Test
    public void bookWithInvalidYearIsNotAdded() {
        String error = "Vääränmallinen vuosiluku";
        checkThatBookIsNotAdded(error, "MAOL", "", "2k20", "", "");
    }
    
    @Test
    public void bookWithInvalidPagesIsNotAdded() {
        String error = "Vääränmallinen sivumäärä";
        checkThatBookIsNotAdded(error, "MAOL", "", "", "sata", "");
    }
    
    @Test
    public void bookWithInvalidISBNIsNotAdded() {
        String error = "ISBN tunnus täytyy olla muotoa 'xxxx-xxx-xx-x', jossa x:t ovat numeroita";
        checkThatBookIsNotAdded(error, "MAOL", "", "", "", "1234567-89-0");
    }
    
    private void checkThatBookGetsAdded(String name, String writer, String year, String pages, String isbn) {
        enterValuesForBook(name, writer, year, pages, isbn);
        clickAddInGui();
        AddingRespondsWith("\nKirja '" + name + "' lisätty");
    }
    
    private void checkThatBookIsNotAdded(String errMsg, String name, String writer, String year, String pages, String isbn) {
        enterValuesForBook(name, writer, year, pages, isbn);
        clickAddInGui();
        AddingRespondsWith("\n" + errMsg);
    }
    
    private void enterValuesForBook(String name, String writer, String year, String pages, String isbn) {
        enterValueInGui("#Nimike", name);
        enterValueInGui("#Kirjailija", writer);
        enterValueInGui("#Julkaisuvuosi", year);
        enterValueInGui("#Sivumäärä", pages);
        enterValueInGui("#ISBN-tunniste", isbn);
    }
    
    private void enterValueInGui(String id, String value) {
        clickOn(id).write(value);
    }
    
    private void clickAddInGui() {
        clickOn("#add");
    }

    private void selectBook() {
        clickOn("#split");
        clickOn("#Kirja");
    }
    
    private void AddingRespondsWith(String response) {
        verifyThat("#messages", hasText(response));
    }
    
}
