/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import org.junit.Before;
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
    
    private Character character;
    
    @Before
    public void setUp() {
        this.character = new CharacterSubclass();
    }

    @Test
    public void toStringContainsAttack() {
        assertTrue(this.character.toString().contains("attack"));
    }
}
