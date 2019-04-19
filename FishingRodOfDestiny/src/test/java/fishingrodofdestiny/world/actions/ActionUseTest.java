/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.Item;
import fishingrodofdestiny.world.gameobjects.KitchenKnife;
import fishingrodofdestiny.world.gameobjects.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ActionUseTest {
    
    private Player player;
    
    @Before
    public void setUp() {
        this.player = new Player();
    }
    
    @Test
    public void usingWeaponWieldsIt() {
        Item weapon = new KitchenKnife();
        weapon.getLocation().moveTo(this.player);
        Action action = new ActionUse(weapon);
        action.act(this.player);
        assertEquals(weapon, this.player.getWeapon());
    }

    @Test
    public void usingWeaponNotInInventoryDoesNotWieldIt() {
        Item weapon = new KitchenKnife();
        Action action = new ActionUse(weapon);
        action.act(this.player);
        assertNull(this.player.getWeapon());
    }
}
