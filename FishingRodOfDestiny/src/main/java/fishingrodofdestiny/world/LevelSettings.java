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
    private final List<Enemy> enemies;
    private int               minimumNumberOfEnemies;
    private int               maximumNumberOfEnemies;
    
    public LevelSettings() {
        this.enemies = new ArrayList<>();
        this.minimumNumberOfEnemies = 0;
        this.maximumNumberOfEnemies = 1;
    }
    
    public void addEnemyType(Class type, int maximumCount, double weight) {
        if (!NonPlayerCharacter.class.isAssignableFrom(type)) {
            throw new RuntimeException("Illegal type " + type + ": it is not a subclass of NonPlayerCharacter.");
        }
        
        this.enemies.add(new Enemy(type, maximumCount, weight));
    }


    public Class getEnemyForLevel(Random random, Level level) {
        List<Enemy> possibleEnemies = new ArrayList<>();
        for (int i = 0; i < this.enemies.size(); i++) {
            Enemy enemy = this.enemies.get(i);
            if (level.getObjectCount(enemy.getType()) < enemy.getMaxCount()) {
                possibleEnemies.add(enemy);
            }
        }

        if (possibleEnemies.isEmpty()) {
            return null;
        }
        
        possibleEnemies.forEach(enemy -> enemy.setProbabilityModifier(random.nextDouble()));
        Collections.sort(possibleEnemies);
        
        return possibleEnemies.get(0).getType();
    }
}


class Enemy implements Comparable<Enemy> {
    private Class  type;
    private double weight;
    private double currentProbability;
    private int    maxCount;

    public Enemy(Class type, int maxCount, double weight) {
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
    public int compareTo(Enemy other) {
        if (other.currentProbability > this.currentProbability) {
            return 1;
        } else if (other.currentProbability < this.currentProbability) {
            return -1;
        } else {
            return 0;
        }
    }
}
