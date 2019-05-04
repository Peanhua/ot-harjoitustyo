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
import fishingrodofdestiny.world.gameobjects.Armor;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.Item;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ActionDropTest {
    
    private Tile       tile;
    private Character  character;
    private Item       weapon;
    private Armor      armor;
    
    @Before
    public void setUp() {
        this.tile = new FloorTile(null, 0, 0);
        this.character = new Player();
        this.character.getLocation().moveTo(this.tile);
        this.weapon = (Item) GameObjectFactory.create("kitchen knife");
        this.weapon.getLocation().moveTo(this.character);
        this.armor = (Armor) GameObjectFactory.create("hat");
        this.armor.getLocation().moveTo(this.character);
    }
    
    @Test
    public void droppingPutsTheItemOnTile() {
        Action action = new ActionDrop(this.weapon);
        action.act(this.character);
        assertEquals(this.tile, this.weapon.getLocation().getContainerTile());
    }

    @Test
    public void droppingWieldedWeaponPutsTheItemOnTile() {
        this.weapon.useItem(this.character, this.character);
        Action action = new ActionDrop(this.weapon);
        action.act(this.character);
        assertEquals(this.tile, this.weapon.getLocation().getContainerTile());
    }

    @Test
    public void droppingWieldedWeaponUnwieldsTheWeapon() {
        this.weapon.useItem(this.character, this.character);
        Action action = new ActionDrop(this.weapon);
        action.act(this.character);
        assertNull(this.character.getWeapon());
    }

    @Test
    public void droppingEquippedArmorUnequipsTheArmor() {
        this.armor.useItem(this.character, this.character);
        Action action = new ActionDrop(this.armor);
        action.act(this.character);
        assertNull(this.character.getArmor(this.armor.getSlot()));
    }
    
    @Test
    public void droppingItemThatIsNotInInventoryDoesNotMoveTheItem() {
        Tile somewhereElse = new FloorTile(null, 0, 0);
        this.armor.getLocation().moveTo(somewhereElse);
        Action action = new ActionDrop(this.armor);
        action.act(this.character);
        assertEquals(somewhereElse, this.armor.getLocation().getContainerTile());
    }
    
    @Test
    public void droppingNullDoesntCrash() {
        boolean exceptionWasThrown = false;
        Action action = new ActionDrop(null);
        try {
            action.act(this.character);
        } catch (Exception e) {
            exceptionWasThrown = true;
        }
        assertFalse(exceptionWasThrown);
    }
}
