/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import library.domain.*;

import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
/**
 *
 * @author hiira
 */
public class Gui extends Application {
    private Stage stage;
    private SearchView searchView;
    
    @Override
    public void start(Stage stg) {
        this.stage = stg;
        this.searchView = new SearchView();
        
        this.stage.setTitle("Lukuvinkkikirjasto");
        
        //start the application with the search view
        stage.setScene(searchView.createSearchScene());
        stage.show();
    }

}
