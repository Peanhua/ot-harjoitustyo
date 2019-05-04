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
package fishingrodofdestiny.world.controllers;

import fishingrodofdestiny.resources.SettingsCache;
import fishingrodofdestiny.savedata.settings.KeyboardSettings;
import fishingrodofdestiny.ui.screens.Screen;
import fishingrodofdestiny.ui.windows.ChooseItemRequester;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionActivateTile;
import fishingrodofdestiny.world.actions.ActionAttack;
import fishingrodofdestiny.world.actions.ActionDrop;
import fishingrodofdestiny.world.actions.ActionLevelUp;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.actions.ActionPickUp;
import fishingrodofdestiny.world.actions.ActionUse;
import fishingrodofdestiny.world.actions.ActionWait;
import fishingrodofdestiny.world.gameobjects.Armor;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author joyr
 */
public class PlayerController extends Controller {
    private final Player player;

    public PlayerController(Player owner) {
        super(owner);
        this.player = owner;
    }
    
    public boolean handleJavaFXEvent(Screen screen, Event event) {
        Action.Type actionType = null;
        
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyEvent ke = (KeyEvent) event;
            KeyboardSettings settings = SettingsCache.getInstance().getKeyboardSettings();
            actionType = settings.getAction(ke.getCode());
        }
        
        this.performAction(screen, actionType);
        
        return actionType != null;
    }
    
    private boolean performAction(Screen screen, Action.Type actionType) {
        // Return value is only there so that we can write the switch-case using returns thus redusing lines of code to make checkstyle happy!
        if (actionType != null) {
            switch (actionType) {
                case ACTIVATE_TILE: return this.actionActivateTile();
                case ATTACK:        return this.actionAttack();
                case DROP:          return this.actionDrop(screen);
                case MOVE_NORTH:    return this.actionMove(0, -1);
                case MOVE_SOUTH:    return this.actionMove(0, 1);
                case MOVE_WEST:     return this.actionMove(-1, 0);
                case MOVE_EAST:     return this.actionMove(1, 0);
                case PICK_UP:       return this.actionPickUp(screen);
                case WAIT:          return this.actionWait();
                case USE:           return this.actionUse(screen);
                case LEVEL_UP:      return this.actionLevelUp();
            }
        }
        return false;
    }
    
    private boolean actionActivateTile() {
        this.setNextAction(new ActionActivateTile());
        return true;
    }
    
    private boolean actionAttack() {
        Character owner = this.getOwner();
        Tile tile = owner.getLocation().getContainerTile();
        List<GameObject> targets = this.getOwner().getValidAttackTargets(tile);
        if (targets == null) {
            this.setNextAction(new ActionAttack(null));
            return true;
        }
        this.setNextAction(new ActionAttack(targets.get(0)));
        return true;
    }
    
    private boolean actionMove(int deltaX, int deltaY) {
        this.setNextAction(new ActionMove(deltaX, deltaY));
        return true;
    }
    
    private boolean actionPickUp(Screen screen) {
        List<GameObject> items = this.getOwner().getValidPickUpTargets();
        if (items.isEmpty()) {
            this.setNextAction(new ActionPickUp(null));
        } else if (items.size() == 1) {
            this.setNextAction(new ActionPickUp(items.get(0)));
        } else {
            ChooseItemRequester itemChooser = new ChooseItemRequester(screen, "Choose item to pick up", items, (chosenItems) -> {
                if (chosenItems.size() > 0) {
                    this.setNextAction(new ActionPickUp(chosenItems.get(0)));
                }
            });
            itemChooser.show();
        }
        return true;
    }
    
    
    private boolean actionDrop(Screen screen) {
        List<GameObject> items = this.getOwner().getInventory().getObjects();
        ChooseItemRequester itemChooser = new ChooseItemRequester(screen, "Choose item to drop", items, (chosenItems) -> {
            if (chosenItems.size() > 0) {
                this.setNextAction(new ActionDrop(chosenItems.get(0)));
            }
        });
        itemChooser.show();
        return true;
    }
    
    
    private boolean actionWait() {
        this.setNextAction(new ActionWait());
        return true;
    }
    
    
    private boolean actionUse(Screen screen) {
        List<GameObject> items = new ArrayList<>();
        this.getOwner().getInventory().getObjects().forEach(object -> {
            if (!this.isObjectInUse(object)) {
                items.add(object);
            }
        });
        ChooseItemRequester itemChooser = new ChooseItemRequester(screen, "Choose item to use", items, (chosenItems) -> {
            if (chosenItems.size() > 0) {
                this.setNextAction(new ActionUse(chosenItems.get(0)));
            }
        });
        itemChooser.show();
        return true;
    }
    
    private boolean isObjectInUse(GameObject object) {
        if (object == this.getOwner().getWeapon()) {
            return true;
        }
        
        for (Armor.Slot slot : Armor.Slot.values()) {
            if (object == this.getOwner().getArmor(slot)) {
                return true;
            }
        }
        
        return false;
    }
    
    
    private boolean actionLevelUp() {
        this.setNextAction(new ActionLevelUp());
        return true;
    }
}
