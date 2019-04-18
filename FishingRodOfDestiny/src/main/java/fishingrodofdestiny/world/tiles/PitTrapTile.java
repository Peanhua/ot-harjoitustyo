/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.gameobjects.GameObject;

/**
 *
 * @author joyr
 */
public class PitTrapTile extends Tile {
    private boolean opened;
    private Tile    target;
    
    public PitTrapTile(Level level, int x, int y) {
        super(level, x, y, "floor");
        this.opened = false;
        this.target = null;
        
        Tile floor = new FloorTile(level, x, y);
        this.setGraphics(floor.getGraphics());
    }
    
    public void setTarget(Tile target) {
        this.target = target;
    }

    @Override
    public boolean canBeEntered() {
        return true;
    }
    
    @Override
    public void onEnter(GameObject object) {
        if (this.target == null) {
            return;
        }
        if (!this.opened) {
            object.addMessage("The floor beneath you opens and you fall down!");
            this.setName("pit");
            this.opened = true;
            TileGfx background = this.getGraphics();
            TileGfx gfx = new TileGfx("rltiles/nh32", 384, 928, 32, 32);
            gfx.setBackground(background.getNextFrame());
            this.setGraphics(gfx);
        } else {
            object.addMessage("You fall down through the hole.");
        }
        
        object.getLocation().moveTo(this.target);
        object.hit(null, 1);
    }
}
