/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.savedata.settings.KeyboardSettings;
import java.io.File;
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
public class JdbcSettingsDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();    
    private File folder;

    @Before
    public void setUp() throws Exception {
        this.folder = testFolder.newFolder();
    }

    private String getURL(String id) {
        return "jdbc:sqlite:" + this.folder.getAbsolutePath() + "/test" + id + ".txt";
    }

    @Test
    public void savingThenLoadingReturnsSame() {
        String id = "savingThenLoadingReturnsSame";
        SettingsDao dao1 = new JdbcSettingsDao(this.getURL(id));
        assertFalse(dao1.isLoadable());
        KeyboardSettings ks1 = new KeyboardSettings(dao1);
        ks1.addKeybinding("X", "WAIT");
        ks1.addKeybinding("Y", "ZOOM_OUT");
        ks1.save();
        
        SettingsDao dao2 = new JdbcSettingsDao(this.getURL(id));
        assertTrue(dao2.isLoadable());
        KeyboardSettings ks2 = new KeyboardSettings(dao2);
        ks2.load();
        
        assertEquals(ks1.getAction(KeyCode.X), ks2.getAction(KeyCode.X));
        assertEquals(ks1.getCommand(KeyCode.Y), ks2.getCommand(KeyCode.Y));
    }
}
