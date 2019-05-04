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
import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionActivateTile;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Player;
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
    public void canLeaveAfterReactivatedBySomeoneElse() {
        this.character.getLocation().moveTo(this.floor);
        Action action = new ActionMove(1, 0);
        action.act(this.character);
        
        Character someoneElse = (Character) GameObjectFactory.create("rat");
        someoneElse.getLocation().moveTo(this.trap);
        action = new ActionActivateTile();
        action.act(someoneElse);
        
        action = new ActionMove(-1, 0);
        action.act(this.character);
        
        assertEquals(this.floor, this.character.getLocation().getContainerTile());
    }
    
    @Test
    public void reactivatingMoreThanOnceGivesDifferentMessage() {
        Player player = new Player();
        player.setMaxHitpoints(999);
        player.getLocation().moveTo(this.floor);
        Action action = new ActionMove(1, 0);
        action.act(player);
        
        player.popMessage();
        action = new ActionActivateTile();
        action.act(player);
        String firstMessage = player.popMessage();
        
        action = new ActionActivateTile();
        action.act(player);
        String secondMessage = player.popMessage();
        
        assertFalse(firstMessage.equals(secondMessage));
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
