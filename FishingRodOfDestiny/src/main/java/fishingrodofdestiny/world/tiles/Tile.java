/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Inventory;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author joyr
 */
public abstract class Tile {
    private Level     level;
    private int       x;
    private int       y;
    private Inventory inventory;
    
    public Tile(Level level, int x, int y) {
        this.level     = level;
        this.x         = x;
        this.y         = y;
        this.inventory = new Inventory(0);
    }
    
    public final Level getLevel() {
        return this.level;
    }
    
    public final int getX() {
        return this.x;
    }
    
    public final int getY() {
        return this.y;
    }
    
    public final Inventory getInventory() {
        return this.inventory;
    }
    
    public abstract boolean canBeEntered();
    
    public abstract void draw(GraphicsContext context, int x, int y, int size);
    
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
