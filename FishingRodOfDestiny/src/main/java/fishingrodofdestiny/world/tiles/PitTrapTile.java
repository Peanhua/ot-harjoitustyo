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
import fishingrodofdestiny.world.gameobjects.GameObject;

/**
 * A tile with pit trap on it. Connected to a tile on a level below.
 * <p>
 * When a Character enters the tile, the trap is opened and the Character falls down to the level below onto the connected tile.
 * 
 * @author joyr
 */
public class PitTrapTile extends Tile {
    private boolean opened;
    private Tile    target;
    
    public PitTrapTile(Level level, int x, int y) {
        super(level, x, y, "floor");
        this.opened = false;
        this.target = null;
        
        Tile floor = new FloorTile(level, x, y);
        this.setGraphics(floor.getGraphics());
    }
    
    public void setTarget(Tile target) {
        this.target = target;
    }

    @Override
    public boolean canBeEntered() {
        return true;
    }
    
    @Override
    public void onEnter(GameObject object) {
        if (this.target == null) {
            return;
        }
        if (!this.opened) {
            object.addMessage("The floor beneath you opens and you fall down!");
            this.openTrap();
        } else {
            object.addMessage("You fall down through the hole.");
        }
        
        object.getLocation().moveTo(this.target);
        
        int damage = object.getFallDamage(5);
        if (damage > 0) {
            object.hit(null, 1);
        }
    }
    
    private void openTrap() {
        this.opened = true;
        this.setName("pit");
        TileGfx background = this.getGraphics();
        TileGfx gfx = new TileGfx("rltiles/nh32", 384, 928, 32, 32);
        gfx.setBackground(background.getNextFrame());
        this.setGraphics(gfx);
    }
}
