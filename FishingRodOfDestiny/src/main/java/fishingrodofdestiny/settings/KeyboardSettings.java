/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.settings;

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
    
    
    private HashMap<GameObject.Action, KeyCode> actionsToKeys;
    private HashMap<KeyCode, GameObject.Action> keysToActions;
  
    private KeyboardSettings() {
        this.actionsToKeys = new HashMap<>();
        
        // Setup default keymapping, hard-coded for now:
        this.actionsToKeys.put(GameObject.Action.MOVE_NORTH,    KeyCode.UP);
        this.actionsToKeys.put(GameObject.Action.MOVE_SOUTH,    KeyCode.DOWN);
        this.actionsToKeys.put(GameObject.Action.MOVE_WEST,     KeyCode.LEFT);
        this.actionsToKeys.put(GameObject.Action.MOVE_EAST,     KeyCode.RIGHT);
        this.actionsToKeys.put(GameObject.Action.ACTIVATE_TILE, KeyCode.E);
        
        // Clone to keysToActions: */
        this.keysToActions = new HashMap<>();
        for (GameObject.Action action : this.actionsToKeys.keySet()) {
            this.keysToActions.put(this.actionsToKeys.get(action), action);
        }
    }
    
    public KeyCode getKey(GameObject.Action action) {
        return this.actionsToKeys.get(action);
    }
    
    public GameObject.Action getAction(KeyCode keyCode) {
        return this.keysToActions.get(keyCode);
    }
}
