/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.settings;

import fishingrodofdestiny.resources.SettingsCache;
import fishingrodofdestiny.world.actions.Action;
import java.util.List;
import javafx.scene.input.KeyCode;
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

    @Test
    public void addingKeybindingWithInvalidActionOrCommandThrowsRuntimeException() {
        KeyboardSettings settings = new KeyboardSettings(null);
        boolean gotRuntimeException = false;
        try {
            settings.addKeybinding("X", "lehmä");
        } catch (RuntimeException e) {
            gotRuntimeException = true;
        }
        assertTrue(gotRuntimeException);
    }
    
    @Test
    public void savingWithoutChangesDoesNothing() {
        KeyboardSettings settings = new KeyboardSettings(null);
        boolean exceptionWasThrown = false;
        try {
            settings.save();
        } catch (Exception e) {
            exceptionWasThrown = true;
        }
        assertFalse(exceptionWasThrown);
    }
    
    @Test
    public void bindingKeysAreMemorized() {
        KeyboardSettings settings = new KeyboardSettings(null);
        settings.addKeybinding("X", "MOVE_NORTH");
        settings.addKeybinding("Y", "ZOOM_IN");
        List<KeyCode> keys = settings.getConfiguredKeys();
        assertTrue(keys.contains(KeyCode.X));
        assertTrue(keys.contains(KeyCode.Y));
    }
    
    @Test
    public void bindingActionIsMemorized() {
        KeyboardSettings settings = new KeyboardSettings(null);
        settings.addKeybinding("X", "MOVE_NORTH");
        assertEquals(Action.Type.MOVE_NORTH, settings.getAction(KeyCode.X));
    }

    @Test
    public void bindingCommandIsMemorized() {
        KeyboardSettings settings = new KeyboardSettings(null);
        settings.addKeybinding("Y", "ZOOM_IN");
        assertEquals(KeyboardSettings.Command.ZOOM_IN, settings.getCommand(KeyCode.Y));
    }
}
