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

import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Location;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class GameTest {
    
    private Player player;
    private Game   game;
    
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
    public void playerTickIsCalledFromGameTick() {
        class MockPlayer extends Player {
            public boolean wasCalled = false;
            
            @Override
            public void tick(double deltaTime) {
                this.wasCalled = true;
            }
        }
        
        MockPlayer plr = new MockPlayer();
        Game g = new Game(0, plr, Game.RescueTarget.PRINCESS);
        g.tick();
        assertTrue(plr.wasCalled);
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

    @Test
    public void plotFinishDifferBasedOnRescueTarget() {
        Game game1 = new Game(0, this.player, Game.RescueTarget.PRINCESS);
        Game game2 = new Game(0, this.player, Game.RescueTarget.PRINCE);
        
        List<String> plot1 = game1.getPlotFinish();
        List<String> plot2 = game2.getPlotFinish();
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
    
    @Test
    public void playerEventuallyDiesIfThereAreRatsNearby() {
        int[][] positions = { { -1, -1 }, { 1, -1 }, {-1, 1 }, { 1, 1 }, { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        Level level = this.game.getPlayer().getLocation().getContainerTile().getLevel();
        int x = this.game.getPlayer().getLocation().getContainerTile().getX();
        int y = this.game.getPlayer().getLocation().getContainerTile().getY();
        
        int ratcount = 0;
        for (int distance = 1; distance < 5; distance++) {
            for (int i = 0; i < positions.length; i++) {
                Tile floor = level.getTile(x + positions[i][0] * distance, y * positions[i][1] * distance);
                if (floor != null && floor.canBeEntered()) {
                    GameObject rat = GameObjectFactory.create("rat");
                    rat.getLocation().moveTo(floor);
                    ratcount++;
                }
            }
        }
        assertTrue(ratcount > 0); // This is actually an error in this test, but should be caught nevertheless.
        
        for (int i = 0; this.game.getPlayer().isAlive() && i < 10000; i++) {
            this.game.tick();
        }
        assertFalse(this.game.getPlayer().isAlive());
    }
}
