/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.resources;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

/**
 *
 * @author joyr
 */
public class SettingsCacheTest {
    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Test
    public void nonexistentSettingsGiveDefaults() {
        environmentVariables.set("FISHINGRODOFDESTINY_SETTINGS", "memory");
        assertTrue(SettingsCache.getInstance().getKeyboardSettings().getConfiguredKeys().size() > 0);
    }
}
