/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author joyr
 */
public class LevelSettings {
    private final List<ObjectConfiguration> objects;
    private int                             minimumNumberOfObjects;
    private int                             maximumNumberOfObjects;
    
    public LevelSettings() {
        this.objects                = new ArrayList<>();
        this.minimumNumberOfObjects = 0;
        this.maximumNumberOfObjects = 1;
    }
    
    public void addType(Class type, int maximumCount, double weight) {
        this.objects.add(new ObjectConfiguration(type, maximumCount, weight));
    }


    public Class getNext(Random random, Level level) {
        List<ObjectConfiguration> possible = new ArrayList<>();
        for (int i = 0; i < this.objects.size(); i++) {
            ObjectConfiguration conf = this.objects.get(i);
            if (level.getObjectCount(conf.getType()) < conf.getMaxCount()) {
                possible.add(conf);
            }
        }

        if (possible.isEmpty()) {
            return null;
        }
        
        possible.forEach(conf -> conf.setProbabilityModifier(random.nextDouble()));
        Collections.sort(possible);
        
        return possible.get(0).getType();
    }
}


class ObjectConfiguration implements Comparable<ObjectConfiguration> {
    private Class  type;
    private double weight;
    private double currentProbability;
    private int    maxCount;

    public ObjectConfiguration(Class type, int maxCount, double weight) {
        this.type     = type;
        this.maxCount = maxCount;
        this.weight   = weight;
        this.currentProbability = weight;
    }

    @Override
    public String toString() {
        return "Enemy(type=" + this.type + ", maxCount=" + this.maxCount + ", weight=" + this.weight + ", currentProbability=" + this.currentProbability + ", maxCount=" + this.maxCount;
    }

    public Class getType() {
        return this.type;
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
