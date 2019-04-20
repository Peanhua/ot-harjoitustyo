/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

/**
 *
 * @author joyr
 */
public abstract class Weapon extends Item {
    
    public Weapon(String name) {
        super(name);
    }
    
    public abstract int getDamage();
    
    public double getChanceToHitMultiplier() {
        return 1.0;
    }
    
    @Override    
    public void useItem(GameObject instigator, GameObject user) {
        super.useItem(instigator, user);
        if (user instanceof Character) {
            Character me = (Character) user;
            Weapon current = me.getWeapon();
            if (current != null) {
                me.addMessage("You swap your " + current.getName() + " to " + this.getName() + ".");
            } else {
                me.addMessage("You wield " + this.getName() + ".");
            }
            me.setWeapon(this);
        }
    }
}
