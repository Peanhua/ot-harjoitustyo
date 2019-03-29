/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.gameobjects;

import java.util.ArrayList;

/**
 *
 * @author joyr
 */
public class GameObject {
    private String name;
    private int    maxHitpoints;
    private int    currentHitpoints;
    
    private ArrayList<GameObject> inventory;
    private int                   inventoryWeightLimit;
    
    public GameObject() {
        this.name                 = null;
        this.maxHitpoints         = 0;
        this.currentHitpoints     = 0;
        this.inventory            = new ArrayList<>();
        this.inventoryWeightLimit = 0;
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
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

    public int getMaxHitpoints() {
        return this.maxHitpoints;
    }
    
    public void adjustMaxHitpoints(int amount) {
        this.maxHitpoints += amount;
        if(this.maxHitpoints < 0)
            this.maxHitpoints = 0;
    }
    
    public int getInventoryWeightLimit() {
        return this.inventoryWeightLimit;
    }
    
    public void adjustInventoryWeightLimit(int amount) {
        this.inventoryWeightLimit += amount;
        if(this.inventoryWeightLimit < 0)
            this.inventoryWeightLimit = 0;
    }
}
