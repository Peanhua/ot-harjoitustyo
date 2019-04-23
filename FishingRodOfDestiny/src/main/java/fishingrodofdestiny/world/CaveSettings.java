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
public class CaveSettings {
    private final Random         random;
    private final LevelGenerator levelGenerator;
    
    public CaveSettings(Random random) {
        this.random = random;
        this.levelGenerator = new BSPLevelGenerator(this);
    }
    
    public Random getRandom() {
        return this.random;
    }
    
    public int getNumberOfLevels() {
        return 10;
    }
    
    public LevelGenerator getLevelGenerator(int caveLevel) {
        return this.levelGenerator;
    }
    
    public int getLevelWidth(int caveLevel) {
        return 40;
    }
    
    public int getLevelHeight(int caveLevel) {
        return 30;
    }
    
    public int getMaxPitTraps(int caveLevel) {
        return 15;
    }
    
    public int getMaxBearTraps(int caveLevel) {
        return 5;
    }
    
    public GameObjectSpawner getItemSpawner(int caveLevel) {
        GameObjectSpawner itemSettings = new GameObjectSpawner();
        itemSettings.setMaximumTotalCount(5 + caveLevel * 2 + this.random.nextInt(1 + caveLevel * 5));
        itemSettings.addType("gold coin",        3 + caveLevel * 3,                  0.7);
        itemSettings.addType("kitchen knife",    this.random.nextInt(1 + caveLevel), 0.3);
        itemSettings.addType("hat",              this.random.nextInt(3),             0.3);
        itemSettings.addType("leather clothing", 1,                                  0.2);
        itemSettings.addType("apple",            this.random.nextInt(5),             0.4);
        itemSettings.addType("leather boots",    1,                                  0.05 * caveLevel);
        itemSettings.addType("short sword",      1,                                  0.05 * caveLevel);
        itemSettings.addType("leather armor",    1,                                  0.05 * caveLevel);
        if (caveLevel > 1) {
            itemSettings.addType("potion of healing",      1, 0.1);
            itemSettings.addType("potion of regeneration", 1, 0.1);
        }
        if (caveLevel > 2) {
            itemSettings.addType("ring of regeneration", this.random.nextInt(1), 0.1 * caveLevel);
        }
        return itemSettings;
    }

    public GameObjectSpawner getEnemySpawner(int caveLevel) {
        GameObjectSpawner enemySpawner = new GameObjectSpawner();
        enemySpawner.addType("rat", this.random.nextInt(10) + this.getLevelWidth(caveLevel) * this.getLevelHeight(caveLevel) / 175, 1.0);
        return enemySpawner;
    }        
}
