/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.savedata.settings.KeyboardSettings;
import java.io.File;
import java.net.URL;
import java.util.List;
import javafx.scene.input.KeyCode;
import org.ini4j.Ini;

/**
 *
 * @author joyr
 */
public class FileSettingsDao extends SettingsDao {
    private Ini     ini;
    private URL     url;
    private boolean loadable;

    public FileSettingsDao(URL url) {
        super();
        this.ini = new Ini();
        this.url = url;
        try {
            this.ini.load(url);
            this.loadable = true;
        } catch (Exception e) {
            this.loadable = false;
        }
        if (this.ini == null) {
            this.loadable = false;
        }
    }
    
    @Override
    public boolean isLoadable() {
        return this.loadable;
    }
    
    @Override
    public void loadKeyboardSettings(KeyboardSettings to) {
        if (this.ini == null) {
            return;
        }
        
        Ini.Section section = this.ini.get("keyboard");
        if (section == null) {
            return;
        }
        section.keySet().forEach(key -> {
            try {
                to.addKeybinding(key, section.get(key));
            } catch (Exception e) {
                System.out.println("Error while loading keyboard settings: " + e);
            }
        });
    }
    
    @Override
    public void saveKeyboardSettings(KeyboardSettings from) {
        if (this.ini == null) {
            return;
        }
        
        Ini.Section section = this.ini.get("keyboard");
        if (section != null) {
            section.clear();
        }
        
        List<KeyCode> keys = from.getConfiguredKeys();
        keys.forEach(key -> {
            this.ini.put("keyboard", key.toString(), from.getActionStringForKey(key));
        });
        
        this.save();
    }
    
    private void save() {
        if (this.isReadOnly()) {
            return;
        }
        
        try {
            this.ini.store(new File(this.url.getFile()));
            
        } catch (Exception e) {
            System.out.println("Failed to save keyboard settings: " + e);
        }
    }
}
