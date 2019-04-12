/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.widgets;

import fishingrodofdestiny.world.Level;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author joyr
 */
public class LevelView extends Widget {
    private Pane            container;
    private Canvas          canvas;
    private GraphicsContext graphicsContext;
    private int             width;
    private int             height;
    private Level           level;
    private int             centerAtX;
    private int             centerAtY;
    
    public LevelView() {
        this.level  = null;
    }
    
    @Override
    public Node createUserInterface() {
        this.container = new Pane();
        this.container.setMaxWidth(Double.MAX_VALUE);
        this.container.setMaxHeight(Double.MAX_VALUE);

        this.width           = 0;
        this.height          = 0;
        this.canvas          = null;
        this.graphicsContext = null;
        
        this.container.heightProperty().addListener((o) -> {
            this.onResized();
        });

        return this.container;
    }

    
    private void onResized() {
        this.width  = (int) this.container.getWidth();
        this.height = (int) this.container.getHeight();
        
        if (this.canvas != null) {
            this.container.getChildren().remove(this.canvas);
        }
        
        this.canvas = new Canvas(this.width, this.height);
        this.graphicsContext = canvas.getGraphicsContext2D();

        this.container.getChildren().add(this.canvas);
        
        this.refresh();
    }


    @Override
    public void refresh() {
        if (this.width == 0 || this.canvas == null) {
            return;
        }

        this.graphicsContext.setFill(Color.BLACK);
        //this.graphicsContext.clearRect(0, 0, this.width, this.height);
        this.graphicsContext.fillRect(0, 0, this.width, this.height);
        
        if (this.level != null) {
            this.level.draw(this.graphicsContext);
        }
    }
    
    
    public void setLevel(Level level) {
        this.level = level;
    }
    
    
    public void centerAtTile(int x, int y) {
    }
}
