/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.controllers.PlayerController;

/**
 *
 * @author joyr
 */
public class Player extends Character {
    private boolean gameCompleted;
    
    public Player() {
        super();
        this.gameCompleted = false;
        this.setController(new PlayerController(this));
        this.setGraphics(new TileGfx("rltiles/nh32", 160, 352, 32, 32));
    }
    
    @Override
    public String toString() {
        return "Player(" + super.toString()
                + ")";
    }
    
    @Override
    public boolean isValidAttackTarget(GameObject target) {
        if (!super.isValidAttackTarget(target)) {
            return false;
        }
        
        return target instanceof Character;
    }

    
    public void setGameCompleted() {
        this.gameCompleted = true;
    }
    
    public boolean getGameCompleted() {
        return this.gameCompleted;
    }
}
