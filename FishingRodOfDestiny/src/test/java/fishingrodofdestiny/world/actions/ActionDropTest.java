/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.Item;
import fishingrodofdestiny.world.gameobjects.KitchenKnife;
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
    
    @Before
    public void setUp() {
        this.tile = new FloorTile(null, 0, 0);
        this.character = new Player();
        this.character.getLocation().moveTo(this.tile);
        this.weapon = new KitchenKnife();
        this.weapon.getLocation().moveTo(this.character);
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
    public void droppingWieldedWeaponPutsUnwieldsTheWeapon() {
        this.weapon.useItem(this.character, this.character);
        Action action = new ActionDrop(this.weapon);
        action.act(this.character);
        assertNull(this.character.getWeapon());
    }
}
