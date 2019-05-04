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
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author joyr
 */
public abstract class Window {
    private final Screen screen;
    private Pane         window;
    private Node         focusDefault;
    
    public Window(Screen screen) {
        this.screen       = screen;
        this.window       = null;
        this.focusDefault = null;
    }
    
    public final void show() {
        if (this.window != null) {
            return;
        }

        this.window = new StackPane();
        Region shadow = new Region();
        shadow.getStyleClass().add("shadow");
        shadow.setOpacity(0.5);
        window.getChildren().add(shadow);
        
        window.getChildren().add(this.createUserInterface());
        
        screen.getRoot().getChildren().add(this.window);

        screen.disableInputHandlers();
        if (this.focusDefault != null) {
            this.focusDefault.requestFocus();
        }
    }
    
    public final void close() {
        if (this.window != null) {
            screen.getRoot().getChildren().remove(this.window);
            this.window = null;
            screen.enableInputHandlers();
        }
    }
    
    public final Screen getScreen() {
        return this.screen;
    }
    
    protected final void setFocusDefault(Node node) {
        this.focusDefault = node;
    }

    protected abstract Node createUserInterface();
    
    protected final Node createWindowTitle(String title) {
        StackPane pane = new StackPane();
        pane.getStyleClass().add("windowTitle");
        
        Text titleText = UserInterfaceFactory.createText(title);
        pane.getChildren().add(titleText);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.getStyleClass().add("windowTitle");
        
        return pane;
    }
}
