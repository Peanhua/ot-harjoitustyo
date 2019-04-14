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
public class GoldCoin extends Item {
    public GoldCoin() {
        super("gold coin");
        this.setGraphics(new TileGfx("rltiles/nh32", 192, 832, 32, 32));
    }
}
