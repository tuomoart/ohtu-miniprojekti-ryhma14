/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import library.domain.Book;
import library.domain.*;
import org.kordamp.bootstrapfx.BootstrapFX;

/**
 *
 * @author hiira
 */
public class SearchView {
    private BorderPane mainLayout;
    private BorderPane content;
    private TextField searchBox;
    private Logic logic;
    private Gui gui;
    private TableView<Tip> table;
    
    public SearchView(Gui gui, Logic logic) {
        this.gui = gui;
        this.logic = logic;
    }

    public Scene createSearchScene() {
        // create main layout
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setPrefSize(642,582);

        // create top bar
        mainLayout.setTop(getTopBar());
        BorderPane.setMargin(mainLayout.getTop(),new Insets(0,0,30,0));

        // create content
        mainLayout.setCenter(getContent());
        BorderPane.setMargin(mainLayout.getCenter(),new Insets(0,0,30,0));

        // create bottom bar
        Region spacer = new Region();
        spacer.setPrefHeight(32);
        mainLayout.setBottom(spacer);

        // create the scene that will be returned
        Scene searchView = new Scene(mainLayout);
        searchView.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        searchView.getRoot().setStyle("-fx-background: #243447");
        return searchView;
    }

    private VBox createBookTable() {
        table = new TableView<>();
        table.setMaxHeight(268);
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
        TableColumn column = new TableColumn();
        Label l = new Label(label);
        l.setMaxWidth(Integer.MAX_VALUE);
        l.getStyleClass().addAll("lbl","lbl-primary");
        column.setGraphic(l);
        column.setMinWidth(100);
        column.setCellValueFactory(new PropertyValueFactory<Book, String>(contents));
        return column;
    }

    private Pane getTopBar() {
        Pane menu = new HBox();
        menu.getChildren().add(getTitle());
        Region spacer = new Region();
        menu.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button addNewTipButton = getCreationButton();
        menu.getChildren().add(addNewTipButton);

        return menu;
    }

    private Label getTitle() {
        Label title = new Label("");
        return title;
    }

    private Pane getContent() {
        content = new BorderPane();
        content.setPadding(new Insets(10));
        ((BorderPane) content).setTop(getTipSplitMenu());
        BorderPane.setAlignment(((BorderPane) content).getTop(),Pos.CENTER);
        BorderPane.setMargin(((BorderPane) content).getTop(),new Insets(0,0,30,0));
        Region searchspacer = new Region();
        searchspacer.setPrefSize(193,32);
        ((BorderPane) content).setCenter(searchspacer);
        BorderPane.setAlignment(((BorderPane) content).getCenter(),Pos.CENTER);
        BorderPane.setMargin(((BorderPane) content).getCenter(),new Insets(0,0,30,0));
        Region tablespacer = new Region();
        tablespacer.setPrefSize(602,268);
        ((BorderPane) content).setBottom(tablespacer);
        BorderPane.setAlignment(((BorderPane) content).getBottom(),Pos.CENTER);
        return content;
    }

    private SplitMenuButton getTipSplitMenu() {
        SplitMenuButton splitMenu = new SplitMenuButton();
        splitMenu.setId("searchsplit");
        splitMenu.setPrefWidth(345);
        splitMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splitMenu.show();
            }
        });
        MenuItem books = new MenuItem("Kirja");
        books.setId("searchsplitbook");
        books.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((BorderPane) content).setCenter(getSearchBox());
                BorderPane.setAlignment(((BorderPane) content).getCenter(),Pos.CENTER);
                content.setBottom(createBookTable());
                BorderPane.setAlignment(((BorderPane) content).getBottom(),Pos.CENTER);
                mainLayout.setBottom(getDeleteAndRead());
            }
        });
        splitMenu.getItems().add(books);
        splitMenu.setText("Valitse haettavan vinkin tyyppi");
        splitMenu.getStyleClass().addAll("split-menu-btn","split-menu-btn-lg","split-menu-btn-primary");
        return splitMenu;
    }

    private Pane getDeleteAndRead() {
        Pane bottomBar = new HBox(5);
        Region spacer = new Region();
        bottomBar.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomBar.getChildren().addAll(getMarkReadButton(),getDeleteButton());
        return bottomBar;
    }

    private Button getDeleteButton() {
        Button deleteButton = new Button("Poista");
        deleteButton.getStyleClass().addAll("btn","btn-danger");
        deleteButton.setId("deleteButton");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteSelectedRow();
            }
        });
        return deleteButton;
    }

    private Button getMarkReadButton() {
        Button markReadButton = new Button("Lue");
        markReadButton.getStyleClass().addAll("btn","btn-success");
        markReadButton.setId("markReadButton");
        markReadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                markSelectedRowRead();
            }
        });
        return markReadButton;
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

    private TextField getSearchBox() {
        searchBox = new TextField();
        searchBox.setMaxWidth(193);
        searchBox.getStyleClass().addAll("btn");
        searchBox.setId("searchBox");
        searchBox.setPromptText("hakusana");
        return searchBox;
    }
    
    private void updateTable() {
        table.setItems(filteredBooks(searchBox.getText()));
        table.refresh();
    }
    
    private Button getCreationButton() {
        Button creationButton = new Button("Lisää uusi lukuvinkki");
        creationButton.getStyleClass().addAll("btn","btn-primary");
        creationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gui.switchToCreation();
            }
        });
        return creationButton;
    }
}
