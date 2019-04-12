/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.StairsDownTile;
import fishingrodofdestiny.world.tiles.StairsUpTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.StairsTile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author joyr
 */
public class Level {
    
    private LevelSettings settings;
    private int           width;
    private int           height;
    private List<Tile>    tiles;
    
    public Level(LevelSettings settings, int width, int height) {
        this.settings = settings;
        this.width    = width;
        this.height   = height;
        
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
    
    
    public void draw(GraphicsContext context, int tileSize, int topLeftX, int topLeftY, int maxWidth, int maxHeight) {
        for (int y = 0; topLeftY + y < this.height; y++) {
            for (int x = 0; topLeftX + x < this.width; x++) {
                Tile t = this.getTile(topLeftX + x, topLeftY + y);
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
    

    public Tile getRandomTileOfType(Random random, Class type) {
        return this.getRandomTileOfTypeInArea(random, type, 0, 0, this.width, this.height);
    }
    
    public Tile getRandomTileOfTypeInArea(Random random, Class type, int topleftX, int topleftY, int areaWidth, int areaHeight) {
        areaWidth  = Math.min(areaWidth,  this.width - topleftX);
        areaHeight = Math.min(areaHeight, this.height - topleftY);
        if (areaWidth <= 0 || areaHeight <= 0) {
            return null;
        }
        for (int i = 0; i < areaWidth * areaHeight; i++) {
            int x = topleftX + random.nextInt(areaWidth);
            int y = topleftY + random.nextInt(areaHeight);
            Tile t = this.getTile(x, y);
            if (t.getClass() == type) {
                return t;
            }
        }
        return null;
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
    
    
    public int getObjectCount(Class type) {
        // TODO: cache the most frequently queried types (all variations of NonPlayerCharacters)
        int count = 0;
        
        for (Tile tile : this.tiles) {
            for (GameObject object : tile.getInventory().getObjects()) {
                if (object.getClass() == type) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /*
    * Spawn a new NPC based on settings for this level.
    */
    public NonPlayerCharacter spawnNPC(Random random) {
        // Generate the NPC:
        Class type = this.settings.getEnemyForLevel(random, this);
        NonPlayerCharacter npc = null;
        if (type == NonPlayerCharacter.class) {
            npc = new NonPlayerCharacter();
        }
        if (npc == null) {
            return null;
        }
        // Place the NPC appropriately:
        Tile tile = this.getRandomTileOfType(random, FloorTile.class);
        if (tile == null) {
            return null;
        }
        npc.getLocation().moveTo(tile);
        return npc;
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
    
    
    @Override
    public String toString() {
        String rv = "";
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Tile t = this.getTile(x, y);
                if (t == null) {
                    rv += "X";
                } else if (t.getClass() == FloorTile.class) {
                    rv += ".";
                } else {
                    rv += " ";
                }
            }
            rv += "\n";
        }
        return rv;
    }
    
    
    private List<List<Tile>> connectedTileGroups;
    private boolean[]        visitedTiles;
    private List<Tile>       currentConnectedTileGroup;
    
    public List<List<Tile>> getConnectedTileGroups() {
        this.connectedTileGroups = new ArrayList<>();
        this.visitedTiles = new boolean[this.width * this.height];
        
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                List<Tile> list = this.getConnectedTiles(x, y);
                if (list != null) {
                    this.connectedTileGroups.add(list);
                }
            }
        }
        
        return this.connectedTileGroups;
    }
    
    public List<Tile> getConnectedTiles(int x, int y) {
        if (this.visitedTiles[x + y * this.width]) {
            return null;
        }
        
        this.currentConnectedTileGroup = new ArrayList<>();
        this.getConnectedTilesDFS(x, y);
        if (this.currentConnectedTileGroup.size() == 0) {
            return null;
        }
        return this.currentConnectedTileGroup;
    }
    
    private void getConnectedTilesDFS(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return;
        }
        
        if (this.visitedTiles[x + y * this.width]) {
            return;
        }

        this.visitedTiles[x + y * this.width] = true;

        Tile tile = this.getTile(x, y);
        if (!tile.canBeEntered()) {
            return;
        }

        this.currentConnectedTileGroup.add(tile);
        this.getConnectedTilesDFS(x, y - 1);
        this.getConnectedTilesDFS(x, y + 1);
        this.getConnectedTilesDFS(x - 1, y);
        this.getConnectedTilesDFS(x + 1, y);
    }
}
