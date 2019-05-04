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
 *
 * @author joyr
 */
public class LevelMap {
    private final int        width;
    private final int        height;
    private final List<Tile> tiles;

    public LevelMap(int width, int height) {
        this.width  = width;
        this.height = height;
        this.tiles = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            this.tiles.add(null);
        }
    }
    

    public int getWidth() {
        return this.width;
    }
    

    public int getHeight() {
        return this.height;
    }


    public List<Tile> getTiles() {
        return this.tiles;
    }
    

    public List<Tile> getTiles(Class cls) {
        List<Tile> rv = new ArrayList<>();
        
        for (int i = 0; i < this.width * this.height; i++) {
            Tile tile = this.tiles.get(i);
            if (tile.getClass() == cls) {
                rv.add(tile);
            }
        }
        
        return rv;
    }
    

    public boolean isValidLocation(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }
        
    
    public Tile getTile(int x, int y) {
        if (!this.isValidLocation(x, y)) {
            return null;
        }
        return this.tiles.get(y * this.width + x);
    }


    public void setTile(int x, int y, Tile tile) {
        if (this.isValidLocation(x, y)) {
            this.tiles.set(y * this.width + x, tile);
        }
    }
}
