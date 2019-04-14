/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.controllers;

import fishingrodofdestiny.world.actions.ActionAttack;
import fishingrodofdestiny.world.actions.Action;
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
}
