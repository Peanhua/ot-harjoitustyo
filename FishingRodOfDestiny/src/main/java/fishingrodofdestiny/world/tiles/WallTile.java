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
public class WallTile extends Tile {

    public WallTile(Level level, int x, int y) {
        super(level, x, y, "wall");
        this.setGraphics(new TileGfx("rltiles/tile", 320, 0, 32, 32));
    }
    
    @Override
    public boolean canBeEntered() {
        return false;
    }
}
