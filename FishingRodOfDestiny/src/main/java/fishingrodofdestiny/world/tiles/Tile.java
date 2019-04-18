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
 *
 * @author joyr
 */
public abstract class Tile {
    private Level     level;
    private int       x;
    private int       y;
    private Inventory inventory;
    private TileGfx   graphics;
    private String    name;
    
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
    
    public abstract boolean canBeEntered();
    
    public final void draw(GraphicsContext context, int x, int y, int size, boolean drawInventory) {
        this.graphics.draw(context, x, y, size);
        if (drawInventory) {
            this.drawInventory(context, x, y, size);
        }
    }
    
    private final void drawInventory(GraphicsContext context, int x, int y, int size) {
        this.inventory.getObjects().stream()
                .sorted((a, b) -> a.getDrawingOrder() - b.getDrawingOrder())
                .forEach(obj -> obj.draw(context, x, y, size));
    }

    // Called when the given object moves into this tile:
    public void onEnter(GameObject object) {
    }
    
    
    // Called when the given object wants to activate some tile specific special action in this tile:
    public void activate(GameObject object) {
    }
}
