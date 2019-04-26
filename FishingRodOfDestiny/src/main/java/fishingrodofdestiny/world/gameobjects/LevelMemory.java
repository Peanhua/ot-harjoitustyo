/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.tiles.Tile;
import rlforj.los.ILosBoard;

/**
 *
 * @author joyr
 */
public class LevelMemory implements ILosBoard {
    private final boolean[] explored;
    private final Level     level;
    private final int       width;
    private final int       height;
    private final int       maxFovRadius;
    private final boolean[] currentVision;
    private int             currentVisionCenterX;
    private int             currentVisionCenterY;
    
    
    public LevelMemory(Level level) {
        this.level         = level;
        this.width         = level.getWidth();
        this.height        = level.getHeight();
        this.explored      = new boolean[this.width * this.height];
        this.maxFovRadius  = 20;
        this.currentVision = new boolean[2 * this.maxFovRadius * 2 * this.maxFovRadius];
        this.currentVisionCenterX = 0;
        this.currentVisionCenterY = 0;
    }
    
    private boolean isValidLocation(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }
    
    public boolean isExplored(int x, int y) {
        if (this.isValidLocation(x, y)) {
            return this.explored[x + y * this.width];
        }
        return false;
    }
    
    private int getCurrentVisionIndex(int x, int y) {
        x = x - this.currentVisionCenterX + this.maxFovRadius;
        y = y - this.currentVisionCenterY + this.maxFovRadius;
        if (x < 0 || x >= 2 * this.maxFovRadius || y < 0 || y >= 2 * this.maxFovRadius) {
            return -1;
        }
        return x + y * 2 * this.maxFovRadius;
    }
    
    public void setCurrentVisionCenter(int x, int y) {
        this.currentVisionCenterX = x;
        this.currentVisionCenterY = y;
        for (int i = 0; i < 2 * this.maxFovRadius * 2 * this.maxFovRadius; i++) {
            this.currentVision[i] = false;
        }
    }
    
    public boolean isSeen(int x, int y) {
        if (!this.isValidLocation(x, y)) {
            return false;
        }
        int index = this.getCurrentVisionIndex(x, y);
        if (index >= 0) {
            return this.currentVision[index];
        }
        return false;
    }
    
    private void setSeen(int x, int y) {
        if (!this.isValidLocation(x, y)) {
            return;
        }
        int index = this.getCurrentVisionIndex(x, y);
        if (index >= 0) {
            this.currentVision[index] = true;
        }
    }

    
    public void remember(int x, int y) {
        if (this.isValidLocation(x, y)) {
            this.explored[x + y * this.width] = true;
        }
    }
    
    public void forget(int x, int y) {
        if (this.isValidLocation(x, y)) {
            this.explored[x + y * this.width] = false;
        }
    }
    
    
    
    // ILosBoard interface:
    @Override
    public boolean contains(int x, int y) {
        return this.isValidLocation(x, y);
    }

    @Override
    public boolean isObstacle(int x, int y) {
        Tile tile = this.level.getTile(x, y);
        if (tile == null) {
            return true;
        }
        return !tile.canBeEntered();
    }

    @Override
    public void visit(int x, int y) {
        this.remember(x, y);
        this.setSeen(x, y);
    }
}
