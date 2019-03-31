/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.gameobjects.GameObject;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author joyr
 */
public abstract class Tile {
    private Level inLevel;
    
    public Tile(Level inLevel) {
        this.inLevel = inLevel;
    }
    
    public final Level getInLevel() {
        return this.inLevel;
    }
    
    public abstract boolean canBeEntered();
    public abstract void    draw(GraphicsContext context, int x, int y, int size);
    
    public void onEnter(GameObject object) {
    }
}
