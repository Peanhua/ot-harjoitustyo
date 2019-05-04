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
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class TileTest {

    @Test
    public void getObjectsReturnsOnlyTheProperObjects() {
        Tile floor = new FloorTile(null, 0, 0);
        List<GameObject> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GameObject obj = GameObjectFactory.create("gold coin");
            objects.add(obj);
            obj.getLocation().moveTo(floor);
            
            GameObject other = GameObjectFactory.create("hat");
            other.getLocation().moveTo(floor);
        }
        
        List<GameObject> queryResults = floor.getObjects("gold coin");
        assertEquals(objects.size(), queryResults.size());
        for (int i = 0; i < objects.size(); i++) {
            assertTrue(queryResults.contains(objects.get(i)));
        }
    }
    
    @Test
    public void getObjectCountReturnsCorrectValueForNoObjects() {
        Tile floor = new FloorTile(null, 0, 0);
        assertEquals(0, floor.getObjectCount("gold coin"));
    }

    @Test
    public void getObjectCountReturnsCorrectValueForSomeObjects() {
        Tile floor = new FloorTile(null, 0, 0);

        for (int i = 0; i < 10; i++) {
            GameObject obj = GameObjectFactory.create("gold coin");
            obj.getLocation().moveTo(floor);
            
            GameObject other = GameObjectFactory.create("hat");
            other.getLocation().moveTo(floor);
        }

        assertEquals(10, floor.getObjectCount("gold coin"));
    }
}
