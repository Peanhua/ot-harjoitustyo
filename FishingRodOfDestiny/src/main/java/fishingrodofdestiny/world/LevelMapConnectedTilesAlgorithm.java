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

import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;

/**
 * Uses depth first search to provide lists of connected tile groups.
 * 
 * @author joyr
 */
public class LevelMapConnectedTilesAlgorithm {
    private final LevelMap   map;
    private List<List<Tile>> connectedTileGroups;
    private boolean[]        visitedTiles;
    

    public LevelMapConnectedTilesAlgorithm(LevelMap map) {
        this.map                 = map;
        this.connectedTileGroups = new ArrayList<>();
        this.visitedTiles        = new boolean[this.map.getWidth() * this.map.getHeight()];
    }
    

    public List<List<Tile>> getConnectedTileGroups() {
        for (int y = 0; y < this.map.getHeight(); y++) {
            for (int x = 0; x < this.map.getWidth(); x++) {
                List<Tile> list = this.getConnectedTiles(x, y);
                if (list != null) {
                    this.connectedTileGroups.add(list);
                }
            }
        }
        
        return this.connectedTileGroups;
    }
    
    private List<Tile> getConnectedTiles(int x, int y) {
        if (this.visitedTiles[x + y * this.map.getWidth()]) {
            return null;
        }
        
        List<Tile> group = new ArrayList<>();
        this.getConnectedTilesDFS(group, x, y);
        if (group.isEmpty()) {
            return null;
        }
        return group;
    }
    
    private void getConnectedTilesDFS(List<Tile> group, int x, int y) {
        if (x < 0 || x >= this.map.getWidth() || y < 0 || y >= this.map.getHeight()) {
            return;
        }
        
        if (this.visitedTiles[x + y * this.map.getWidth()]) {
            return;
        }

        this.visitedTiles[x + y * this.map.getWidth()] = true;

        Tile tile = this.map.getTile(x, y);
        if (!tile.canBeEntered()) {
            return;
        }

        group.add(tile);
        this.getConnectedTilesDFS(group, x, y - 1);
        this.getConnectedTilesDFS(group, x, y + 1);
        this.getConnectedTilesDFS(group, x - 1, y);
        this.getConnectedTilesDFS(group, x + 1, y);
    }
}
