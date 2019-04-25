/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.GameObjectFactory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class BuffTest {

    @Test
    public void buffLinkedToObjectDoesNotDieAutomatically() {
        GameObject g = GameObjectFactory.create("gold coin");
        Buff b = new Buff(g);
        for (int i = 0; i < 1000; i++) {
            b.tick(1.0);
        }
        assertTrue(b.isAlive());
    }
}
