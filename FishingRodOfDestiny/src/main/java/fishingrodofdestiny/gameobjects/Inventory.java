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
public class Inventory {
    private final List<GameObject> objects;
    private int                    weightLimit;
    
    public Inventory(int weightLimit) {
        this.objects = new ArrayList<>();
        this.setWeightLimit(weightLimit);
    }
    
    
    public void add(GameObject object) {
        this.objects.add(object);
    }
    
    
    public void remove(GameObject object) {
        this.objects.remove(object);
    }
    
    
    public int getWeightLimit() {
        return this.weightLimit;
    }
    
    public final void setWeightLimit(int limit) {
        if(limit >= 0)
            this.weightLimit = limit;
        else
            throw new RuntimeException("Illegal inventory weight limit " + limit + " given to Inventory.setWeightLimit().");
    }
    
    public void adjustWeightLimit(int amount) {
        if(amount <= 0 || this.weightLimit < Integer.MAX_VALUE - amount) {
            this.weightLimit += amount;
            if(this.weightLimit < 0)
                this.weightLimit = 0;
        } else {
            this.weightLimit = Integer.MAX_VALUE;
        }
    }

    public List<GameObject> getObjects() {
        return this.objects;
    }
    
    public int getWeight() {
        int weight = 0;
        
        for(GameObject obj : this.objects) {
            weight += obj.getWeight();
        }
        
        return weight;
    }
    

}
