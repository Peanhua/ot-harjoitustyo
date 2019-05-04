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

import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.gameobjects.GameObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class StairsTileTest {
    
    @Test
    public void canClimbStairs() {
        StairsTile stairs1 = new StairsDownTile(null, 0, 0);
        StairsTile stairs2 = new StairsUpTile(null, 0, 0);
        stairs1.setTarget(stairs2);
        stairs2.setTarget(stairs1);
        GameObject obj = GameObjectFactory.create("gold coin");
        obj.getLocation().moveTo(stairs1);
        stairs1.activate(obj);
        assertEquals(stairs2, obj.getLocation().getContainerTile());
    }
    
    @Test
    public void cantClimbStairsThatAreNotPresent() {
        StairsTile stairs1 = new StairsDownTile(null, 0, 0);
        StairsTile stairs2 = new StairsUpTile(null, 0, 0);
        FloorTile  floor   = new FloorTile(null, 0, 0);
        stairs1.setTarget(stairs2);
        stairs2.setTarget(stairs1);
        GameObject obj = GameObjectFactory.create("gold coin");
        obj.getLocation().moveTo(floor);
        stairs1.activate(obj);
        assertEquals(floor, obj.getLocation().getContainerTile());
    }
}
