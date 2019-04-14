/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.widgets;

import fishingrodofdestiny.ui.screens.Screen;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

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
        shadow.setStyle("-fx-background-color: #000000;");
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
}
