/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Player;

/**
 *
 * @author joyr
 */
public class ExitCaveTile extends FloorTile {

    public ExitCaveTile(Level level, int x, int y) {
        super(level, x, y);
        this.setGraphics(new TileGfx("rltiles/nh32", 352, 896, 32, 32));
        Tile floor = new FloorTile(level, x, y);
        this.setGraphicsBackground(floor.getGraphics().getNextFrame());
    }

    @Override
    public void activate(GameObject object) {
        if (object instanceof Player) {
            if (!this.hasFishingRod(object)) {
                object.addMessage("You lack the required Magical Fishing Rod. You are not ready to exit the cave yet.");
                return;
            }
            
            object.addMessage("You exit the cave with the Magical Fishing Rod!");
            Player player = (Player) object;
            player.setGameCompleted();
        }
    }
    
    private boolean hasFishingRod(GameObject object) {
        return object.getObjectCount("fishing rod") > 0;
    }
}
