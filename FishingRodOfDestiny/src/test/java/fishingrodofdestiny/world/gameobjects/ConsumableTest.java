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
public class ConsumableTest {
    private Character character;

    @Before
    public void setUp() {
        this.character = (Character) GameObjectFactory.create("rat");
    }
    
    @Test
    public void healingPotionHeals() {
        Consumable potion = (Consumable) GameObjectFactory.create("potion of healing");
        potion.getLocation().moveTo(this.character);
        this.character.setHitpoints(1);
        potion.useItem(this.character, this.character);
        assertTrue(this.character.getHitpoints() > 1);
    }
    
    @Test
    public void enoughAntivenomsRemovePoison() {
        int n = 1000;
        Buff poison = new Buff(n * 10, Buff.Type.POISON, 1);
        this.character.addBuff(poison);
        for (int i = 0; i < n; i++) {
            Consumable potion = (Consumable) GameObjectFactory.create("potion of antivenom");
            potion.getLocation().moveTo(this.character);
            potion.useItem(this.character, this.character);
        }
        assertFalse(poison.isAlive());
    }
}
