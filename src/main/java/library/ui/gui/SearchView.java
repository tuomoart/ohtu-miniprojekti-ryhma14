/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui.gui;

import java.util.function.Predicate;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.layout.HBox;
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
    private Button searchLibraryButton;
    private ComboBox tipDropdownlist;
    private TextField searchBox;
    private Logic logic;
    private Gui gui;
    private final ObservableList<Book> data;
            
    
    public SearchView(Gui gui, Logic logic) {
        this.gui = gui;
        this.logic = logic;
        this.data = FXCollections.observableArrayList(logic.getBooks());
    }
    
    public VBox createBookTable() {
        
        TableView<Book> table = new TableView<>();
        
        final Label label = new Label("Kirjat");

        table.setEditable(true);

        TableColumn authorCol = new TableColumn("Author");
        authorCol.setMinWidth(100);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("author"));

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("title"));

        TableColumn pagesCol = new TableColumn("Pages");
        pagesCol.setMinWidth(100);
        pagesCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("pages"));
        
        TableColumn yearCol = new TableColumn("Year");
        yearCol.setMinWidth(100);
        yearCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("year"));
        
        TableColumn isbnCol = new TableColumn("ISBN");
        isbnCol.setMinWidth(100);
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("ISBN"));

        FilteredList<Book> filteredBooks = new FilteredList(data, p -> true);
        table.setItems(filteredBooks);
        table.getColumns().addAll(authorCol, titleCol, yearCol, pagesCol, isbnCol);
        
        ObjectProperty<Predicate<Book>> filter = new SimpleObjectProperty<>();

        filter.bind(Bindings.createObjectBinding(() -> 
            book -> book.getAuthor().toLowerCase().contains(searchBox.getText().toLowerCase()) ||
                    book.getTitle().toLowerCase().contains(searchBox.getText().toLowerCase()) ||
                    book.getPages().toLowerCase().contains(searchBox.getText().toLowerCase()) ||
                    book.getISBN().toLowerCase().contains(searchBox.getText().toLowerCase()) ||
                    book.getYear().toLowerCase().contains(searchBox.getText().toLowerCase()), 
            searchBox.textProperty()));
        
        
        searchBox.setOnKeyReleased(keyEvent ->
        {
            filteredBooks.predicateProperty().bind(Bindings.createObjectBinding(
                () -> filter.get()));
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
        
        return vbox;
    }
    
    public Scene createSearchScene() {
        Scene scene = new Scene(new Group());
        
        VBox searchLayout = new VBox();
        searchLayout.setSpacing(10);
        searchLayout.getChildren().add(createMenu());
        searchLayout.getChildren().add(createTitle());
        searchLayout.getChildren().add(createDropDownListForTypeOfTip());
        searchLayout.getChildren().add(createSearchBox());
        searchLayout.getChildren().add(createBookTable());
        //searchLayout.getChildren().add(createListOfResults());
        
        ((Group) scene.getRoot()).getChildren().addAll(searchLayout);
        
        return scene;
    }
    
    public HBox createMenu() {
        HBox menu = new HBox();
        // this.searchLibraryButton = new Button("Hae lukuvinkkiä");
        this.addNewTipButton = getCreationButton();
        menu.setSpacing(10);
        menu.getChildren().add(addNewTipButton);
        //menu.getChildren().add(searchLibraryButton);
        
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
        
        dropdownlistLayout.setAlignment(Pos.CENTER);
        tipDropdownlist.getItems().addAll("Kirja");
        
        dropdownlistLayout.getChildren().add(new Label("Vinkkityyppi: "));
        dropdownlistLayout.getChildren().add(this.tipDropdownlist);
        
        return dropdownlistLayout;
    }
    
    public HBox createSearchBox() {
        HBox searchBoxLayout = new HBox();
        searchBoxLayout.setAlignment(Pos.CENTER);
        this.searchBox = new TextField();
        
        searchBox.setPromptText("Hakusana: ");
        searchBoxLayout.getChildren().add(this.searchBox);
        
        return searchBoxLayout;
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
