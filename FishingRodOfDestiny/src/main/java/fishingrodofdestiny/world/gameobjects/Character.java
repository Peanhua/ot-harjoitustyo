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
public abstract class Character extends GameObject {
    
    private int attack;
    private int defence;
    private int level;
    private int experiencePoints;

    public Character(String gfxFilename) {
        super(gfxFilename);
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
    public void onDestroyTarget(GameObject target) {
        int xp = 1;
        
        if (target instanceof Character) {
            Character targetCharacter = (Character) target;
            xp = 1 + targetCharacter.getLevel() * 3;
        }
        
        this.adjustExperiencePoints(xp);
    }
    
    @Override
    public void act(Action action) {
        this.setMessage("");
        
        if (action == null) {
            return;
        }

        switch (action) {
            case NONE:
                this.actionWait();
                break;
            case MOVE_NORTH:
                this.actionMove(0, -1);
                break;
            case MOVE_SOUTH:
                this.actionMove(0, 1);
                break;
            case MOVE_WEST:
                this.actionMove(-1, 0);
                break;
            case MOVE_EAST:
                this.actionMove(1, 0);
                break;
            case ACTIVATE_TILE:
                this.actionActivateTile();
                break;
            case ATTACK:
                this.actionAttack();
                break;
        }
    }
    
    private void actionWait() {
        this.setMessage("You wait.");
    }
    
    private void actionActivateTile() {
        Tile tile = this.getLocation().getContainerTile();
        if (tile != null) {
            tile.activate(this);
        }
    }
    
    private void actionMove(int deltaX, int deltaY) {
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
    
    protected boolean isValidAttackTarget(GameObject target) {
        return target != this;
    }
    
    private void actionAttack() {
        Tile tile = this.getLocation().getContainerTile();
        if (tile == null) {
            return;
        }
        
        GameObject target =
                tile
                .getInventory()
                .getObjects()
                .stream()
                .reduce(null, (a, b) -> this.isValidAttackTarget(b) ? b : a);
        
        if (target == null) {
            this.setMessage("You attack thin air!");
            return;
        }
        
        int damage = this.getDamage();
        this.setMessage("You hit " + target.getName() + " for " + damage + "!");
        target.setMessage(this.getCapitalizedName() + " hits you for " + damage + "!");
        target.hit(this, damage);
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
        return 1; // TODO: calculate from stats and currently equipped weapon
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
    
    public void adjustExperiencePoints(int amount) {
        this.experiencePoints += amount;
        this.onChange.notifyObservers();
    }
}
