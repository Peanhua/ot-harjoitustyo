/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.tiles.Tile;

/**
 *
 * @author joyr
 */
public class SimpleAiController extends Controller {

    public SimpleAiController(GameObject owner) {
        super(owner);
    }

    @Override
    public GameObject.Action getNextAction() {
        return this.tryToAttack();
    }
    
    protected GameObject.Action tryToAttack() {
        Tile tile = this.getOwner().getLocation().getContainerTile();
        if (tile == null) {
            return null;
        }
        
        for (GameObject object : tile.getInventory().getObjects()) {
            if (object instanceof Player) {
                return GameObject.Action.ATTACK;
            }
        }
        
        return null;
    }
}
