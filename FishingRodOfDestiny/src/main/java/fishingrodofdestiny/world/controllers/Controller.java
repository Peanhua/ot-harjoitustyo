/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.controllers;

import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.gameobjects.Character;

/**
 *
 * @author joyr
 */
public abstract class Controller {
    private Character owner;
    private Action    nextAction;
    private Subject   onNewAction;
    
    
    protected Controller(Character owner) {
        this.owner       = owner;
        this.nextAction  = null;
        this.onNewAction = new Subject();
    }
    
    public final Character getOwner() {
        return this.owner;
    }

    public final void listenOnNewAction(Observer observer) {
        this.onNewAction.addObserver(observer);
    }
    
    public final void setNextAction(Action action) {
        this.nextAction = action;
        if (action != null) {
            this.onNewAction.notifyObservers();
        }
    }
    
    public Action getNextAction() {
        return this.nextAction;
    }
}
