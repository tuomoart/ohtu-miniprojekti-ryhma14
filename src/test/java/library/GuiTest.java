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
import library.dao.StubDao;
import library.domain.Logic;
import library.ui.gui.CreationView;
import library.ui.gui.Gui;
import library.ui.gui.SearchView;
import static org.junit.Assert.assertTrue;
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
    private Scene scene;
    private Parent rootNode;
    
    private Logic logic;
    private SearchView searchView;
    private CreationView creationView;
    
    @Override
    public void start(Stage stage) throws Exception {
        Gui sovellus = new Gui(new Logic(new StubDao()));
        
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
    
    @Test
    public void canAddBookWithValidName() {
        enterNameInGui("kirjannimi");
        
        guiRespondsWith("\nKirja 'kirjannimi' lisätty");
    }
    
    public void enterNameInGui(String name) {
        clickOn("#Nimike").write(name);
        clickOn("#add");
        
    }
    
    public void guiRespondsWith(String response) {
        verifyThat("#messages", hasText(response));
    }
    
}
