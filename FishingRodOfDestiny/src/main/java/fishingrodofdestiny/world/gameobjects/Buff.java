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
 * A temporary bonus of some attribute(s) to a Character.
 * <p>
 * There are two variants.
 * One is linked to an GameObject, and will be active only when the linked GameObject is being used by the Character.
 * The other variant is timed, and will die once the timer has run out.
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

    
    /**
     * Returns whether this buff is active or not.
     * 
     * @return True if this buff is active
     */
    public final boolean isAlive() {
        return this.alive;
    }
    
    /**
     * Kill this buff, making it inactive.
     */
    public final void destroy() {
        this.alive = false;
    }
    
    /**
     * Advance the time on this buff.
     * 
     * @param deltaTime Seconds since last advance
     */
    public final void tick(double deltaTime) {
        if (this.linkedTo == null) {
            this.timeToLive -= deltaTime;
            if (this.timeToLive < 0.0) {
                this.alive = false;
            }
        }
    }
    
    /**
     * Returns the bonus amount this buff gives for the given type.
     * 
     * @param forType The attribute type
     * @return The bonus amount
     */
    public final double getBonus(Type forType) {
        return this.bonuses.getOrDefault(forType, 0.0);
    }
    
    /**
     * Set the amount of bonus for the given type.
     * 
     * @param forType The attribute type
     * @param bonus The bonus amount
     */
    protected final void setBonus(Type forType, double bonus) {
        this.bonuses.put(forType, bonus);
    }
    
    /**
     * Returns a displayable name for this buff.
     * 
     * @return A displayable name
     */
    public final String getName() {
        String name = this.bonuses.keySet().stream().map(b -> b.toString()).reduce(null, (a, b) -> (a != null ? a + "+" : "") + b);
        if (name == null) {
            name = "unknown";
        }
        return name;
    }
}
