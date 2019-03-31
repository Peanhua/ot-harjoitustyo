/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;


/**
 *
 * @author joyr
 */
public abstract class StairsTile extends Tile {
    private Tile target;
    
    public StairsTile(Level inLevel) {
        super(inLevel);
        this.target = null;
    }
    
    public boolean canBeEntered() {
        return true;
    }
    
    public void setTarget(Tile target) {
        this.target = target;
    }
}
