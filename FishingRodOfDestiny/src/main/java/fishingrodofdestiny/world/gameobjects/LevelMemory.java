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
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.tiles.Tile;
import rlforj.los.ILosBoard;

/**
 * Keep track of what parts of the level have been explored, and what parts are currently visible.
 * 
 * @author joyr
 */
public class LevelMemory implements ILosBoard {
    private final boolean[] explored;
    private final Level     level;
    private final int       width;
    private final int       height;
    private final int       maxFovRadius;
    /**
     * currentVision holds a rectangle that can fit a circle with radius of maxFovRadius
     * This is done so that we don't have to clear the size of the level amount of booleans every time player moves.
     */
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
    
    /**
     * Return whether the given location has been explored.
     * 
     * @param x X coordinate in level
     * @param y Y coordinate in level
     * @return True if the location has been explored
     */
    public boolean isExplored(int x, int y) {
        if (this.isValidLocation(x, y)) {
            return this.explored[x + y * this.width];
        }
        return false;
    }
    
    /**
     * Return index into currentVision using level coordinates.
     * 
     * @param x X coordinate in level
     * @param y Y coordinate in level
     * @return Index into currentVision
     */
    private int getCurrentVisionIndex(int x, int y) {
        x = x - this.currentVisionCenterX + this.maxFovRadius;
        y = y - this.currentVisionCenterY + this.maxFovRadius;
        if (x < 0 || x >= 2 * this.maxFovRadius || y < 0 || y >= 2 * this.maxFovRadius) {
            return -1;
        }
        return x + y * 2 * this.maxFovRadius;
    }
    
    /**
     * Move the center of currentVision. Also clears the currentVision buffer.
     * 
     * @param x X coordinate in level
     * @param y Y coordinate in level
     */
    public void setCurrentVisionCenter(int x, int y) {
        this.currentVisionCenterX = x;
        this.currentVisionCenterY = y;
        for (int i = 0; i < 2 * this.maxFovRadius * 2 * this.maxFovRadius; i++) {
            this.currentVision[i] = false;
        }
    }
    
    /**
     * Returns true if the location pointed by x and y level coordinates can be seen.
     * 
     * @param x X coordinate in level
     * @param y Y coordinate in level
     * @return True if the location can be seen
     */
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


    /**
     * Mark a location to be explored.
     * 
     * @param x X coordinate in level
     * @param y Y coordinate in level
     */
    public void remember(int x, int y) {
        if (this.isValidLocation(x, y)) {
            this.explored[x + y * this.width] = true;
        }
    }
    
    /**
     * Remove a location from being explored.
     * 
     * @param x X coordinate in level
     * @param y Y coordinate in level
     */
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
