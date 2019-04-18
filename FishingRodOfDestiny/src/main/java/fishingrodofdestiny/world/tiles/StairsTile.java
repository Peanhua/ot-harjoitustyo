/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.gameobjects.GameObject;


/**
 *
 * @author joyr
 */
public abstract class StairsTile extends Tile {
    private Tile target;
    
    public StairsTile(Level level, int x, int y) {
        super(level, x, y, "ladders");
        this.target = null;
    }
    
    @Override
    public boolean canBeEntered() {
        return true;
    }
    
    public void setTarget(Tile target) {
        this.target = target;
    }
    
    @Override
    public void activate(GameObject object) {
        if (this.target == null) {
            return;
        }
        
        if (object.getLocation().getContainerTile() != this) {
            return;
        }
        
        object.addMessage("You climb the stairs.");
        object.getLocation().moveTo(this.target);
    }
}
