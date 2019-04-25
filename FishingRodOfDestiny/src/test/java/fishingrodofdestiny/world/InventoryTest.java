/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
