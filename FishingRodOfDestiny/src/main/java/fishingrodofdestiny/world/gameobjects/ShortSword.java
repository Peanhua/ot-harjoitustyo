/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.TileGfx;

/**
 *
 * @author joyr
 */
public class ShortSword extends Weapon {

    public ShortSword() {
        super("short sword");
        this.setGraphics(new TileGfx("rltiles/nh32", 160, 448, 32, 32));
        this.setWeight(5);
    }
    
    @Override
    public int getDamage() {
        return 20;
    }

    @Override
    public double getChanceToHitMultiplier() {
        return 2.0;
    }
}
