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

}
