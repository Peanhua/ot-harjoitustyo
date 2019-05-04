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

import fishingrodofdestiny.world.tiles.FloorTile;

/**
 * Simple level generator, creates an empty level with borders.
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
