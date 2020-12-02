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
    private String selectedTip = "Kirja";
    private Map<String,List<String>> tips = new HashMap<>() {
        {
            put("Kirja", new ArrayList<String>() {
                {
                    add("Nimike");
                    add("Kirjailija");
                    add("Julkaisuvuosi");
                    add("Sivumäärä");
                    add("ISBN-tunniste");
                }
            });
        }
    };
    private Map<String,TextField> textfields = new HashMap<>();
    private Label messageLabel;

    public CreationView(Gui gui) {
        this.gui = gui;
    }

    public Scene getCreationScene() {
        // create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPrefSize(360,420);
        mainLayout.setPadding(new Insets(10,20,10,20));

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
        VBox adding = new VBox(20);
        adding.setAlignment(Pos.CENTER);
        messageLabel = new Label("");
        messageLabel.setId("messages");
        messageLabel.setPrefHeight(100);
        messageLabel.setPrefHeight(100);
        adding.getChildren().add(messageLabel);
        Button add = getAddButton();
        adding.getChildren().add(add);
        mainLayout.setBottom(adding);

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
        searchButton.setId("search");
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
        addButton.setId("add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addTip();
            }
        });
        return addButton;
    }

    private void addTip() {
        List<String> messages = new ArrayList<>();
        if (selectedTip.equals("Kirja")) {
            messages = addBook();
        }
        setMessages(messages);
        if (messages.get(0).contains("lisätty")) {
            empty();
        }
    }

    private List<String> addBook() {
        String name = textfields.get("Nimike").getText();
        String author = textfields.get("Kirjailija").getText();
        String year = textfields.get("Julkaisuvuosi").getText();
        String pages = textfields.get("Sivumäärä").getText();
        String ISBN = textfields.get("ISBN-tunniste").getText();
        return gui.addBook(name, author, year, pages, ISBN);
    }

    public void setMessages(List<String> messages) {
        messageLabel.setText("");
        for (String message : messages) {
            messageLabel.setText(messageLabel.getText() + "\n" + message);
        }
    }

    private void empty() {
        for (TextField textfield : textfields.values()) {
            textfield.setText("");
        }
    }

    private ComboBox getTipMenu() {
        ComboBox tipMenu = new ComboBox(FXCollections.observableArrayList(tips.keySet()));
        tipMenu.setPromptText("Valitse lisättävän vinkin tyyppi");
        tipMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedTip = (String) tipMenu.getValue();
                //TODO oikeantyyppisen vinkkityypin Paneen vaihtaminen, varmaan paras ratkaisu käyttää borderpanen setCenter...
            }
        });
        return tipMenu;
    }

    private VBox getBookCreationLayout() {
        VBox bookLayout = new VBox(5);
        for (String infotype : tips.get("Kirja")) {
            bookLayout.getChildren().add(getQuery(infotype));
        }

        // title
        textfields.get("Nimike").setPromptText("pakollinen tieto");

        /* real time error listening - not yet supported */
        /* textfields.get("Nimike").textProperty().addListener((arg0, oldValue, newValue) -> {
            if (true) {
                textfields.get("Nimike").setStyle("-fx-border-color: red;" + " -fx-border-width: 1.5px");
            }
        }); */

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
        input.setId(infotype);
        textfields.put(infotype,input);
        query.getChildren().add(input);
        return query;
    }
}
