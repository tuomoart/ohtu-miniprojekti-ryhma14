package library.ui.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.*;

/**
 * for getting the javafx scene for adding new tips to the library
 *
 * @author osekeranen
 */

public class CreationView {

    private Gui gui;

    public CreationView(Gui gui) {
        this.gui = gui;
    }

    public Scene getCreationScene() {
        // create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPrefSize(320,320);
        mainLayout.setPadding(new Insets(10,10,10,10));

        // create top bar for search, etc.
        Pane top = getTopBar();
        mainLayout.setTop(top);

        // create vbox for tips
        VBox tip = new VBox(20);
        tip.setAlignment(Pos.CENTER);
        tip.getChildren().addAll(getTipMenu(),getBookCreationLayout());
        BorderPane.setAlignment(tip,Pos.BOTTOM_CENTER);
        mainLayout.setCenter(tip);

        // create button for adding the tip
        Button add = getAddButton();
        BorderPane.setAlignment(add,Pos.CENTER);
        mainLayout.setBottom(add);

        // create the scene that will be returned
        Scene creationScene = new Scene(mainLayout);
        return creationScene;
    }

    private HBox getTopBar() {
        HBox topBar = new HBox();
        Region spacer = new Region();
        topBar.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().add(getSearchButton());
        return topBar;
    }

    private Button getSearchButton() {
        Button searchButton = new Button("Hae");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gui.switchToSearch();
            }
        });
        return searchButton;
    }

    private Button getAddButton() {
        Button addButton = new Button("Lisää");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO logiikan kutsuminen
            }
        });
        return addButton;
    }

    private ComboBox getTipMenu() {
        Map tipTypes = getTipTypes();
        ComboBox tipMenu = new ComboBox(FXCollections.observableArrayList(tipTypes.keySet()));
        tipMenu.setPromptText("Valitse lisättävän vinkin tyyppi");
        tipMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO oikeantyyppisen vinkkityypin Paneen vaihtaminen
            }
        });
        return tipMenu;
    }

    private Map getTipTypes() {
        HashMap<String,Pane> types = new HashMap<>();
        types.put("Kirja",getBookCreationLayout());
        return types;
    }

    private VBox getBookCreationLayout() {
        VBox bookLayout = new VBox(5);
        bookLayout.getChildren().addAll(getTitleQuery(),getAuthorQuery(),getYearQuery(),getPagesQuery(),getISBNQuery());
        return bookLayout;
    }

    private HBox getQuery(String infotype) {
        HBox query = new HBox();
        Label info = new Label(infotype + ":");
        query.getChildren().add(info);
        Region spacer = new Region();
        query.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        TextField input = new TextField();
        query.getChildren().add(input);
        return query;
    }

    private HBox getTitleQuery() {
        return getQuery("Nimike");
    }

    private HBox getAuthorQuery() {
        return getQuery("Kirjailija");
    }

    private HBox getYearQuery() {
        return getQuery("Julkaisuvuosi");
    }

    private HBox getPagesQuery() {
        return getQuery("Sivumäärä");
    }

    private HBox getISBNQuery() {
        return getQuery("ISBN-tunniste");
    }
}
