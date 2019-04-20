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
public class PotionOfHealing extends Consumable {
    
    public PotionOfHealing() {
        super("potion of healing", "drink");
        this.setGraphics(new TileGfx("rltiles/nh32", 128, 704, 32, 32));
    }
    
    @Override
    public void useItem(GameObject instigator, GameObject user) {
        super.useItem(instigator, user);
        user.adjustHitpoints(user.getMaxHitpoints());
    }
}
