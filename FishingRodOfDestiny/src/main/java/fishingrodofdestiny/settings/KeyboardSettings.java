/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.settings;

import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.gameobjects.GameObject;
import java.util.HashMap;
import javafx.scene.input.KeyCode;

/**
 * Singleton to query mapping between keys and actions.
 *
 * @author joyr
 */
public class KeyboardSettings {
    private static KeyboardSettings instance = null;
    
    public static KeyboardSettings getInstance() {
        if (KeyboardSettings.instance == null) {
            KeyboardSettings.instance = new KeyboardSettings();
        }
        return KeyboardSettings.instance;
    }
    
    
    public enum Command {
        ZOOM_IN,
        ZOOM_OUT,
        EXIT
    };
    
    
    private HashMap<Action.Type, KeyCode> actionsToKeys;
    private HashMap<KeyCode, Action.Type> keysToActions;
    private HashMap<KeyCode, Command>     keysToCommands;
  
    private KeyboardSettings() {
        this.actionsToKeys = new HashMap<>();
        
        // Setup default keymapping, hard-coded for now:
        this.actionsToKeys.put(Action.Type.MOVE_NORTH,    KeyCode.UP);
        this.actionsToKeys.put(Action.Type.MOVE_SOUTH,    KeyCode.DOWN);
        this.actionsToKeys.put(Action.Type.MOVE_WEST,     KeyCode.LEFT);
        this.actionsToKeys.put(Action.Type.MOVE_EAST,     KeyCode.RIGHT);
        this.actionsToKeys.put(Action.Type.ACTIVATE_TILE, KeyCode.E);
        this.actionsToKeys.put(Action.Type.ATTACK,        KeyCode.A);
        this.actionsToKeys.put(Action.Type.WAIT,          KeyCode.W);
        this.actionsToKeys.put(Action.Type.PICK_UP,       KeyCode.P);
        this.actionsToKeys.put(Action.Type.DROP,          KeyCode.D);
        
        // Clone to keysToActions: */
        this.keysToActions = new HashMap<>();
        for (Action.Type action : this.actionsToKeys.keySet()) {
            this.keysToActions.put(this.actionsToKeys.get(action), action);
        }

        this.keysToCommands = new HashMap<>();
        this.keysToCommands.put(KeyCode.PAGE_DOWN, Command.ZOOM_IN);
        this.keysToCommands.put(KeyCode.PAGE_UP,   Command.ZOOM_OUT);
        this.keysToCommands.put(KeyCode.ESCAPE,    Command.EXIT);
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
}
