/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.FishingRod;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.tiles.BearTrapTile;
import fishingrodofdestiny.world.tiles.ExitCaveTile;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.PitTrapTile;
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
    private final List<Level> levels;
    private final String[][] possibleTrapScenarios = {
        { "...",
          ".X.",
          "..."
        },
        { "..#",
          ".X#",
          "..#"
        },
        { "###",
          ".X.",
          "..."
        },
        { "#..",
          "#X.",
          "#.."
        },
        { "...",
          ".X.",
          "###"
        },
        { "#..",
          "#X.",
          "###"
        },
        { "###",
          "#X.",
          "#.."
        },
        { "###",
          ".X#",
          "..#"
        },
        { "..#",
          ".X#",
          "###"
        },
        { "###",
          "#X#",
          "#.#"
        },
        { "###",
          ".X#",
          "###"
        },
        { "#.#",
          "#X#",
          "###"
        },
        { "###",
          "#X.",
          "###"
        }
    };
    
    public Cave(Random random) {
        this.levels = new ArrayList<>();
        LevelGenerator lg = new BSPLevelGenerator(random, 30, 20);
        for (int i = 0; i < 5; i++) {
            this.addLevel(lg.generateLevel(i));
        }
        
        this.addStairs(random);
        this.levels.forEach(level -> lg.connectStartEnd(level));
        this.connectStairs();
        this.setupGameCompletionObjects();
        this.addTraps(random);
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
                GameObject npc = level.spawnNPC(random);
                if (npc == null) {
                    break;
                }
            }
        });
    }
    
    
    private void setupGameCompletionObjects() {
        // Change the stairs up on first level to exit cave tile:
        Level level = this.levels.get(0);
        List<StairsTile> stairs = level.getStairsUp();
        Tile tile = stairs.get(0);
        Tile newTile = new ExitCaveTile(level, tile.getX(), tile.getY());
        level.setTile(tile.getX(), tile.getY(), newTile);
        // Change the stairs down on last level to normal floor tile:
        level = this.levels.get(this.levels.size() - 1);
        stairs = level.getStairsDown();
        tile = stairs.get(0);
        newTile = new FloorTile(level, tile.getX(), tile.getY());
        level.setTile(tile.getX(), tile.getY(), newTile);
        // And add the fishing rod in there:
        FishingRod rod = new FishingRod();
        rod.getLocation().moveTo(newTile);
    }
    
    
    private void addTraps(Random random) {
        this.addPitTraps(random);
        this.addBearTraps(random);
    }
    
    private void addPitTraps(Random random) {
        for (int i = 1; i < this.levels.size(); i++) {
            Level prev = this.levels.get(i - 1);
            Level cur  = this.levels.get(i);
            
            for (int j = 0; j < 10; j++) {
                Tile floor = prev.getRandomTileOfType(random, FloorTile.class);
                Tile floorDownstairs = cur.getTile(floor.getX(), floor.getY());
                if (floorDownstairs.getClass() == floor.getClass()) {
                    String[] thisLocation = this.getScenarioData(prev, floor.getX(), floor.getY());
                    if (this.matchScenario(thisLocation, this.possibleTrapScenarios)) {
                        PitTrapTile trap = new PitTrapTile(prev, floor.getX(), floor.getY());
                        trap.setTarget(floorDownstairs);
                        prev.setTile(floor.getX(), floor.getY(), trap);
                    }
                }
            }
        }
    }
    
    private void addBearTraps(Random random) {
        this.levels.forEach(level -> {
            int count = random.nextInt(5);
            for (int i = 0; i < count; i++) {
                Tile floor = level.getRandomTileOfType(random, FloorTile.class);
                if (floor != null) {
                    BearTrapTile trap = new BearTrapTile(level, floor.getX(), floor.getY());
                    level.setTile(floor.getX(), floor.getY(), trap);
                }
            }
        });
    }
    
    
    private String[] getScenarioData(Level level, int x, int y) {
        String[] map = new String[3];
        for (int yi = -1; yi <= 1; yi++) {
            String currentLine = "";
            for (int xi = -1; xi <= 1; xi++) {
                if (xi == 0 && yi == 0) {
                    currentLine += "X";
                } else {
                    Tile tile = level.getTile(x + xi, y + yi);
                    if (tile.canBeEntered()) {
                        currentLine += ".";
                    } else {
                        currentLine += "#";
                    }
                }
            }
            map[yi + 1] = currentLine;
        }
        return map;
    }
    
    private boolean matchScenario(String[] location, String[][] scenarios) {
        for (String[] scenario : scenarios) {
            boolean match = true;
            for (int i = 0; match && i < 3; i++) {
                if (!location[i].equals(scenario[i])) {
                    match = false;
                }
            }
            if (match) {
                return true;
            }
        }
        
        return false;
    }
}
