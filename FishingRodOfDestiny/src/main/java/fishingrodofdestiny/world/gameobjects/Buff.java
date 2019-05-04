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
package fishingrodofdestiny.world.gameobjects;

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
        POISON,
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
    
    public final String getName() {
        String name = this.bonuses.keySet().stream().map(b -> b.toString()).reduce(null, (a, b) -> (a != null ? a + "+" : "") + b);
        if (name == null) {
            name = "unknown";
        }
        return name;
    }
}
