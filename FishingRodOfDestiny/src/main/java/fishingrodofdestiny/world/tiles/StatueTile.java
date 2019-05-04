/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
