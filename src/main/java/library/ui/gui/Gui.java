/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import library.domain.Logic;
import library.dao.SQLBookDao;

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
    
    public Gui(Logic logic) {
        this.logic = logic;
    }
    
    public Gui() throws Exception{
        this.logic = new Logic(new SQLBookDao("jdbc:sqlite:database.db"));
    }

    @Override
    public void start(Stage stg) {
        stage = stg;
        format();
        
        stage.setTitle("Lukuvinkkikirjasto");
        
        //start the application with the search view
        stage.setScene(creationView.getCreationScene());
        stage.show();
    }
    
    public void format() {
        searchView = new SearchView(this, logic);
        creationView = new CreationView(this);
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
    
    @lombok.Generated
    public Logic getLogic() {
        return this.logic;
    }

    @lombok.Generated
    public SearchView getSearchView() {
        return searchView;
    }

    @lombok.Generated
    public CreationView getCreationView() {
        return creationView;
    }

    public boolean textIsValidTitle(String text) {
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
    }
}
