/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Weapon;

/**
 *
 * @author joyr
 */
public class ActionUse extends Action {
    private final GameObject item;

    public ActionUse(GameObject item) {
        super(Action.Type.USE);
        this.item = item;
    }

    @Override
    public void act(Character me) {
        if (this.item == null) {
            me.addMessage("You use nothing.");
            return;
        }
        
        if (this.item.getLocation().getContainerObject() != me) {
            me.addMessage("You don't have " + this.item.getName());
            return;
        }
        
        if (this.item instanceof Weapon) {
            this.wieldWeapon(me, (Weapon) this.item);
        } else {
            me.addMessage("You don't know what to do with " + this.item.getName() + ".");
        }
    }
    
    private void wieldWeapon(Character me, Weapon weapon) {
        Weapon current = me.getWeapon();
        if (current != null) {
            me.addMessage("You swap your " + current.getName() + " to " + weapon.getName() + ".");
        } else {
            me.addMessage("You wield " + weapon.getName() + ".");
        }
        me.setWeapon(weapon);
    }
}
