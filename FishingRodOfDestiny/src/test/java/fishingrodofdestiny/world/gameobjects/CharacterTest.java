/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import javafx.embed.swing.JFXPanel;
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
public class CharacterTest {
    
    class CharacterSubclass extends Character {
        public CharacterSubclass() {
            super();
        }
    };
    
    private JFXPanel  jfxPanel;
    private Character character;
    
    public CharacterTest() {
    }
    
    @Before
    public void setUp() {
        this.jfxPanel  = new JFXPanel();
        this.character = new CharacterSubclass();
    }

    @After
    public void tearDown() {
        this.jfxPanel = null;
    }
    
    @Test
    public void toStringContainsAttack() {
        assertTrue(this.character.toString().contains("attack"));
    }
}
