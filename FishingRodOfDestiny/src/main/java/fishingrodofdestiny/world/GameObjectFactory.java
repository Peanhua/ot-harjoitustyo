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

import fishingrodofdestiny.world.gameobjects.Armor;
import fishingrodofdestiny.world.gameobjects.Buff;
import fishingrodofdestiny.world.gameobjects.Consumable;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Item;
import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
import fishingrodofdestiny.world.gameobjects.Weapon;
import java.net.URL;
import org.ini4j.Ini;

/**
 * Create new game objects.
 * <p>
 * Uses singleton pattern, and loads the game object templates from an ini file.
 * 
 * @author joyr
 */
public class GameObjectFactory {
    private static Ini ini = null;

    private static void initialize() {
        if (ini == null) {
            ini = new Ini();
            String fullname = "fishingrodofdestiny/items.ini";
            URL url = GameObjectFactory.class.getClassLoader().getResource(fullname);
            try {
                ini.load(url);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load items: " + e);
            }
        }
    }
    
    /**
     * Create a new GameObject of the given type.
     * 
     * @param objectType The type of the GameObject to create
     * @return A new GameObject
     */
    public static GameObject create(String objectType) {
        if (objectType == null) {
            return null;
        }
        
        initialize();
        
        Ini.Section section = ini.get(objectType);
        String type = section.get("Type");
        if (type == null) {
            throw new RuntimeException("Error while loading '" + objectType + "': missing Type");
        }
        return createByType(section, objectType, type);
    }
    
