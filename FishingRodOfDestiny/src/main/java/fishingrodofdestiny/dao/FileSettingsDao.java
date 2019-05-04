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
import java.net.URL;
import java.util.List;
import javafx.scene.input.KeyCode;
import org.ini4j.Ini;

/**
 * File based dao for settings.
 * 
 * @author joyr
 */
public class FileSettingsDao extends SettingsDao {
    private Ini     ini;
    private URL     url;
    private boolean loadable;

    /**
     * Create a new FileSettingsDao.
     * 
     * @param url URL to the file, usually starts with "file:"
     */
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
