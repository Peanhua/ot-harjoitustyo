/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.tiles.Tile;


/**
 *
 * @author joyr
 */
public class Character extends GameObject {
    
    private int attack;
    private int defence;
    private int level;
    private int experiencePoints;

    public Character() {
        super();
        this.attack           = 0;
        this.defence          = 0;
        this.level            = 0;
        this.experiencePoints = 0;
        this.getInventory().setWeightLimit(20);
    }
    
    @Override
    public String toString() {
        return "Character(" + super.toString()
                + ",attack=" + this.attack
                + ",defence=" + this.defence
                + ",level=" + this.level
                + ",xp=" + this.experiencePoints
                + ")";
    }
    
    @Override
    public void act(Action action) {
        if (action == null) {
            return;
        }
        
        switch (action) {
            case MOVE_NORTH:
                this.move(0, -1);
                break;
            case MOVE_SOUTH:
                this.move(0, 1);
                break;
            case MOVE_WEST:
                this.move(-1, 0);
                break;
            case MOVE_EAST:
                this.move(1, 0);
                break;
            case ACTIVATE_TILE:
                Tile tile = this.getLocation().getContainerTile();
                if (tile != null) {
                    tile.activate(this);
                }
                break;
        }
    }
    
    private void move(int deltaX, int deltaY) {
        Tile myTile = this.getLocation().getContainerTile();
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
        
        this.getLocation().moveTo(targetTile);
    }
    
    public int getAttack() {
        return this.attack;
    }
    
    public int getDefence() {
        return this.defence;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public int getExperiencePoints() {
        return this.experiencePoints;
    }
    
    public int getArmorClass() {
        return 0; // TODO: calculate from worn armor
    }
    
    public int getDamage() {
        return 0; // TODO: calculate from stats and currently equipped weapon
    }
    
    public void adjustAttack(int amount) {
        this.attack += amount;
        if (this.attack < 0) {
            this.attack = 0;
        }
        this.onChange.notifyObservers();
    }
    
    public void adjustDefence(int amount) {
        this.defence += amount;
        if (this.defence < 0) {
            this.defence = 0;
        }
        this.onChange.notifyObservers();
    }
}
