/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.windows;

import fishingrodofdestiny.resources.SettingsCache;
import fishingrodofdestiny.settings.KeyboardSettings;
import fishingrodofdestiny.ui.screens.Screen;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.world.actions.Action;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author joyr
 */
public class SettingsEditor extends Window {
    private Node             defaultFocus;
    private final List<Text> keybindKeyTexts;

    private final KeyboardSettings.Command[] commands = {
        KeyboardSettings.Command.EXIT,
        KeyboardSettings.Command.ZOOM_IN,
        KeyboardSettings.Command.ZOOM_OUT
    };
    private final Action.Type[] actions = {
        Action.Type.MOVE_NORTH,
        Action.Type.MOVE_SOUTH,
        Action.Type.MOVE_WEST,
        Action.Type.MOVE_EAST,
        Action.Type.ACTIVATE_TILE,
        Action.Type.ATTACK,
        Action.Type.PICK_UP,
        Action.Type.DROP,
        Action.Type.USE,
        Action.Type.LEVEL_UP,
        Action.Type.WAIT,
    };

    public SettingsEditor(Screen screen) {
        super(screen);
        this.defaultFocus    = null;
        this.keybindKeyTexts = new ArrayList<>();
        for (int i = 0; i < this.commands.length + this.actions.length; i++) {
            this.keybindKeyTexts.add(null);
        }
    }

    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("window");
        pane.setMaxSize(600, 500);

        pane.setTop(this.createTitle());
        pane.setCenter(this.createKeybindingControls());
        pane.setBottom(this.createCloseButton());

        this.setFocusDefault(defaultFocus);
        
        return pane;
    }
    
    
    private Node createTitle() {
        Text titleText = UserInterfaceFactory.createText("SETTINGS");
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.getStyleClass().add("windowTitle");
        return titleText;
    }
    
    
    private Node createKeybindingControls() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        
        GridPane grid = new GridPane();
        grid.getStyleClass().add("settings");
        hbox.getChildren().add(grid);
        
        KeyboardSettings keyboardSettings = SettingsCache.getInstance().getKeyboardSettings();
        int row = this.createCommandRows(keyboardSettings, grid, 0);
        row = this.createActionRows(keyboardSettings, grid, row);
        
        keyboardSettings.listenOnChange(() -> this.refresh(keyboardSettings));
        this.refresh(keyboardSettings);
        
        return hbox;
    }

    
    private Node createCloseButton() {
        String[] labels = { "Close" };
        List<Button> buttons = new ArrayList<>();
        Node brow = UserInterfaceFactory.createButtonRow(labels, buttons);
        brow.getStyleClass().add("windowButtonRow");
        
        buttons.get(0).setOnAction(e -> {
            this.close();
        });
        
        this.defaultFocus = buttons.get(0);
        
        return brow;
    }
    
    
    private void refresh(KeyboardSettings keyboardSettings) {
        for (int i = 0; i < this.commands.length; i++) {
            this.keybindKeyTexts.get(i).setText(this.keysToString(keyboardSettings.getKeys(this.commands[i])));
        }
        for (int i = 0; i < this.actions.length; i++) {
            this.keybindKeyTexts.get(this.commands.length + i).setText(this.keysToString(keyboardSettings.getKeys(this.actions[i])));
        }
    }

    private String keysToString(List<KeyCode> keys) {
        return keys.stream().map(k -> k.toString()).reduce(null, (a, b) -> (a != null ? a + ", " : "") + b);
    }

    
    private int createCommandRows(KeyboardSettings keyboardSettings, GridPane grid, int row) {
        this.createLabelRow(grid, row, "Commands:");
        row++;

        for (int i = 0; i < this.commands.length; i++) {
            this.createKeybindingRow(keyboardSettings, grid, row, this.commands[i].toString(), i);
            row++;
        }
        
        return row;
    }

    private int createActionRows(KeyboardSettings keyboardSettings, GridPane grid, int row) {
        this.createLabelRow(grid, row, "Actions:");
        row++;
        
        for (int i = 0; i < this.actions.length; i++) {
            this.createKeybindingRow(keyboardSettings, grid, row, this.actions[i].toString(), this.commands.length + i);
            row++;
        }
        
        return row;
    }

    
    private void createLabelRow(GridPane grid, int row, String label) {
        Text t = UserInterfaceFactory.createText(label);
        GridPane.setConstraints(t, 0, row);
        GridPane.setColumnSpan(t, 3);
        grid.getChildren().add(t);
    }

    
    private void createKeybindingRow(KeyboardSettings keyboardSettings, GridPane grid, int row, String command, int textIndex) {
        Text commandText = UserInterfaceFactory.createSmallText(" " + command);
        GridPane.setConstraints(commandText, 0, row);

        Text keysText = UserInterfaceFactory.createSmallText("");
        this.keybindKeyTexts.set(textIndex, keysText);
        GridPane.setConstraints(keysText, 1, row);
        
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> this.addCommand(keyboardSettings, command));
        GridPane.setConstraints(addButton, 2, row);

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> this.clearCommand(keyboardSettings, command));
        GridPane.setConstraints(clearButton, 3, row);

        grid.getChildren().addAll(commandText, keysText, addButton, clearButton);
    }
    
    private void addCommand(KeyboardSettings keyboardSettings, String command) {
        KeybindRequester req = new KeybindRequester(this.getScreen(), command, (keyCode) -> {
            keyboardSettings.addKeybinding(keyCode, command);
        });
        req.show();
    }
    
    private void clearCommand(KeyboardSettings keyboardSettings, String command) {
        keyboardSettings.removeKeyMappings(command);
    }
}