    private static GameObject createByType(Ini.Section section, String objectType, String type) {
        try {
            switch (type) {
                case "ARMOR":      return createArmor(section, objectType);
                case "CONSUMABLE": return createConsumable(section, objectType);
                case "ITEM":       return createItem(section, objectType);
                case "NPC":        return createNPC(section, objectType);
                case "OBJECT":     return createObject(section, objectType);
                case "WEAPON":     return createWeapon(section, objectType);
                default:           throw new RuntimeException("Uknown type: " + type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while loading '" + objectType + "': " + e);
        }
    }
    
    
    private static GameObject createObject(Ini.Section section, String objectType) {
        GameObject object = new GameObject(objectType);
        loadBasics(section, object);
        loadGfx(section, object);
        return object;
    }
    
    private static GameObject createItem(Ini.Section section, String objectType) {
        Item item = new Item(objectType);
        loadBasics(section, item);
        loadGfx(section, item);
        loadUseBuffs(section, item);
        return item;
    }
    
    private static GameObject createConsumable(Ini.Section section, String objectType) {
        Consumable consumable = new Consumable(objectType);
        loadBasics(section, consumable);
        loadGfx(section, consumable);
        loadUseBuffs(section, consumable);
        loadConsumableBasics(section, consumable);
        loadConsumableSpecials(section, consumable);
        return consumable;
    }
    
    private static GameObject createWeapon(Ini.Section section, String objectType) {
        Weapon weapon = new Weapon(objectType);
        loadBasics(section, weapon);
        loadGfx(section, weapon);
        loadBuffs(section, weapon);
        Integer damage = section.get("Damage", Integer.class);
        if (damage != null) {
            weapon.setDamage(damage);
        }
        Double chanceToHitMultiplier = section.get("ChanceToHitMultiplier", Double.class);
        if (chanceToHitMultiplier != null) {
            weapon.setChanceToHitMultiplier(chanceToHitMultiplier);
        }
        return weapon;
    }
    
    private static GameObject createArmor(Ini.Section section, String objectType) {
        Armor armor = new Armor(objectType);
        loadBasics(section, armor);
        loadGfx(section, armor);
        loadBuffs(section, armor);
        String slot = section.get("Slot");
        if (slot == null) {
            throw new RuntimeException("Missing Slot.");
        }
        armor.setSlot(Armor.Slot.valueOf(slot));
        
        return armor;
    }
    
    private static GameObject createNPC(Ini.Section section, String objectType) {
        NonPlayerCharacter npc = new NonPlayerCharacter(objectType);
        loadBasics(section, npc);
        loadGfx(section, npc);
        loadLevel(section, npc);
        loadInventoryItems(section, npc.getCharacterLevel(), npc);
        loadAttackBuffs(section, npc);
        return npc;
    }
    
    private static void loadBasics(Ini.Section section, GameObject object) {
        Integer hitpoints = section.get("Hitpoints", Integer.class);
        if (hitpoints != null) {
            object.setMaxHitpoints(hitpoints);
        }
        object.setTimeToLive(section.get("TimeToLive", Double.class));
        Integer weight = section.get("Weight", Integer.class);
        if (weight != null) {
            object.setWeight(weight);
        }
    }
    
    private static void loadGfx(Ini.Section section, GameObject object) {
        String  tileSet    = section.get("TileSet");
        Integer tileX      = section.get("TileX", Integer.class);
        Integer tileY      = section.get("TileY", Integer.class);
        Integer tileWidth  = section.get("TileWidth", Integer.class);
        Integer tileHeight = section.get("TileHeight", Integer.class);
        
        if (tileSet == null || tileX == null || tileY == null || tileWidth == null || tileHeight == null) {
            throw new RuntimeException("Missing Gfx information.");
        }
        
        object.setGraphics(new TileGfx(tileSet, tileX, tileY, tileWidth, tileHeight));
    }
    
    private static void loadUseBuffs(Ini.Section section, Item item) {
        // TODO: support multiple buffs
        String buffType = section.get("BuffType");
        if (buffType != null) {
            Double buffTime = section.get("BuffTime", Double.class);
            Double buffAmount = section.get("BuffAmount", Double.class);
            if (buffTime == null || buffAmount == null) {
                throw new RuntimeException("Missing BuffTime or BuffAmount");
            }
            item.addUseBuff(new Buff(buffTime, Buff.Type.valueOf(buffType), buffAmount));
        }
    }
    
    private static void loadAttackBuffs(Ini.Section section, Character character) {
        // TODO: support multiple buffs
        // TODO: utilize same code for loadUseBuffs(), loadBuffs(), and loadAttackBuffs()
        String buffType = section.get("AttackBuffType");
        if (buffType != null) {
            Double buffTime = section.get("AttackBuffTime", Double.class);
            Double buffAmount = section.get("AttackBuffAmount", Double.class);
            Double buffChance = section.get("AttackBuffChance", Double.class);
            if (buffTime == null || buffAmount == null || buffChance == null) {
                throw new RuntimeException("Missing AttackBuffTime, AttackBuffAmount, or AttackBuffChance");
            }
            character.addAttackBuff(buffChance, new Buff(buffTime, Buff.Type.valueOf(buffType), buffAmount));
        }
    }
    
    private static void loadBuffs(Ini.Section section, Item item) {
        String buffType = section.get("BuffType");
        if (buffType != null) {
            Double buffAmount = section.get("BuffAmount", Double.class);
            if (buffAmount == null) {
                throw new RuntimeException("Missing BuffAmount");
            }
            item.addBuff(new Buff(item, Buff.Type.valueOf(buffType), buffAmount));
        }
    }
    
    private static void loadLevel(Ini.Section section, NonPlayerCharacter npc) {
        Integer min = section.get("LevelMin", Integer.class);
        Integer max = section.get("LevelMax", Integer.class);
        if (min == null || max == null) {
            throw new RuntimeException("Missing LevelMin or LevelMax");
        }
        int level = min;
        if (max > min) {
            level += npc.getRandom().nextInt(max - min);
        }
        npc.setCharacterLevel(level);
        npc.setMaxHitpoints(loadLeveledAttribute(section, level, "Hitpoints"));
        npc.setAttack(loadLeveledAttribute(section, level, "Attack"));
        npc.setDefence(loadLeveledAttribute(section, level, "Defence"));
        npc.setNaturalArmorClass(loadLeveledAttribute(section, level, "ArmorClass"));
        npc.setNaturalRegeneration(loadLeveledAttribute(section, level, "Regeneration"));
    }
    
    private static int loadLeveledAttribute(Ini.Section section, int level, String attribute) {
        Double value = section.get(attribute, Double.class);
        if (value == null) {
            throw new RuntimeException("Missing " + attribute);
        }
        
        Double add = section.get("LevelAdd" + attribute, Double.class);
        if (add == null) {
            throw new RuntimeException("Missing LevelAdd" + attribute);
        }

        Double mul = section.get("LevelMul" + attribute, Double.class);
        if (mul == null) {
            throw new RuntimeException("Missing LevelMul" + attribute);
        }
        
        for (int i = 1; i <= level; i++) {
            value += add;
            value *= mul;
        }

        return (int) (double) value;
    }

    private static void loadInventoryItems(Ini.Section section, int level, GameObject object) {
        GameObjectSpawner gameObjectSpawner = loadInventoryItemSpawner(section);

        int max = loadLeveledAttribute(section, level, "InventoryItemCountMax");
        if (max == 0) {
            return;
        }
        
        int count = object.getRandom().nextInt(max);
        
        for (int i = 0; i < count; i++) {
            GameObject item = GameObjectFactory.create(gameObjectSpawner.getNextObjectType(object.getRandom(), object));
            if (item != null) {
                item.getLocation().moveTo(object);
            }
        }
    }

    private static GameObjectSpawner loadInventoryItemSpawner(Ini.Section section) {
        GameObjectSpawner gameObjectSpawner = new GameObjectSpawner();
        
        String[] objectIds = section.getAll("InventoryItem",       String[].class);
        int[]    maxes     = section.getAll("InventoryItemMax",    int[].class);
        double[] weights   = section.getAll("InventoryItemWeight", double[].class);
        if (objectIds.length != maxes.length || objectIds.length != weights.length) {
            throw new RuntimeException("Mismatched lengths for inventory item values.");
        }
        
        for (int i = 0; i < objectIds.length; i++) {
            gameObjectSpawner.addType(objectIds[i], maxes[i], weights[i]);
        }
        
        return gameObjectSpawner;
    }

    private static void loadConsumableBasics(Ini.Section section, Consumable consumable) {
        String useVerb = section.get("UseVerb");
        if (useVerb != null) {
            consumable.setUseVerb(useVerb);
        }
        Integer healOnUse = section.get("HealOnUse", Integer.class);
        if (healOnUse != null) {
            consumable.setHealOnUse(healOnUse);
        }
        Integer healPercentageOnUse = section.get("HealOnUse%", Integer.class);
        if (healPercentageOnUse != null) {
            consumable.setHealOnUse(healPercentageOnUse);
            consumable.setHealOnUsePercentage(true);
        }
    }
    
    private static void loadConsumableSpecials(Ini.Section section, Consumable consumable) {
        String[] specials = section.getAll("Special", String[].class);
        for (int i = 0; i < specials.length; i++) {
            switch (specials[i]) {
                case "ANTIVENOM":
                    consumable.setAntivenom();
                    break;
                case "REVEAL_MAP":
                    consumable.setRevealsMap();
                    break;
                default:
                    throw new RuntimeException("Unknown consumable special: " + specials[i]);
            }
        }
    }
}
