/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ArmorTest {

    @Test
    public void allSlotsHaveDisplayName() {
        for (Armor.Slot slot : Armor.Slot.values()) {
            assertNotNull(slot.getDisplayName());
        }
    }
}
