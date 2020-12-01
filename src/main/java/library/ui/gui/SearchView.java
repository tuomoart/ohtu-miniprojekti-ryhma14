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
import javafx.scene.control.TextField;
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
    private TextField searchBox;
    
    public Scene createSearchScene() {
        VBox searchLayout = new VBox();
        
        searchLayout.setSpacing(10);
        searchLayout.getChildren().add(createMenu());
        searchLayout.getChildren().add(createTitle());
        searchLayout.getChildren().add(createDropDownListForTypeOfTip());
        searchLayout.getChildren().add(createSearchBox());
        //searchLayout.getChildren().add(createListOfResults());
        
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
        HBox dropdownlistLayout = new HBox();
        this.tipDropdownlist = new ComboBox();
        
        tipDropdownlist.getItems().addAll("Kirja");
        
        dropdownlistLayout.getChildren().add(new Label("Vinkkityyppi: "));
        dropdownlistLayout.getChildren().add(this.tipDropdownlist);
        
        return dropdownlistLayout;
    }
    
    public HBox createSearchBox() {
        HBox searchBoxLayout = new HBox();
        this.searchBox = new TextField();
        
        searchBoxLayout.getChildren().add(new Label("Hakusana: "));
        searchBoxLayout.getChildren().add(this.searchBox);
        
        return searchBoxLayout;
    }
    
    /*
    public Label createListOfResults() {
        
    }
    */
        
}
