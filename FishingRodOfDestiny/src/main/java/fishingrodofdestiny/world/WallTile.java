/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author joyr
 */
public class WallTile extends Tile {

    public WallTile(Level inLevel) {
        super(inLevel);
    }
    
    public boolean canBeEntered() {
        return false;
    }
    
    public void draw(GraphicsContext context, int x, int y, int size) {
        context.setFill(Color.DARKGRAY);
        context.fillRect(x, y, size, size);
    }
}
