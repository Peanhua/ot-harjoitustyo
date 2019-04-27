/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.windows;

import fishingrodofdestiny.ui.screens.Screen;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.world.gameobjects.GameObject;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author joyr
 */
public class ChooseItemRequester extends Window {
    public interface ConfirmationHandler {
        void confirmed(List<GameObject> selectedItems);
    }

    private final String              title;
    private final List<GameObject>    items;
    private final ConfirmationHandler handler;
    private ListView<GameObject>      itemListView;

    public ChooseItemRequester(Screen screen, String title, List<GameObject> items, ConfirmationHandler handler) {
        super(screen);
        this.title        = title;
        this.items        = items;
        this.handler      = handler;
        this.itemListView = null;
    }

    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("window");
        pane.setMaxSize(300, 300);
        
        pane.setTop(this.createWindowTitle(this.title));
        pane.setCenter(this.createItemList());
        pane.setBottom(this.createButtons());
        
        return pane;
    }
    
    private Node createItemList() {
        ObservableList<GameObject> listItems = FXCollections.observableArrayList(this.items);
        this.itemListView = new ListView<>(listItems);
        this.itemListView.setCellFactory(param -> new ListCell<GameObject>() {
            @Override
            protected void updateItem(GameObject item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        this.itemListView.setEditable(false);
        
        return this.itemListView;
    }
    
    private Node createButtons() {
        String[] labels = { "Cancel", "Ok" };
        List<Button> buttons = new ArrayList<>();
        Node brow = UserInterfaceFactory.createButtonRow(labels, buttons);
        brow.getStyleClass().add("windowButtonRow");
        
        buttons.get(0).setOnAction(e -> {
            this.close();
        });

        buttons.get(1).setOnAction(e -> {
            this.close();
            List<GameObject> selectedItems = new ArrayList<>();
            this.itemListView.getSelectionModel().getSelectedItems().forEach(obj -> selectedItems.add(obj));
            this.handler.confirmed(selectedItems);
        });
        
        this.setFocusDefault(buttons.get(0));
        return brow;
    }
}
