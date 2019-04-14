/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Location;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.List;
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
        this.game = new Game(0, this.player, Game.RescueTarget.PRINCESS);
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
        
        Action action;
        
        action = new ActionMove(0, -1);
        action.act(this.player);
        boolean movedNorth = location.getContainerTile() != originalTile;

        action = new ActionMove(0, 1);
        action.act(this.player);
        boolean movedSouth = location.getContainerTile() != originalTile;

        action = new ActionMove(-1, 0);
        action.act(this.player);
        boolean movedWest = location.getContainerTile() != originalTile;

        action = new ActionMove(1, 0);
        action.act(this.player);
        boolean movedEast = location.getContainerTile() != originalTile;
        
        assertTrue(movedNorth || movedSouth || movedWest || movedEast);
    }
    
    @Test
    public void plotsDifferBasedOnRescueTarget() {
        Game game1 = new Game(0, this.player, Game.RescueTarget.PRINCESS);
        Game game2 = new Game(0, this.player, Game.RescueTarget.PRINCE);
        
        List<String> plot1 = game1.getPlot();
        List<String> plot2 = game2.getPlot();
        if (plot1.size() == plot2.size()) {
            boolean differs = false;
            for (int i = 0; i < plot1.size(); i++) {
                if (!plot1.get(i).equals(plot2.get(i))) {
                    differs = true;
                    break;
                }
            }
            assertTrue(differs);
        }
    }
}
