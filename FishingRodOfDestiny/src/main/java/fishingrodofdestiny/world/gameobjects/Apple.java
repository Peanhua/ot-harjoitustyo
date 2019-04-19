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
public class Apple extends Consumable {
    
    public Apple() {
        super("apple", "eat");
        this.setGraphics(new TileGfx("rltiles/nh32", 448, 672, 32, 32));
    }
    
    @Override
    public void useItem(GameObject instigator, GameObject user) {
        super.useItem(instigator, user);
        user.adjustHitpoints(1);
    }
}
