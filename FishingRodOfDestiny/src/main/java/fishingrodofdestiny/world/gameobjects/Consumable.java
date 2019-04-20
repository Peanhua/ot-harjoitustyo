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
    private String useVerb;
    private int    healOnUse;
    
    public Consumable(String name) {
        super(name);
        this.useVerb   = "use";
        this.healOnUse = 0;
    }
    
    public final void setUseVerb(String useVerb) {
        this.useVerb = useVerb;
    }
    
    public final void setHealOnUse(int amount) {
        this.healOnUse = amount;
    }

    @Override
    public void useItem(GameObject instigator, GameObject user) {
        super.useItem(instigator, user);
        user.addMessage("You " + this.useVerb + " " + this.getName() + ".");
        if (this.healOnUse != 0) {
            user.adjustHitpoints(this.healOnUse);
        }
        this.destroy(instigator);
    }
}
