/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.Armor;
import fishingrodofdestiny.world.gameobjects.Buff;
import fishingrodofdestiny.world.gameobjects.Consumable;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Item;
import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
import fishingrodofdestiny.world.gameobjects.Weapon;
import java.net.URL;
import org.ini4j.Ini;

/**
 * Create new game objects.
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
    
    public static GameObject create(String id) {
        if (id == null) {
            return null;
        }
        
        initialize();
        
        Ini.Section section = ini.get(id);
        String type = section.get("Type");
        if (type == null) {
            throw new RuntimeException("Error while loading '" + id + "': missing Type");
        }
        return createByType(section, id, type);
    }
    
    private static GameObject createByType(Ini.Section section, String id, String type) {
        GameObject obj = null;
        try {
            switch (type) {
                case "OBJECT":     return createObject(section, id);
                case "ITEM":       return createItem(section, id);
                case "CONSUMABLE": return createConsumable(section, id);
                case "WEAPON":     return createWeapon(section, id);
                case "ARMOR":      return createArmor(section, id);
                case "NPC":        return createNPC(section, id);
                default:           throw new RuntimeException("Uknown type: " + type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while loading '" + id + "': " + e);
        }
    }
    
    
    private static GameObject createObject(Ini.Section section, String id) {
        GameObject object = new GameObject(id);
        loadBasics(section, object);
        loadGfx(section, object);
        return object;
    }
    
    private static GameObject createItem(Ini.Section section, String id) {
        Item item = new Item(id);
        loadBasics(section, item);
        loadGfx(section, item);
        loadUseBuffs(section, item);
        return item;
    }
    
    private static GameObject createConsumable(Ini.Section section, String id) {
        Consumable consumable = new Consumable(id);
        loadBasics(section, consumable);
        loadGfx(section, consumable);
        loadUseBuffs(section, consumable);
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
        return consumable;
    }
    
    private static GameObject createWeapon(Ini.Section section, String id) {
        Weapon weapon = new Weapon(id);
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
    
    private static GameObject createArmor(Ini.Section section, String id) {
        Armor armor = new Armor(id);
        loadBasics(section, armor);
        loadGfx(section, armor);
        loadBuffs(section, armor);
        String slot = section.get("Slot");
        if (slot == null) {
            throw new RuntimeException("Missing Slot.");
        }
        armor.setSlot(Armor.Slot.nameToSlot(slot));
        
        return armor;
    }
    
    private static GameObject createNPC(Ini.Section section, String id) {
        NonPlayerCharacter npc = new NonPlayerCharacter(id);
        loadBasics(section, npc);
        loadGfx(section, npc);
        loadLevel(section, npc);
        loadInventoryItems(section, npc.getCharacterLevel(), npc);
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
            item.addUseBuff(new Buff(buffTime, Buff.Type.nameToType(buffType), buffAmount));
        }
    }
    
    private static void loadBuffs(Ini.Section section, Item item) {
        String buffType = section.get("BuffType");
        if (buffType != null) {
            Double buffAmount = section.get("BuffAmount", Double.class);
            if (buffAmount == null) {
                throw new RuntimeException("Missing BuffAmount");
            }
            item.addUseBuff(new Buff(item, Buff.Type.nameToType(buffType), buffAmount));
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
            GameObject item = GameObjectFactory.create(gameObjectSpawner.getNextObjectId(object.getRandom(), object));
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
}
