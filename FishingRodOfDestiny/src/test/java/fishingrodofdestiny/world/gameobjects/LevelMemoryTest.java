/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
