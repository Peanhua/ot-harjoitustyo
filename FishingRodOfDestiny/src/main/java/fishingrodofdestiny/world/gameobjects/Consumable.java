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
public class Consumable extends Item {
    private String  useVerb;
    private int     healOnUse;
    private boolean healOnUsePercentage;
    
    public Consumable(String objectType) {
        super(objectType);
        this.useVerb             = "use";
        this.healOnUse           = 0;
        this.healOnUsePercentage = false;
    }
    
    public final void setUseVerb(String useVerb) {
        this.useVerb = useVerb;
    }
    
    public final void setHealOnUse(int amount) {
        this.healOnUse = amount;
    }
    
    public final void setHealOnUsePercentage(boolean usePercentage) {
        this.healOnUsePercentage = usePercentage;
    }

    @Override
    public void useItem(GameObject instigator, GameObject user) {
        super.useItem(instigator, user);
        user.addMessage("You " + this.useVerb + " " + this.getName() + ".");
        if (this.healOnUse != 0) {
            double amount;
            if (this.healOnUsePercentage) {
                amount = (double) this.healOnUse * (double) user.getMaxHitpoints() / 100.0;
            } else {
                amount = this.healOnUse;
            }
            user.adjustHitpoints((int) amount);
        }
        this.destroy(instigator);
    }
}
