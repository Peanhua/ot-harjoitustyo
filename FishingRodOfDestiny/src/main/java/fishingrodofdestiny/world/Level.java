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
import fishingrodofdestiny.world.gameobjects.LevelMemory;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.StairsDownTile;
import fishingrodofdestiny.world.tiles.StairsUpTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.StairsTile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A single level (or "floor") of a cave.
 * 
 * @author joyr
 */
public class Level implements GameObjectContainer {
    
    private final GameObjectSpawner enemySettings;
    private final int           depth; // how low this is in the cave, top-most is 0
    private final int           width;
    private final int           height;
    private final LevelMap      map;
    private final Color         shadowColor;

    
    public Level(GameObjectSpawner enemySettings, int depth, int width, int height) {
        this.enemySettings = enemySettings;
        this.depth         = depth;
        this.width         = width;
        this.height        = height;
        this.map           = new LevelMap(this.width, this.height);
        this.shadowColor   = new Color(0, 0, 0, 0.5);
    }
    
    /**
     * Returns the depth of this level, ie. how deep into the ground this level is vertically. The top-most levels depth is 0.
     * 
     * @return The depth of this level
     */
    public int getDepth() {
        return this.depth;
    }
    
    /**
     * Returns the number of tiles in X-axis.
     * 
     * @return Number of tiles in X-axis
     */
    public int getWidth() {
        return this.width;
    }
    
