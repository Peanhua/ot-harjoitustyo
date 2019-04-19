/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Inventory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The base class for all tile classes.
 * <p>
 * Each tile represents a location on a level.
 * 
 * @author joyr
 */
public abstract class Tile {
    private final Level     level;
    private final int       x;
    private final int       y;
    private final Inventory inventory;
    private TileGfx         graphics;
    private String          name;

    /**
     * Create a new tile bound to the given level and location.
     * 
     * @param level The level this tile is bound to.
     * @param x     The X coordinate in the level.
     * @param y     The Y coordinate in the level.
     * @param name  The name of this tile.
     */
    public Tile(Level level, int x, int y, String name) {
        this.level     = level;
        this.x         = x;
        this.y         = y;
        this.inventory = new Inventory(0);
        this.graphics  = null;
        this.name      = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    protected void setName(String name) {
        this.name = name;
    }
    
    public TileGfx getGraphics() {
        return this.graphics;
    }
    
    protected final void setGraphics(TileGfx graphics) {
        this.graphics = graphics;
    }

    /**
     * Change the background image for graphics.
     * 
     * @param background The new background graphics.
     */
    protected final void setGraphicsBackground(Image background) {
        this.graphics.setBackground(background);
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
    
    /**
     * Returns true if this tile be entered by a movable GameObject.
     * 
     * @return True if the tile can be entered.
     */
    public abstract boolean canBeEntered();

    /**
     * Checks if the given object can move away from this tile.
     * <p>
     * This is only called when trying to move away from this tile, so it is safe to do things like object.addMessage().
     * 
     * @param object The object trying to leave this tile.
     * @return True if the object can leave this tile.
     */
    public boolean canLeave(GameObject object) {
        return true;
    }
    
    /**
     * Draw the tile, and optionally its inventory.
     * 
     * @param context       The context which the tile is drawn onto.
     * @param x             The X coordinate on the context to draw onto.
     * @param y             The Y coordinate on the context to draw onto.
     * @param size          The target size (width and height).
     * @param drawInventory If true, the inventory is drawn on top of the tile.
     */
    public final void draw(GraphicsContext context, int x, int y, int size, boolean drawInventory) {
        this.graphics.draw(context, x, y, size);
        if (drawInventory) {
            this.drawInventory(context, x, y, size);
        }
    }
    
    private void drawInventory(GraphicsContext context, int x, int y, int size) {
        this.inventory.getObjects().stream()
                .sorted((a, b) -> a.getDrawingOrder() - b.getDrawingOrder())
                .forEach(obj -> obj.draw(context, x, y, size));
    }

    /**
     * Called when the given object moves into this tile:
     * 
     * @param object The GameObject that enters this tile.
     */
    public void onEnter(GameObject object) {
    }
    
    
    /**
     * Called when the given object wants to activate some tile specific special action in this tile:
     * 
     * @param object The GameObject that activates this tile.
     */
    public void activate(GameObject object) {
    }
}
