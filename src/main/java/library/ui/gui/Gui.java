/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import library.domain.Logic;

import java.util.List;

/**
 *
 * @author hiira
 */
public class Gui extends Application {
    private Logic logic;
    private Stage stage;
    private SearchView searchView;
    private CreationView creationView;

    @Override
    public void start(Stage stg) {
        logic = new Logic();    // should dependency injection be used here?
        stage = stg;
        searchView = new SearchView(this, logic);
        creationView = new CreationView(this);
        
        stage.setTitle("Lukuvinkkikirjasto");
        
        //start the application with the search view
        stage.setScene(creationView.getCreationScene());
        stage.show();
    }

    public void switchToSearch() {
        stage.setScene(searchView.createSearchScene());
    }

    public void switchToCreation() {
        stage.setScene(creationView.getCreationScene());
    }
    
    public List<String> addBook(String title, String author, String year, String pages, String ISBN) {
        return logic.addBook(title, author, year, pages, ISBN);
    }
    
    public Logic getLogic() {
        return this.logic;
    }

    /* public boolean textIsValidTitle(String text) {
        return !text.isBlank();
    }

    public boolean textIsValidAuthorName(String text) {
        return logic.textIsValidAuthorName(text);
    }

    public boolean textIsValidInteger(String text) {
        return logic.textIsValidInteger(text);
    }

    public boolean textIsValidISBN(String text) {
        return logic.textIsValidISBN(text);
    } */
}
