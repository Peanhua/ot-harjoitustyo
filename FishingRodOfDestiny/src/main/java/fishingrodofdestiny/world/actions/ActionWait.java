/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.Character;


/**
 *
 * @author joyr
 */
public class ActionWait extends Action {
    public ActionWait() {
        super(Type.WAIT);
    }
    
    
    @Override
    public void act(Character me) {
        me.addMessage("You wait.");
    }
}
