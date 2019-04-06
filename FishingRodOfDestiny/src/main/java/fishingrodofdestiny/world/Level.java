/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.tiles.StairsUpTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.StairsTile;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author joyr
 */
public class Level {
    
    private int        width;
    private int        height;
    private List<Tile> tiles;
    
    public Level(int width, int height) {
        this.width  = width;
        this.height = height;
        
        this.tiles = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            this.tiles.add(null);
        }
    }
    
    public void draw(GraphicsContext context) {
        int tileSize = 16;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Tile t = this.getTile(x, y);
                if (t != null) {
                    t.draw(context, x * tileSize, y * tileSize, tileSize);
                    t.drawInventory(context, x * tileSize, y * tileSize, tileSize);
                }
            }
        }
    }
    
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= this.width) {
            return null;
        }
        if (y < 0 || y >= this.height) {
            return null;
        }
        
        return this.tiles.get(y * this.width + x);
    }
    
    public void setTile(int x, int y, Tile tile) {
        this.tiles.set(y * this.width + x, tile);
    }
    
    public List<StairsTile> getStairsUp() {
        // TODO: optimize a bit: keep a list of stairs going up
        List<StairsTile> stairs = new ArrayList<>();
        
        for (int i = 0; i < this.width * this.height; i++) {
            try { // TODO: maybe this could be done smarter?
                StairsUpTile tmp = (StairsUpTile) this.tiles.get(i);
                if (tmp != null) {
                    stairs.add(tmp);
                }
            } catch (Exception e) {
            }
        }
        
        return stairs;
    }
}
