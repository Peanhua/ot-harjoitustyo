/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Base class for all the screens.
 *
 * Screens contain the glue to tie the visual representation and game logic together.
 *
 * @author joyr
 */
public abstract class Screen {
    private final Screen parentScreen;
    private final Stage  stage;
    private Scene        scene;
    private StackPane    root;
    
    /**
     * Create a new Screen setting the parent screen and the stage.
     * 
     * @param parent The parent screen (to return to after this), can be null.
     * @param stage  The JavaFX stage object this screen is tied to.
     */
    public Screen(Screen parent, Stage stage) {
        this.parentScreen = parent;
        this.stage        = stage;
        this.scene        = null;
        this.root         = null;
    }

    /**
     * Create the JavaFX node to represent the this screen.
     *
     * @return The JavaFX node representing this screen, usually a Pane containing child nodes.
     */
    protected abstract Node createUserInterface();
    
    protected final Stage getStage() {
        return this.stage;
    }
    
    /**
     * Return the parent screen of this screen.
     * 
     * @return The parent screen.
     */
    public final Screen getParent() {
        return this.parentScreen;
    }

    /**
     * Show this screen.
     * <p>
     * Calls stage.setScene() to change the currently shown screen.
     */
    public final void show() {
        if (this.scene == null) {
            BorderPane bp = new BorderPane();
            this.root = new StackPane();
            bp.setCenter(root);
            root.getChildren().add(this.createUserInterface());
            this.scene = new Scene(bp, 800, 600, Color.BLACK);
            this.scene.getStylesheets().add("/fishingrodofdestiny/css/scene.css");
        }
        
        if (this.scene != null) {
            this.stage.setScene(this.scene);
            this.onShow();
        }
    }

    /**
     * Close this screen.
     * <p>
     * If parent screen is not null, show the parent screen.
     * When the last screen is closed (ie. when the parent screen is null), the application is exited.
     */
    public final void close() {
        this.onClose();
        if (this.parentScreen == null) {
            Platform.exit();
        } else {
            this.parentScreen.show();
        }
    }


    /**
     * Return a StackPane behind everything.
     * <p>
     * Can be used to add Nodes on top of others (for example requester windows).
     *
     * @return The StackPane containing the screen and other stacked nodes in view.
     */
    public final StackPane getRoot() {
        return this.root;
    }


    /**
     * Enable input handlers for this screen.
     */
    public void enableInputHandlers() {
    }

    /**
     * Disable input handlers for this screen.
     * <p>
     * Used when a modal requester window is opened on top of this screen.
     */
    public void disableInputHandlers() {
    }

    /**
     * This method is called when this screens <a href="#show--">show()</a> is called.
     *
     * @see #show()
     */
    public void onShow() {
    }
    
    /**
     * This method is called when this screens <a href="#close--">close()</a> is called.
     * 
     * @see #close()
     */
    public void onClose() {
    }
}
