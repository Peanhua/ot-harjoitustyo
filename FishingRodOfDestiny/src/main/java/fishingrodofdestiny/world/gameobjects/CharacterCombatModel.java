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

import java.util.ArrayList;
import java.util.List;

/**
 * Manage the calculations for the combat system.
 * 
 * @author Joni Yrjänä <joniyrjana@gmail.com>
 */
public class CharacterCombatModel {
    private final Character owner;
    private final List<Double> attackBuffChances;
    private final List<Buff>   attackBuffs;
    
    public CharacterCombatModel(Character owner) {
        this.owner             = owner;
        this.attackBuffChances = new ArrayList<>();
        this.attackBuffs       = new ArrayList<>();
    }
    
    
    /**
     * Calculate and return the damage this character does per hit.
     * 
     * @return amount of damage per hit
     */
    public int getDamage() {
        int damage = 1;
        
        damage += this.owner.getAttack() / 3;
        
        if (this.owner.getEquipment().getWeapon() != null) {
            damage += this.owner.getEquipment().getWeapon().getDamage();
        }
        
        return damage;
    }
    
    
    /**
     * Calculate and return the chance to hit the target.
     * 
     * @param target At who the hit is aimed to.
     * 
     * @return chance in the range of [0..1]
     */
    public double getChanceToHit(GameObject target) {
        if (target instanceof Character) {
            Character targetCharacter = (Character) target;
            double def = targetCharacter.getDefence();
            if (def > 0.0) {
                // Clamp the minimum chance to 5%:
                double chance = Math.max(0.05, (double) this.owner.getAttack() / def);
                
                if (this.owner.getEquipment().getWeapon() != null) {
                    chance *= this.owner.getEquipment().getWeapon().getChanceToHitMultiplier();
                }
                
                return chance;
            }
        }
        return 1.0;
    }
    
    
    /**
     * Calculate how much the damage is reduced based on armor class and other factors.
     * 
     * @param damage The damage before reduction.
     * @return The damage that should be subtracted prior calling this.hit().
     */
    public int getDamageReduction(int damage) {
        int ac = Math.min(100, this.owner.getArmorClass()); // clamp max ac
        double acModifier = (double) ac * 0.01;
        double damageReduction = (double) damage * 0.9 * acModifier; // not all damage can be reduced by armor class
        return (int) damageReduction;
    }
    
    
    /**
     * Add a buff that can be randomly given to the attack target when attacking.
     * 
     * @param chance The chance for the buff to be given, in range [0..1]
     * @param buff   The buff to give
     */
    public void addAttackBuff(double chance, Buff buff) {
        this.attackBuffChances.add(chance);
        this.attackBuffs.add(buff);
    }
    
    /**
     * Return a random attack buff.
     * 
     * @return A random attack buff
     */
    public Buff getRandomAttackBuff() {
        for (int i = 0; i < this.attackBuffs.size(); i++) {
            if (this.owner.getRandom().nextDouble() < this.attackBuffChances.get(i)) {
                return this.attackBuffs.get(i);
            }
        }
        return null;
    }
}
