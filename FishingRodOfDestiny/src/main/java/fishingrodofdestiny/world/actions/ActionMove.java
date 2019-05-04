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
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.gameobjects.Buff;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.Inventory;
import fishingrodofdestiny.world.tiles.Tile;


/**
 *
 * @author joyr
 */
public class ActionMove extends Action {
    private final int deltaX;
    private final int deltaY;
    
    public ActionMove(int deltaX, int deltaY) {
        super(Type.MOVE);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
    

    public int getDeltaX() {
        return this.deltaX;
    }
    

    public int getDeltaY() {
        return this.deltaY;
    }


    @Override
    public void act(Character me) {
        Tile targetTile = this.getTargetTile(me);
        if (targetTile == null) {
            return;
        }
        
        if (me.getInventory().getWeight() > me.getCarryingCapacity()) {
            me.addMessage("You are carrying too much and unable to move!");
            return;
        }
        
        Tile myTile = me.getLocation().getContainerTile();
        if (!myTile.canLeave(me)) {
            return;
        }
        
        me.getLocation().moveTo(targetTile);
    }
    
    private Tile getTargetTile(Character me) {
        Tile myTile = me.getLocation().getContainerTile();
        if (myTile == null) {
            return null;
        }
        
        Level level = myTile.getLevel();
        if (level == null) {
            return null;
        }
        
        Tile targetTile = level.getTile(myTile.getX() + this.deltaX, myTile.getY() + this.deltaY);
        if (targetTile == null) {
            return null;
        }
        
        if (!targetTile.canBeEntered()) {
            return null;
        }
        
        return targetTile;
    }        
}
