/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
import fishingrodofdestiny.world.tiles.StairsUpTile;
import fishingrodofdestiny.world.tiles.Tile;
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
        LevelSettings settings = new LevelSettings();
        settings.addEnemyType(NonPlayerCharacter.class, 1 + caveLevel * 5, 0.2);
        settings.addEnemyType(NonPlayerCharacter.class, 1 + caveLevel * 5, 0.8);
        Level level = new Level(settings, this.width, this.height);

        this.createLevelBorders(level);
        
        // Rest of the level is just floor:
        for (int y = 1; y < this.height - 1; y++) {
            for (int x = 1; x < this.width - 1; x++) {
                level.setTile(x, y, new FloorTile(level, x, y));
            }
        }
        
        return level;
    }
}
