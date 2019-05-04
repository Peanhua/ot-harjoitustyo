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
package fishingrodofdestiny.savedata.settings;

import fishingrodofdestiny.dao.SettingsDao;
import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
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
    };
    
    private final HashMap<KeyCode, Action.Type> keysToActions;
    private final HashMap<KeyCode, Command>     keysToCommands;
    private final SettingsDao                   dao;
    private boolean                             dirty;
    private final Subject                       onChange;
  
    public KeyboardSettings(SettingsDao dao) {
        this.keysToActions  = new HashMap<>();
        this.keysToCommands = new HashMap<>();
        this.dao            = dao;
        this.dirty          = false;
        this.onChange       = new Subject();
    }
    
    public KeyboardSettings(SettingsDao dao, KeyboardSettings copyFrom) {
        this(dao);
        copyFrom.keysToActions.forEach((k, v) -> this.keysToActions.put(k, v));
        copyFrom.keysToCommands.forEach((k, v) -> this.keysToCommands.put(k, v));
    }
    
    public void listenOnChange(Observer observer) {
        this.onChange.addObserver(observer);
    }
    
    public void load() {
        this.dao.loadKeyboardSettings(this);
        this.onChange.notifyObservers();
        this.dirty = false;
    }
    
    public void save() {
        if (!this.dirty) {
            return;
        }
        System.out.print("Saving keyboard settings...");
        System.out.flush();
        this.dao.saveKeyboardSettings(this);
        System.out.println("done.");
    }
    
    public List<KeyCode> getKeys(Action.Type forAction) {
        List<KeyCode> rv = new ArrayList<>();
        this.keysToActions.forEach((key, action) -> {
            if (action == forAction) {
                rv.add(key);
            }
        });
        return rv;
    }
    
    public List<KeyCode> getKeys(Command forCommand) {
        List<KeyCode> rv = new ArrayList<>();
        this.keysToCommands.forEach((key, command) -> {
            if (command == forCommand) {
                rv.add(key);
            }
        });
        return rv;
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

    public String getActionStringForKey(KeyCode key) {
        String actionStr = null;
        Action.Type action = this.getAction(key);
        if (action != null) {
            actionStr = action.toString();
        } else {
            KeyboardSettings.Command command = this.getCommand(key);
            if (command != null) {
                actionStr = command.toString();
            }
        }
        return actionStr;
    }
    
    public void addKeybinding(String key, String value) {
        this.addKeybinding(KeyCode.valueOf(key), value);
    }
    
    public void addKeybinding(KeyCode keyCode, String value) {
        try {
            Action.Type action = Action.Type.valueOf(value);
            this.setActionKeyMapping(action, keyCode);
            return;
        } catch (Exception e) {
        }

        try {
            KeyboardSettings.Command command = KeyboardSettings.Command.valueOf(value);
            this.setCommandKeyMapping(command, keyCode);
            return;
        } catch (Exception e) {
        }

        throw new RuntimeException("Unknown value '" + value + "' for key '" + keyCode.toString() + "'");
    }

    private void setActionKeyMapping(Action.Type action, KeyCode keyCode) {
        this.keysToActions.put(keyCode, action);
        this.onChange.notifyObservers();
        this.dirty = true;
    }
    
    private void setCommandKeyMapping(Command command, KeyCode keyCode) {
        this.keysToCommands.put(keyCode, command);
        this.onChange.notifyObservers();
        this.dirty = true;
    }
    
    public void removeKeyMappings(String actionCommand) {
        try {
            Action.Type action = Action.Type.valueOf(actionCommand);
            this.removeActionKeyMappings(action);
            return;
        } catch (Exception e) {
        }

        try {
            KeyboardSettings.Command command = KeyboardSettings.Command.valueOf(actionCommand);
            this.removeCommandKeyMappings(command);
            return;
        } catch (Exception e) {
        }

        throw new RuntimeException("Unknown actionCommand '" + actionCommand + "'");
    }
    
    public void removeActionKeyMappings(Action.Type action) {
        List<KeyCode> keysToRemove = new ArrayList<>();
        this.keysToActions.forEach((k, a) -> {
            if (a == action) {
                keysToRemove.add(k);
            }
        });
        keysToRemove.forEach(k -> this.keysToActions.remove(k));
        
        this.onChange.notifyObservers();
        this.dirty = true;
    }
    
    public void removeCommandKeyMappings(Command command) {
        List<KeyCode> keysToRemove = new ArrayList<>();
        this.keysToCommands.forEach((k, c) -> {
            if (c == command) {
                keysToRemove.add(k);
            }
        });
        keysToRemove.forEach(k -> this.keysToCommands.remove(k));

        this.onChange.notifyObservers();
        this.dirty = true;
    }
}