    /**
     * Returns the number of tiles in Y-axis.
     * 
     * @return Number of tiles in Y-axis
     */
    public int getHeight() {
        return this.height;
    }
    
    
    /**
     * Returns the LevelMap containing the tiles.
     * 
     * @return LevelMap
     */
    public LevelMap getMap() {
        return this.map;
    }
    
    
    /**
     * Draw onto a context, using the given memory to mask out parts of the level.
     * 
     * @param memory    The memory used to mask out parts of the level
     * @param context   The target GraphicsContext to draw onto
     * @param tileSize  The size of each tile, in pixels, usually 32
     * @param topLeftX  The X coordinate of the top-left tile of drawn area
     * @param topLeftY  The Y coordinate of the top-left tile of drawn area
     * @param maxWidth  The maximum number of tiles in X-axis to draw
     * @param maxHeight The maximum number of tiles in Y-axis to draw
     */
    public void draw(LevelMemory memory, GraphicsContext context, int tileSize, int topLeftX, int topLeftY, int maxWidth, int maxHeight) {
        // TODO: test with slower hardware and maybe optimize a bit
        //       first draw shadowed, shadow them with one call, then draw the normally lit tiles
        //       also check if there is some blending/drawing mode available from javafx to do the shadowing
        //       also check if using a scroll pane with the whole level rendered once (only updating what changes) and then scrolling to proper location would be better
        context.setFill(this.shadowColor);
        for (int y = 0; topLeftY + y < this.height; y++) {
            for (int x = 0; topLeftX + x < this.width; x++) {
                if (memory.isExplored(topLeftX + x, topLeftY + y)) {
                    Tile t = this.getTile(topLeftX + x, topLeftY + y);
                    if (t != null) {
                        boolean seen = memory.isSeen(topLeftX + x, topLeftY + y);

                        t.draw(context, x * tileSize, y * tileSize, tileSize, seen);
                        
                        if (!seen) {
                            context.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Return tile at the given coordinates.
     * 
     * @param x X coordinate
     * @param y Y coordinate
     * @return Tile at the given coordinates
     */
    public Tile getTile(int x, int y) {
        return this.map.getTile(x, y);
    }
    

    /**
     * Returns a random tile whose type is the given.
     * 
     * @param random Random number generator to use
     * @param type   The type of tile to look for
     * @return A random tile, or null if can't be found
     */
    public Tile getRandomTileOfType(Random random, Class type) {
        return this.getRandomTileOfTypeInArea(random, type, 0, 0, this.width, this.height);
    }
    
    /**
     * Returns a random tile whose type is given from a rectangular area of the level.
     * 
     * @param random     Random number generator to use
     * @param type       The type of tile to look for
     * @param topleftX   The X coordinate of the top-left corner of the rectangular area
     * @param topleftY   The Y coordinate of the top-left corner of the rectangular area
     * @param areaWidth  The width of the rectangular area
     * @param areaHeight The height of the rectangular area
     * @return A random tile, or null if can't be found
     */
    public Tile getRandomTileOfTypeInArea(Random random, Class type, int topleftX, int topleftY, int areaWidth, int areaHeight) {
        areaWidth  = Math.min(areaWidth,  this.width - topleftX);
        areaHeight = Math.min(areaHeight, this.height - topleftY);
        if (areaWidth <= 0 || areaHeight <= 0) {
            return null;
        }
        for (int i = 0; i < areaWidth * areaHeight; i++) {
            int x = topleftX + random.nextInt(areaWidth);
            int y = topleftY + random.nextInt(areaHeight);
            Tile t = this.getTile(x, y);
            if (t.getClass() == type) {
                return t;
            }
        }
        return null;
    }

    
    /**
     * Set/change a tile in the level.
     * 
     * @param x    X coordinate
     * @param y    Y coordinate
     * @param tile The new tile at the coordinates
     */
    public void setTile(int x, int y, Tile tile) {
        this.map.setTile(x, y, tile);
    }

    
    // GameObjectContainer implementation:
    @Override
    public List<GameObject> getObjects(String objectType) {
        List<GameObject> objects = new ArrayList<>();
        this.map.getTiles().forEach(tile ->
            tile.getInventory().getObjects(objectType).forEach(obj -> objects.add(obj))
        );
        return objects;
    }
    
    @Override
    public int getObjectCount(String objectId) {
        // TODO: cache the most frequently queried types (all variations of NonPlayerCharacters)
        int count = 0;
        
        for (Tile tile : this.map.getTiles()) {
            count += tile.getInventory().getObjectCount(objectId);
        }
        
        return count;
    }
    
    
    /**
     * Spawn a new NPC based on settings for this level.
     * 
     * @param random Random number generator
     * @return The new NPC that was spawned
     */
    public GameObject spawnNPC(Random random) {
        // Generate the NPC:
        GameObject npc = GameObjectFactory.create(this.enemySettings.getNextObjectType(random, this));
        if (npc == null) {
            return null;
        }
        // Place the NPC appropriately:
        Tile tile = this.getRandomTileOfType(random, FloorTile.class);
        if (tile == null) {
            return null;
        }
        npc.getLocation().moveTo(tile);
        return npc;
    }

    
    /**
     * Return all the stair tiles leading up on this level.
     * 
     * @return All stair tiles leading up
     */
    public List<StairsTile> getStairsUp() {
        // TODO: optimize a bit: keep a list of stairs going up
        List<StairsTile> stairs = new ArrayList<>();
        this.map.getTiles(StairsUpTile.class).forEach((tile) -> stairs.add((StairsTile) tile));
        return stairs;
    }

    
    /**
     * Return all the stair tiles leading down on this level.
     * 
     * @return All stair tiles leading down
     */
    public List<StairsTile> getStairsDown() {
        List<StairsTile> stairs = new ArrayList<>();
        this.map.getTiles(StairsDownTile.class).forEach((tile) -> stairs.add((StairsTile) tile));
        return stairs;
    }
    
    
    /**
     * Advance the level, and all the GameObjects located in it, "one step".
     * 
     * @param deltaTime The number of seconds passed since last update
     */
    public void tick(double deltaTime) {
        // TODO: cache game objects in this level
        List<GameObject> objects = new ArrayList<>();
        this.map.getTiles().forEach(tile -> {
            objects.addAll(tile.getInventory().getObjects());
        });

        // TODO: sort objects based on their time to execute
        
        objects.forEach(obj -> obj.tick(deltaTime));
    }
}
