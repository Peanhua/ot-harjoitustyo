/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.settings;

import fishingrodofdestiny.resources.SettingsCache;
import fishingrodofdestiny.world.actions.Action;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class KeyboardSettingsTest {
    
    @Test
    public void makeSureAllActionsHaveDefaultKeySetting() {
        KeyboardSettings settings = SettingsCache.getInstance().getKeyboardSettings();
        for (Action.Type action : Action.Type.values()) {
            if (action == Action.Type.MOVE) { // Ignore MOVE as it alone is not currently a useful command for the player to issue.
                continue;
            }
            assertNotNull(settings.getKey(action));
        }
    }
}
