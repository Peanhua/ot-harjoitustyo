/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Location;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.Tile;
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
public class GameTest {
    
    private Player player;
    private Game   game;
    
    public GameTest() {
    }
    
    @Before
    public void setUp() {
        this.player = new Player();
        this.game = new Game(this.player, Game.RescueTarget.PRINCESS);
    }

    @Test
    public void makeSureThePlayerIsLocatedSomewhere() {
        assertTrue(this.player.getLocation() != null);
        assertTrue(this.player.getLocation().getContainerTile() != null);
    }
    
    @Test
    public void makeSureThePlayerCanMove() {
        Location location = this.player.getLocation();
        assertTrue(location != null);
        
        Tile originalTile = location.getContainerTile();
        assertTrue(originalTile != null);
        
        this.player.act(GameObject.Action.MOVE_NORTH);
        boolean movedNorth = location.getContainerTile() != originalTile;

        this.player.act(GameObject.Action.MOVE_SOUTH);
        boolean movedSouth = location.getContainerTile() != originalTile;

        this.player.act(GameObject.Action.MOVE_WEST);
        boolean movedWest = location.getContainerTile() != originalTile;

        this.player.act(GameObject.Action.MOVE_EAST);
        boolean movedEast = location.getContainerTile() != originalTile;
        
        assertTrue(movedNorth || movedSouth || movedWest || movedEast);
    }
}
