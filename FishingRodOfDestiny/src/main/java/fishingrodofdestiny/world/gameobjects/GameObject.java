/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;


/**
 *
 * @author joyr
 */
public abstract class GameObject {
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
    private   TileGfx    graphics;
    private   int        drawingOrder;
    private   boolean    canBePickedUp;
    
    public GameObject() {
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
        this.graphics         = null;
        this.drawingOrder     = 0;
        this.canBePickedUp    = false;
    }
    
    public GameObject(String name) {
        this();
        this.name = name;
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
    
    
    public int getDrawingOrder() {
        return this.drawingOrder;
    }
    
    
    public boolean getCanBePickedUp() {
        return this.canBePickedUp;
    }
    
    protected final void setCanBePickedUp(boolean canBePickedUp) {
        this.canBePickedUp = canBePickedUp;
    }
    
    
    protected final void setDrawingOrder(int order) {
        this.drawingOrder = order;
    }
    
    
    protected final void setGraphics(TileGfx graphics) {
        this.graphics = graphics;
    }
        
    
    
    public void listenOnChange(Observer observer) {
        this.onChange.addObserver(observer);
    }
    
    
    public void listenOnMessage(Observer observer) {
        this.onMessage.addObserver(observer);
    }
    
    public void addMessage(String message) {
        if (this.message.length() > 0) {
            this.message += " ";
        }
        this.message += message;
        this.onMessage.notifyObservers();
    }
    
    public String popMessage() {
        String rv = this.message;
        this.message = "";
        return rv;
    }
    
    
    public Location getLocation() {
        return this.location;
    }
    
    public final void setName(String name) {
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
        if (this.getName() == null) {
            return "";
        }
        if (this.getName().length() == 0) {
            return this.getName();
        }
        String rv = this.getName().substring(0, 1).toUpperCase();
        if (this.getName().length() > 1) {
            rv += this.getName().substring(1);
        }
        return rv;
    }
    
    public void hit(GameObject instigator, int damage) {
        if (this.currentHitpoints > damage) {
            this.currentHitpoints -= damage;
            this.onChange.notifyObservers();
        } else {
            if (instigator != null) {
                instigator.addMessage(this.getCapitalizedName() + " is destroyed.");
            }
            this.currentHitpoints = 0;
            this.destroy(instigator);
            // onChange.notifyObservers() is not called here because destroy() does that for us
            // TODO: make it "safe" to call notifyObservers() multiple times (asynchronously) within a timeframe so that the observers get notified only once
        }
    }
    
    public void destroy(GameObject instigator) {
        // TODO: separate destroy() and onDestroyed() stuffs to their own methods, destroy() can then be final
        this.addMessage("You die!");
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
    
    
    public final Inventory getInventory() {
        return this.inventory;
    }
    
    
    public int getWeight() {
        return this.weight + this.inventory.getWeight();
    }

    
    public List<GameObject> getValidAttackTargets(Tile tile) {
        return null;
    }
    

    public void draw(GraphicsContext context, int x, int y, int size) {
        this.graphics.draw(context, x, y, size);
    }
    

    public void tick(double deltaTime) {
    }
}
