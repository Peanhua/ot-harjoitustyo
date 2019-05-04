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
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.GameObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class InventoryTest {
    
    private Inventory inventory;
    
    
    @Before
    public void setUp() {
        this.inventory = new Inventory();
    }
    
    
    @Test
    public void calculatingTotalWeightWorks() {
        GameObject coin = GameObjectFactory.create("gold coin");
        int startWeight = coin.getWeight();
        int coinWeight = coin.getWeight();
        for (int i = 0; i < 10; i++) {
            GameObject c = GameObjectFactory.create("gold coin");
            c.getLocation().moveTo(coin);
        }
        assertEquals(coinWeight * 10, coin.getInventory().getWeight());
    }
}
