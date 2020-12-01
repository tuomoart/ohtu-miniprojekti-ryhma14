/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 *
 * @author hiira
 */
public class SearchView {
    private Button addNewTipButton;
    private Button searchLibraryButton;
    
    public Scene createSearchScene() {
        VBox searchLayout = new VBox();
        
        searchLayout.setSpacing(10);
        searchLayout.getChildren().add(createMenu());
        searchLayout.getChildren().add(new Label("Hae lukuvinkkiä"));
        
        return new Scene(searchLayout);
    }
    
    public HBox createMenu() {
        HBox menu = new HBox();
        this.addNewTipButton = new Button("Lisää uusi lukuvinkki");
        this.searchLibraryButton = new Button("Hae lukuvinkkiä");
        
        menu.setSpacing(10);
        menu.getChildren().add(addNewTipButton);
        menu.getChildren().add(searchLibraryButton);
        
        return menu;
    }
}
