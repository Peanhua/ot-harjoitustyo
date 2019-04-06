package fishingrodofdestiny.world.gameobjects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class GameObjectTest {
    
    class GameObjectSubclass extends GameObject {
        public GameObjectSubclass() {
            super("test");
        }
    }
    
    private JFXPanel   jfxPanel;
    private GameObject object;
    
    public GameObjectTest() {
    }
    
    @Before
    public void setUp() {
        this.jfxPanel = new JFXPanel();
        this.object   = new GameObjectSubclass();
    }
    
    @After
    public void tearDown() {
        this.jfxPanel = null;
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
        this.object.getInventory().adjustWeightLimit(1);
        String s3 = this.object.toString();
        assertTrue(!s2.equals(s3));
    }
    
    
    @Test
    public void adjustingMaxHitpointsWorks() {
        assertEquals(1, this.object.getMaxHitpoints());
        int n = 42;
        for(int i = 0; i < n; i++) {
            this.object.adjustMaxHitpoints(1);
            assertEquals(1 + i + 1, this.object.getMaxHitpoints());
        }
        this.object.adjustMaxHitpoints(10);
        assertEquals(1 + n + 10, this.object.getMaxHitpoints());
        this.object.adjustMaxHitpoints(-5);
        assertEquals(1 + n + 10 - 5, this.object.getMaxHitpoints());
    }

    @Test
    public void maxHitpointsDontGoBelowZero() {
        assertEquals(1, this.object.getMaxHitpoints());
        this.object.adjustMaxHitpoints(-3);
        assertEquals(0, this.object.getMaxHitpoints());
    }
    
    @Test
    public void maxHitpointsDontOverflow() {
        assertEquals(1, this.object.getMaxHitpoints());
        this.object.adjustMaxHitpoints(Integer.MAX_VALUE / 2 + 3);
        assertTrue(this.object.getMaxHitpoints() > 0);
        this.object.adjustMaxHitpoints(Integer.MAX_VALUE / 2 + 3);
        assertTrue(this.object.getMaxHitpoints() > 0);
    }
}
