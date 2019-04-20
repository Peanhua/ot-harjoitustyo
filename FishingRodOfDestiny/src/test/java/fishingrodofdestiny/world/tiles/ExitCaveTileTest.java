/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionActivateTile;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ExitCaveTileTest {

    private ExitCaveTile tile;
    private Player       player;
    
    @Before
    public void setUp() {
        this.tile = new ExitCaveTile(null, 0, 0);
        this.player = new Player();
        this.player.getLocation().moveTo(tile);
    }
    
    @Test
    public void canExitWithFishingRod() {
        GameObject rod = GameObjectFactory.create("fishing rod");
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
