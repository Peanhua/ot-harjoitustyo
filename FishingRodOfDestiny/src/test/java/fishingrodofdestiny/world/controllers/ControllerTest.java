/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.controllers;

import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.gameobjects.Character;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ControllerTest {
    
    class MyController extends Controller {
        public boolean wasCalled;
        
        public MyController(Character owner) {
            super(owner);
            this.wasCalled = false;
        }
    }
        
    private MyController controller;
    
    @Before
    public void setUp() {
        this.controller = new MyController(null);
    }
    
    @Test
    public void nextActionWorks() {
        controller.setNextAction(null);
        assertNull(controller.getNextAction());
        
        controller.setNextAction(new ActionMove(1, 0));
        assertNotNull(controller.getNextAction());
    }
    
    @Test
    public void onNewActionObserverIsCalled() {
        controller.listenOnNewAction(() -> {
            controller.wasCalled = true;
        });
        controller.setNextAction(new ActionMove(1, 0));
        assertTrue(controller.wasCalled);
    }
}
