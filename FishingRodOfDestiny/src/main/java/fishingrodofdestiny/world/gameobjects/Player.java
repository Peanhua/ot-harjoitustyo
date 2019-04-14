/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.controllers.PlayerController;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joyr
 */
public class Player extends Character {
    private boolean           gameCompleted;
    private List<LevelMemory> levelMemories;
    
    public Player() {
        super();
        this.gameCompleted = false;
        this.levelMemories = new ArrayList<>();
        this.setController(new PlayerController(this));
        this.setGraphics(new TileGfx("rltiles/nh32", 160, 352, 32, 32));
        this.getLocation().listenOnChange(() -> this.explore());
    }
    
    @Override
    public String toString() {
        return "Player(" + super.toString()
                + ")";
    }
    
    @Override
    public boolean isValidAttackTarget(GameObject target) {
        if (!super.isValidAttackTarget(target)) {
            return false;
        }
        
        return target instanceof Character;
    }

    
    public void setGameCompleted() {
        this.gameCompleted = true;
    }
    
    public boolean getGameCompleted() {
        return this.gameCompleted;
    }
    
    
    public LevelMemory getLevelMemory(Level level) {
        while (this.levelMemories.size() <= level.getDepth()) {
            this.levelMemories.add(new LevelMemory(level));
        }
        return this.levelMemories.get(level.getDepth());
    }
    
    private void explore() {
        Tile tile = this.getLocation().getContainerTile();
        if (tile == null) {
            return;
        }
        
        Level level = tile.getLevel();
        LevelMemory memory = this.getLevelMemory(level);
        int myX = tile.getX();
        int myY = tile.getY();
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                memory.remember(myX + x, myY + y);
            }
        }
    }
}
