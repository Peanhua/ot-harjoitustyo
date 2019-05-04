/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
