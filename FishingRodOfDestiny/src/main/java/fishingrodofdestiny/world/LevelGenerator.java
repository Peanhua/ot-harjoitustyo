/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.StairsTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.WallTile;
import java.util.List;
import java.util.Random;

/**
 *
 * @author joyr
 */
public abstract class LevelGenerator {
    protected Random random;
    protected int    width;
    protected int    height;
    
    public LevelGenerator(Random random, int width, int height) {
        this.random = random;
        this.width  = width;
        this.height = height;
    }
    

    protected void createLevelBorders(Level level) {
        for (int x = 0; x < this.width; x++) {
            int y = 0;
            level.setTile(x, y, new WallTile(level, x, y));
            y = this.height - 1;
            level.setTile(x, y, new WallTile(level, x, y));
        }
        
        for (int y = 1; y < this.height - 1; y++) {
            int x = 0;
            level.setTile(x, y, new WallTile(level, x, y));
            x = this.width - 1;
            level.setTile(x, y, new WallTile(level, x, y));
        }
    }


    protected void fillEmptySpace(Level level) {
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                Tile t = level.getTile(x, y);
                if (t == null) {
                    level.setTile(x, y, new WallTile(level, x, y));
                }
            }
        }
    }
    
    
    public void placeItems(GameObjectSpawner itemSettings, Level level) {
        while (true) {
            GameObject item = GameObjectFactory.create(itemSettings.getNextObjectType(random, level));

            if (item == null) {
                break;
            }

            Tile tile = level.getRandomTileOfType(random, FloorTile.class);
            if (tile != null) {
                item.getLocation().moveTo(tile);
            }
        }
    }

    
    public abstract Level generateLevel(int caveLevel);
    public abstract void  connectStartEnd(Level level);
    
    /**
     * Find out areas that can't be accessed and fill them with something.
     * 
     * @param level Level to process.
     */
    public void fillUnusedSpace(Level level) {
        Tile stairs = this.getStairsUpOrDown(level); // Doesn't matter which one.
        
        LevelMapConnectedTilesAlgorithm cta = new LevelMapConnectedTilesAlgorithm(level.getMap());
        List<List<Tile>> connectedTileGroups = cta.getConnectedTileGroups();
  
        connectedTileGroups.forEach(group -> {
            if (!group.contains(stairs)) {
                group.forEach(tile -> level.setTile(tile.getX(), tile.getY(), new WallTile(level, tile.getX(), tile.getY())));
            }
        });
    }
    
    private Tile getStairsUpOrDown(Level level) {
        List<StairsTile> stairs = level.getStairsUp();
        if (stairs.isEmpty()) {
            stairs = level.getStairsDown();
        }
        if (stairs.isEmpty()) {
            throw new RuntimeException("No stairs found for level " + level);
        }
        return stairs.get(0);
    }
}
