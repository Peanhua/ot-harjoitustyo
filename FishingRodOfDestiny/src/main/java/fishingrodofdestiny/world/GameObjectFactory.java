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
import fishingrodofdestiny.world.gameobjects.GoldCoin;
import fishingrodofdestiny.world.gameobjects.Hat;
import fishingrodofdestiny.world.gameobjects.KitchenKnife;
import fishingrodofdestiny.world.gameobjects.LeatherJacket;
import fishingrodofdestiny.world.gameobjects.Rat;

/**
 * Create new game objects.
 * 
 * @author joyr
 */
public class GameObjectFactory {
    // The following enum trick is stolen from https://stackoverflow.com/a/548710
    // with the purpose of evading the maximum 20 line length checkstyle rule.
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
        GoldCoin() {
            @Override
            public GameObject create() {
                return new GoldCoin();
            }
            @Override
            public Class getJavaClass() {
                return GoldCoin.class;
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
        Rat() {
            @Override
            public GameObject create() {
                return new Rat();
            }
            @Override
            public Class getJavaClass() {
                return Rat.class;
            }
        }
    }
    
    // Note that Type and ObjectSwitch enums must be in same order.
    public enum Type {
        Apple,
        BloodSplatter,
        FishingRod,
        GoldCoin,
        Hat,
        KitchenKnife,
        LeatherJacket,
        Rat
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
}
