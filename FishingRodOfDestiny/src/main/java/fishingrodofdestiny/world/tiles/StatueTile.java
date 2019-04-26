/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import java.util.List;

/**
 *
 * @author joyr
 */
public class StatueTile extends Tile {

    public StatueTile(Level level, int x, int y) {
        super(level, x, y, "statue");
        this.setGraphics(new TileGfx("rltiles/nh32", 448, 864, 32, 32));
        Tile floor = new FloorTile(level, x, y);
        this.setGraphicsBackground(floor.getGraphics().getNextFrame());
    }
    
    @Override
    public boolean canBeEntered() {
        return true;
    }

    @Override
    public void activate(GameObject object) {
        List<GameObject> coins = object.getInventory().getObjects("gold coin");

        if (coins.isEmpty()) {
            object.addMessage("You don't have any gold coins to sacrifice.");
            return;
        }

        object.addMessage("You sacrifice " + coins.size() + " gold coins.");
        coins.forEach(o -> o.destroy(null));
        
        object.adjustHitpoints(1);
        if (object instanceof Character) {
            ((Character) object).adjustExperiencePoints(coins.size());
        }
    }
}
