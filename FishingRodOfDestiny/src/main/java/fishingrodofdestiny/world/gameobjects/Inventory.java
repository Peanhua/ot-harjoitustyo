/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
import fishingrodofdestiny.world.GameObjectContainer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joyr
 */
public class Inventory implements GameObjectContainer {
    private final List<GameObject> objects;
    private int                    weightLimit;
    private final Subject          onChange;
    
    public Inventory(int weightLimit) {
        this.objects  = new ArrayList<>();
        this.onChange = new Subject();

        this.checkWeightLimit(weightLimit);
        this.weightLimit = weightLimit;
    }
    
    
    public void listenOnChange(Observer observer) {
        this.onChange.addObserver(observer);
    }
    
    
    public void add(GameObject object) {
        this.objects.add(object);
        this.onChange.notifyObservers();
    }
    
    
    public void remove(GameObject object) {
        this.objects.remove(object);
        this.onChange.notifyObservers();
    }
    
    
    public int getWeightLimit() {
        return this.weightLimit;
    }

    
    private void checkWeightLimit(int limit) {
        if (limit < 0) {
            throw new RuntimeException("Illegal inventory weight limit " + limit + " given to Inventory.setWeightLimit().");
        }
    }
    
    public final void setWeightLimit(int limit) {
        this.checkWeightLimit(limit);
        this.weightLimit = limit;
        this.onChange.notifyObservers();
    }

    
    public void adjustWeightLimit(int amount) {
        if (amount <= 0 || this.weightLimit < Integer.MAX_VALUE - amount) {
            this.weightLimit += amount;
            if (this.weightLimit < 0) {
                this.weightLimit = 0;
            }
        } else {
            this.weightLimit = Integer.MAX_VALUE;
        }
        this.onChange.notifyObservers();
    }

    public List<GameObject> getObjects() {
        return this.objects;
    }

    

    public int getWeight() {
        int weight = 0;
        
        for (GameObject obj : this.objects) {
            weight += obj.getWeight();
        }
        
        return weight;
    }


    // GameObjectContainer implementation:
    @Override
    public List<GameObject> getObjects(String objectType) {
        if (objectType == null) {
            return this.objects;
        }
        
        List<GameObject> rv = new ArrayList<>();
        this.objects.forEach(object -> {
            if (object.getObjectType().equals(objectType)) {
                rv.add(object);
            }
        });
        return rv;
    }

    @Override
    public int getObjectCount(String objectType) {
        if (objectType == null) {
            return this.objects.size();
        }
        
        int count = 0;
        
        for (GameObject object : this.objects) {
            if (object.getObjectType().equals(objectType)) {
                count++;
            }
        }
        
        return count;
    }
}
