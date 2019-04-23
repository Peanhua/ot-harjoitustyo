/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.tiles.StairsTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.WallTile;
import java.util.List;

/**
 *
 * @author joyr
 */
public abstract class LevelGenerator {
    CaveSettings settings;
    
    public LevelGenerator(CaveSettings settings) {
        this.settings = settings;
    }
    
    
    protected final CaveSettings getSettings() {
        return this.settings;
    }
    

    protected void createLevelBorders(Level level) {
        for (int x = 0; x < level.getWidth(); x++) {
            int y = 0;
            level.setTile(x, y, new WallTile(level, x, y));
            y = level.getHeight() - 1;
            level.setTile(x, y, new WallTile(level, x, y));
        }
        
        for (int y = 1; y < level.getHeight() - 1; y++) {
            int x = 0;
            level.setTile(x, y, new WallTile(level, x, y));
            x = level.getWidth() - 1;
            level.setTile(x, y, new WallTile(level, x, y));
        }
    }


    protected void fillEmptySpace(Level level) {
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                Tile t = level.getTile(x, y);
                if (t == null) {
                    level.setTile(x, y, new WallTile(level, x, y));
                }
            }
        }
    }
    
    

    
    public abstract Level generateLevel(int caveLevel);
    public abstract void  connectStartEnd(Level level);
    
    /**
     * Find out areas that can't be accessed and fill them with something.
     * 
     * @param level Level to process.
     */
    public void fillUnusedSpace(Level level) {
        Tile stairs = this.getStairsUpOrDown(level); // Doesn't matter which one.
        
        LevelMapConnectedTilesAlgorithm cta = new LevelMapConnectedTilesAlgorithm(level.getMap());
        List<List<Tile>> connectedTileGroups = cta.getConnectedTileGroups();
  
        connectedTileGroups.forEach(group -> {
            if (!group.contains(stairs)) {
                group.forEach(tile -> level.setTile(tile.getX(), tile.getY(), new WallTile(level, tile.getX(), tile.getY())));
            }
        });
    }
    
    private Tile getStairsUpOrDown(Level level) {
        List<StairsTile> stairs = level.getStairsUp();
        if (stairs.isEmpty()) {
            stairs = level.getStairsDown();
        }
        if (stairs.isEmpty()) {
            throw new RuntimeException("No stairs found for level " + level);
        }
        return stairs.get(0);
    }
}
