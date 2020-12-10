/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import library.domain.Book;
import library.domain.*;

/**
 *
 * @author hiira
 */
public class SearchView {

    private Button addNewTipButton;
    private Button deleteButton;
    private Button markReadButton;
    private ComboBox tipDropdownlist;
    private TextField searchBox;
    private Logic logic;
    private Gui gui;
    private TableView<Tip> table;

    
    
    public SearchView(Gui gui, Logic logic) {
        this.gui = gui;
        this.logic = logic;
    }

    
    public Scene createSearchScene() {
        Scene scene = new Scene(new Group());
        
        
        VBox searchLayout = new VBox(10);
        searchLayout.setPadding(new Insets(10,20,10,20));
        searchLayout.setAlignment(Pos.CENTER);
        
        searchLayout.getChildren().add(createMenu());
        searchLayout.getChildren().add(createTitle());
        searchLayout.getChildren().add(createDropDownListForTypeOfTip());
        searchLayout.getChildren().add(createSearchBoxAndModifyingButtons());
        searchLayout.getChildren().add(createBookTable());
        
        searchLayout.setPrefSize(642,520);

        ((Group) scene.getRoot()).getChildren().addAll(searchLayout);

        return scene;
    }
    
    
    private VBox createBookTable() {
        table = new TableView<>();
        table.setId("list");
        final Label label = new Label("Kirjat");
        table.setEditable(true);
        
        TableColumn authorCol = createTableColumn("Kirjailija", "author");
        TableColumn titleCol = createTableColumn("Nimike", "title");
        TableColumn pagesCol = createTableColumn("Sivumäärä", "pages");
        TableColumn yearCol = createTableColumn("Vuosi", "year");
        TableColumn isbnCol = createTableColumn("ISBN", "ISBN");
        TableColumn readCol = createTableColumn("Luettu", "read");
        
        authorCol.setId("authorCol");
        
        FilteredList<Tip> flBooks = filteredBooks("");
        table.setItems(flBooks);
        table.getColumns().addAll(authorCol, titleCol, yearCol, pagesCol, isbnCol, readCol);

        searchBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                updateTable();
            }
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(label, table);
        
        return vbox;
    }
    
    
    
    private FilteredList<Tip> filteredBooks(String filter) {
        ObservableList<Tip> data = FXCollections.observableArrayList(logic.filteredList(filter));
        FilteredList<Tip> flBooks = new FilteredList(data, p -> true);
        return flBooks;
    }

    
    private TableColumn createTableColumn(String label, String contents) {
        TableColumn column = new TableColumn(label);
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<Book, String>(contents));
        return column;
    }

    
    private HBox createMenu() {
        HBox menu = new HBox();
        Region spacer = new Region();
        menu.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.addNewTipButton = getCreationButton();
        menu.setSpacing(10);
        menu.getChildren().add(addNewTipButton);

        return menu;
    }

    private Label createTitle() {
        Label title = new Label("Hae lukuvinkkiä");
        title.setFont(Font.font("Arial", 20));

        return title;
    }

    private HBox createDropDownListForTypeOfTip() {
        HBox dropdownlistLayout = new HBox();
        this.tipDropdownlist = new ComboBox();

        dropdownlistLayout.setAlignment(Pos.CENTER);
        tipDropdownlist.getItems().addAll("Kirja");
        tipDropdownlist.getSelectionModel().selectFirst();

        dropdownlistLayout.getChildren().add(new Label("Vinkkityyppi: "));
        dropdownlistLayout.getChildren().add(this.tipDropdownlist);
        
        tipDropdownlist.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String tip = (String) tipDropdownlist.getValue();
                //tänne muut vinkkityypit, jos ehtii
                updateTable();
            }
        });
        
        return dropdownlistLayout;
    }

    private BorderPane createSearchBoxAndModifyingButtons() {
        BorderPane searchBoxLayout = new BorderPane();
        
        this.searchBox = new TextField();
        this.markReadButton = new Button("Merkitse luetuksi");
        markReadButton.setId("markReadButton");
        this.deleteButton = new Button("Poista");
        this.deleteButton.setId("deleteButton");
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(markReadButton, deleteButton);
        hbox.setSpacing(10);
        
        searchBox.setId("searchBox");
        searchBox.setPromptText("Hakusana");
        searchBox.setMaxWidth(200);
        
        searchBoxLayout.setCenter(searchBox);
        searchBoxLayout.setRight(hbox);
        
        markReadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                markSelectedRowRead();
            }
        });
        
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteSelectedRow();
            }
        });
        
        return searchBoxLayout;
    }

    private void markSelectedRowRead() {
        ObservableList<Tip> selected = table.getSelectionModel().getSelectedItems();
        
        for (Tip tip : selected) {
            logic.toggleRead(tip.getId());
        }
        
        updateTable();
    }
    
    private void deleteSelectedRow() {
        ObservableList<Tip> selected = table.getSelectionModel().getSelectedItems();
        
        for (Tip tip : selected) {
            logic.removeTip(tip.getId());
        }
        
        updateTable();
    }
    
    private void updateTable() {
        table.setItems(filteredBooks(searchBox.getText()));
        table.refresh();
    }
    
    private Button getCreationButton() {
        Button creationButton = new Button("Lisää uusi lukuvinkki");
        creationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gui.switchToCreation();
            }
        });
        return creationButton;
    }
}
