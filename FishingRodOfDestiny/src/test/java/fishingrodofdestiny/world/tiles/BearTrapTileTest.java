/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionActivateTile;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class BearTrapTileTest {
    
    private Tile floor;
    private BearTrapTile trap;
    private Character character;
    
    @Before
    public void setUp() {
        Level level = new Level(null, 0, 2, 2);
        this.floor = new FloorTile(level, 0, 0);
        this.trap = new BearTrapTile(level, 1, 0);
        level.setTile(0, 0, this.floor);
        level.setTile(1, 0, this.trap);
        this.character = (Character) GameObjectFactory.create("rat");
    }

    @Test
    public void trapActivatesWhenCharacterEnters() {
        this.character.getLocation().moveTo(this.floor);
        Action action = new ActionMove(1, 0);
        action.act(this.character);
        assertEquals(this.character, trap.getTrappedObject());
    }

    @Test
    public void canLeaveAfterReactivating() {
        this.character.getLocation().moveTo(this.floor);
        Action action = new ActionMove(1, 0);
        action.act(this.character);
        action = new ActionActivateTile();
        action.act(this.character);
        action = new ActionMove(-1, 0);
        action.act(this.character);
        assertEquals(this.floor, this.character.getLocation().getContainerTile());
    }
    
    @Test
    public void cantLeaveAfterGettingTrapped() {
        this.character.getLocation().moveTo(this.floor);
        Action action = new ActionMove(1, 0);
        action.act(this.character);
        action = new ActionMove(-1, 0);
        action.act(this.character);
        assertEquals(this.trap, this.character.getLocation().getContainerTile());
    }

    @Test
    public void cantLeaveAfterEnoughTries() {
        this.character.getLocation().moveTo(this.floor);
        Action action = new ActionMove(1, 0);
        action.act(this.character);
        for (int i = 0; this.character.getLocation().getContainerTile() != this.floor && i < 100; i++) {
            action = new ActionMove(-1, 0);
            action.act(this.character);
        }
        assertEquals(this.floor, this.character.getLocation().getContainerTile());
    }
    
    @Test
    public void secondCharacterEnteringDoesNotGetTrapped() {
        Character second = (Character) GameObjectFactory.create("rat");
        this.character.getLocation().moveTo(this.floor);
        second.getLocation().moveTo(this.floor);
        Action action = new ActionMove(1, 0);
        action.act(this.character);
        action.act(second);
        action = new ActionMove(-1, 0);
        action.act(second);
        assertEquals(this.floor, second.getLocation().getContainerTile());
    }
    
    @Test
    public void nonCharacterObjectIsNotTrapped() {
        GameObject coin = GameObjectFactory.create("gold coin");
        coin.getLocation().moveTo(this.floor);
        coin.getLocation().moveTo(this.trap);
        assertNotEquals(coin, this.trap.getTrappedObject());
    }
}
