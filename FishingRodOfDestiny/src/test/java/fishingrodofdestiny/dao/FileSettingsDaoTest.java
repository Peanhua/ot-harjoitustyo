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
package fishingrodofdestiny.dao;

import fishingrodofdestiny.savedata.settings.KeyboardSettings;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author joyr
 */
public class FileSettingsDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();    
    private File folder;

    @Before
    public void setUp() throws Exception {
        this.folder = testFolder.newFolder();
    }

    private URL getFilename(String id) throws MalformedURLException {
        return new URL("file:" + this.folder.getAbsolutePath() + "/test" + id + ".txt");
    }

    @Test
    public void savingThenLoadingReturnsSame() throws MalformedURLException {
        String id = "savingThenLoadingReturnsSame";
        SettingsDao dao1 = new FileSettingsDao(this.getFilename(id));
        assertFalse(dao1.isLoadable());
        KeyboardSettings ks1 = new KeyboardSettings(dao1);
        ks1.addKeybinding("X", "WAIT");
        ks1.addKeybinding("Y", "ZOOM_OUT");
        ks1.save();
        
        SettingsDao dao2 = new FileSettingsDao(this.getFilename(id));
        assertTrue(dao2.isLoadable());
        KeyboardSettings ks2 = new KeyboardSettings(dao2);
        ks2.load();
        
        assertEquals(ks1.getAction(KeyCode.X), ks2.getAction(KeyCode.X));
        assertEquals(ks1.getCommand(KeyCode.Y), ks2.getCommand(KeyCode.Y));
    }
}
