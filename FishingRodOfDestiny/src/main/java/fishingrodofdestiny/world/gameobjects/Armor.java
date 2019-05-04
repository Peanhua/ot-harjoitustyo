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
 * Wearable, and usually protective, armor.
 * 
 * @author joyr
 */
public class Armor extends Item {
    
    public enum Slot {
        HEAD,
        ARMOR,
        HANDS,
        RING,
        FEET;

        /**
         * Returns a displayable name for this slot.
         * 
         * @return A displayable name
         */
        public String getDisplayName() {
            switch (this) {
                case HEAD:  return "Head";
                case ARMOR: return "Armor";
                case HANDS: return "Hands";
                case RING:  return "Ring";
                case FEET:  return "Feet";
                default:    throw new RuntimeException("Unknown slot type: " + this);
            }
        }
    }
    
    private Slot slot;
    
    public Armor(String objectType) {
        super(objectType);
        this.slot = null;
    }
    
    public final Slot getSlot() {
        return this.slot;
    }
    
    public final void setSlot(Slot slot) {
        this.slot = slot;
    }
    
    
    @Override    
    public void useItem(GameObject instigator, GameObject user) {
        super.useItem(instigator, user);
        if (user instanceof Character) {
            Character me = (Character) user;
            Armor current = me.getEquipment().getArmor(this.getSlot());
            if (current != null) {
                me.addMessage("You swap your " + current.getName() + " to " + this.getName() + ".");
            } else {
                me.addMessage("You equip " + this.getName() + ".");
            }
            me.getEquipment().setArmor(this);
        }
    }
}
