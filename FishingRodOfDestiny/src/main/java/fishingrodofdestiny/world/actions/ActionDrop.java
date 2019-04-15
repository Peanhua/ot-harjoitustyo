/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.tiles.Tile;

/**
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
            me.addMessage("You are unable to pick up " + target.getName() + " because you don't have it!");
            return;
        }

        me.addMessage("You drop " + target.getName() + ".");
        Tile myTile = me.getLocation().getContainerTile();
        if (myTile != null) {
            target.getLocation().moveTo(myTile);
        } else {
            target.getLocation().moveTo(me.getLocation().getContainerObject());
        }
    }
}
