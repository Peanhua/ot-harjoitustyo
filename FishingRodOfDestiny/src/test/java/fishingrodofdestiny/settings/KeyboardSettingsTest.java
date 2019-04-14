/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.settings;

import fishingrodofdestiny.world.actions.Action;
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
public class KeyboardSettingsTest {
    
    public KeyboardSettingsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void makeSureAllActionsHaveDefaultKeySetting() {
        KeyboardSettings settings = KeyboardSettings.getInstance();
        for (Action.Type action : Action.Type.values()) {
            if (action == Action.Type.MOVE) { // Ignore MOVE as it alone is not currently a useful command for the player to issue.
                continue;
            }
            assertNotNull(settings.getKey(action));
        }
    }
}
