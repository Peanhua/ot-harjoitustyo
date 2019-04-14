/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;

/**
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
    }
}
