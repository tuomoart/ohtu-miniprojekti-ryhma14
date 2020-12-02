/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 *
 * @author tuomoart
 */
public class GuiTemporaryTest extends ApplicationTest {
    private Scene scene;
    private Parent rootNode;
    
    private Logic logic;
    private SearchView searchView;
    private CreationView creationView;
    
    @Override
    public void start(Stage stage) throws Exception {
        logic = new Logic(new StubDao());
        Gui gui = new Gui(logic);
        gui.format();
        
        searchView = gui.getSearchView();
        creationView = gui.getCreationView();
        
        scene = creationView.getCreationScene();
        rootNode = scene.getRoot();
        
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
    
    @Test
    public void commandNewBookInGui() {
        Button[] buttons = from(rootNode).lookup(".button").queryAll().toArray(new Button[0]);
        
        for (Button b: buttons) {
            if (b.getText().equals("Lisää uusi lukuvinkki")) {
                clickOn(b);
            }
        }
        
        enterNameInGui("kirjannimi");
        
        guiRespondsWith("\nKirja 'kirjannimi' lisätty");
    }
    
    public void enterNameInGui(String name) {
//        TextField[] fields = from(rootNode).lookup(".textfield").queryAll().toArray(new TextField[0]);
//        
//        fields[0].setText(name);
        clickOn("#Nimike").write(name);
        clickOn("#add");
        
    }
    
    public void guiRespondsWith(String response) {
        verifyThat("#messages", hasText(response));
    }
    
}
