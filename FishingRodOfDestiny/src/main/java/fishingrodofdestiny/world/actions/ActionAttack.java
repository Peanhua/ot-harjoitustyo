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
        
        if (!me.getLocation().equals(target.getLocation())) {
            me.addMessage("You attack thin air as " + target.getCapitalizedName() + " has slipped out of your reach!");
            return;
        }
        
        double chance = me.getChanceToHit(target);
        if (me.getRandom().nextDouble() < chance) {
            this.hitTarget(me);
            
        } else {
            me.addMessage("You try to hit " + target.getName() + ", but miss!");
            target.addMessage(me.getCapitalizedName() + " tries to hit you, but misses!");
        }
    }
    
    private void hitTarget(Character me) {
        int damage = me.getDamage();
        if (target instanceof Character) {
            damage -= ((Character) target).getDamageReduction(damage);
        }
        me.addMessage("You hit " + target.getName() + " for " + damage + "!");
        target.addMessage(me.getCapitalizedName() + " hits you for " + damage + "!");
        target.hit(me, damage);
    }
}
