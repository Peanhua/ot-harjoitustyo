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
public class Armor extends Item {
    
    public enum Slot {
        HEAD,
        ARMOR,
        HANDS,
        RING,
        FEET;
        
        @Override
        public String toString() {
            switch (this) {
                case HEAD:  return "Head";
                case ARMOR: return "Armor";
                case HANDS: return "Hands";
                case RING:  return "Ring";
                case FEET:  return "Feet";
                default:    throw new RuntimeException("Unknown slot type: " + this);
            }
        }
        
        public static Slot nameToSlot(String name) {
            switch (name) {
                case "HEAD":  return HEAD;
                case "ARMOR": return ARMOR;
                case "HANDS": return HANDS;
                case "RING":  return RING;
                case "FEET":  return FEET;
                default:      throw new RuntimeException("Unknown name for armor slot '" + name + "'.");
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
            Armor current = me.getArmor(this.getSlot());
            if (current != null) {
                me.addMessage("You swap your " + current.getName() + " to " + this.getName() + ".");
            } else {
                me.addMessage("You equip " + this.getName() + ".");
            }
            me.setArmor(this);
        }
    }
}
