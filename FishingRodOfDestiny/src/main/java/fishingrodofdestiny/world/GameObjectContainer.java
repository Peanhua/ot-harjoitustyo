/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.GameObject;
import java.util.List;

/**
 *
 * @author joyr
 */
public interface GameObjectContainer {
    /**
     * Return objects in this container.
     * 
     * @param objectType The type of the objects to search for, or null for all objects.
     * @return List of objects matching the given objectType
     */
    public List<GameObject> getObjects(String objectType);

    /**
     * Return the number of objects in this container.
     * 
     * @param objectType The type of the objects to search for, or null for all objects.
     * @return List of objects matching the given objectType
     */
    public int getObjectCount(String objectType);
}
