/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.BloodSplatter;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GoldCoin;
import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
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
public class ActionPickUpTest {

    private GameObject container1;
    private GameObject container2;
    private GameObject item;
    private Character  character;
    
    public ActionPickUpTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.container1 = new BloodSplatter();
        this.container2 = new BloodSplatter();
        this.item       = new GoldCoin();
        this.character  = new NonPlayerCharacter();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void itemCanBePickedUp() {
        int originalInventorySize = this.character.getInventory().getObjects().size();
        this.item.getLocation().moveTo(this.container1);
        this.character.getLocation().moveTo(this.container1);
        Action a = new ActionPickUp(this.item);
        a.act(this.character);
        assertEquals(this.item.getLocation().getContainerObject(), this.character);
        assertEquals(originalInventorySize + 1, this.character.getInventory().getObjects().size());
    }
    
    @Test
    public void itemCantBePickedUpFromDifferentLocation() {
        this.item.getLocation().moveTo(this.container1);
        this.character.getLocation().moveTo(this.container2);
        Action a = new ActionPickUp(this.item);
        a.act(this.character);
        assertNotEquals(this.item.getLocation().getContainerObject(), this.character);
    }
    
    @Test
    public void pickingUpNothing() {
        int originalInventorySize = this.character.getInventory().getObjects().size();
        Action a = new ActionPickUp(null);
        a.act(this.character);
        assertEquals(originalInventorySize, this.character.getInventory().getObjects().size());
    }
}