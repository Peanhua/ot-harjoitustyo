/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.gameobjects.GameObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joyr
 */
public class Buff {
    public enum Type {
        ARMOR_CLASS,
        ATTACK,
        CARRY,
        DEFENCE,
        HITPOINT,
        REGENERATION;
    }
    private boolean           alive;
    private GameObject        linkedTo;
    private double            timeToLive;
    private Map<Type, Double> bonuses;
    
    private Buff() {
        this.alive   = true;
        this.bonuses = new HashMap<>();
    }
    
    public Buff(double timeToLive) {
        this();
        this.timeToLive = timeToLive;
        this.linkedTo   = null;
    }

    public Buff(double timeToLive, Type type, double amount) {
        this(timeToLive);
        this.setBonus(type, amount);
    }
    
    public Buff(GameObject linkedTo) {
        this();
        this.timeToLive = 0.0;
        this.linkedTo   = linkedTo;
    }
    
    public Buff(GameObject linkedTo, Type type, double amount) {
        this(linkedTo);
        this.setBonus(type, amount);
    }
    
    public Buff(Buff source) {
        this();
        this.linkedTo   = source.linkedTo;
        this.timeToLive = source.timeToLive;
        source.bonuses.keySet().forEach(type -> {
            this.setBonus(type, source.getBonus(type));
        });
    }

    
    public final boolean isAlive() {
        return this.alive;
    }
    
    public final void tick(double deltaTime) {
        if (this.linkedTo == null) {
            this.timeToLive -= deltaTime;
            if (this.timeToLive < 0.0) {
                this.alive = false;
            }
        }
    }
    
    public final double getBonus(Type forType) {
        return this.bonuses.getOrDefault(forType, 0.0);
    }
    
    protected final void setBonus(Type forType, double bonus) {
        this.bonuses.put(forType, bonus);
    }
}
