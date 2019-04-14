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
public class ActionAttack extends Action {
    private final GameObject target;
    
    public ActionAttack(GameObject target) {
        super(Type.ATTACK);
        this.target = target;
    }


    @Override
    public void act(Character me) {
        if (target == null) {
            me.addMessage("You attack thin air!");
            return;
        }
        
        int damage = me.getDamage();
        me.addMessage("You hit " + target.getName() + " for " + damage + "!");
        target.addMessage(me.getCapitalizedName() + " hits you for " + damage + "!");
        target.hit(me, damage);
    }
}
