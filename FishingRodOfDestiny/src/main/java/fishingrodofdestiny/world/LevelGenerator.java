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

import fishingrodofdestiny.world.tiles.StairsTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.WallTile;
import java.util.List;

/**
 * Level generator, used to generate cave levels.
 * 
 * @author joyr
 */
public abstract class LevelGenerator {
    CaveSettings settings;
    
    public LevelGenerator(CaveSettings settings) {
        this.settings = settings;
    }
    
    
    protected final CaveSettings getSettings() {
        return this.settings;
    }
    

    /**
     * Place wall tiles around the edges of the level.
     * 
     * @param level Level to receive the borders
     */
    protected void createLevelBorders(Level level) {
        for (int x = 0; x < level.getWidth(); x++) {
            int y = 0;
            level.setTile(x, y, new WallTile(level, x, y));
            y = level.getHeight() - 1;
            level.setTile(x, y, new WallTile(level, x, y));
        }
        
        for (int y = 1; y < level.getHeight() - 1; y++) {
            int x = 0;
            level.setTile(x, y, new WallTile(level, x, y));
            x = level.getWidth() - 1;
            level.setTile(x, y, new WallTile(level, x, y));
        }
    }

    /**
     * Place default wall tiles in locations where there is not yet any tile set.
     * 
     * @param level The level to operate on
     */
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
    
    

    /**
     * Generate a new cave level.
     * 
     * @param caveLevel The depth of the level to generate
     * @return A new cave level
     */
    public abstract Level generateLevel(int caveLevel);
    
    /**
     * Make sure there is a path between the start (stairs up) and end (stairs down).
     * 
     * @param level The level to operate on
     */
    public abstract void  connectStartEnd(Level level);
    
    /**
     * Find out areas that can't be accessed and fill them with something.
     * 
     * @param level Level to process.
     */
    public void fillUnusedSpace(Level level) {
        Tile stairs = this.getStairsUpOrDown(level); // Doesn't matter which one.
        
        LevelMapConnectedTilesAlgorithm cta = new LevelMapConnectedTilesAlgorithm(level.getMap());
        List<List<Tile>> connectedTileGroups = cta.getConnectedTileGroups();
  
        connectedTileGroups.forEach(group -> {
            if (!group.contains(stairs)) {
                group.forEach(tile -> level.setTile(tile.getX(), tile.getY(), new WallTile(level, tile.getX(), tile.getY())));
            }
        });
    }
    
    private Tile getStairsUpOrDown(Level level) {
        List<StairsTile> stairs = level.getStairsUp();
        if (stairs.isEmpty()) {
            stairs = level.getStairsDown();
        }
        if (stairs.isEmpty()) {
            throw new RuntimeException("No stairs found for level " + level);
        }
        return stairs.get(0);
    }
}
