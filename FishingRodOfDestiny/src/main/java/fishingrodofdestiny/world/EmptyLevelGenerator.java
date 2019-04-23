/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.tiles.FloorTile;

/**
 *
 * @author joyr
 */
public class EmptyLevelGenerator extends LevelGenerator {
    
    public EmptyLevelGenerator(CaveSettings settings) {
        super(settings);
    }

    
    @Override
    public Level generateLevel(int caveLevel) {
        GameObjectSpawner spawner = new GameObjectSpawner();
        spawner.addType("rat", 1 + caveLevel * 5, 0.2);
        spawner.addType("rat", 1 + caveLevel * 5, 0.8);
        Level level = new Level(spawner, caveLevel, this.getSettings().getLevelWidth(caveLevel), this.settings.getLevelHeight(caveLevel));

        this.createLevelBorders(level);
        
        // Rest of the level is just floor:
        for (int y = 1; y < level.getHeight() - 1; y++) {
            for (int x = 1; x < level.getWidth() - 1; x++) {
                level.setTile(x, y, new FloorTile(level, x, y));
            }
        }
        
        return level;
    }

    @Override    
    public void connectStartEnd(Level level) {
    }
}
