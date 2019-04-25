/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.resources;

import fishingrodofdestiny.dao.FileSettingsDao;
import fishingrodofdestiny.dao.JdbcSettingsDao;
import fishingrodofdestiny.dao.MemorySettingsDao;
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
        SettingsDao dao = this.getDao("jdbc:sqlite:FishingRodOfDestiny.db", System.getenv("FISHINGRODOFDESTINY_SETTINGS"));
        if (dao != null && dao.isLoadable()) {
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
    
    private SettingsDao getDao(String defaultUri, String uri) {
        if (uri == null) {
            uri = defaultUri;
        }
        
        SettingsDao dao;
        if (uri.startsWith("jdbc:")) {
            dao = new JdbcSettingsDao(uri);
        } else if (uri.startsWith("file:")) {
            try {
                dao = new FileSettingsDao(new URL(uri));
            } catch (Exception e) {
                System.out.println(e);
                dao = null;
            }
        } else {
            dao = new MemorySettingsDao();
        }
        return dao;
    }
    
    public KeyboardSettings getKeyboardSettings() {
        return this.keyboardSettings;
    }
    
    public void save() {
        this.keyboardSettings.save();
    }
}
