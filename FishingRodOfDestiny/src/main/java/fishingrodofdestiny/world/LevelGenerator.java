/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

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
            level.setTile(x, 0, new WallTile(level));
            level.setTile(x, this.height - 1, new WallTile(level));
        }
        for (int y = 1; y < this.height - 1; y++) {
            level.setTile(0, y, new WallTile(level));
            level.setTile(this.width - 1, y, new WallTile(level));
        }
    }        
    
    public abstract Level generateLevel(int caveLevel);
}
