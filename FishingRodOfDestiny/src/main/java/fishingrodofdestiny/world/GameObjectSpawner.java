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
