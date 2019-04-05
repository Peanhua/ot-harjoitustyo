/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.gameobjects.GameObject;
import fishingrodofdestiny.gameobjects.Inventory;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author joyr
 */
public abstract class Tile {
    private Level     inLevel;
    private Inventory inventory;
    
    public Tile(Level inLevel) {
        this.inLevel   = inLevel;
        this.inventory = new Inventory(0);
    }
    
    public final Level getInLevel() {
        return this.inLevel;
    }
    
    public final Inventory getInventory() {
        return this.inventory;
    }
    
    public abstract boolean canBeEntered();
    public abstract void    draw(GraphicsContext context, int x, int y, int size);
    
    public void drawInventory(GraphicsContext context, int x, int y, int size) {
        for (GameObject obj : this.inventory.getObjects()) {
            if (obj != null) {
                obj.draw(context, x, y, size);
            }
        }
    }
    
    public void onEnter(GameObject object) {
    }
}
