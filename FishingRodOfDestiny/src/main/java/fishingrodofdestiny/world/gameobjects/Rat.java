/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.controllers.SimpleAiController;

/**
 *
 * @author joyr
 */
public class Rat extends NonPlayerCharacter {
    public Rat() {
        super();
        this.setController(new SimpleAiController(this));
        this.setName("rat");
        int level = 1 + this.getRandom().nextInt(5);
        this.setCharacterLevel(level);
        this.setAttack(2 + level);
        this.setDefence(20 + level);
        this.setNaturalArmorClass(5 + level / 2);
        this.setMaxHitpoints(5 + this.getRandom().nextInt(5 + level));
        this.setGraphics(new TileGfx("rltiles/nh32", 928, 64, 32, 32));
        this.setupInventoryItems(level);
        this.spawnInventoryItems();
    }
    
    private void setupInventoryItems(int level) {
        int itemCount = this.getRandom().nextInt(level);
        if (itemCount == 0) {
            return;
        }
        this.getGameObjectSpawner().setMaximumTotalCount(itemCount);
        this.getGameObjectSpawner().addType("gold coin",              1, 0.2);
        this.getGameObjectSpawner().addType("kitchen knife",          1, 0.3);
        this.getGameObjectSpawner().addType("apple",                  3, 1.0);
        this.getGameObjectSpawner().addType("potion of healing",      1, 0.1);
        this.getGameObjectSpawner().addType("potion of regeneration", 1, 0.1);
    }
}
