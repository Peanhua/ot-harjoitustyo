/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.tiles.StairsDownTile;
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
    
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return height;
    }
    
    
    public void draw(GraphicsContext context) {
        int tileSize = 16;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Tile t = this.getTile(x, y);
                if (t != null) {
                    t.draw(context, x * tileSize, y * tileSize, tileSize);
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


    public List<Tile> getTiles(Class cls) {
        List<Tile> rv = new ArrayList<>();
        
        for (int i = 0; i < this.width * this.height; i++) {
            Tile tile = this.tiles.get(i);
            if (tile.getClass() == cls) {
                rv.add(tile);
            }
        }
        
        return rv;
    }

    
    public List<StairsTile> getStairsUp() {
        // TODO: optimize a bit: keep a list of stairs going up
        List<StairsTile> stairs = new ArrayList<>();
        this.getTiles(StairsUpTile.class).forEach((tile) -> stairs.add((StairsTile) tile));
        return stairs;
    }

    
    public List<StairsTile> getStairsDown() {
        List<StairsTile> stairs = new ArrayList<>();
        this.getTiles(StairsDownTile.class).forEach((tile) -> stairs.add((StairsTile) tile));
        return stairs;
    }
    
    
    public void tick(double deltaTime) {
        // TODO: cache game objects in this level
        List<GameObject> objects = new ArrayList<>();
        this.tiles.forEach(tile -> {
            objects.addAll(tile.getInventory().getObjects());
        });

        // TODO: sort objects based on their time to execute
        
        objects.forEach(obj -> obj.tick(deltaTime));
    }
}
