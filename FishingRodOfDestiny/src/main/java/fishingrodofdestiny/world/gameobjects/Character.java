/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.controllers.Controller;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author joyr
 */
public abstract class Character extends GameObject {
    
    private int    attack;
    private int    defence;
    private int    level;
    private int    experiencePoints;
    private Controller controller;

    public Character() {
        super();
        this.attack           = 0;
        this.defence          = 0;
        this.level            = 0;
        this.experiencePoints = 0;
        this.controller       = null;
        this.setDrawingOrder(100);
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
    public void destroy(GameObject instigator) {
        Tile tile = this.getLocation().getContainerTile();
        if (tile != null) {
            GameObject splatter = new BloodSplatter();
            splatter.getLocation().moveTo(tile);
        }
        super.destroy(instigator);
    }
    
    @Override
    public void onDestroyTarget(GameObject target) {
        int xp = 1;
        
        if (target instanceof Character) {
            Character targetCharacter = (Character) target;
            xp = 1 + targetCharacter.getLevel() * 3;
        }
        
        this.adjustExperiencePoints(xp);

        super.onDestroyTarget(target);
    }
    
    
    public boolean isValidAttackTarget(GameObject target) {
        return target != this;
    }

    
    @Override
    public List<GameObject> getValidAttackTargets(Tile tile) {
        if (tile == null) {
            return null;
        }
        
        List<GameObject> targets = new ArrayList<>();

        tile.getInventory().getObjects().forEach(obj -> {
            if (this.isValidAttackTarget(obj)) {
                targets.add(obj);
            }
        });
        
        return targets.isEmpty() ? null : targets;
    }        

    
    public List<GameObject> getValidPickUpTargets() {
        List<GameObject> items = new ArrayList<>();
        this.getLocation().getContainerTile().getInventory().getObjects().forEach(object -> {
            if (object.getCanBePickedUp()) {
                items.add(object);
            }
        });
        return items;
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

    
    public Controller getController() {
        return this.controller;
    }
    
    protected final void setController(Controller controller) {
        this.controller = controller;
    }
    
    
    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        
        if (!this.isAlive()) {
            return;
        }
        if (this.controller == null) {
            return;
        }
        
        Action nextAction = this.controller.getNextAction();
        if (nextAction != null) {
            nextAction.act(this);
        }
    }
}
