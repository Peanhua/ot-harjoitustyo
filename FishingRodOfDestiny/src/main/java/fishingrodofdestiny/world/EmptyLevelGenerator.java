/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

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
        
        // Borders:
        for(int x = 0; x < this.width; x++) {
            level.setTile(x, 0, new WallTile(level));
            level.setTile(x, this.height - 1, new WallTile(level));
        }
        for(int y = 1; y < this.height - 1; y++) {
            level.setTile(0, y, new WallTile(level));
            level.setTile(this.width - 1, y, new WallTile(level));
        }
        
        // Rest of the level is just floor:
        for(int y = 1; y < this.height - 1; y++)
            for(int x = 1; x < this.width - 1 ; x++)
                level.setTile(x, y, new FloorTile(level));

        // Place stairs:
        int stairsUpX = -1;
        int stairsUpY = -1;
        while(true) {
            int x = this.random.nextInt(this.width);
            int y = this.random.nextInt(this.height);
            Tile t = level.getTile(x, y);
            if(t.canBeEntered()) {
                stairsUpX = x;
                stairsUpY = y;
                level.setTile(x, y, new StairsUpTile(level));
                break;
            }
        }
        
        StairsTile down = new StairsDownTile(level);
        while(true) {
            int x = this.random.nextInt(this.width);
            int y = this.random.nextInt(this.height);
            if(x != stairsUpX || y != stairsUpY) {
                Tile t = level.getTile(x, y);
                if(t.canBeEntered()) {
                    level.setTile(x, y, new StairsDownTile(level));
                    break;
                }
            }
        }
        
        return level;
    }
    
}
