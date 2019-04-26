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
    
    @Test
    public void regenerationBuffHeals() {
        Buff b = new Buff(100, Buff.Type.REGENERATION, 1);
        Character rat = (Character) GameObjectFactory.create("rat");
        rat.setHitpoints(1);
        rat.addBuff(b);
        for (int i = 0; i < 100; i++) {
            rat.tick(1.0);
        }
        assertTrue(rat.getHitpoints() > 1);
    }
}
