/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.Apple;
import fishingrodofdestiny.world.gameobjects.BloodSplatter;
import fishingrodofdestiny.world.gameobjects.FishingRod;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Hat;
import fishingrodofdestiny.world.gameobjects.Item;
import fishingrodofdestiny.world.gameobjects.PotionOfHealing;
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
        Apple() {
            @Override
            public GameObject create() {
                return new Apple();
            }
            @Override
            public Class getJavaClass() {
                return Apple.class;
            }
        },
        BloodSplatter() {
            @Override
            public GameObject create() {
                return new BloodSplatter();
            }
            @Override
            public Class getJavaClass() {
                return BloodSplatter.class;
            }
        },
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
        PotionOfHealing() {
            @Override
            public GameObject create() {
                return new PotionOfHealing();
            }
            @Override
            public Class getJavaClass() {
                return PotionOfHealing.class;
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
        Apple,
        BloodSplatter,
        FishingRod,
        Hat,
        KitchenKnife,
        LeatherJacket,
        PotionOfHealing,
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
            if (type.equals("ITEM")) {
                return createItem(section, id);
            } else {
                throw new RuntimeException("Uknown type: " + type);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while loading '" + id + "': " + e);
        }
    }
    
    private static GameObject createItem(Ini.Section section, String id) {
        Item item = new Item(id);
        loadGfx(section, item);
        return item;
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
