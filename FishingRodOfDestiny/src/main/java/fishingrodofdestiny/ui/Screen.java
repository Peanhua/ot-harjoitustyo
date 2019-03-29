/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
        if(this.scene == null) {
            Group root = new Group();
            this.scene = new Scene(root, 800, 600, Color.BLACK);

            this.setup(root, this.scene);
        }
        
        if(this.scene != null)
            this.stage.setScene(this.scene);
    }

    public final void close() {
        if(parent == null)
            Platform.exit();
        else
            parent.show();
    }
}
