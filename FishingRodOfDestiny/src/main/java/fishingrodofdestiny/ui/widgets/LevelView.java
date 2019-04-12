/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.widgets;

import fishingrodofdestiny.world.Level;
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
    
    public LevelView(int width, int height) {
        this.width  = width;
        this.height = height;
        this.level  = null;
    }
    
    @Override
    public Node createUserInterface() {
        this.container = new Pane();
        this.container.setMaxWidth(Double.MAX_VALUE);

        this.canvas = new Canvas(this.width, this.height);
        this.graphicsContext = canvas.getGraphicsContext2D();

        this.container.getChildren().add(this.canvas);
        
        return this.container;
    }
    
    
    @Override
    public void refresh() {
        // TODO: if the current size of the container is changed, re-create or adjust the canvas to fit the container to make this scalable
        //       then also get rid of the width and height parameters for the constructor
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
}
