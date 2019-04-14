/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.tiles.Tile;


/**
 *
 * @author joyr
 */
public class ActionMove extends Action {
    private final int deltaX;
    private final int deltaY;
    
    public ActionMove(int deltaX, int deltaY) {
        super(Type.MOVE);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
    

    public int getDeltaX() {
        return this.deltaX;
    }
    

    public int getDeltaY() {
        return this.deltaY;
    }


    @Override
    public void act(Character me) {
        Tile myTile = me.getLocation().getContainerTile();
        if (myTile == null) {
            return;
        }
        
        Level level = myTile.getLevel();
        if (level == null) {
            return;
        }
        
        Tile targetTile = level.getTile(myTile.getX() + deltaX, myTile.getY() + deltaY);
        if (targetTile == null) {
            return;
        }
        
        if (!targetTile.canBeEntered()) {
            return;
        }
        
        me.getLocation().moveTo(targetTile);
    }
}
