/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fishingrodofdestiny.savedata.settings;

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
            assertTrue(settings.getKeys(action).size() > 0);
        }
    }

    @Test
    public void makeSureAllCommandsHaveDefaultKeySetting() {
        KeyboardSettings settings = SettingsCache.getInstance().getKeyboardSettings();
        for (KeyboardSettings.Command command : KeyboardSettings.Command.values()) {
            assertTrue(settings.getKeys(command).size() > 0);
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
