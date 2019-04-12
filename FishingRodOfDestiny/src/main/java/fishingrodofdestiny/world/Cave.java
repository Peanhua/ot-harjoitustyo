/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.StairsDownTile;
import fishingrodofdestiny.world.tiles.StairsTile;
import fishingrodofdestiny.world.tiles.StairsUpTile;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author joyr
 */
public class Cave {
    private List<Level> levels;
    
    public Cave(Random random) {
        this.levels = new ArrayList<>();
        LevelGenerator lg = new BSPLevelGenerator(random, 80, 60);
        for (int i = 0; i < 10; i++) {
            this.addLevel(lg.generateLevel(i));
        }
        
        this.addStairs(random);
        this.levels.forEach(level -> lg.connectStartEnd(level));
        this.connectStairs();
        this.populateNPCs(random);
    }

    
    private void addLevel(Level level) {
        this.levels.add(level);
    }

    
    public final Level getLevel(int depth) {
        return this.levels.get(depth);
    }

    
    // Add stairs, alternating between "topleft = stairs up and bottomright = stairs down" with the opposite "bottomright = stairs up and topleft = stairs down".
    private void addStairs(Random random) {
        boolean topLeftIsUp = true;
        for (Level level : this.levels) {
            Tile up   = this.findFloorFromTopLeft(random, level);
            Tile down = this.findFloorFromBottomRight(random, level);
            if (!topLeftIsUp) {
                Tile tmp = up;
                up = down;
                down = tmp;
            }
            level.setTile(up.getX(),   up.getY(),   new StairsUpTile(level,   up.getX(),   up.getY()));
            level.setTile(down.getX(), down.getY(), new StairsDownTile(level, down.getX(), down.getY()));
            topLeftIsUp = !topLeftIsUp;
        }
    }
    
    
    private Tile findFloorFromTopLeft(Random random, Level level) {
        for (int i = 0; i < Math.max(level.getWidth(), level.getHeight()); i++) {
            Tile target = level.getRandomTileOfTypeInArea(random, FloorTile.class, 0, 0, i, i);
            if (target != null) {
                return target;
            }
        }
        return null;
    }
    
    
    private Tile findFloorFromBottomRight(Random random, Level level) {
        for (int i = Math.min(level.getWidth(), level.getHeight()) - 1; i >= 0; i--) {
            Tile target = level.getRandomTileOfTypeInArea(random, FloorTile.class, i, i, level.getWidth() - i, level.getHeight() - i);
            if (target != null) {
                return target;
            }
        }
        return null;
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
    
    
    private void populateNPCs(Random random) {
        this.levels.forEach(level -> {
            while (true) {
                NonPlayerCharacter npc = level.spawnNPC(random);
                if (npc == null) {
                    break;
                }
            }
        });
    }
}
