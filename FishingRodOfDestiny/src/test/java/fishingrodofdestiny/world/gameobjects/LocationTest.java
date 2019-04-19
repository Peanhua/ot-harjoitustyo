/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class LocationTest {
    
    class GameObjectSubclass extends GameObject {
        public GameObjectSubclass() {
            super();
        }
    }
    
    private GameObject object1;
    private GameObject object2;
    private Tile       tile;
    
    @Before
    public void setUp() {
        this.object1 = new GameObjectSubclass();
        this.object2 = new GameObjectSubclass();
        this.tile    = new FloorTile(null, 0, 0);
    }
    

    @Test
    public void moveToTile() {
        this.object1.getLocation().moveTo(this.tile);
        assertTrue(this.tile.getInventory().getObjects().contains(this.object1));
        assertEquals(this.object1.getLocation().getContainerInventory(), this.tile.getInventory());
        assertEquals(this.object1.getLocation().getContainerTile(), this.tile);
    }
    
    @Test
    public void moveToGameObject() {
        this.object1.getLocation().moveTo(this.object2);
        assertTrue(this.object2.getInventory().getObjects().contains(this.object1));
        assertEquals(this.object1.getLocation().getContainerInventory(), this.object2.getInventory());
        assertEquals(this.object1.getLocation().getContainerObject(), this.object2);
    }
    
    @Test
    public void moveFromObjectToTile() {
        this.object1.getLocation().moveTo(this.object2);
        this.object1.getLocation().moveTo(this.tile);
        assertTrue(this.tile.getInventory().getObjects().contains(this.object1));
        assertEquals(this.object1.getLocation().getContainerInventory(), this.tile.getInventory());
        assertEquals(this.object1.getLocation().getContainerTile(), this.tile);
        assertFalse(this.object2.getInventory().getObjects().contains(this.object1));
        assertNotEquals(this.object1.getLocation().getContainerInventory(), this.object2.getInventory());
        assertNotEquals(this.object1.getLocation().getContainerObject(), this.object2);
    }

    @Test
    public void moveFromTileToObject() {
        this.object1.getLocation().moveTo(this.tile);
        this.object1.getLocation().moveTo(this.object2);
        assertFalse(this.tile.getInventory().getObjects().contains(this.object1));
        assertNotEquals(this.object1.getLocation().getContainerInventory(), this.tile.getInventory());
        assertNotEquals(this.object1.getLocation().getContainerTile(), this.tile);
        assertTrue(this.object2.getInventory().getObjects().contains(this.object1));
        assertEquals(this.object1.getLocation().getContainerInventory(), this.object2.getInventory());
        assertEquals(this.object1.getLocation().getContainerObject(), this.object2);
    }
    
    @Test
    public void moveFromObjectToNullTile() {
        this.object1.getLocation().moveTo(this.object2);
        Tile t = null;
        this.object1.getLocation().moveTo(t);
        assertFalse(this.tile.getInventory().getObjects().contains(this.object1));
        assertEquals(this.object1.getLocation().getContainerInventory(), null);
        assertEquals(this.object1.getLocation().getContainerTile(), null);
        assertFalse(this.object2.getInventory().getObjects().contains(this.object1));
    }

    @Test
    public void moveFromTileToNullObject() {
        this.object1.getLocation().moveTo(this.tile);
        GameObject t = null;
        this.object1.getLocation().moveTo(t);
        assertFalse(this.tile.getInventory().getObjects().contains(this.object1));
        assertEquals(this.object1.getLocation().getContainerInventory(), null);
        assertEquals(this.object1.getLocation().getContainerTile(), null);
        assertFalse(this.object2.getInventory().getObjects().contains(this.object1));
    }
}
