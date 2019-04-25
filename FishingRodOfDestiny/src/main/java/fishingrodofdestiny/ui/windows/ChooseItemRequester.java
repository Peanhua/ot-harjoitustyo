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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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

    public ChooseItemRequester(Screen screen, String title, List<GameObject> items, ConfirmationHandler handler) {
        super(screen);
        this.title   = title;
        this.items   = items;
        this.handler = handler;
    }

    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("window");
        pane.setMaxSize(300, 300);
        
        Text titleText = UserInterfaceFactory.createText(this.title);
        titleText.setTextAlignment(TextAlignment.CENTER);
        pane.getStyleClass().add("windowTitle");
        pane.setTop(titleText);
        
        ObservableList<GameObject> listItems = FXCollections.observableArrayList(this.items);
        ListView<GameObject> lview = new ListView<>(listItems);
        lview.setCellFactory(param -> new ListCell<GameObject>() {
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
        lview.setEditable(false);
        pane.setCenter(lview);
        
        String[] labels = { "Cancel", "Ok" };
        List<Button> buttons = new ArrayList<>();
        Node brow = UserInterfaceFactory.createButtonRow(labels, buttons);
        brow.getStyleClass().add("windowButtonRow");
        pane.setBottom(brow);
        
        buttons.get(0).setOnAction(e -> {
            this.close();
        });

        buttons.get(1).setOnAction(e -> {
            this.close();
            List<GameObject> selectedItems = new ArrayList<>();
            lview.getSelectionModel().getSelectedItems().forEach(obj -> selectedItems.add(obj));
            this.handler.confirmed(selectedItems);
        });
        
        this.setFocusDefault(buttons.get(0));

        return pane;
        
    }
    
}
