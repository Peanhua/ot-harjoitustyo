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
public abstract class Consumable extends Item {
    private final String useVerb;
    
    public Consumable(String name, String useVerb) {
        super(name);
        this.useVerb = useVerb;
    }

    @Override
    public void useItem(GameObject instigator, GameObject user) {
        super.useItem(instigator, user);
        user.addMessage("You " + this.useVerb + " " + this.getName() + ".");
        this.destroy(instigator);
    }
}
