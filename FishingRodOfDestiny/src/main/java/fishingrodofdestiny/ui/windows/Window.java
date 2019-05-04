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
 * User interface control: a modal requester window.
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
    
    /**
     * Adds this window to the screen, so that it is shown to the user.
     */
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
    
    /**
     * Remove this window from the screen.
     */
    public final void close() {
        if (this.window != null) {
            screen.getRoot().getChildren().remove(this.window);
            this.window = null;
            screen.enableInputHandlers();
        }
    }
    
    /**
     * Return the screen this window is part of.
     * 
     * @return The screen
     */
    public final Screen getScreen() {
        return this.screen;
    }
    
    /**
     * Set the Node to receive focus when this window is shown.
     * 
     * @param node The node to receive focus when this window is shown
     */
    protected final void setFocusDefault(Node node) {
        this.focusDefault = node;
    }

    /**
     * Create the user interface elements to represent this window.
     * 
     * @return A node containing all the user interface elements
     */
    protected abstract Node createUserInterface();
    
    /**
     * Helper method to create the window title portion of the user interface elements.
     * <p>
     * Usually the window is a BorderPane, and the title is set as its top element.
     * 
     * @param title The window title text
     * @return A node containing the window title
     */
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
