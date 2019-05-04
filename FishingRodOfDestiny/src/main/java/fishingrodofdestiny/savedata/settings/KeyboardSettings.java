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
 * Holds mapping between keys and actions/commands.
 * <p>
 * Actions are performed by the player character, whereas commands are related to the non-game part of the user interface (for example zooming in/out).
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
    
    /**
     * Register an observer to receive notifications whenever changes are made.
     * 
     * @param observer The observer to receive notifications
     */
    public void listenOnChange(Observer observer) {
        this.onChange.addObserver(observer);
    }
    
    /**
     * Load settings.
     */
    public void load() {
        this.dao.loadKeyboardSettings(this);
        this.onChange.notifyObservers();
        this.dirty = false;
    }
    
    /**
     * Save settings, if changes have been made since last save.
     */
    public void save() {
        if (!this.dirty) {
            return;
        }
        System.out.print("Saving keyboard settings...");
        System.out.flush();
        this.dao.saveKeyboardSettings(this);
        System.out.println("done.");
    }
    
    /**
     * Reteurn all the keys for given action.
     * 
     * @param forAction The action
     * @return The keys
     */
    public List<KeyCode> getKeys(Action.Type forAction) {
        List<KeyCode> rv = new ArrayList<>();
        this.keysToActions.forEach((key, action) -> {
            if (action == forAction) {
                rv.add(key);
            }
        });
        return rv;
    }
    
    /**
     * Return all the keys for given command.
     * 
     * @param forCommand The command
     * @return The keys
     */
    public List<KeyCode> getKeys(Command forCommand) {
        List<KeyCode> rv = new ArrayList<>();
        this.keysToCommands.forEach((key, command) -> {
            if (command == forCommand) {
                rv.add(key);
            }
        });
        return rv;
    }
    
    /**
     * Return the action associated to the given key.
     * 
     * @param keyCode The key
     * @return The action, or null if no action is bound to the given key
     */
    public Action.Type getAction(KeyCode keyCode) {
        return this.keysToActions.get(keyCode);
    }
    
    /**
     * Return the command associated to the given key.
     * 
     * @param keyCode The key
     * @return The command, or null if no command is bound to the given key
     */
    public Command getCommand(KeyCode keyCode) {
        return this.keysToCommands.get(keyCode);
    }
    
    /**
     * Return list of keys that have been bound to either action or command.
     * 
     * @return The keys
     */
    public List<KeyCode> getConfiguredKeys() {
        List<KeyCode> keys = new ArrayList<>();
        keys.addAll(this.keysToActions.keySet());
        keys.addAll(this.keysToCommands.keySet());
        return keys;
    }

    /**
     * Returns a string representing the action or command mapped for the given key.
     * 
     * @param key The key
     * @return The action or the command in String form
     */
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
    
    /**
     * Bind a key to action or command.
     * 
     * @param key The key
     * @param value The action or command
     */
    public void addKeybinding(String key, String value) {
        this.addKeybinding(KeyCode.valueOf(key), value);
    }
    
    /**
     * Bind a key to action or command.
     * 
     * @param keyCode The key
     * @param value The action or command
     */
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
    
    /**
     * Removes all keybindings for the given action or command.
     * 
     * @param actionCommand The action or command whose keybindings are removed
     */
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
    
    /**
     * Remove all keybindings for the given action.
     * 
     * @param action The action whose keybindings are removed.
     */
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
    
    /**
     * Remove all keybindings for the given command.
     * 
     * @param command The command whose keybindings are removed.
     */
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
