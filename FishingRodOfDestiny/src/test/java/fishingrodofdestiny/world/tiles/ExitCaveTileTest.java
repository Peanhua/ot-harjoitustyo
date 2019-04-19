/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionActivateTile;
import fishingrodofdestiny.world.gameobjects.FishingRod;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Player;
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
public class ExitCaveTileTest {

    private ExitCaveTile tile;
    private Player       player;
    
    public ExitCaveTileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.tile = new ExitCaveTile(null, 0, 0);
        this.player = new Player();
        this.player.getLocation().moveTo(tile);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void canExitWithFishingRod() {
        GameObject rod = new FishingRod();
        rod.getLocation().moveTo(player);
        Action action = new ActionActivateTile();
        action.act(player);
        assertTrue(player.getGameCompleted());
    }

    @Test
    public void cantExitWithoutFishingRod() {
        Action action = new ActionActivateTile();
        action.act(player);
        assertFalse(player.getGameCompleted());
    }
}
