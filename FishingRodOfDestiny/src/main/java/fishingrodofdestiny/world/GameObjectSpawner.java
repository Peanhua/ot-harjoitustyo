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
    
    public final void addType(String objectId, int maximumCount, double weight) {
        this.objects.add(new ObjectConfiguration(objectId, maximumCount, weight));
    }
    
    public final String getNextObjectType(Random random, GameObjectContainer container) {
        int currentObjectCount = container.getObjectCount(null);
        if (this.maximumTotalCount > 0 && currentObjectCount >= this.maximumTotalCount) {
            return null;
        }
        
        List<ObjectConfiguration> possibleChoices = this.getPossible(container);
        if (possibleChoices.isEmpty()) {
            return null;
        }
        
        possibleChoices.forEach(conf -> conf.setProbabilityModifier(random.nextDouble()));
        Collections.sort(possibleChoices);
        
        return possibleChoices.get(0).getObjectType();
    }

    
    private List<ObjectConfiguration> getPossible(GameObjectContainer container) {
        List<ObjectConfiguration> possible = new ArrayList<>();
        this.objects.forEach(conf -> {
            if (container.getObjectCount(conf.getObjectType()) < conf.getMaxCount()) {
                possible.add(conf);
            }
        });
        return possible;
    }
}


class ObjectConfiguration implements Comparable<ObjectConfiguration> {
    private final String objectType;
    private final double weight;
    private final int    maxCount;
    private double       currentProbability;

    public ObjectConfiguration(String objectType, int maxCount, double weight) {
        this.objectType         = objectType;
        this.maxCount           = maxCount;
        this.weight             = weight;
        this.currentProbability = weight;
    }

    public String getObjectType() {
        return this.objectType;
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
