/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
import fishingrodofdestiny.world.GameObjectContainer;
import fishingrodofdestiny.world.gameobjects.GameObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joyr
 */
public class Inventory implements GameObjectContainer {
    private final List<GameObject> objects;
    private final Subject          onChange;
    
    public Inventory() {
        this.objects  = new ArrayList<>();
        this.onChange = new Subject();
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
