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
public abstract class Armor extends Item {
    
    public enum Slot {
        HEAD,
        CHEST,
        LEGS,
        HANDS,
        RING;
        
        @Override
        public String toString() {
            switch (this) {
                case HEAD:  return "Head";
                case CHEST: return "Chest";
                case LEGS:  return "Legs";
                case HANDS: return "Hands";
                case RING:  return "Ring";
            }
            throw new RuntimeException("Unknown slot type: " + this);
        }
    }
    
    private final Slot slot;
    
    public Armor(String name, Slot slot) {
        super(name);
        this.slot = slot;
    }
    
    public final Slot getSlot() {
        return this.slot;
    }
    
    public int getArmorClassBonus() {
        return 1;
    }

    public int getDefenceBonus() {
        return 0;
    }
}