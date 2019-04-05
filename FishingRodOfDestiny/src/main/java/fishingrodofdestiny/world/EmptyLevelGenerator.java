/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.tiles.StairsUpTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.StairsTile;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.StairsDownTile;
import java.util.Random;

/**
 *
 * @author joyr
 */
public class EmptyLevelGenerator extends LevelGenerator {
    
    public EmptyLevelGenerator(Random random, int width, int height) {
        super(random, width, height);
    }

    
    @Override
    public Level generateLevel(int caveLevel) {
        Level level = new Level(this.width, this.height);

        this.createLevelBorders(level);
        
        // Rest of the level is just floor:
        for (int y = 1; y < this.height - 1; y++) {
            for (int x = 1; x < this.width - 1; x++) {
                level.setTile(x, y, new FloorTile(level));
            }
        }

        this.placeStairsUp(level);
        this.placeStairsDown(level);
        
        return level;
    }

    private void placeStairsUp(Level level) {
        int stairsUpX = -1;
        int stairsUpY = -1;
        while (true) {
            int x = this.random.nextInt(this.width);
            int y = this.random.nextInt(this.height);
            Tile t = level.getTile(x, y);
            if (t.getClass() == FloorTile.class) {
                stairsUpX = x;
                stairsUpY = y;
                level.setTile(x, y, new StairsUpTile(level));
                break;
            }
        }
    }

    private void placeStairsDown(Level level) {
        StairsTile down = new StairsDownTile(level);
        while (true) {
            int x = this.random.nextInt(this.width);
            int y = this.random.nextInt(this.height);
            Tile t = level.getTile(x, y);
            if (t.getClass() == FloorTile.class) {
                level.setTile(x, y, new StairsDownTile(level));
                break;
            }
        }
    }
    
}
