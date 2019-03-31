/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.gameobjects;

import fishingrodofdestiny.world.Level;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joyr
 */
public class Game {
    private Player      player;
    private List<Level> levels;
    
    public Game(Player player) {
        this.player = player;
        this.levels = new ArrayList<>();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public void addLevel(Level level) {
        this.levels.add(level);
    }
    
    public Level getLevel(int depth) {
        return this.levels.get(depth);
    }
}
