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
public class WallTile extends Tile {

    public WallTile(Level level, int x, int y) {
        super(level, x, y);
        this.setGraphics(new TileGfx("DawnLike/Objects/Wall", 48, 192, 16, 16));
    }
    
    public boolean canBeEntered() {
        return false;
    }
}
