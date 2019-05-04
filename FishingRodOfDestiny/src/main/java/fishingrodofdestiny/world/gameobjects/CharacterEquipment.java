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

import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
import java.util.HashMap;

/**
 * Class to manage equipment held by a Character.
 * 
 * @author Joni Yrjänä <joniyrjana@gmail.com>
 */
public class CharacterEquipment {
    private final HashMap<Armor.Slot, Armor> armor;
    private Weapon                           weapon;
    private final Subject                    onChange;
    
    public CharacterEquipment() {
        this.weapon   = null;
        this.armor    = new HashMap<>();
        this.onChange = new Subject();
    }
    
    
    /**
     * Register an observer to be called whenever the equipment is changed.
     *
     * @param observer The observer object to be called upon change.
     */
    public final void listenOnChange(Observer observer) {
        this.onChange.addObserver(observer);
    }
    
    /**
     * Set the currently wield weapon.
     * 
     * @param weapon The weapon to wield
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.onChange.notifyObservers();
    }
    
    /**
     * Return the currently wield weapon.
     * 
     * @return The currently wield weapon
     */
    public Weapon getWeapon() {
        return this.weapon;
    }
    
    /**
     * Equip the given armor.
     * 
     * @param armor The armor to equip
     */
    public void setArmor(Armor armor) {
        this.armor.put(armor.getSlot(), armor);
        this.onChange.notifyObservers();
    }
    
    /**
     * Un-equip armor from the given slot.
     * 
     * @param fromSlot The slot from where to remove the armor
     */
    public void removeArmor(Armor.Slot fromSlot) {
        this.armor.remove(fromSlot);
        this.onChange.notifyObservers();
    }
    
    /**
     * Return the armor worn at the given slot.
     * 
     * @param slot The slot to check
     * @return Armor in the slot, or null
     */
    public Armor getArmor(Armor.Slot slot) {
        return this.armor.get(slot);
    }
}
