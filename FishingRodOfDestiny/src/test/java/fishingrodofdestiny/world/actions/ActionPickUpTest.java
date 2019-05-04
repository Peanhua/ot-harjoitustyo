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
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Character;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ActionPickUpTest {

    private GameObject container1;
    private GameObject container2;
    private GameObject item;
    private Character  character;
    
    @Before
    public void setUp() {
        this.container1 = GameObjectFactory.create("pool of blood");
        this.container2 = GameObjectFactory.create("pool of blood");
        this.item       = GameObjectFactory.create("gold coin");
        this.character  = (Character) GameObjectFactory.create("rat");
    }
    
    @Test
    public void itemCanBePickedUp() {
        int originalInventorySize = this.character.getInventory().getObjects().size();
        this.item.getLocation().moveTo(this.container1);
        this.character.getLocation().moveTo(this.container1);
        Action a = new ActionPickUp(this.item);
        a.act(this.character);
        assertEquals(this.item.getLocation().getContainerObject(), this.character);
        assertEquals(originalInventorySize + 1, this.character.getInventory().getObjects().size());
    }
    
    @Test
    public void itemCantBePickedUpFromDifferentLocation() {
        this.item.getLocation().moveTo(this.container1);
        this.character.getLocation().moveTo(this.container2);
        Action a = new ActionPickUp(this.item);
        a.act(this.character);
        assertNotEquals(this.item.getLocation().getContainerObject(), this.character);
    }
    
    @Test
    public void pickingUpNothing() {
        int originalInventorySize = this.character.getInventory().getObjects().size();
        Action a = new ActionPickUp(null);
        a.act(this.character);
        assertEquals(originalInventorySize, this.character.getInventory().getObjects().size());
    }
}
