/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 *
 * @author joyr
 */
public class GameObject {
    
    public enum Action {
        MOVE_NORTH,
        MOVE_SOUTH,
        MOVE_WEST,
        MOVE_EAST,
        ACTIVATE_TILE  // Tile specific action.
    };
    
    private   String    name;
    private   int       maxHitpoints;
    private   int       currentHitpoints;
    private   int       weight;
    private   Location  location;
    protected Subject   onChange;
    private   Inventory inventory;
    
    public GameObject() {
        this.name             = null;
        this.maxHitpoints     = 1;
        this.currentHitpoints = 1;
        this.weight           = 1;
        this.inventory        = new Inventory(0);
        this.onChange         = new Subject();
        this.location         = new Location(this);
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

    public int getMaxHitpoints() {
        return this.maxHitpoints;
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
    
    
    public void draw(GraphicsContext context, int x, int y, int size) {
        context.setFill(Color.CADETBLUE);
        context.fillRect(x, y, size, size);
    }
    
    
    public void act(Action action) {
    }
}
