/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public abstract class Screen {
    private Screen parent;
    private Stage  stage;
    private Scene  scene;
    
    public Screen(Screen parent, Stage stage) {
        this.parent = parent;
        this.stage  = stage;
        this.scene  = null;
    }
    
    protected abstract Node createUserInterface();
    
    protected final Stage getStage() {
        return this.stage;
    }
    
    public final Screen getParent() {
        return this.parent;
    }

    public final void show() {
        if (this.scene == null) {
            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: #000000;");
            root.setCenter(this.createUserInterface());
            this.scene = new Scene(root, 800, 600, Color.BLACK);
        }
        
        if (this.scene != null) {
            this.stage.setScene(this.scene);
        }
    }

    public final void close() {
        if (parent == null) {
            Platform.exit();
        } else {
            parent.show();
        }
    }
}
