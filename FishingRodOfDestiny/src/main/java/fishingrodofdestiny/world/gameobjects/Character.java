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

import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.controllers.Controller;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;


/**
 * A "living" GameObject, controlled by a Controller.
 * 
 * @author joyr
 */
public abstract class Character extends GameObject {
    
    private final static int INITIAL_CARRYING_CAPACITY = 20;
    
    private int        attack;
    private int        defence;
    private int        carryingCapacity;
    private int        naturalArmorClass;
    private double     naturalRegeneration;
    private int        characterLevel;
    private int        experiencePoints;
    private Controller controller;
    private final CharacterEquipment   equipment;
    private final CharacterCombatModel combatModel;
    private double     accumulatedRegeneration;
    private long       actionsTaken;
    private final List<Buff>   buffs;

    public Character(String objectType) {
        super(objectType);
        this.attack              = 0;
        this.defence             = 0;
        this.carryingCapacity    = Character.INITIAL_CARRYING_CAPACITY;
        this.naturalArmorClass   = 0;
        this.naturalRegeneration = 0.0;
        this.characterLevel      = 0;
        this.experiencePoints    = 0;
        this.controller          = null;
        this.equipment           = new CharacterEquipment();
        this.combatModel         = new CharacterCombatModel(this);
        this.accumulatedRegeneration = 0.0;
        this.actionsTaken        = 0;
        this.buffs               = new ArrayList<>();
        this.setDrawingOrder(100);
    }
    
    @Override
    protected void onDestroyed(GameObject instigator) {
        this.addMessage("You die!");
        Tile tile = this.getLocation().getContainerTile();
        if (tile != null) {
            GameObject splatter = GameObjectFactory.create("pool of blood");
            splatter.getLocation().moveTo(tile);
        }
        super.onDestroyed(instigator);
    }
    
    @Override
    public void onDestroyTarget(GameObject target) {
        int xp = 1;
        
        if (target instanceof Character) {
            Character targetCharacter = (Character) target;
            xp = 1 + targetCharacter.getCharacterLevel() * 3;
        }
        
        this.adjustExperiencePoints(xp);

        super.onDestroyTarget(target);
    }
    
    /**
     * Checks whether the given GameObject is a valid target for attacking.
     * 
     * @param target The checked GameObject
     * @return True if the target is a valid target
     */
    public boolean isValidAttackTarget(GameObject target) {
        return target != this;
    }

    
    /**
     * Return the number of actions this Character has performed.
     * 
     * @return Number of actions
     */
    public long getActionsTaken() {
        return this.actionsTaken;
    }
    
    public final CharacterEquipment getEquipment() {
        return this.equipment;
    }
    
    public final CharacterCombatModel getCombatModel() {
        return this.combatModel;
    }
    
    @Override
    public List<GameObject> getValidAttackTargets(Tile tile) {
        if (tile == null) {
            return null;
        }
        
        List<GameObject> targets = new ArrayList<>();

        tile.getInventory().getObjects().forEach(obj -> {
            if (this.isValidAttackTarget(obj)) {
                targets.add(obj);
            }
        });
        
        return targets.isEmpty() ? null : targets;
    }        

    /**
     * Return list of GameObjects that can be picked up by this Character.
     * 
     * @return A list of GameObjects that can be picked up
     */
    public List<GameObject> getValidPickUpTargets() {
        List<GameObject> items = new ArrayList<>();
        this.getLocation().getContainerTile().getInventory().getObjects().forEach(object -> {
            if (object.getCanBePickedUp()) {
                items.add(object);
            }
        });
        return items;
    }
    
    public final void setAttack(int amount) {
        this.attack = amount;
    }
    
    public final void setDefence(int amount) {
        this.defence = amount;
    }
    
    public final void setCarryingCapacity(int amount) {
        this.carryingCapacity = amount;
    }
    
    public final void setNaturalArmorClass(int ac) {
        this.naturalArmorClass = ac;
    }
    
    public final void setNaturalRegeneration(double amount) {
        this.naturalRegeneration = amount;
    }
    
    public final int getCharacterLevel() {
        return this.characterLevel;
    }
    
    public final void setCharacterLevel(int level) {
        this.characterLevel = level;
    }

    /**
     * Get the amount of total experience points this Character needs in order to level up to the given level.
     * 
     * @param level The level
     * @return Amount of experience points required for the level
     */
    public int getExperiencePointsForCharacterLevel(int level) {
        final double maxIncreasePerLevel = 20000.0;

        double xp = 0.0;
        for (int i = 1; i <= level; i++) {
            double increase = (double) i * 25.0;
            if (increase > maxIncreasePerLevel) {
                increase = maxIncreasePerLevel;
            }
            xp += increase;
        }
        
        return (int) xp;
    }
    
    /**
     * Increase the character level by one.
     * 
     * @return A displayable text describing what was gained by the level increase.
     */
    public String increaseCharacterLevel() {
        this.characterLevel++;

        String increased = this.getStatToIncrease();
        int amount = Math.min(5, 1 + this.characterLevel / 2);
        switch (increased) {
            case "attack":
                this.adjustAttack(amount);
                break;
            case "defence":
                this.adjustDefence(amount);
                break;
            case "carrying capacity":
                this.adjustCarryingCapacity(amount);
                break;
            case "hit points":
                this.adjustMaxHitpoints(amount);
                break;
        }
        return increased + " +" + amount;
    }
    
