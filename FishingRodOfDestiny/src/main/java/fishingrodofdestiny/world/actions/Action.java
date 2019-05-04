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
 * A single action that a Character can perform.
 * 
 * @author joyr
 */
public abstract class Action {
    public enum Type {
        WAIT,
        MOVE,
        MOVE_NORTH,
        MOVE_SOUTH,
        MOVE_WEST,
        MOVE_EAST,
        ACTIVATE_TILE,  // Tile specific action.
        ATTACK,         // Attack whoever is in the same tile.
        PICK_UP,
        DROP,
        USE,
        LEVEL_UP;
    };
    
    private final Type type;
    
    protected Action(Type type) {
        this.type = type;
    }
    
    public final Type getType() {
        return this.type;
    }
    
    /**
     * Perform the action this Action defines.
     * 
     * @param me The Character who performs this Action
     */
    public abstract void act(Character me);
}
