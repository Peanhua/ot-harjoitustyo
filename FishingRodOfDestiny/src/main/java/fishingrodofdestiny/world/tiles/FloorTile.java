/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author joyr
 */
public class FloorTile extends Tile {

    public FloorTile(Level level, int x, int y) {
        super(level, x, y, "tiles/Floor");
    }
    
    public boolean canBeEntered() {
        return true;
    }
}
