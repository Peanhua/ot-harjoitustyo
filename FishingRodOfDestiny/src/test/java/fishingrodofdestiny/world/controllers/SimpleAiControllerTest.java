/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.controllers;

import fishingrodofdestiny.world.Cave;
import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionAttack;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class SimpleAiControllerTest {
    class MyController extends SimpleAiController {
        public boolean wasCalled;
        
        public MyController(Character owner) {
            super(owner);
            this.wasCalled = false;
        }
    }

    private Player player;
    private Game   game;
    private MyController mockController;

    @Before
    public void setUp() {
        this.player = new Player();
        this.game = new Game(0, this.player, Game.RescueTarget.PRINCESS);
        this.mockController = new MyController(null);

        // Remove NPCs from level 0:
        Cave cave = this.game.getCave();
        Level level = cave.getLevel(0);
        level.getObjects(null).forEach(obj -> {
                if (obj instanceof NonPlayerCharacter) {
                    obj.destroy(null);
                }
        });
        
    }
    
    @Test
    public void npcAttacksPlayerWhenInSameTile() {
        Character npc = (Character) GameObjectFactory.create("rat");
        npc.getLocation().moveTo(player.getLocation().getContainerTile());
        Action action = npc.getController().getNextAction();
        assertTrue(action instanceof ActionAttack);
    }
    
    @Test
    public void npcMovesTowardsPlayer() {
        Character npc = (Character) GameObjectFactory.create("rat");
        npc.getLocation().moveTo(this.findNearbyFloorTile(this.player.getLocation().getContainerTile()));
        SimpleAiController controller = (SimpleAiController) npc.getController();
        controller.setAggressive(true);
        Action action = controller.getNextAction();
        assertTrue(action instanceof ActionMove);
    }
    
    @Test
    public void npcEventuallyGetsAggressiveNearPlayer() {
        Character npc = (Character) GameObjectFactory.create("rat");
        npc.getLocation().moveTo(this.findNearbyFloorTile(this.player.getLocation().getContainerTile()));
        SimpleAiController controller = (SimpleAiController) npc.getController();
        for (int i = 0; i < 100; i++) {
            controller.getNextAction();
        }
        assertTrue(controller.isAggressive());
    }
    
    @Test
    public void onNewActionObserverIsCalled() {
        mockController.listenOnNewAction(() -> {
            mockController.wasCalled = true;
        });
        mockController.setNextAction(new ActionMove(1, 0));
        assertTrue(mockController.wasCalled);
    }

    
    private Tile findNearbyFloorTile(Tile fromTile) {
        Tile targetTile = null;
        Level level = this.game.getCave().getLevel(0);
        for (int y = -1; targetTile == null && y <= 1; y++) {
            for (int x = -1; targetTile == null && x <= 1; x++) {
                if (x != 0 || y != 0) {
                    Tile tmp = level.getTile(x + fromTile.getX(), y + fromTile.getY());
                    if (tmp instanceof FloorTile) {
                        targetTile = tmp;
                    }
                }
            }
        }
        return targetTile;
    }
}
