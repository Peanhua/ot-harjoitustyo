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
    
    public int getAttackBonus() {
        return 0;
    }
    
    public int getDefenceBonus() {
        return 0;
    }
}
