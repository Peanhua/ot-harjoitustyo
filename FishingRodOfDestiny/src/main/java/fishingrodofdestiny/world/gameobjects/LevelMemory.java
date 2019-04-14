/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.Level;

/**
 *
 * @author joyr
 */
public class LevelMemory {
    private final boolean[] explored;
    private final int       levelDepth;
    private final int       width;
    private final int       height;
    
    public LevelMemory(Level level) {
        this.levelDepth = level.getDepth();
        this.width      = level.getWidth();
        this.height     = level.getHeight();
        this.explored   = new boolean[this.width * this.height];
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
}
