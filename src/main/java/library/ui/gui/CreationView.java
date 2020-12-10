package library.ui.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.util.*;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.kordamp.bootstrapfx.BootstrapFX;

/**
 * for getting the javafx scene for adding new tips to the library
 *
 * @author osekeranen
 */
public class CreationView {

    private Gui gui;
    private BorderPane mainLayout;
    private BorderPane content;
    private String selectedTip;
    private Map<String, List<String>> tips = new HashMap<>() {
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
    private Map<String, TextField> textfields = new HashMap<>();
    private Map<String, Pane> layouts = new HashMap<>();
    private Label messageLabel;

    public CreationView(Gui gui) {
        this.gui = gui;
    }

    public Scene getCreationScene() {
        // create main layout
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // create top bar for search, etc.
        Pane top = getTopBar();
        mainLayout.setTop(top);
        BorderPane.setMargin(mainLayout.getTop(),new Insets(0,0,30,0));

        // create main content for the page
        content = (BorderPane) getTipCreationLayout();
        mainLayout.setCenter(content);
        BorderPane.setMargin(mainLayout.getCenter(),new Insets(0,0,30,0));

        // create spaceholder for add button
        Region spacer = new Region();
        spacer.setPrefSize(80,32);
        BorderPane.setAlignment(spacer,Pos.BOTTOM_CENTER);
        mainLayout.setBottom(spacer);

        messageLabel = new Label("");
        messageLabel.setId("messages");
        messageLabel.setMaxSize(0,0);

        // create the scene that will be returned
        Scene creationScene = new Scene(mainLayout);
        creationScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        creationScene.getRoot().setStyle("-fx-background: #243447");
        return creationScene;
    }

    private HBox getTopBar() {
        HBox topBar = new HBox();
        topBar.getChildren().add(getTitle());
        Region spacer = new Region();
        topBar.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().add(getSearchButton());
        return topBar;
    }

    private Button getSearchButton() {
        Button searchButton = new Button("Hae");
        searchButton.getStyleClass().addAll("btn","btn-primary");
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
        addButton.getStyleClass().addAll("btn","btn-primary");
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
        Pane messagePane = getMessagePane(messages);
        BorderPane.setAlignment(messagePane, Pos.CENTER);
        content.setBottom(messagePane);
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

    private Pane getMessagePane(List<String> messages) {
        Pane messagePane = new VBox(5);
        ((VBox) messagePane).setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        for (String msg : messages) {
            messageLabel.setText(messageLabel.getText() + "\n" + msg);
            TextFlow tf = new TextFlow();
            tf.setMaxWidth(342);
            if (msg.contains("lisätty")) {
                tf.getStyleClass().addAll("alert","alert-success");
            } else {
                tf.getStyleClass().addAll("alert","alert-danger");
            }
            Text pre = new Text("");
            pre.getStyleClass().add("strong");
            Text post = new Text(msg);
            post.getStyleClass().add("p");
            tf.getChildren().addAll(pre,post);
            messagePane.getChildren().add(tf);
        }
        return messagePane;
    }

    private void empty() {
        for (TextField textfield : textfields.values()) {
            textfield.setText("");
        }
    }

    private Label getTitle() {
        Label title = new Label();
        title.setId("CreateViewTitle");
        return title;
    }

    private Pane getTipCreationLayout() {
        Pane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        ((BorderPane) layout).setTop(getTipSplitMenu());
        BorderPane.setAlignment(((BorderPane) layout).getTop(),Pos.CENTER);
        BorderPane.setMargin(((BorderPane) layout).getTop(),new Insets(0,0,30,0));
        ((BorderPane) layout).setCenter(getEmptyCreationLayout());
        layouts.put("Kirja",getBookCreationLayout());
        BorderPane.setAlignment(((BorderPane) layout).getCenter(),Pos.TOP_CENTER);
        BorderPane.setMargin(((BorderPane) layout).getCenter(),new Insets(0,0,30,0));
        Region spacer = new Region();
        spacer.setPrefSize(342,63);
        BorderPane.setAlignment(spacer,Pos.BOTTOM_CENTER);
        ((BorderPane) layout).setBottom(spacer);
        return layout;
    }

    private SplitMenuButton getTipSplitMenu() {
        SplitMenuButton splitMenu = new SplitMenuButton();
        splitMenu.setId("split");
        splitMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splitMenu.show();
            }
        });
        splitMenu.setText("Valitse lisättävän vinkin tyyppi");
        splitMenu.getStyleClass().addAll("split-menu-btn","split-menu-btn-lg","split-menu-btn-primary");
        for (String tip : tips.keySet()) {
            MenuItem item = new MenuItem(tip);
            item.setId(tip);
            splitMenu.getItems().add(item);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selectedTip = tip;
                    content.setCenter(layouts.get(tip));
                    Button addButton = getAddButton();
                    BorderPane.setAlignment(addButton,Pos.BOTTOM_CENTER);
                    mainLayout.setBottom(addButton);
                }
            });
        }
        return splitMenu;
    }

    private Region getEmptyCreationLayout() {
        Region region = new Region();
        region.setPrefSize(462,180);
        return region;
    }

    private Pane getBookCreationLayout() {
        Pane layout = new GridPane();
        ((GridPane) layout).setAlignment(Pos.TOP_CENTER);
        ((GridPane) layout).setHgap(140);
        ((GridPane) layout).setVgap(5);
        for (int i = 0; i < tips.get("Kirja").size(); i++) {
            String infotype = tips.get("Kirja").get(i);

            Label infolabel = new Label(infotype + ":");
            infolabel.setStyle("-fx-text-fill: #ffffff");
            infolabel.getStyleClass().addAll("h4");
            ((GridPane) layout).add(infolabel,0,i);

            TextField input = new TextField();
            input.getStyleClass().addAll("btn");
            input.setId(infotype);
            textfields.put(infotype, input);
            ((GridPane) layout).add(input,1,i);
        }
        bookErrorProcessing();
        return layout;
    }

    private void bookErrorProcessing() {
        // title
        TextField title = textfields.get("Nimike");
        title.setPromptText("pakollinen tieto");
        title.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!gui.textIsValidTitle(title.getText())) {
                    title.getStyleClass().add("btn-danger");
                } else {
                    title.getStyleClass().removeAll("btn-danger");
                }
            }
        });

        // author
        TextField author = textfields.get("Kirjailija");
        author.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!gui.textIsValidAuthorName(author.getText())) {
                    author.getStyleClass().add("btn-danger");
                }  else {
                    author.getStyleClass().removeAll("btn-danger");
                }
            }
        });

        // year
        TextField year = textfields.get("Julkaisuvuosi");
        year.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!gui.textIsValidInteger(year.getText())) {
                    year.getStyleClass().add("btn-danger");
                }  else {
                    year.getStyleClass().removeAll("btn-danger");
                }
            }
        });

        // pages
        TextField pages = textfields.get("Sivumäärä");
        pages.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!gui.textIsValidInteger(pages.getText())) {
                    pages.getStyleClass().add("btn-danger");
                }  else {
                    pages.getStyleClass().removeAll("btn-danger");
                }
            }
        });

        // isbn
        TextField isbn = textfields.get("ISBN-tunniste");
        isbn.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!gui.textIsValidISBN(isbn.getText())) {
                    isbn.getStyleClass().add("btn-danger");
                }  else {
                    isbn.getStyleClass().removeAll("btn-danger");
                }
            }
        });
    }
}
