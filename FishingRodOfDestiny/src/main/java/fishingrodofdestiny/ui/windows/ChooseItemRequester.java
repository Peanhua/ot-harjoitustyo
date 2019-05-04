/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * Requester allowing user to choose one GameObject from the given list.
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

    /**
     * Create a new requester.
     * <p>
     * Once the user has made the choice, the handler is called with a list of the chosen items. Currently the list will always contain a maximum of one item.
     * 
     * @param screen  The screen where to place this requester
     * @param title   The title of the requester window
     * @param items   List of the GameObjects to choose from
     * @param handler The callback handler, only called if the user clicks "Ok"
     */
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
