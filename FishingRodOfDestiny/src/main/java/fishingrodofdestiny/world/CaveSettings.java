/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fishingrodofdestiny.world;

import java.util.Random;

/**
 * Settings for a cave, defines the rules how to construct the cave and its levels.
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
    
    /**
     * Returns the random number generator associated to these settings.
     * 
     * @return Random number generator
     */
    public Random getRandom() {
        return this.random;
    }
    
    /**
     * Return the number of cave levels the cave has.
     * 
     * @return Number of levels
     */
    public int getNumberOfLevels() {
        return 10;
    }
    
    /**
     * Return the cave generator object used to generate the level at the given cave level (depth).
     * 
     * @param caveLevel The cave level
     * @return LevelGenerator used to generate the level
     */
    public LevelGenerator getLevelGenerator(int caveLevel) {
        return this.levelGenerator;
    }
    
    /**
     * Returns the width (number of tiles in X-axis) of the level at the given cave level.
     * 
     * @param caveLevel The cave level
     * @return Number of tiles horizontally
     */
    public int getLevelWidth(int caveLevel) {
        return 40;
    }
    
    /**
     * Returns the height (number of tiles in Y-axis) of the level at the given cave level.
     * 
     * @param caveLevel The cave level
     * @return Number of tiles vertically
     */
    public int getLevelHeight(int caveLevel) {
        return 30;
    }
    
    /**
     * Returns the maximum number of pit traps at the given cave level.
     * 
     * @param caveLevel The cave level
     * @return Maximum number of pit traps
     */
    public int getMaxPitTraps(int caveLevel) {
        return 15;
    }
    
    /**
     * Returns the maximum number of bear traps at the given cave level.
     * 
     * @param caveLevel The cave level
     * @return Maximum number of bear traps
     */
    public int getMaxBearTraps(int caveLevel) {
        return 5;
    }
    
    /**
     * Returns the number of statue tiles in the given cave level.
     * 
     * @param caveLevel The cave level
     * @return Number of statues
     */
    public int getStatues(int caveLevel) {
        if (caveLevel > 0 && caveLevel % 5 == 0) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Returns the GameObjectSpawner used to place items into the given cave level.
     * 
     * @param caveLevel The cave level
     * @return GameObjectSpawner
     */
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
        return itemSettings;
    }

    /**
     * Returns the GameObjectSpawner used to place enemies into the given cave level.
     * 
     * @param caveLevel The cave level
     * @return GameObjectSpawner
     */
    public GameObjectSpawner getEnemySpawner(int caveLevel) {
        GameObjectSpawner enemySpawner = new GameObjectSpawner();
        int levelSizeBasedCount = this.getLevelWidth(caveLevel) * this.getLevelHeight(caveLevel) / 175;
        enemySpawner.addType("rat", this.random.nextInt(10) + levelSizeBasedCount, 0.5);
        if (caveLevel > 2) {
            enemySpawner.addType("cobra", this.random.nextInt(10 + caveLevel), 0.2);
        }
        if (this.getStatues(caveLevel) > 0) {
            enemySpawner.addType("kobold", 10 + this.random.nextInt(10 + caveLevel + levelSizeBasedCount), 0.75);
        }
        enemySpawner.addType("giant spider", this.random.nextInt(10) + levelSizeBasedCount, 1.0);
        return enemySpawner;
    }        
}
