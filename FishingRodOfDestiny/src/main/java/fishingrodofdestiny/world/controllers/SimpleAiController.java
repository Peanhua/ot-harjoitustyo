/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.controllers;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.actions.ActionAttack;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.List;

/**
 *
 * @author joyr
 */
public class SimpleAiController extends Controller {

    public SimpleAiController(Character owner) {
        super(owner);
    }

    @Override
    public Action getNextAction() {
        Action action = super.getNextAction();
        if (action == null) {
            action = this.tryToAttack();
        }
        if (action == null) {
            action = this.tryToFindPlayer();
        }
        return action;
    }
    
    protected Action tryToAttack() {
        Tile tile = this.getOwner().getLocation().getContainerTile();
        List<GameObject> targets = this.getOwner().getValidAttackTargets(tile);
        for (GameObject object : tile.getInventory().getObjects()) {
            if (object instanceof Player) {
                return new ActionAttack(object);
            }
        }
        
        return null;
    }
    
    protected Action tryToFindPlayer() {
        Tile myTile = this.getOwner().getLocation().getContainerTile();
        if (myTile == null) {
            return null;
        }
        Level level = myTile.getLevel();
        List<GameObject> players = level.getObjects(Player.class);
        for (GameObject player : players) {
            Tile tile = player.getLocation().getContainerTile();
            int deltaX = tile.getX() - myTile.getX();
            int deltaY = tile.getY() - myTile.getY();
            int maxDistance = 7;
            if (Math.abs(deltaX) <= maxDistance || Math.abs(deltaY) <= maxDistance) {
                return this.moveTowards(deltaX, deltaY);
            }
        }
        
        return null;
    }
    
    protected Action moveTowards(int deltaX, int deltaY) {
        int dx = 0;
        int dy = 0;
        if (deltaX != 0) {
            dx = deltaX / Math.abs(deltaX);
        }
        if (deltaY != 0) {
            dy = deltaY / Math.abs(deltaY);
        }
        if (dx != 0 && dy != 0) {
            if (this.getOwner().getRandom().nextInt(100) < 50) {
                dx = 0;
            } else {
                dy = 0;
            }
        }
        return new ActionMove(dx, dy);
    }
}
