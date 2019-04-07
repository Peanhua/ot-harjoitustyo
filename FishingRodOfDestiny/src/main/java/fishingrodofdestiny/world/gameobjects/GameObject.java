/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
import fishingrodofdestiny.resources.ImageCache;
import fishingrodofdestiny.world.tiles.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


/**
 *
 * @author joyr
 */
public abstract class GameObject {
    
    public enum Action {
        NONE,
        MOVE_NORTH,
        MOVE_SOUTH,
        MOVE_WEST,
        MOVE_EAST,
        ACTIVATE_TILE,  // Tile specific action.
        ATTACK          // Attack whoever is in the same tile.
    };
    
    private   String     name;
    private   boolean    isAlive;
    private   int        maxHitpoints;
    private   int        currentHitpoints;
    private   int        weight;
    private   Location   location;
    protected Subject    onChange;
    private   Inventory  inventory;
    private   Subject    onMessage;
    private   String     message;
    private   Image      onScreenImage;
    private   Action     nextAction;
    private   Controller controller;
    
    public GameObject(String gfxFilename) {
        this.name             = null;
        this.isAlive          = true;
        this.maxHitpoints     = 1;
        this.currentHitpoints = 1;
        this.weight           = 1;
        this.inventory        = new Inventory(0);
        this.onChange         = new Subject();
        this.location         = new Location(this);
        this.onMessage        = new Subject();
        this.message          = "";
        this.onScreenImage    = ImageCache.getInstance().get(gfxFilename);
        this.nextAction       = Action.NONE;
        this.controller       = null;
    }
    
    @Override
    public String toString() {
        return "GameObject("
                + "name='" + this.name + "'"
                + ",hp=" + this.currentHitpoints + "/" + this.maxHitpoints
                + ",inventoryWLimit=" + this.inventory.getWeightLimit()
                + ",inventorySize=" + this.inventory.getObjects().size()
                + ")";
    }
    
    
    public void listenOnChange(Observer observer) {
        this.onChange.addObserver(observer);
    }
    
    
    public void listenOnMessage(Observer observer) {
        this.onMessage.addObserver(observer);
    }
    
    public void setMessage(String message) {
        this.message = message;
        this.onMessage.notifyObservers();
    }
    
    public String getMessage() {
        return this.message;
    }
    
    
    protected void setController(Controller controller) {
        this.controller = controller;
    }


    public Location getLocation() {
        return this.location;
    }
    
    public void setName(String name) {
        this.name = name;
        this.onChange.notifyObservers();
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getHitpoints() {
        return this.currentHitpoints;
    }
    
    public void setHitpoints(int hitpoints) {
        if (hitpoints < 1) {
            return;
        }
        this.currentHitpoints = Math.min(hitpoints, this.maxHitpoints);
        this.onChange.notifyObservers();
    }

    public int getMaxHitpoints() {
        return this.maxHitpoints;
    }
    
    public String getCapitalizedName() {
        return this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1);
    }
    
    public void hit(GameObject instigator, int damage) {
        if (this.currentHitpoints > damage) {
            this.currentHitpoints -= damage;
        } else {
            if (instigator != null) {
                instigator.setMessage(instigator.getMessage() + " " + this.getCapitalizedName() + " is destroyed.");
            }
            this.currentHitpoints = 0;
            this.destroy(instigator);
        }
        this.onChange.notifyObservers();
    }
    
    public void destroy(GameObject instigator) {
        this.setMessage(this.getMessage() + " You die!");
        if (instigator != null) {
            instigator.onDestroyTarget(this);
        }
        this.isAlive = false;
        this.getLocation().moveTo((Tile) null);
        this.onChange.notifyObservers();
    }
    
    public boolean isAlive() {
        return this.isAlive;
    }
    
    // Called when this destroys the given target:
    public void onDestroyTarget(GameObject target) {
    }
    
    public void adjustMaxHitpoints(int amount) {
        if (amount <= 0 || this.maxHitpoints < Integer.MAX_VALUE - amount) {
            this.maxHitpoints += amount;
            if (this.maxHitpoints < 0) {
                this.maxHitpoints = 0;
            }
        } else {
            this.maxHitpoints = Integer.MAX_VALUE;
        }
        this.onChange.notifyObservers();
    }
    
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    
    public int getWeight() {
        return this.weight + this.inventory.getWeight();
    }


    protected final void setOnScreenImage(Image image) {
        this.onScreenImage = image;
    }
    
    public void draw(GraphicsContext context, int x, int y, int size) {
        if (this.onScreenImage != null) {
            context.drawImage(this.onScreenImage, x, y, size, size);
            
        } else {
            context.setFill(Color.CADETBLUE);
            context.fillRect(x, y, size, size);
        }
    }
    
    public final void setNextAction(Action action) {
        this.nextAction = action;
    }
    
    
    public void act(Action action) {
    }
    
    
    public void tick(double deltaTime) {
        if (this.nextAction == null && this.controller != null) {
            this.nextAction = this.controller.getNextAction();
        }
        if (this.nextAction != null) {
            this.act(this.nextAction);
            this.nextAction = null;
        }
    }
}
