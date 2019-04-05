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

    public FloorTile(Level inLevel) {
        super(inLevel);
    }
    
    public boolean canBeEntered() {
        return true;
    }
    
    public void draw(GraphicsContext context, int x, int y, int size) {
        context.setFill(Color.CHOCOLATE);
        context.fillRect(x, y, size, size);
    }
}
