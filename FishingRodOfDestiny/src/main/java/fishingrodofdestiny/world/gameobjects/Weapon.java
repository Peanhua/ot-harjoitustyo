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
public class Weapon extends Item {
    private int    damage;
    private double chanceToHitMultiplier;
    
    public Weapon(String objectType) {
        super(objectType);
        this.damage                = 1;
        this.chanceToHitMultiplier = 1.0;
    }
    
    public final int getDamage() {
        return this.damage;
    }
    
    public final void setDamage(int damage) {
        this.damage = damage;
    }
    
    public final double getChanceToHitMultiplier() {
        return this.chanceToHitMultiplier;
    }
    
    public final void setChanceToHitMultiplier(double multiplier) {
        this.chanceToHitMultiplier = multiplier;
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
