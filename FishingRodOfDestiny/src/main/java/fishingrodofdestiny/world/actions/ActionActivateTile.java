/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.tiles.Tile;


/**
 *
 * @author joyr
 */
public class ActionActivateTile extends Action {
    public ActionActivateTile() {
        super(Type.ACTIVATE_TILE);
    }
    
    @Override
    public void act(Character me) {
        Tile tile = me.getLocation().getContainerTile();
        if (tile != null) {
            tile.activate(me);
        }
    }
}
