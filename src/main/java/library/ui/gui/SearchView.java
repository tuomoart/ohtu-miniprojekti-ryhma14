/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 *
 * @author hiira
 */
public class SearchView {
    private Button addNewTipButton;
    private Button searchLibraryButton;
    private ComboBox tipDropdownlist;
    
    public Scene createSearchScene() {
        VBox searchLayout = new VBox();
        
        searchLayout.setSpacing(10);
        searchLayout.getChildren().add(createMenu());
        searchLayout.getChildren().add(createTitle());
        searchLayout.getChildren().add(createDropDownListForTypeOfTip());
        
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
    
    public Label createTitle() {
        Label title = new Label("Hae lukuvinkkiä");
        title.setFont(Font.font("Arial", 20));
        
        return title;
    }
    
    public HBox createDropDownListForTypeOfTip() {
        HBox layout = new HBox();
        this.tipDropdownlist = new ComboBox();
        
        tipDropdownlist.getItems().addAll("Kirja");
        
        layout.getChildren().add(new Label("Vinkkityyppi: "));
        layout.getChildren().add(this.tipDropdownlist);
        
        return layout;
    }
        
}
