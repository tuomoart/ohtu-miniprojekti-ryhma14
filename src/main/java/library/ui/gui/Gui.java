/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 *
 * @author hiira
 */
public class Gui extends Application {
    private Stage stage;
    private SearchView searchView;
    private CreationView creationView;
    
    @Override
    public void start(Stage stg) {
        this.stage = stg;
        this.searchView = new SearchView();
        this.creationView = new CreationView(this);
        
        this.stage.setTitle("Lukuvinkkikirjasto");
        
        //start the application with the search view
        stage.setScene(creationView.getCreationScene());
        stage.show();
        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());
    }

    public void switchToSearch() {
        stage.setScene(searchView.createSearchScene());
    }

    public void switchToCreation() {
        stage.setScene(creationView.getCreationScene());
    }

}
