/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.GameObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author joyr
 */
public class GameObjectSpawner {
    private final List<ObjectConfiguration> objects;
    private int maximumTotalCount;
    
    public GameObjectSpawner() {
        this.objects           = new ArrayList<>();
        this.maximumTotalCount = 0;
    }
    
    public final void setMaximumTotalCount(int count) {
        this.maximumTotalCount = count;
    }
    
    public final void addType(GameObjectFactory.Type type, int maximumCount, double weight) {
        this.objects.add(new ObjectConfiguration(type, maximumCount, weight));
    }

    public final void addType(String objectId, int maximumCount, double weight) {
        this.objects.add(new ObjectConfiguration(objectId, maximumCount, weight));
    }
    
    // TODO: GameObject and Level (and Tile) could implement the same interface to simplify this.
    public final GameObjectFactory.Type getNext(Random random, Level level) {
        return this.getNext(random, this.getPossible(level), level.getObjectCount((Class) null));
    }

    public final GameObjectFactory.Type getNext(Random random, GameObject object) {
        return this.getNext(random, this.getPossible(object), object.getInventory().getObjectCount((Class) null));
    }
    
    private GameObjectFactory.Type getNext(Random random, List<ObjectConfiguration> possibleChoices, int currentObjectCount) {
        if (this.maximumTotalCount > 0 && currentObjectCount >= this.maximumTotalCount) {
            return null;
        }
        if (possibleChoices.isEmpty()) {
            return null;
        }
        
        possibleChoices.forEach(conf -> conf.setProbabilityModifier(random.nextDouble()));
        Collections.sort(possibleChoices);
        
        return possibleChoices.get(0).getType();
    }
    
    public final String getNextObjectId(Random random, Level level) {
        return this.getNextObjectId(random, this.getPossible(level), level.getObjectCount((Class) null));
    }

    public final String getNextObjectId(Random random, GameObject object) {
        return this.getNextObjectId(random, this.getPossible(object), object.getInventory().getObjectCount((Class) null));
    }
    
    private String getNextObjectId(Random random, List<ObjectConfiguration> possibleChoices, int currentObjectCount) {
        if (this.maximumTotalCount > 0 && currentObjectCount >= this.maximumTotalCount) {
            return null;
        }
        if (possibleChoices.isEmpty()) {
            return null;
        }
        
        possibleChoices.forEach(conf -> conf.setProbabilityModifier(random.nextDouble()));
        Collections.sort(possibleChoices);
        
        return possibleChoices.get(0).getObjectId();
    }

    
    private List<ObjectConfiguration> getPossible(Level level) {
        List<ObjectConfiguration> possible = new ArrayList<>();
        this.objects.forEach(conf -> {
            if (level.getObjectCount(conf.getType()) < conf.getMaxCount()) {
                possible.add(conf);
            }
        });
        return possible;
    }
    
    private List<ObjectConfiguration> getPossible(GameObject object) {
        List<ObjectConfiguration> possible = new ArrayList<>();
        this.objects.forEach(conf -> {
            if (object.getInventory().getObjectCount(conf.getType()) < conf.getMaxCount()) {
                possible.add(conf);
            }
        });
        return possible;
    }
}


class ObjectConfiguration implements Comparable<ObjectConfiguration> {
    private final GameObjectFactory.Type type;
    private final String                 objectId;
    private final double                 weight;
    private final int                    maxCount;
    private double                       currentProbability;

    public ObjectConfiguration(GameObjectFactory.Type type, int maxCount, double weight) {
        this.type               = type;
        this.objectId           = null;
        this.maxCount           = maxCount;
        this.weight             = weight;
        this.currentProbability = weight;
    }

    public ObjectConfiguration(String objectId, int maxCount, double weight) {
        this.type               = null;
        this.objectId           = objectId;
        this.maxCount           = maxCount;
        this.weight             = weight;
        this.currentProbability = weight;
    }

    public GameObjectFactory.Type getType() {
        return this.type;
    }
    
    public String getObjectId() {
        return this.objectId;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public void setProbabilityModifier(double modifier) {
        this.currentProbability *= modifier;
    }

    public double getProbability() {
        return this.currentProbability;
    }

    @Override
    public int compareTo(ObjectConfiguration other) {
        if (other.currentProbability > this.currentProbability) {
            return 1;
        } else if (other.currentProbability < this.currentProbability) {
            return -1;
        } else {
            return 0;
        }
    }
}
