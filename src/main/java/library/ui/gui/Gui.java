/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import library.domain.Logic;
/**
 *
 * @author hiira
 */
public class Gui extends Application {
    private Stage stage;
    private SearchView searchView;
    private CreationView creationView;
    private Logic logic;
    
    @Override
    public void start(Stage stg) {
        this.logic = new Logic();
        this.stage = stg;
        this.searchView = new SearchView(this, logic);
        this.creationView = new CreationView(this);
        
        this.stage.setTitle("Lukuvinkkikirjasto");
        
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
    
     public void addBook(String title, String author, String year, String pages, String ISBN) {
        logic.addBook(title, author, year, pages, ISBN);
    }

}
