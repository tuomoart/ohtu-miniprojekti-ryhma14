/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.dao.SQLBookDao;
import library.dao.StubDao;
import library.domain.Logic;
import library.ui.gui.CreationView;
import library.ui.gui.Gui;
import library.ui.gui.SearchView;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 *
 * @author tuomoart
 */
public class GuiTest extends ApplicationTest {
    private Gui sovellus;
    
    @Override
    public void start(Stage stage) throws Exception {
        sovellus = new Gui(new Logic(new SQLBookDao("jdbc:sqlite:testdatabase.db")));
        
        Application app = Application.class.cast(sovellus);
        app.start(stage);
    }
    
    @BeforeClass
    public static void setupSpec() throws Exception {
        Boolean headless = false;
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
    }
    
    @Test
    public void canAddBookWithValidName() {
        checkThatBookGetsAdded("kirjannimi", "", "", "", "");
    }
    
    @Test
    public void canAddBookWithAllValidData() {
        checkThatBookGetsAdded("kirjannimi", "kirjailija", "1999", "100", "978-952-264-438-1");
    }
    
    private void checkThatBookGetsAdded(String name, String writer, String year, String pages, String isbn) {
        enterValuesForBook(name, writer, year, pages, isbn);
        clickAddInGui();
        AddingRespondsWith("\nKirja '" + name + "' lisätty");
    }
    
    private void enterValuesForBook(String name, String writer, String year, String pages, String isbn) {
        enterValueInGui("#Nimike", name);
        enterValueInGui("#Kirjailija", writer);
        enterValueInGui("#Julkaisuvuosi", year);
        enterValueInGui("#Sivumäärä", pages);
        enterValueInGui("#ISBN-tunniste", isbn);
    }
    
    private void enterValueInGui(String id, String name) {
        clickOn(id).write(name);
    }
    
    private void clickAddInGui() {
        clickOn("#add");
    }
    
    private void AddingRespondsWith(String response) {
        verifyThat("#messages", hasText(response));
    }
    
}
