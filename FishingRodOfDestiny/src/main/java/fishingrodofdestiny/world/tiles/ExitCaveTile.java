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
