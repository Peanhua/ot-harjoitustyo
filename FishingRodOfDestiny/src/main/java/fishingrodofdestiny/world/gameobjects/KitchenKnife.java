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
public class KitchenKnife extends Weapon {
    
    public KitchenKnife() {
        super("kitchen knife");
        this.setGraphics(new TileGfx("rltiles/nh32", 864, 416, 32, 32));
    }

    @Override
    public int getDamage() {
        return 5;
    }
}
