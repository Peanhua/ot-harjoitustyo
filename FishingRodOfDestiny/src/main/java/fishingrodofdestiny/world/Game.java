/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.EmptyLevelGenerator;
import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.StairsTile;
import fishingrodofdestiny.world.gameobjects.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        
        EmptyLevelGenerator elg = new EmptyLevelGenerator(new Random(0), 43, 37); // 43x37 fits the screen without scrolling
        for (int i = 0; i < 10; i++) {
            this.addLevel(elg.generateLevel(i));
        }
        
        List<StairsTile> stairs = this.getLevel(0).getStairsUp();
        player.getLocation().moveTo(stairs.get(0));
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
