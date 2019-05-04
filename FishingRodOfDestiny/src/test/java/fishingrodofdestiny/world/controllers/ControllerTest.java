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
