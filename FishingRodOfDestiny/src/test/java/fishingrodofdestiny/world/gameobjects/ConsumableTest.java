/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.GameObjectFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ConsumableTest {
    private Character character;

    @Before
    public void setUp() {
        this.character = (Character) GameObjectFactory.create("rat");
    }
    
    @Test
    public void healingPotionHeals() {
        Consumable potion = (Consumable) GameObjectFactory.create("potion of healing");
        potion.getLocation().moveTo(this.character);
        this.character.setHitpoints(1);
        potion.useItem(this.character, this.character);
        assertTrue(this.character.getHitpoints() > 1);
    }
}
