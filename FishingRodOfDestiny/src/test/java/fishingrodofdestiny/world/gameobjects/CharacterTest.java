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
            super("test character");
        }
    };
    
    private Character character;
    
    @Before
    public void setUp() {
        this.character = new CharacterSubclass();
    }

    @Test
    public void adjustingCarryingCapacityWorks() {
        int initialCapacity = this.character.getCarryingCapacity();
        int n = 42;
        for(int i = 0; i < n; i++) {
            this.character.adjustCarryingCapacity(1);
            assertEquals(initialCapacity + i + 1, this.character.getCarryingCapacity());
        }
        this.character.adjustCarryingCapacity(10);
        assertEquals(initialCapacity + n + 10, this.character.getCarryingCapacity());
        this.character.adjustCarryingCapacity(-5);
        assertEquals(initialCapacity + n + 10 - 5, this.character.getCarryingCapacity());
    }
        

    @Test
    public void carryingCapacityDoesntGoBelowZero() {
        int initialCapacity = this.character.getCarryingCapacity();
        this.character.adjustCarryingCapacity(-initialCapacity - 4);
        assertEquals(0, this.character.getCarryingCapacity());
    }
    
}
