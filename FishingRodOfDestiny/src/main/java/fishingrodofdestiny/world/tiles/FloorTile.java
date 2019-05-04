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
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.TileGfx;

/**
 * A simple floor tile.
 * 
 * @author joyr
 */
public class FloorTile extends Tile {

    public FloorTile(Level level, int x, int y) {
        super(level, x, y, "floor");
        this.setGraphics(new TileGfx("rltiles/nh32", 256, 896, 32, 32));
    }
    
    @Override
    public boolean canBeEntered() {
        return true;
    }
}
