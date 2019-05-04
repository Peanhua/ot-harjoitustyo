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

import fishingrodofdestiny.world.CaveSettings;
import fishingrodofdestiny.world.EmptyLevelGenerator;
import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.LevelGenerator;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ShadowCasting;

/**
 *
 * @author joyr
 */
public class LevelMemoryTest {

    private LevelMemory memory;
    private Level       level;
    private int         x;
    private int         y;
    private int         fov;
    
    @Before
    public void setUp() {
        CaveSettings cs = new CaveSettings(new Random(0));
        LevelGenerator lg = new EmptyLevelGenerator(cs);
        this.level = lg.generateLevel(0);
        this.memory = new LevelMemory(this.level);
        IFovAlgorithm fova = new ShadowCasting();
        Tile floor = level.getRandomTileOfType(new Random(0), FloorTile.class);
        this.x = floor.getX();
        this.y = floor.getY();
        this.memory.setCurrentVisionCenter(x, y);
        this.fov = 5;
        fova.visitFieldOfView(this.memory, x, y, this.fov);
    }
    
    @Test
    public void partsOfLevelAreExplored() {
        assertTrue(this.memory.isExplored(x, y));
    }
    
    @Test
    public void partsOfLevelAreSeen() {
        assertTrue(this.memory.isSeen(x, y));
    }
    
    @Test
    public void forgetLocationIsNoLongerExplored() {
        this.memory.forget(x, y);
        assertFalse(this.memory.isExplored(x, y));
    }
}
