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

import fishingrodofdestiny.world.gameobjects.Buff;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Character;

/**
 * Action to attack the given target.
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
        
        if (target instanceof Character) {
            Buff buff = me.getRandomAttackBuff();
            if (buff != null) {
                ((Character) target).addBuff(new Buff(buff));
            }
        }
    }
}
