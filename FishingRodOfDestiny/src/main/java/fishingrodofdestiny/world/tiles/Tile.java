/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.resources.ImageCache;
import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Inventory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author joyr
 */
public abstract class Tile {
    private Level     level;
    private int       x;
    private int       y;
    private Inventory inventory;
    private Image     onScreenImage;
    
    public Tile(Level level, int x, int y, String gfxFilename) {
        this.level     = level;
        this.x         = x;
        this.y         = y;
        this.inventory = new Inventory(0);
        this.onScreenImage = ImageCache.getInstance().get(gfxFilename);
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
    
    public final void draw(GraphicsContext context, int x, int y, int size) {
        if (this.onScreenImage != null) {
            context.drawImage(this.onScreenImage, x, y, size, size);
        }
        this.drawInventory(context, x, y, size);
    }
    
    private final void drawInventory(GraphicsContext context, int x, int y, int size) {
        for (GameObject obj : this.inventory.getObjects()) {
            if (obj != null) {
                obj.draw(context, x, y, size);
            }
        }
    }

    // Called when the given object moves into this tile:
    public void onEnter(GameObject object) {
    }
    
    
    // Called when the given object wants to activate some tile specific special action in this tile:
    public void activate(GameObject object) {
    }
}
