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
    private Random       random;
    private Cave         cave;
    
    public Game(long randomSeed, Player player, RescueTarget rescueTarget) {
        this.player       = player;
        this.rescueTarget = rescueTarget;
        this.random       = new Random(randomSeed);
        this.cave         = new Cave(this.random);
        
        List<StairsTile> stairs = this.cave.getLevel(0).getStairsUp();
        player.setHitpoints(player.getMaxHitpoints());
        player.getLocation().moveTo(stairs.get(0));
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
    
    
    public final void tick() {
        if (this.player == null || !this.player.isAlive()) {
            return;
        }
        
        List<Level> levels = new ArrayList<>();
        levels.add(this.player.getLocation().getContainerTile().getLevel());
        
        levels.forEach(level -> {
            level.tick(1.0);
        });
    }
    
}
