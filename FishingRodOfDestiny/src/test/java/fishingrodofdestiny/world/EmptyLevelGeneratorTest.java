/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class EmptyLevelGeneratorTest {
    
    private EmptyLevelGenerator generator;
    
    @Before
    public void setUp() {
        Random random = new Random(42);
        this.generator = new EmptyLevelGenerator(random, 100, 100);
    }
    
    @Test
    public void canLevelBeCreated() {
        Level level = this.generator.generateLevel(0);
        assertTrue(level != null);
    }
}
