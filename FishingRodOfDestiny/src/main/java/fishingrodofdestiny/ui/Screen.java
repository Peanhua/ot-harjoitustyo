/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
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
    
    protected abstract void setup(Group root, Scene scene);
    
    protected final Stage getStage() {
        return this.stage;
    }
    
    public final Screen getParent() {
        return this.parent;
    }

    public final void show() {
        if (this.scene == null) {
            Group root = new Group();
            this.scene = new Scene(root, 800, 600, Color.BLACK);

            this.setup(root, this.scene);
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
