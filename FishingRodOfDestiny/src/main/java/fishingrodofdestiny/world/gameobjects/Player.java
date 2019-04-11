/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.TileGfx;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joyr
 */
public class Player extends Character {
    public Player() {
        super();
        this.setGraphics(new TileGfx("rltiles/nh32", 160, 352, 32, 32));
    }
    
    @Override
    public String toString() {
        return "Player(" + super.toString()
                + ")";
    }
}
