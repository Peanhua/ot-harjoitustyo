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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author joyr
 */
public class KeybindRequester extends Window {
    public interface KeybindRequesterHandler {
        void bind(KeyCode keyCode);
    }

    private final String                  targetText;
    private final KeybindRequesterHandler handler;

    public KeybindRequester(Screen screen, String targetText, KeybindRequesterHandler handler) {
        super(screen);
        this.targetText = targetText;
        this.handler    = handler;
    }

    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("window");
        pane.setMaxSize(400, 150);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        pane.setCenter(vbox);
        
        Text t = UserInterfaceFactory.createText("Press key to bind to " + this.targetText + ".");
        
        Button b = new Button("Cancel");
        b.setOnAction(e -> this.close());
        b.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            this.close();
            this.handler.bind(event.getCode());
        });

        this.setFocusDefault(b);
        
        vbox.getChildren().addAll(t, b);
        
        return pane;
    }
    
}
