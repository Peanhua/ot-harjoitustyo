/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.TileGfx;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author joyr
 */
public class FloorTile extends Tile {

    public FloorTile(Level level, int x, int y) {
        super(level, x, y);
        this.setGraphics(new TileGfx("rltiles/nh32", 256, 896, 32, 32));
    }
    
    public boolean canBeEntered() {
        return true;
    }
}
