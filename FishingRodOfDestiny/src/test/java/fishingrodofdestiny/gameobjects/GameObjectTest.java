package fishingrodofdestiny.gameobjects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class GameObjectTest {
    
    private GameObject object;
    
    public GameObjectTest() {
    }
    
    @Before
    public void setUp() {
        object = new GameObject();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void changingNameWorks() {
        String s1 = this.object.getName();
        this.object.setName("testing");
        assertTrue(this.object.getName().equals("testing"));
    }
    
    @Test
    public void toStringWorks() {
        this.object.setName("abc");
        String s1 = this.object.toString();
        assertTrue(s1.getClass() == String.class);
        this.object.setName("def");
        String s2 = this.object.toString();
        assertTrue(!s1.equals(s2));
        this.object.adjustInventoryWeightLimit(1);
        String s3 = this.object.toString();
        assertTrue(!s2.equals(s3));
    }
    
    @Test
    public void adjustingInventoryWeightLimitWorks() {
        assertEquals(0, this.object.getInventoryWeightLimit());
        int n = 42;
        for(int i = 0; i < n; i++) {
            this.object.adjustInventoryWeightLimit(1);
            assertEquals(i + 1, this.object.getInventoryWeightLimit());
        }
        this.object.adjustInventoryWeightLimit(10);
        assertEquals(n + 10, this.object.getInventoryWeightLimit());
        this.object.adjustInventoryWeightLimit(-5);
        assertEquals(n + 10 - 5, this.object.getInventoryWeightLimit());
    }
        

    @Test
    public void inventoryWeightLimitDoesntGoBelowZero() {
        assertEquals(0, this.object.getInventoryWeightLimit());
        this.object.adjustInventoryWeightLimit(-4);
        assertEquals(0, this.object.getInventoryWeightLimit());
    }
    
    @Test
    public void inventoryWeightLimitDoesntOverflow() {
        assertEquals(0, this.object.getInventoryWeightLimit());
        this.object.adjustInventoryWeightLimit(Integer.MAX_VALUE / 2 + 3);
        assertTrue(this.object.getInventoryWeightLimit() > 0);
        this.object.adjustInventoryWeightLimit(Integer.MAX_VALUE / 2 + 3);
        assertTrue(this.object.getInventoryWeightLimit() > 0);
    }
        
    
    @Test
    public void adjustingMaxHitpointsWorks() {
        assertEquals(0, this.object.getMaxHitpoints());
        int n = 42;
        for(int i = 0; i < n; i++) {
            this.object.adjustMaxHitpoints(1);
            assertEquals(i + 1, this.object.getMaxHitpoints());
        }
        this.object.adjustMaxHitpoints(10);
        assertEquals(n + 10, this.object.getMaxHitpoints());
        this.object.adjustMaxHitpoints(-5);
        assertEquals(n + 10 - 5, this.object.getMaxHitpoints());
    }

    @Test
    public void maxHitpointsDontGoBelowZero() {
        assertEquals(0, this.object.getMaxHitpoints());
        this.object.adjustMaxHitpoints(-3);
        assertEquals(0, this.object.getMaxHitpoints());
    }
    
    @Test
    public void maxHitpointsDontOverflow() {
        assertEquals(0, this.object.getMaxHitpoints());
        this.object.adjustMaxHitpoints(Integer.MAX_VALUE / 2 + 3);
        assertTrue(this.object.getMaxHitpoints() > 0);
        this.object.adjustMaxHitpoints(Integer.MAX_VALUE / 2 + 3);
        assertTrue(this.object.getMaxHitpoints() > 0);
    }
        
}
