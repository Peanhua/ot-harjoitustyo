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

import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Item;

/**
 * Action to use an item from inventory.
 * 
 * @author joyr
 */
public class ActionUse extends Action {
    private final GameObject item;

    public ActionUse(GameObject item) {
        super(Action.Type.USE);
        this.item = item;
    }

    @Override
    public void act(Character me) {
        if (this.item == null) {
            me.addMessage("You use nothing.");
            return;
        }
        
        if (this.item.getLocation().getContainerObject() != me) {
            me.addMessage("You don't have " + this.item.getName());
            return;
        }
        
        if (this.item instanceof Item) {
            ((Item) this.item).useItem(me, me);
        } else {
            me.addMessage("You don't know what to do with " + this.item.getName() + ".");
        }
    }
}
