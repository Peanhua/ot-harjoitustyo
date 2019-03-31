/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.gameobjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joyr
 */
public class GameObject {
    private String   name;
    private int      maxHitpoints;
    private int      currentHitpoints;
    private int      weight;
    private Location location;
    
    private List<GameObject> inventory; // TODO: split into own class
    private int              inventoryWeightLimit;
    
    public GameObject() {
        this.name                 = null;
        this.maxHitpoints         = 1;
        this.currentHitpoints     = 1;
        this.inventory            = new ArrayList<>();
        this.inventoryWeightLimit = 0;
        this.weight               = 1;
        this.location             = new Location();
    }
    
    @Override
    public String toString() {
        return "GameObject("
                + "name='" + this.name + "'"
                + ",hp=" + this.currentHitpoints + "/" + this.maxHitpoints
                + ",inventoryWLimit=" + this.inventoryWeightLimit
                + ",inventorySize=" + this.inventory.size()
                + ")";
    }

    public Location getLocation() {
        return this.location;
    }
    
    public void setName(String name) {
        this.name = name;
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
        if(amount <= 0 || this.maxHitpoints < Integer.MAX_VALUE - amount) {
            this.maxHitpoints += amount;
            if(this.maxHitpoints < 0)
                this.maxHitpoints = 0;
        } else {
            this.maxHitpoints = Integer.MAX_VALUE;
        }
    }
    
    public int getInventoryWeightLimit() {
        return this.inventoryWeightLimit;
    }
    
    public void setInventoryWeightLimit(int limit) {
        if(limit >= 0)
            this.inventoryWeightLimit = limit;
        else
            throw new RuntimeException("Illegal inventory weight limit " + limit + " given to GameObject.setInventoryWeightLimit().");
    }
    
    public void adjustInventoryWeightLimit(int amount) {
        if(amount <= 0 || this.inventoryWeightLimit < Integer.MAX_VALUE - amount) {
            this.inventoryWeightLimit += amount;
            if(this.inventoryWeightLimit < 0)
                this.inventoryWeightLimit = 0;
        } else {
            this.inventoryWeightLimit = Integer.MAX_VALUE;
        }
    }
    
    public List<GameObject> getInventory() {
        return this.inventory;
    }
    
    public int getInventoryWeight() {
        int weight = 0;
        
        for(GameObject obj : this.inventory) {
            weight += obj.getWeight();
        }
        
        return weight;
    }
    
    public int getWeight() {
        return this.weight + this.getInventoryWeight();
    }
}
