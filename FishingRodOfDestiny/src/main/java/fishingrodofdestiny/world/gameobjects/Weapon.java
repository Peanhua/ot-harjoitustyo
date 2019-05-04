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
            Weapon current = me.getEquipment().getWeapon();
            if (current != null) {
                me.addMessage("You swap your " + current.getName() + " to " + this.getName() + ".");
            } else {
                me.addMessage("You wield " + this.getName() + ".");
            }
            me.getEquipment().setWeapon(this);
        }
    }
}
