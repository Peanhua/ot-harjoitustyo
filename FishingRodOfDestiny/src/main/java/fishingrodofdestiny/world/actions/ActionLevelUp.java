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

/**
 *
 * @author joyr
 */
public class ActionLevelUp extends Action {
    public ActionLevelUp() {
        super(Type.LEVEL_UP);
    }
    
    @Override
    public void act(Character me) {
        int currentLevel = me.getCharacterLevel();
        int xpNeeded = me.getExperiencePointsForCharacterLevel(currentLevel + 1);
        if (me.getExperiencePoints() < xpNeeded) {
            me.addMessage("You don't have enough experience points to level up.");
            return;
        }

        String increased = me.increaseCharacterLevel();
        me.addMessage("You level up to " + (currentLevel + 1) + " and gain " + increased + "!");
    }
}
