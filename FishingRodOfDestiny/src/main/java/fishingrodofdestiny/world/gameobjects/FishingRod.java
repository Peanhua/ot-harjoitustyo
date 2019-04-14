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
public class FishingRod extends Item {
    public FishingRod() {
        super("fishing rod");
        this.setWeight(3);
        this.setGraphics(new TileGfx("rltiles/tile", 928, 384, 32, 32));
    }
}
