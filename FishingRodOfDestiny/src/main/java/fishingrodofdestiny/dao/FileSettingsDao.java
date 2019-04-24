/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.settings.KeyboardSettings;
import fishingrodofdestiny.world.actions.Action;
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
    Ini ini;
    URL url;

    public FileSettingsDao(URL url) {
        super();
        this.ini = new Ini();
        this.url = url;
        try {
            this.ini.load(url);
        } catch (Exception e) {
            this.ini = null;
        }
    }
    
    @Override
    public boolean isLoadable() {
        return this.ini != null;
    }
    
    @Override
    public void loadKeyboardSettings(KeyboardSettings to) {
        Ini.Section section = this.ini.get("keyboard");
        if (section == null) {
            return;
        }
        for (String key : section.keySet()) {
            KeyCode keyCode = KeyCode.valueOf(key);
            if (keyCode == null) {
                System.out.println("Error while loading keyboard settings: Unknown key: " + key);
                continue;
            }

            String value = section.get(key);
            Action.Type action = Action.Type.fromString(value);
            if (action != null) {
                to.setActionKeyMapping(action, keyCode);
                
            } else {
                KeyboardSettings.Command command = KeyboardSettings.Command.fromString(value);
                if (command != null) {
                    to.setCommandKeyMapping(command, keyCode);
                }
            }
        }
    }
    

    @Override
    public void saveKeyboardSettings(KeyboardSettings from) {
        Ini.Section section = this.ini.get("keyboard");

        section.clear();
        
        List<KeyCode> keys = from.getConfiguredKeys();
        keys.forEach(key -> {
            Action.Type action = from.getAction(key);
            if (action != null) {
                section.put(key.toString(), action.toString());
            } else {
                KeyboardSettings.Command command = from.getCommand(key);
                if (command != null) {
                    section.put(key.toString(), command.toString());
                }
            }
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
