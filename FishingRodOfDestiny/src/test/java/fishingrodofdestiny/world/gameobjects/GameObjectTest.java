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
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.GameObjectFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class GameObjectTest {
    
    class GameObjectSubclass extends GameObject {
        public GameObjectSubclass() {
            super("test object");
        }
    }
    
    private GameObject object;
    
    @Before
    public void setUp() {
        this.object   = new GameObjectSubclass();
    }
    
    @Test
    public void changingNameWorks() {
        String s1 = this.object.getName();
        this.object.setName("testing");
        assertTrue(this.object.getName().equals("testing"));
    }
    
    @Test
    public void getCapitalizedNameWorksWithEmptyName() {
        this.object.setName("");
        assertTrue(this.object.getCapitalizedName().equals(""));
    }
    
    @Test
    public void getCapitalizedNameWorksWithShortName() {
        this.object.setName("x");
        assertTrue(this.object.getCapitalizedName().equals("X"));
    }
    
    @Test
    public void messagesAreRemovedWhenPopped() {
        this.object.addMessage("first");
        this.object.popMessage();
        assertEquals(0, this.object.popMessage().length());
    }
    
    @Test
    public void multipleMessagesWork() {
        this.object.addMessage("first");
        this.object.addMessage("second");
        this.object.addMessage("third");
        String msg = this.object.popMessage();
        assertTrue(msg.contains("first"));
        assertTrue(msg.contains("second"));
        assertTrue(msg.contains("third"));
    }
    
    @Test
    public void objectIsNotAliveAfterHittingItWithEnoughDamage() {
        this.object.hit(null, this.object.getMaxHitpoints());
        assertFalse(this.object.isAlive());
    }
    
    @Test
    public void getCapitalizedNameWorks() {
        this.object.setName("abc");
        assertTrue(this.object.getCapitalizedName().equals("Abc"));
    }
    
    @Test
    public void objectIsDestroyedAfterTimeToLiveExpires() {
        this.object.setTimeToLive(1.0);
        for (int i = 0; i < 10; i++) {
            this.object.tick(1.0);
        }
        assertFalse(this.object.isAlive());
    }
    
    @Test
    public void charactersAreDrawnOnTopOfOtherObjects() {
        GameObject rat = GameObjectFactory.create("rat");
        GameObject coin = GameObjectFactory.create("gold coin");
        assertTrue(rat.getDrawingOrder() > coin.getDrawingOrder());
    }
}
