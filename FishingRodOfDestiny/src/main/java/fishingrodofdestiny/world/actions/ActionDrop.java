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

import fishingrodofdestiny.world.gameobjects.Armor;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.tiles.Tile;

/**
 * Action to drop the given item.
 * 
 * @author joyr
 */
public class ActionDrop extends Action {
    private final GameObject target;
    
    public ActionDrop(GameObject target) {
        super(Type.DROP);
        this.target = target;
    }
    
    @Override
    public void act(Character me) {
        if (target == null) {
            me.addMessage("You drop nothing.");
            return;
        }
        
        if (!target.isAlive() || target.getLocation().getContainerObject() != me) {
            me.addMessage("You are unable to drop " + target.getName() + " because you don't have it!");
            return;
        }

        this.stopUsing(me);
        this.dropItem(me);
    }
    
    private void stopUsing(Character me) {
        if (this.target == me.getWeapon()) {
            me.addMessage("You unwield your " + this.target.getName() + ".");
            me.setWeapon(null);
        }
        for (Armor.Slot slot : Armor.Slot.values()) {
            GameObject armor = me.getArmor(slot);
            if (armor == this.target) {
                me.addMessage("You stop wearing " + this.target.getName() + ".");
                me.removeArmor(slot);
            }
        }
    }
    
    private void dropItem(Character me) {
        me.addMessage("You drop " + target.getName() + ".");
        Tile myTile = me.getLocation().getContainerTile();
        if (myTile != null) {
            target.getLocation().moveTo(myTile);
        } else {
            target.getLocation().moveTo(me.getLocation().getContainerObject());
        }
    }
}
