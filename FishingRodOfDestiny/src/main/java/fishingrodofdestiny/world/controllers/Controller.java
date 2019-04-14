/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.controllers;

import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.gameobjects.Character;

/**
 *
 * @author joyr
 */
public abstract class Controller {
    private Character owner;
    private Action    nextAction;
    
    
    protected Controller(Character owner) {
        this.owner      = owner;
        this.nextAction = null;
    }
    
    public Character getOwner() {
        return this.owner;
    }
    
    public final void setNextAction(Action action) {
        this.nextAction = action;
    }
    
    public Action getNextAction() {
        return this.nextAction;
    }
}
