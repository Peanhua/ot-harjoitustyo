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
    
    public abstract Level generateLevel(int caveLevel);
}
