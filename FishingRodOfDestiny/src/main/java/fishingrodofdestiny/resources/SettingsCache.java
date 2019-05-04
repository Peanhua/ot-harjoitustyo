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
package fishingrodofdestiny.resources;

import fishingrodofdestiny.dao.FileSettingsDao;
import fishingrodofdestiny.dao.JdbcSettingsDao;
import fishingrodofdestiny.dao.MemorySettingsDao;
import fishingrodofdestiny.dao.SettingsDao;
import fishingrodofdestiny.savedata.settings.KeyboardSettings;
import java.net.URL;

/**
 * Manage settings: loading, saving, and caching.
 * <p>
 * Uses singleton pattern.
 * A dao object handles the actual loading/saving, determines the type of dao to use via environment variable.
 * 
 * @author joyr
 */
public class SettingsCache {
    private static SettingsCache instance = null;
    
    /**
     * Returns the single instance of SettingsCache object.
     * 
     * @return The SettingsCache object
     */
    public static SettingsCache getInstance() {
        if (SettingsCache.instance == null) {
            SettingsCache.instance = new SettingsCache();
        }
        return SettingsCache.instance;
    }
    

    private final KeyboardSettings keyboardSettings;
  
    private SettingsCache() {
        SettingsDao dao = this.getDao(this.getUri());
        if (dao != null) {
            if (dao.isLoadable()) {
                this.keyboardSettings = new KeyboardSettings(dao);
            } else {
                System.out.println("No user settings available, loading defaults.");
                this.keyboardSettings = new KeyboardSettings(dao, this.getDefaultKeyboardSettings());
            }
            this.keyboardSettings.load();
            
        } else {
            // Load and use read-only defaults:
            System.out.println("Warning! Unable to save settings to " + this.getUri());
            this.keyboardSettings = this.getDefaultKeyboardSettings();
        }
    }
    
    private KeyboardSettings getDefaultKeyboardSettings() {
        String fullname = "fishingrodofdestiny/defaultSettings.ini";
        URL url = this.getClass().getClassLoader().getResource(fullname);
        SettingsDao dao = new FileSettingsDao(url);
        dao.setReadOnly(true);
        KeyboardSettings ks = new KeyboardSettings(dao);
        ks.load();
        return ks;
    }
    
    private String getUri() {
        String uri = System.getenv("FISHINGRODOFDESTINY_SETTINGS");
        if (uri == null) {
            uri = "jdbc:sqlite:FishingRodOfDestiny.db";
        }
        return uri;
    }        
        
    
    private SettingsDao getDao(String uri) {
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
    
    /**
     * Returns the KeyboardSettings.
     * 
     * @return The KeyboardSettings
     */
    public KeyboardSettings getKeyboardSettings() {
        return this.keyboardSettings;
    }
    
    /**
     * Save all settings.
     */
    public void save() {
        this.keyboardSettings.save();
    }
}
