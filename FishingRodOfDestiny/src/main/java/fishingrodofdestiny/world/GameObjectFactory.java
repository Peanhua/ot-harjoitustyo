/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.Consumable;
import fishingrodofdestiny.world.gameobjects.FishingRod;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Hat;
import fishingrodofdestiny.world.gameobjects.Item;
import fishingrodofdestiny.world.gameobjects.KitchenKnife;
import fishingrodofdestiny.world.gameobjects.LeatherJacket;
import fishingrodofdestiny.world.gameobjects.PotionOfRegeneration;
import fishingrodofdestiny.world.gameobjects.Rat;
import fishingrodofdestiny.world.gameobjects.ShortSword;
import java.net.URL;
import org.ini4j.Ini;

/**
 * Create new game objects.
 * 
 * @author joyr
 */
public class GameObjectFactory {
    // The following enum trick is stolen from https://stackoverflow.com/a/548710
    // with the purpose of evading the maximum 20 line length checkstyle rule.
    // TODO: read possible items from text file(s) and generate them using generic weapon, armor, potion, etc. classes
    private interface GameObjectCreator {
        public GameObject create();
        public Class      getJavaClass();
    }
    
    private enum ObjectSwitch implements GameObjectCreator {
        FishingRod() {
            @Override
            public GameObject create() {
                return new FishingRod();
            }
            @Override
            public Class getJavaClass() {
                return FishingRod.class;
            }
        },
        Hat() {
            @Override
            public GameObject create() {
                return new Hat();
            }
            @Override
            public Class getJavaClass() {
                return Hat.class;
            }
        },
        KitchenKnife() {
            @Override
            public GameObject create() {
                return new KitchenKnife();
            }
            @Override
            public Class getJavaClass() {
                return KitchenKnife.class;
            }
        },
        LeatherJacket() {
            @Override
            public GameObject create() {
                return new LeatherJacket();
            }
            @Override
            public Class getJavaClass() {
                return LeatherJacket.class;
            }
        },
        PotionOfRegeneration() {
            @Override
            public GameObject create() {
                return new PotionOfRegeneration();
            }
            @Override
            public Class getJavaClass() {
                return PotionOfRegeneration.class;
            }
        },
        Rat() {
            @Override
            public GameObject create() {
                return new Rat();
            }
            @Override
            public Class getJavaClass() {
                return Rat.class;
            }
        },
        ShortSword() {
            @Override
            public GameObject create() {
                return new ShortSword();
            }
            @Override
            public Class getJavaClass() {
                return ShortSword.class;
            }
        }
    }
    
    // Note that Type and ObjectSwitch enums must be in same order.
    public enum Type {
        FishingRod,
        Hat,
        KitchenKnife,
        LeatherJacket,
        PotionOfRegeneration,
        Rat,
        ShortSword
    }
    
    private final static ObjectSwitch[] OPTIONS = ObjectSwitch.values();
    
    public static GameObject create(Type type) {
        if (type == null) {
            return null;
        }
        return OPTIONS[type.ordinal()].create();
    }
    
    public static Class getJavaClass(Type type) {
        if (type == null) {
            return null;
        }
        return OPTIONS[type.ordinal()].getJavaClass();
    }
    
    
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
        GameObject obj = null;
        try {
            switch (type) {
                case "OBJECT":     return createObject(section, id);
                case "ITEM":       return createItem(section, id);
                case "CONSUMABLE": return createConsumable(section, id);
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
        return item;
    }
    
    private static GameObject createConsumable(Ini.Section section, String id) {
        Consumable consumable = new Consumable(id);
        loadBasics(section, consumable);
        loadGfx(section, consumable);
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
    
    private static void loadBasics(Ini.Section section, GameObject object) {
        Integer hitpoints = section.get("Hitpoints", Integer.class);
        if (hitpoints != null) {
            object.setMaxHitpoints(hitpoints);
        }
        object.setTimeToLive(section.get("TimeToLive", Double.class));
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
}
