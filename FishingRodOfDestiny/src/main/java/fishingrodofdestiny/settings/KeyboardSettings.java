/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.settings;

import fishingrodofdestiny.dao.SettingsDao;
import fishingrodofdestiny.world.actions.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.input.KeyCode;

/**
 * Singleton to query mapping between keys and actions.
 *
 * @author joyr
 */
public class KeyboardSettings {
    public enum Command {
        ZOOM_IN,
        ZOOM_OUT,
        EXIT;
        
        public static Command fromString(String value) {
            for (Command c : Command.values()) {
                if (value.equals(c.toString())) {
                    return c;
                }
            }
            return null;
        }
    };
    
    private final HashMap<Action.Type, KeyCode> actionsToKeys;
    private final HashMap<KeyCode, Action.Type> keysToActions;
    private final HashMap<KeyCode, Command>     keysToCommands;
    private final SettingsDao                   dao;
    private boolean                             dirty;
  
    public KeyboardSettings(SettingsDao dao) {
        this.actionsToKeys  = new HashMap<>();
        this.keysToActions  = new HashMap<>();
        this.keysToCommands = new HashMap<>();
        this.dao            = dao;
        this.dirty          = false;
    }
    
    public void load() {
        this.dao.loadKeyboardSettings(this);
        this.dirty = false;
    }
    
    public void save() {
        if (!this.dirty) {
            return;
        }
        this.dao.saveKeyboardSettings(this);
    }
    
    public KeyCode getKey(Action.Type action) {
        return this.actionsToKeys.get(action);
    }
    
    public Action.Type getAction(KeyCode keyCode) {
        return this.keysToActions.get(keyCode);
    }
    
    public Command getCommand(KeyCode keyCode) {
        return this.keysToCommands.get(keyCode);
    }
    
    public List<KeyCode> getConfiguredKeys() {
        List<KeyCode> keys = new ArrayList<>();
        keys.addAll(this.keysToActions.keySet());
        keys.addAll(this.keysToCommands.keySet());
        return keys;
    }

    public void setActionKeyMapping(Action.Type action, KeyCode keyCode) {
        this.actionsToKeys.put(action, keyCode);
        this.keysToActions.put(keyCode, action);
        this.dirty = true;
    }
    
    public void setCommandKeyMapping(Command command, KeyCode keyCode) {
        this.keysToCommands.put(keyCode, command);
        this.dirty = true;
    }
}
