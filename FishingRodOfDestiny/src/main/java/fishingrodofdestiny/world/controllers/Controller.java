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
