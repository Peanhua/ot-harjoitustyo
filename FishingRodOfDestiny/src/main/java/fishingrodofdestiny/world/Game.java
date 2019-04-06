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
        
        // TODO: move level generation&setup into cavegenerator class
        EmptyLevelGenerator elg = new EmptyLevelGenerator(new Random(0), 43, 35); // 43x35 fits the screen without scrolling
        for (int i = 0; i < 10; i++) {
            this.addLevel(elg.generateLevel(i));
        }
        
        this.connectStairs();
        
        List<StairsTile> stairs = this.getLevel(0).getStairsUp();
        player.getLocation().moveTo(stairs.get(0));
    }
    
    
    // Connect all the stairs between levels, assumes that there is only one stairs going up and one down on each level:
    private void connectStairs() {
        for (int i = 1; i < this.levels.size(); i++) {
            Level prev = this.levels.get(i - 1);
            Level cur  = this.levels.get(i);
            
            List<StairsTile> prevStairs = prev.getStairsDown();
            List<StairsTile> curStairs  = cur.getStairsUp();
            prevStairs.get(0).setTarget(curStairs.get(0));
            curStairs.get(0).setTarget(prevStairs.get(0));
        }
    }
    
    
    public List<String> getPlot() {
        List<String> rv = new ArrayList<>();
        
        String rescueTargetName          = this.rescueTarget.getTitleName();
        String rescueTargetObjectPronoun = this.rescueTarget.getObjectPronoun();
        
        rv.add("A " + rescueTargetName + " has fallen into the lake of Sunken Nobles!");
        rv.add("");
        rv.add("You must rescue " + rescueTargetObjectPronoun + " by retrieving the Magical Fishing Rod from the depths of cave Caerrbannogh!");
        
        return rv;
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
    
    
    public final void tick() {
        if (this.player == null) {
            return;
        }
        
        List<Level> levels = new ArrayList<>();
        levels.add(this.player.getLocation().getContainerTile().getLevel());
        
        levels.forEach(level -> {
            level.tick(1.0);
        });
    }
    
}
