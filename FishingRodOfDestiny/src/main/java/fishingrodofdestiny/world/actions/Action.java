/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Character;

/**
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
        LEVEL_UP
    };
    
    private final Type type;
    
    protected Action(Type type) {
        this.type = type;
    }
    
    public final Type getType() {
        return this.type;
    }
    
    public abstract void act(Character me);
}
