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
public class Hat extends Armor {
    public Hat() {
        super("hat", Slot.HEAD);
        this.setGraphics(new TileGfx("rltiles/nh32", 576, 480, 32, 32));
    }
    
    @Override
    public int getArmorClassBonus() {
        return 2;
    }
}
