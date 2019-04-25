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
