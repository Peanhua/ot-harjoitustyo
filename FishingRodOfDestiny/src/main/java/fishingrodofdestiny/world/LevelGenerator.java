/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.WallTile;
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
            GameObject item = GameObjectFactory.create(itemSettings.getNext(random, level));
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
}
