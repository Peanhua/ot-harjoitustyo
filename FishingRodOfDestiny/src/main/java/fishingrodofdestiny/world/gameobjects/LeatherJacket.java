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
public class LeatherJacket extends Armor {
    public LeatherJacket() {
        super("leather jacket", Armor.Slot.CHEST);
        this.setGraphics(new TileGfx("rltiles/nh32", 0, 544, 32, 32));
        this.setWeight(7);
        this.addBuff(new Buff(this, Buff.Type.ARMOR_CLASS, 20.0));
    }
}
