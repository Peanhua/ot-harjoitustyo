/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.Character;

/**
 *
 * @author joyr
 */
public class ActionLevelUp extends Action {
    public ActionLevelUp() {
        super(Type.LEVEL_UP);
    }
    
    @Override
    public void act(Character me) {
        int currentLevel = me.getCharacterLevel();
        int xpNeeded = me.getExperiencePointsForCharacterLevel(currentLevel + 1);
        if (me.getExperiencePoints() < xpNeeded) {
            me.addMessage("You don't have enough experience points to level up.");
            return;
        }

        String increased = me.increaseCharacterLevel();
        me.addMessage("You level up to " + (currentLevel + 1) + " and gain " + increased + "!");
    }
}
