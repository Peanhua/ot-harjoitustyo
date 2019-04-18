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
 * The base class for all Character controllers.
 * <p>
 * A controller determines the actions taken by the associated GameObject.
 * 
 * @author joyr
 */
public abstract class Controller {
    private final Character owner;
    private final Subject   onNewAction;
    private Action          nextAction;
    
    /**
     * Create new controlled for the given owner.
     * 
     * @param owner The owner of this controller.
     */
    protected Controller(Character owner) {
        this.owner       = owner;
        this.nextAction  = null;
        this.onNewAction = new Subject();
    }
    
    public final Character getOwner() {
        return this.owner;
    }

    /**
     * Register an observer to be called whenever the next action is changed.
     * 
     * @param observer The observer object to be called when the next action is changed.
     */
    public final void listenOnNewAction(Observer observer) {
        this.onNewAction.addObserver(observer);
    }
    
    /**
     * Change the current action to be taken.
     * <p>
     * The controller can use this, or provide its own implementation of getNextAction().
     * 
     * @see #getNextAction()
     * @param action New current action.
     */
    public final void setNextAction(Action action) {
        this.nextAction = action;
        if (action != null) {
            this.onNewAction.notifyObservers();
        }
    }

    /**
     * Return the next action the owner Character should perform.
     * <p>
     * Can be overridden.
     * 
     * @return The action to take.
     */
    public Action getNextAction() {
        return this.nextAction;
    }
}
