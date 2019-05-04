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

/**
 * Action to pick up the given target item.
 * 
 * @author joyr
 */
public class ActionPickUp extends Action {
    private final GameObject target;
    
    public ActionPickUp(GameObject target) {
        super(Type.PICK_UP);
        this.target = target;
    }
    
    @Override
    public void act(Character me) {
        if (target == null) {
            me.addMessage("You pick up nothing.");
            return;
        }
        
        if (!target.isAlive() || !target.getLocation().equals(me.getLocation())) {
            me.addMessage("You are unable to pick up " + target.getName() + " because it is not here!");
            return;
        }

        me.addMessage("You pick up " + target.getName() + ".");
        target.getLocation().moveTo(me);
    }
}
