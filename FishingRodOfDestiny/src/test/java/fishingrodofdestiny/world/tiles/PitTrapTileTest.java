/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.GoldCoin;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class PitTrapTileTest {
    
    public PitTrapTileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void objectGetsTeleportedWhenEntersTheTrapTile() {
        PitTrapTile trap = new PitTrapTile(null, 0, 0);
        Tile target = new FloorTile(null, 0, 0);
        trap.setTarget(target);
        
        GameObject obj = new GoldCoin();
        obj.getLocation().moveTo(trap);
        assertEquals(target, obj.getLocation().getContainerTile());
    }
}
