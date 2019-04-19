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
    }
}
