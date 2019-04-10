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
        
        List<String> filenames = new ArrayList<>();
        filenames.add("DawnLike/Characters/Player0");
        filenames.add("DawnLike/Characters/Player1");
        this.setGraphics(new TileGfx(filenames, 0, 112, 16, 16));
    }
    
    @Override
    public String toString() {
        return "Player(" + super.toString()
                + ")";
    }
}
