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

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.tiles.BearTrapTile;
import fishingrodofdestiny.world.tiles.ExitCaveTile;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.PitTrapTile;
import fishingrodofdestiny.world.tiles.StairsDownTile;
import fishingrodofdestiny.world.tiles.StairsTile;
import fishingrodofdestiny.world.tiles.StairsUpTile;
import fishingrodofdestiny.world.tiles.StatueTile;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The cave, the game world where the game takes place.
 * <p>
 * Uses the given CaveSettings to generate the cave.
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
    
    public Cave(CaveSettings settings) {
        this.levels = new ArrayList<>();
        this.addLevels(settings);
        this.addStairs(settings);
        this.removeInaccessibleLocations(settings);
        this.setupGameCompletionObjects();
        this.addStatues(settings);
        this.addTraps(settings);
        this.addItems(settings);
        this.addNPCs(settings);
    }

    
    private void addLevels(CaveSettings settings) {
        for (int caveLevel = 0; caveLevel < settings.getNumberOfLevels(); caveLevel++) {
            this.levels.add(settings.getLevelGenerator(caveLevel).generateLevel(caveLevel));
        }
    }

    
    /**
     * Return the cave Level at the given "depth" (starting from 0 as the top-most level).
     * 
     * @param depth The depth, starting from 0
     * @return The level at the given depth
     */
    public final Level getLevel(int depth) {
        return this.levels.get(depth);
    }

    
    // Add stairs, alternating between "topleft = stairs up and bottomright = stairs down" with the opposite "bottomright = stairs up and topleft = stairs down".
    private void addStairs(CaveSettings settings) {
        boolean topLeftIsUp = true;
        for (int caveLevel = 0; caveLevel < this.levels.size(); caveLevel++) {
            final Level level = this.levels.get(caveLevel);
            Tile up   = this.findFloorFromTopLeft(settings.getRandom(), level);
            Tile down = this.findFloorFromBottomRight(settings.getRandom(), level);
            if (!topLeftIsUp) {
                final Tile tmp = up;
                up = down;
                down = tmp;
            }
            level.setTile(up.getX(),   up.getY(),   new StairsUpTile(level,   up.getX(),   up.getY()));
            level.setTile(down.getX(), down.getY(), new StairsDownTile(level, down.getX(), down.getY()));
            settings.getLevelGenerator(caveLevel).connectStartEnd(level);
            topLeftIsUp = !topLeftIsUp;
        }
        this.connectStairs();
    }
    
    
    private void removeInaccessibleLocations(CaveSettings settings) {
        for (int caveLevel = 0; caveLevel < settings.getNumberOfLevels(); caveLevel++) {
            settings.getLevelGenerator(caveLevel).fillUnusedSpace(this.levels.get(caveLevel));
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
    
    
    private void addItems(CaveSettings settings) {
        for (int i = 0; i < this.levels.size(); i++) {
            final Level level = this.levels.get(i);
            final GameObjectSpawner spawner = settings.getItemSpawner(i);
            while (true) {
                final GameObject item = GameObjectFactory.create(spawner.getNextObjectType(settings.getRandom(), level));
                if (item == null) {
                    break;
                }
                final Tile tile = level.getRandomTileOfType(settings.getRandom(), FloorTile.class);
                if (tile != null) {
                    item.getLocation().moveTo(tile);
                }
            }
        }
    }

    
    private void addNPCs(CaveSettings settings) {
        this.levels.forEach(level -> {
            while (true) {
                GameObject npc = level.spawnNPC(settings.getRandom());
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
        GameObject rod = GameObjectFactory.create("fishing rod");
        rod.getLocation().moveTo(newTile);
        // Also add a ghost at same location:
        GameObject ghost = GameObjectFactory.create("ghost");
        ghost.getLocation().moveTo(newTile);
    }
    
    
    private void addStatues(CaveSettings settings) {
        for (int i = 0; i < this.levels.size(); i++) {
            final Level level = this.levels.get(i);
            int count = settings.getStatues(i);
            while (count > 0) {
                Tile target = level.getRandomTileOfType(settings.getRandom(), FloorTile.class);
                if (target != null) {
                    level.setTile(target.getX(), target.getY(), new StatueTile(level, target.getX(), target.getY()));
                    count--;
                }
            }
        }
    }
    
    
    private void addTraps(CaveSettings settings) {
        this.addPitTraps(settings);
        this.addBearTraps(settings);
    }
    
    private void addPitTraps(CaveSettings settings) {
        for (int i = 1; i < this.levels.size(); i++) {
            Level prev = this.levels.get(i - 1);
            Level cur  = this.levels.get(i);
            
            for (int j = 0; j < settings.getMaxPitTraps(i); j++) {
                Tile floor = prev.getRandomTileOfType(settings.getRandom(), FloorTile.class);
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
    
    private void addBearTraps(CaveSettings settings) {
        for (int caveLevel = 0; caveLevel < this.levels.size(); caveLevel++) {
            final Level level = this.levels.get(caveLevel);
            final int count = settings.getRandom().nextInt(settings.getMaxBearTraps(caveLevel));
            for (int j = 0; j < count; j++) {
                Tile floor = level.getRandomTileOfType(settings.getRandom(), FloorTile.class);
                if (floor != null) {
                    BearTrapTile trap = new BearTrapTile(level, floor.getX(), floor.getY());
                    level.setTile(floor.getX(), floor.getY(), trap);
                }
            }
        }
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
