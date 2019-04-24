/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.resources;

import fishingrodofdestiny.dao.FileSettingsDao;
import fishingrodofdestiny.dao.SettingsDao;
import fishingrodofdestiny.settings.KeyboardSettings;
import java.net.URL;

/**
 *
 * @author joyr
 */
public class SettingsCache {
    private static SettingsCache instance = null;
    
    public static SettingsCache getInstance() {
        if (SettingsCache.instance == null) {
            SettingsCache.instance = new SettingsCache();
        }
        return SettingsCache.instance;
    }
    

    private final KeyboardSettings keyboardSettings;
  
    private SettingsCache() {
        SettingsDao dao = new FileSettingsDao(this.getUserSettingsURL());
        if (dao.isLoadable()) {
            this.keyboardSettings = new KeyboardSettings(dao);
            
        } else {
            // Load defaults:
            System.out.println("No user settings available, loading defaults.");
            String fullname = "fishingrodofdestiny/defaultSettings.ini";
            URL url = this.getClass().getClassLoader().getResource(fullname);
            dao = new FileSettingsDao(url);
            dao.setReadOnly(true);
            this.keyboardSettings = new KeyboardSettings(dao);
        }
        this.keyboardSettings.load();
    }
    
    private URL getUserSettingsURL() {
        URL url = null;
        try {
            String filename = System.getenv("FISHINGRODOFDESTINY_SETTINGS");
            if (filename == null || filename.length() == 0) {
                filename = "settings.ini";
            }
            url = new URL("file:" + filename);
            
        } catch (Exception e) {
            // This is never executed.
        }
        return url;
    }
        
    
    public KeyboardSettings getKeyboardSettings() {
        return this.keyboardSettings;
    }
    
    public void save() {
        this.keyboardSettings.save();
    }
}
