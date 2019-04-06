/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class InventoryTest {
    
    private JFXPanel  jfxPanel;
    private Inventory inventory;
    
    public InventoryTest() {
    }
    
    @Before
    public void setUp() {
        this.jfxPanel  = new JFXPanel();
        this.inventory = new Inventory(0);
    }
    
    @After
    public void tearDown() {
        this.jfxPanel = null;
    }
    
    
    @Test
    public void adjustingWeightLimitWorks() {
        assertEquals(0, this.inventory.getWeightLimit());
        int n = 42;
        for(int i = 0; i < n; i++) {
            this.inventory.adjustWeightLimit(1);
            assertEquals(i + 1, this.inventory.getWeightLimit());
        }
        this.inventory.adjustWeightLimit(10);
        assertEquals(n + 10, this.inventory.getWeightLimit());
        this.inventory.adjustWeightLimit(-5);
        assertEquals(n + 10 - 5, this.inventory.getWeightLimit());
    }
        

    @Test
    public void weightLimitDoesntGoBelowZero() {
        assertEquals(0, this.inventory.getWeightLimit());
        this.inventory.adjustWeightLimit(-4);
        assertEquals(0, this.inventory.getWeightLimit());
    }
    
    @Test
    public void weightLimitDoesntOverflow() {
        assertEquals(0, this.inventory.getWeightLimit());
        this.inventory.adjustWeightLimit(Integer.MAX_VALUE / 2 + 3);
        assertTrue(this.inventory.getWeightLimit() > 0);
        this.inventory.adjustWeightLimit(Integer.MAX_VALUE / 2 + 3);
        assertTrue(this.inventory.getWeightLimit() > 0);
    }
}
