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
    private Scene searchScene;
    
    @Override
    public void start(Stage stg) {
        this.stage = stg;
        this.stage.setTitle("Lukuvinkkikirjasto");
        
        createSearchScene();
        stage.setScene(searchScene);
        stage.show();
    }
    
    public void createSearchScene() {
        VBox searchLayout = new VBox();
        
        searchLayout.setSpacing(10);
        searchLayout.getChildren().add(new Label("Hae lukuvinkkikirjastosta"));
        
        searchScene = new Scene(searchLayout);
    }
}
