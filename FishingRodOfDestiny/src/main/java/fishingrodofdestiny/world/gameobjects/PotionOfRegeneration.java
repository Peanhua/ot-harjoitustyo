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
public class PotionOfRegeneration extends Consumable {
    public PotionOfRegeneration() {
        super("potion of regeneration", "drink");
        this.setGraphics(new TileGfx("rltiles/nh32", 224, 704, 32, 32));
    }

    @Override
    public void useItem(GameObject instigator, GameObject user) {
        super.useItem(instigator, user);
        if (user instanceof Character) {
            ((Character) user).addBuff(new Buff(20.0, Buff.Type.REGENERATION, 1.0));
        }
    }
}
