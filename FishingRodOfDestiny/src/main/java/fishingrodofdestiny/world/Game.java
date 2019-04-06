/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.tiles.StairsTile;
import fishingrodofdestiny.world.gameobjects.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author joyr
 */
public class Game {
    public enum RescueTarget {
        PRINCESS,
        PRINCE;
        
        public String getTitleName() {
            if (this == PRINCESS) {
                return "princess";
            } else {
                return "prince";
            }
        }
        
        public String getObjectPronoun() {
            if (this == PRINCESS) {
                return "her";
            } else {
                return "him";
            }
        }
    };
    
    private Player       player;
    private RescueTarget rescueTarget;
    private List<Level>  levels;
    
    public Game(Player player, RescueTarget rescueTarget) {
        this.player       = player;
        this.rescueTarget = rescueTarget;
        this.levels       = new ArrayList<>();
        
        EmptyLevelGenerator elg = new EmptyLevelGenerator(new Random(0), 43, 37); // 43x37 fits the screen without scrolling
        for (int i = 0; i < 10; i++) {
            this.addLevel(elg.generateLevel(i));
        }
        
        List<StairsTile> stairs = this.getLevel(0).getStairsUp();
        player.getLocation().moveTo(stairs.get(0));
    }
    
    
    public RescueTarget getRescueTarget() {
        return this.rescueTarget;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    private void addLevel(Level level) {
        this.levels.add(level);
    }
    
    public final Level getLevel(int depth) {
        return this.levels.get(depth);
    }
}
