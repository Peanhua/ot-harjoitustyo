/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.GoldCoin;
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
        GameObject obj = new GoldCoin();
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
        GameObject obj = new GoldCoin();
        obj.getLocation().moveTo(floor);
        stairs1.activate(obj);
        assertEquals(floor, obj.getLocation().getContainerTile());
    }
}
