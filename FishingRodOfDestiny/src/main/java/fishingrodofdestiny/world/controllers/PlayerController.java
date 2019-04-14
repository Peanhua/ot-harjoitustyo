/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.controllers;

import fishingrodofdestiny.settings.KeyboardSettings;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionActivateTile;
import fishingrodofdestiny.world.actions.ActionAttack;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.actions.ActionPickUp;
import fishingrodofdestiny.world.actions.ActionWait;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.List;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author joyr
 */
public class PlayerController extends Controller {
    private Player player;

    public PlayerController(Player owner) {
        super(owner);
        this.player = owner;
    }
    
    public boolean handleJavaFXEvent(Event event) {
        Action.Type actionType = null;
        
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent ke = (KeyEvent) event;
            KeyboardSettings settings = KeyboardSettings.getInstance();
            actionType = settings.getAction(ke.getCode());
        }
        
        Action action = this.mapActionTypeToAction(actionType);
        this.setNextAction(action);
        
        return action != null;
    }
    
    private Action mapActionTypeToAction(Action.Type actionType) {
        switch (actionType) {
            case ACTIVATE_TILE: return this.actionActivateTile();
            case ATTACK:        return this.actionAttack();
            case MOVE_NORTH:    return this.actionMove(0, -1);
            case MOVE_SOUTH:    return this.actionMove(0, 1);
            case MOVE_WEST:     return this.actionMove(-1, 0);
            case MOVE_EAST:     return this.actionMove(1, 0);
            case PICK_UP:       return this.actionPickUp();
            case WAIT:          return this.actionWait();
        }
        return null;
    }
    
    private Action actionActivateTile() {
        return new ActionActivateTile();
    }
    
    private Action actionAttack() {
        Character owner = this.getOwner();
        Tile tile = owner.getLocation().getContainerTile();
        List<GameObject> targets = this.getOwner().getValidAttackTargets(tile);
        if (targets == null) {
            return new ActionAttack(null);
        }
        return new ActionAttack(targets.get(0));
    }
    
    private Action actionMove(int deltaX, int deltaY) {
        return new ActionMove(deltaX, deltaY);
    }
    
    private Action actionPickUp() {
        return null;
    }
    
    private Action actionWait() {
        return new ActionWait();
    }
}
