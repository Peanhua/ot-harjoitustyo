/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.TileGfx;

/**
 *
 * @author joyr
 */
public class FloorTile extends Tile {

    public FloorTile(Level level, int x, int y) {
        super(level, x, y, "floor");
        this.setGraphics(new TileGfx("rltiles/nh32", 256, 896, 32, 32));
    }
    
    @Override
    public boolean canBeEntered() {
        return true;
    }
}
