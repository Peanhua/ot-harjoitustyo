/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.ExitCaveTile;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The master game object, represents a single instance of a game that is played.
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
    
    private final Player       player;
    private final RescueTarget rescueTarget;
    private final Random       random;
    private final Cave         cave;
    
    /**
     * Create a new Game.
     * 
     * @param randomSeed   The seed used for the content generator
     * @param player       The player object
     * @param rescueTarget The rescue target, used for the plot
     */
    public Game(long randomSeed, Player player, RescueTarget rescueTarget) {
        this.player       = player;
        this.rescueTarget = rescueTarget;
        this.random       = new Random(randomSeed);
        this.cave         = new Cave(new CaveSettings(this.random));
        
        List<Tile> exits = this.cave.getLevel(0).getMap().getTiles(ExitCaveTile.class);
        player.setHitpoints(player.getMaxHitpoints());
        player.getLocation().moveTo(exits.get(0));
    }
    
    
    /**
     * Return the cave for this game.
     * 
     * @return Cave
     */
    public final Cave getCave() {
        return this.cave;
    }
    
    
    /**
     * Generates and returns the plot as lines of texts.
     * 
     * @return The plot
     */
    public List<String> getPlot() {
        List<String> rv = new ArrayList<>();
        
        String rescueTargetName          = this.rescueTarget.getTitleName();
        String rescueTargetObjectPronoun = this.rescueTarget.getObjectPronoun();
        
        rv.add("A " + rescueTargetName + " has fallen into the lake of Sunken Nobles!");
        rv.add("");
        rv.add("You must rescue " + rescueTargetObjectPronoun + " by retrieving the Magical Fishing Rod from the depths of cave Caerrbannogh!");
        
        return rv;
    }
    
    /**
     * Returns the message shown to the player when the player successfully finishes the game.
     * 
     * @return The game ending message
     */
    public List<String> getPlotFinish() {
        List<String> rv = new ArrayList<>();
        
        String rescueTargetName          = this.rescueTarget.getTitleName();
        String rescueTargetObjectPronoun = this.rescueTarget.getObjectPronoun();

        rv.add("Congratulations!");
        rv.add("");
        rv.add("You have successfully retrieved the Magical Fishing Rod from the depths of cave Caerrbannogh, and used it to save the " + rescueTargetName + " from the lake of Sunken Nobles.");
        rv.add("");
        
        Random r = new Random();
        String[] stuff = { "happily", "miserably", "strangely", "gloriously", "insanely", "normally", "colorfully", "not so happily", "very happily", "partying hard", "yellowishly" };
        rv.add("You, and the " + rescueTargetName + ", live the rest of your life " + stuff[r.nextInt(stuff.length)] + ", knowing that you have the Magical Fishing Rod in your hands!");
        
        return rv;
    }

    
    /**
     * Returns the player object.
     * 
     * @return Player
     */
    public Player getPlayer() {
        return this.player;
    }
    
    
    /**
     * Advances the game "one step".
     */
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
