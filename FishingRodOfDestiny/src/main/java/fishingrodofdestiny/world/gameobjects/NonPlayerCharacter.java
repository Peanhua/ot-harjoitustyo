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
public class NonPlayerCharacter extends Character {
    public NonPlayerCharacter() {
        super("NPC");
        this.setController(new SimpleAiController(this));
        this.setName("monster");
    }
    
    @Override
    public String toString() {
        return "NonPlayerCharacter(" + super.toString() + ")";
    }

    @Override
    protected boolean isValidAttackTarget(GameObject target) {
        if (! super.isValidAttackTarget(target)) {
            return false;
        }
        
        return target instanceof Player;
    }

    @Override
    public void addMessage(String message) {
        // Do nothing here, because NPCs don't have any use for messages.
    }
}
