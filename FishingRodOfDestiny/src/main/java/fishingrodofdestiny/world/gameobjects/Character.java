/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.controllers.Controller;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
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
    private Weapon     weapon;
    private final HashMap<Armor.Slot, Armor> equippedArmor;
    private double     accumulatedRegeneration;
    private long       actionsTaken;
    private final List<Buff> buffs;

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
        this.weapon              = null;
        this.equippedArmor       = new HashMap<>();
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
    
    
    public boolean isValidAttackTarget(GameObject target) {
        return target != this;
    }

    
    public long getActionsTaken() {
        return this.actionsTaken;
    }
    
    
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.onChange.notifyObservers();
    }
    
    public Weapon getWeapon() {
        return this.weapon;
    }
    
    public void setArmor(Armor armor) {
        this.equippedArmor.put(armor.getSlot(), armor);
        this.onChange.notifyObservers();
    }
    
    public void removeArmor(Armor.Slot fromSlot) {
        this.equippedArmor.remove(fromSlot);
        this.onChange.notifyObservers();
    }
    
    public Armor getArmor(Armor.Slot slot) {
        return this.equippedArmor.get(slot);
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
    
    
    public void addBuff(Buff buff) {
        for (int i = 0; i < this.buffs.size(); i++) {
            Buff b = this.buffs.get(i);
            if (b == null || !b.isAlive()) {
                this.buffs.set(i, buff);
                return;
            }
        }
        this.buffs.add(buff);
    }
    
    
    public int getBuffBonuses(Buff.Type forType) {
        int bonuses = 0;
        
        if (this.weapon != null) {
            bonuses += this.weapon.getBuffBonuses(forType);
        }
        for (Armor.Slot slot : Armor.Slot.values()) {
            Armor armor = this.getArmor(slot);
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
        return super.getMaxHitpoints() + this.getBuffBonuses(Buff.Type.HITPOINT);
    }
    
    
    public int getAttack() {
        return this.attack + this.getBuffBonuses(Buff.Type.ATTACK);
    }
    
    public int getDefence() {
        return this.defence + this.getBuffBonuses(Buff.Type.DEFENCE);
    }
    
    public int getCarryingCapacity() {
        return this.carryingCapacity + this.getBuffBonuses(Buff.Type.CARRY);
    }
    
    public int getExperiencePoints() {
        return this.experiencePoints;
    }
    
    public int getArmorClass() {
        return this.naturalArmorClass + this.getBuffBonuses(Buff.Type.ARMOR_CLASS);
    }
    
    /**
     * Calculate and return the damage this character does per hit.
     * 
     * @return amount of damage per hit
     */
    public int getDamage() {
        int damage = 1;
        
        damage += this.getAttack() / 3;
        
        if (this.weapon != null) {
            damage += this.weapon.getDamage();
        }
        
        return damage;
    }
    
    /**
     * Calculate and return the chance to hit the target.
     * 
     * @param target At who the hit is aimed to.
     * 
     * @return chance in the range of [0..1]
     */
    public double getChanceToHit(GameObject target) {
        if (target instanceof Character) {
            Character targetCharacter = (Character) target;
            double def = targetCharacter.getDefence();
            if (def > 0.0) {
                // Clamp the minimum chance to 5%:
                double chance = Math.max(0.05, (double) this.getAttack() / def);
                
                if (this.weapon != null) {
                    chance *= this.weapon.getChanceToHitMultiplier();
                }
                
                return chance;
            }
        }
        return 1.0;
    }
    
    /**
     * Calculate how much the damage is reduced based on armor class and other factors.
     * 
     * @param damage The damage before reduction.
     * @return The damage that should be subtracted prior calling this.hit().
     */
    public int getDamageReduction(int damage) {
        int ac = Math.min(100, this.getArmorClass()); // clamp max ac
        double acModifier = (double) ac * 0.01;
        double damageReduction = (double) damage * 0.9 * acModifier; // not all damage can be reduced by armor class
        return (int) damageReduction;
    }

    
    public void adjustAttack(int amount) {
        this.attack += amount;
        if (this.attack < 0) {
            this.attack = 0;
        }
        this.onChange.notifyObservers();
    }
    
    public void adjustDefence(int amount) {
        this.defence += amount;
        if (this.defence < 0) {
            this.defence = 0;
        }
        this.onChange.notifyObservers();
    }
    
    public void adjustCarryingCapacity(int amount) {
        this.carryingCapacity += amount;
        if (this.carryingCapacity < 0) {
            this.carryingCapacity = 0;
        }
        this.onChange.notifyObservers();
    }
    
    public void adjustExperiencePoints(int amount) {
        this.experiencePoints += amount;
        this.onChange.notifyObservers();
    }

    
    public Controller getController() {
        return this.controller;
    }
    
    protected final void setController(Controller controller) {
        this.controller = controller;
    }
    
    
    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        
        this.regenerationTick(deltaTime);
        this.buffTick(deltaTime);
        
        if (!this.isAlive()) {
            return;
        }
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
        if (amount > 0) {
            this.adjustHitpoints(amount);
            this.accumulatedRegeneration -= amount;
        }
    }
    
    public final double getRegenerationPerSecond() {
        return this.naturalRegeneration + this.getBuffBonuses(Buff.Type.REGENERATION);
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
