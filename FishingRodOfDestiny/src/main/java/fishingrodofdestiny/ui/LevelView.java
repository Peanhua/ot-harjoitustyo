/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import fishingrodofdestiny.world.Level;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author joyr
 */
public class LevelView extends Widget {
    private Canvas          canvas;
    private GraphicsContext graphicsContext;
    private int             width;
    private int             height;
    private Level           level;
    
    public LevelView() {
        this.width  = 500;
        this.height = 400;
        this.level  = null;
    }
    
    @Override
    public Node createUserInterface() {
        this.canvas = new Canvas(this.width, this.height);
        this.graphicsContext = canvas.getGraphicsContext2D();
        
        return this.canvas;
    }
    
    
    @Override
    public void refresh() {
        this.graphicsContext.setFill(Color.RED);
        //this.graphicsContext.clearRect(0, 0, this.width, this.height);
        this.graphicsContext.fillRect(0, 0, this.width, this.height);
        
        if(this.level != null)
            this.level.draw(this.graphicsContext);
    }
    
    
    public void setLevel(Level level) {
        this.level = level;
        this.refresh();
    }
}
