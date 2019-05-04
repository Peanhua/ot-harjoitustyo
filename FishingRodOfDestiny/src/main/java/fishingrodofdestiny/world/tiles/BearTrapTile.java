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
import fishingrodofdestiny.world.gameobjects.Character;

/**
 * A tile with a bear trap on it.
 * <p>
 * When a Character enters the tile and if the trap is activated, the Character is damaged and stuck for a while unable to move.
 * Can be reactivated with the "activate tile" action.
 * 
 * @author joyr
 */
public class BearTrapTile extends Tile {
    private boolean       active;
    private GameObject    trappedObject;
    private final TileGfx activeGfx;
    private final TileGfx inactiveGfx;
    private int           tryToLeaveCounter;
    
    public BearTrapTile(Level level, int x, int y) {
        super(level, x, y, "floor");
        this.active            = true;
        this.trappedObject     = null;
        this.tryToLeaveCounter = 0;
        
        Tile floor = new FloorTile(level, x, y);

        this.inactiveGfx = new TileGfx("rltiles/nh32", 128, 928, 32, 32);
        this.inactiveGfx.setBackground(floor.getGraphics().getNextFrame());

        this.activeGfx = new TileGfx("rltiles/nh32", 224, 1120, 32, 32);
        this.activeGfx.setBackground(floor.getGraphics().getNextFrame());

        this.setGraphics(this.activeGfx);
    }
    
    
    public GameObject getTrappedObject() {
        return this.trappedObject;
    }
    

    @Override
    public boolean canBeEntered() {
        return true;
    }
    
    @Override
    public boolean canLeave(GameObject object) {
        if (object != this.trappedObject) {
            return true;
        }
        
        if (this.tryToLeaveCounter > 0) {
            if (object.getRandom().nextInt(100 - this.tryToLeaveCounter * 10) < 20) {
                object.addMessage("You manage to free yourself from the bear trap.");
                this.trappedObject = null;
                return true;
            }
        }
        
        this.tryToLeaveCounter++;
        object.addMessage("You're trapped in a bear trap and unable to move!");
        return false;
    }
    
    @Override
    public void onEnter(GameObject object) {
        if (!this.active || this.trappedObject != null) {
            return;
        }
        
        if (!(object instanceof Character)) {
            return;
        }
        
        object.addMessage("You step into a bear trap!");
        object.hit(null, 3);

        this.active            = false;
        this.trappedObject     = object;
        this.tryToLeaveCounter = 0;
        this.setGraphics(this.inactiveGfx);
    }
    
    @Override
    public void activate(GameObject object) {
        if (this.active) {
            object.addMessage("The bear trap is already activated.");
            return;
        }
        object.addMessage("You re-activate the bear trap.");
        if (object == this.trappedObject) {
            object.addMessage("You free yourself from the bear trap.");
        }
        this.active        = true;
        this.trappedObject = null;
        this.setGraphics(this.activeGfx);
    }
}
