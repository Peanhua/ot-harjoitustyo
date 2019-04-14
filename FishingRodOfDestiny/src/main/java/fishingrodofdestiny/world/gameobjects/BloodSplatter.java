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
public class BloodSplatter extends GameObject {
    
    public BloodSplatter() {
        super("pool of blood");
        this.adjustMaxHitpoints(100);
        this.setHitpoints(this.getMaxHitpoints());
        this.setGraphics(new TileGfx("rltiles/tile", 288, 800, 32, 32));
    }

    public void tick(double deltaTime) {
        super.tick(deltaTime);
        this.hit(null, 1);
    }    
}
