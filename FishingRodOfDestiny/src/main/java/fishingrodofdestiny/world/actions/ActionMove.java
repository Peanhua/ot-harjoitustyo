/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.Inventory;
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
        Tile targetTile = this.getTargetTile(me);
        if (targetTile == null) {
            return;
        }
        
        Inventory myInventory = me.getInventory();
        if (myInventory.getWeight() > myInventory.getWeightLimit()) {
            me.addMessage("You are carrying too much and unable to move!");
            return;
        }
        
        me.getLocation().moveTo(targetTile);
    }
    
    private Tile getTargetTile(Character me) {
        Tile myTile = me.getLocation().getContainerTile();
        if (myTile == null) {
            return null;
        }
        
        Level level = myTile.getLevel();
        if (level == null) {
            return null;
        }
        
        Tile targetTile = level.getTile(myTile.getX() + this.deltaX, myTile.getY() + this.deltaY);
        if (targetTile == null) {
            return null;
        }
        
        if (!targetTile.canBeEntered()) {
            return null;
        }
        
        return targetTile;
    }        
}