    /**
     * Return the name of a random stat what to increase on level up.
     * 
     * @return The name of the stat to increase
     */
    private String getStatToIncrease() {
        String increased;
        int base = 20;
        int attackChance  = this.getRandom().nextInt(base + this.attack);
        int defenceChance = this.getRandom().nextInt(base + this.defence);
        int carryChance   = this.getRandom().nextInt(base + this.carryingCapacity - Character.INITIAL_CARRYING_CAPACITY); // Ignore the initial 20.
        int hpChance      = this.getRandom().nextInt(base + super.getMaxHitpoints() - 1); // Ignore the initial 1.
        if (attackChance > defenceChance && attackChance > carryChance && attackChance > carryChance) {
            increased = "attack";
        } else if (defenceChance > carryChance && defenceChance > hpChance) {
            increased = "defence";
        } else if (carryChance > hpChance) {
            increased = "carrying capacity";
        } else {
            increased = "hit points";
        }
        return increased;
    }
    
    
    /**
     * Add buff to this Character.
     * 
     * @param buff The buff to add
     */
    public void addBuff(Buff buff) {
        for (int i = 0; i < this.buffs.size(); i++) {
            Buff b = this.buffs.get(i);
            if (b == null || !b.isAlive()) {
                this.buffs.set(i, buff);
                return;
            }
        }
        this.buffs.add(buff);
        this.onChange.notifyObservers();
    }
    
    
    /**
     * Get all active buffs affecting this Character.
     * 
     * @return List of active buffs
     */
    public List<Buff> getBuffs() {
        List<Buff> rv = new ArrayList<>();
        this.buffs.forEach(b -> {
            if (b != null && b.isAlive()) {
                rv.add(b);
            }
        });
        return rv;
    }
    
    
    /**
     * Return the total amount of bonuses buffs give for the given buff type.
     * 
     * @param forType The buff type to look for
     * @return The total amount of bonuses
     */
    public double getBuffBonuses(Buff.Type forType) {
        double bonuses = 0;
        
        if (this.equipment.getWeapon() != null) {
            bonuses += this.equipment.getWeapon().getBuffBonuses(forType);
        }
        for (Armor.Slot slot : Armor.Slot.values()) {
            Armor armor = this.equipment.getArmor(slot);
            if (armor != null) {
                bonuses += armor.getBuffBonuses(forType);
            }
        }
        for (Buff buff : this.buffs) {
            if (buff != null && buff.isAlive()) {
                bonuses += buff.getBonus(forType);
            }
        }
        
        return bonuses;
    }
    
    
    @Override
    public int getMaxHitpoints() {
        return super.getMaxHitpoints() + (int) this.getBuffBonuses(Buff.Type.HITPOINT);
    }
    
    
    public int getAttack() {
        return this.attack + (int) this.getBuffBonuses(Buff.Type.ATTACK);
    }
    
    public int getDefence() {
        return this.defence + (int) this.getBuffBonuses(Buff.Type.DEFENCE);
    }
    
    public int getCarryingCapacity() {
        return this.carryingCapacity + (int) this.getBuffBonuses(Buff.Type.CARRY);
    }
    
    public int getExperiencePoints() {
        return this.experiencePoints;
    }
    
    public int getArmorClass() {
        return this.naturalArmorClass + (int) this.getBuffBonuses(Buff.Type.ARMOR_CLASS);
    }
    
    /**
     * Adjust the attack attribute.
     * 
     * @param amount The amount to adjust
     */
    public void adjustAttack(int amount) {
        this.attack += amount;
        if (this.attack < 0) {
            this.attack = 0;
        }
        this.onChange.notifyObservers();
    }
    
    /**
     * Adjust the defence attribute.
     * 
     * @param amount The amount to adjust
     */
    public void adjustDefence(int amount) {
        this.defence += amount;
        if (this.defence < 0) {
            this.defence = 0;
        }
        this.onChange.notifyObservers();
    }
    
    /**
     * Adjust the carrying capacity.
     * 
     * @param amount The amount to adjust
     */
    public void adjustCarryingCapacity(int amount) {
        this.carryingCapacity += amount;
        if (this.carryingCapacity < 0) {
            this.carryingCapacity = 0;
        }
        this.onChange.notifyObservers();
    }
    
    /**
     * Adjust the experience points.
     * 
     * @param amount The amount to adjust
     */
    public void adjustExperiencePoints(int amount) {
        this.experiencePoints += amount;
        this.onChange.notifyObservers();
    }

    /**
     * Return the Controller controlling this Character.
     * 
     * @return The controlling Controller
     */
    public Controller getController() {
        return this.controller;
    }
    
    protected final void setController(Controller controller) {
        this.controller = controller;
    }
    
    
    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        
        if (!this.isAlive()) {
            return;
        }
        
        this.regenerationTick(deltaTime);
        this.buffTick(deltaTime);
        
        if (this.controller == null) {
            return;
        }
        
        Action nextAction = this.controller.getNextAction();
        if (nextAction != null) {
            this.actionsTaken++;
            this.onChange.notifyObservers();
            nextAction.act(this);
        }
    }
    
    
    private void regenerationTick(double deltaTime) {
        this.accumulatedRegeneration += deltaTime * this.getRegenerationPerSecond();
        int amount = (int) this.accumulatedRegeneration;
        if (amount != 0) {
            this.adjustHitpoints(amount);
            this.accumulatedRegeneration -= amount;
        }
    }
    
    private double getRegenerationPerSecond() {
        return this.naturalRegeneration + this.getBuffBonuses(Buff.Type.REGENERATION) - this.getBuffBonuses(Buff.Type.POISON);
    }
    
    private void buffTick(double deltaTime) {
        this.buffs.forEach(buff -> {
            if (buff != null && buff.isAlive()) {
                buff.tick(deltaTime);
            }
        });
    }
    
    
    @Override
    public int getFallDamage(int height) {
        return height / 2 + this.getWeight() / 10;
    }
    
    @Override
    public void onHit(GameObject instigator, int damage) {
        super.onHit(instigator, damage);
        this.accumulatedRegeneration = 0.0;
    }
}
