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
 *
 * @author joyr
 */
public abstract class Screen {
    private Screen    parentScreen;
    private Stage     stage;
    private Scene     scene;
    private StackPane root;
    
    public Screen(Screen parent, Stage stage) {
        this.parentScreen = parent;
        this.stage        = stage;
        this.scene        = null;
        this.root         = null;
    }
    
    protected abstract Node createUserInterface();
    
    protected final Stage getStage() {
        return this.stage;
    }
    
    public final Screen getParent() {
        return this.parentScreen;
    }

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

    public final void close() {
        if (this.parentScreen == null) {
            Platform.exit();
        } else {
            this.parentScreen.show();
        }
    }
    
    public final StackPane getRoot() {
        return this.root;
    }
    
    public void enableInputHandlers() {
    }
    
    public void disableInputHandlers() {
    }

    public void onShow() {
    }
}
