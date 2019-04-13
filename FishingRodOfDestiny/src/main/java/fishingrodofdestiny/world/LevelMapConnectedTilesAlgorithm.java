/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;

/**
 * Uses depth first search to provide lists of connected tile groups.
 * 
 * @author joyr
 */
public class LevelMapConnectedTilesAlgorithm {
    private final LevelMap   map;
    private List<List<Tile>> connectedTileGroups;
    private boolean[]        visitedTiles;
    

    public LevelMapConnectedTilesAlgorithm(LevelMap map) {
        this.map                 = map;
        this.connectedTileGroups = new ArrayList<>();
        this.visitedTiles        = new boolean[this.map.getWidth() * this.map.getHeight()];
    }
    

    public List<List<Tile>> getConnectedTileGroups() {
        for (int y = 0; y < this.map.getHeight(); y++) {
            for (int x = 0; x < this.map.getWidth(); x++) {
                List<Tile> list = this.getConnectedTiles(x, y);
                if (list != null) {
                    this.connectedTileGroups.add(list);
                }
            }
        }
        
        return this.connectedTileGroups;
    }
    
    private List<Tile> getConnectedTiles(int x, int y) {
        if (this.visitedTiles[x + y * this.map.getWidth()]) {
            return null;
        }
        
        List<Tile> group = new ArrayList<>();
        this.getConnectedTilesDFS(group, x, y);
        if (group.isEmpty()) {
            return null;
        }
        return group;
    }
    
    private void getConnectedTilesDFS(List<Tile> group, int x, int y) {
        if (x < 0 || x >= this.map.getWidth() || y < 0 || y >= this.map.getHeight()) {
            return;
        }
        
        if (this.visitedTiles[x + y * this.map.getWidth()]) {
            return;
        }

        this.visitedTiles[x + y * this.map.getWidth()] = true;

        Tile tile = this.map.getTile(x, y);
        if (!tile.canBeEntered()) {
            return;
        }

        group.add(tile);
        this.getConnectedTilesDFS(group, x, y - 1);
        this.getConnectedTilesDFS(group, x, y + 1);
        this.getConnectedTilesDFS(group, x - 1, y);
        this.getConnectedTilesDFS(group, x + 1, y);
    }
}
