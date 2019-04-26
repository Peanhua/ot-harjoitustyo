package fishingrodofdestiny.world.gameobjects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fishingrodofdestiny.world.GameObjectFactory;
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
            super("test object");
        }
    }
    
    private GameObject object;
    
    @Before
    public void setUp() {
        this.object   = new GameObjectSubclass();
    }
    
    @Test
    public void changingNameWorks() {
        String s1 = this.object.getName();
        this.object.setName("testing");
        assertTrue(this.object.getName().equals("testing"));
    }
    
    @Test
    public void getCapitalizedNameWorksWithEmptyName() {
        this.object.setName("");
        assertTrue(this.object.getCapitalizedName().equals(""));
    }
    
    @Test
    public void getCapitalizedNameWorksWithShortName() {
        this.object.setName("x");
        assertTrue(this.object.getCapitalizedName().equals("X"));
    }
    
    @Test
    public void messagesAreRemovedWhenPopped() {
        this.object.addMessage("first");
        this.object.popMessage();
        assertEquals(0, this.object.popMessage().length());
    }
    
    @Test
    public void multipleMessagesWork() {
        this.object.addMessage("first");
        this.object.addMessage("second");
        this.object.addMessage("third");
        String msg = this.object.popMessage();
        assertTrue(msg.contains("first"));
        assertTrue(msg.contains("second"));
        assertTrue(msg.contains("third"));
    }
    
    @Test
    public void objectIsNotAliveAfterHittingItWithEnoughDamage() {
        this.object.hit(null, this.object.getMaxHitpoints());
        assertFalse(this.object.isAlive());
    }
    
    @Test
    public void getCapitalizedNameWorks() {
        this.object.setName("abc");
        assertTrue(this.object.getCapitalizedName().equals("Abc"));
    }
    
    @Test
    public void objectIsDestroyedAfterTimeToLiveExpires() {
        this.object.setTimeToLive(1.0);
        for (int i = 0; i < 10; i++) {
            this.object.tick(1.0);
        }
        assertFalse(this.object.isAlive());
    }
    
    @Test
    public void charactersAreDrawnOnTopOfOtherObjects() {
        GameObject rat = GameObjectFactory.create("rat");
        GameObject coin = GameObjectFactory.create("gold coin");
        assertTrue(rat.getDrawingOrder() > coin.getDrawingOrder());
    }
}
