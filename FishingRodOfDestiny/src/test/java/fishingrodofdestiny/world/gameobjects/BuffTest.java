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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class BuffTest {

    @Test
    public void buffLinkedToObjectDoesNotDieAutomatically() {
        GameObject g = GameObjectFactory.create("gold coin");
        Buff b = new Buff(g);
        for (int i = 0; i < 1000; i++) {
            b.tick(1.0);
        }
        assertTrue(b.isAlive());
    }
    
    @Test
    public void regenerationBuffHeals() {
        Buff b = new Buff(100, Buff.Type.REGENERATION, 1);
        Character rat = (Character) GameObjectFactory.create("rat");
        rat.setHitpoints(1);
        rat.addBuff(b);
        for (int i = 0; i < 100; i++) {
            rat.tick(1.0);
        }
        assertTrue(rat.getHitpoints() > 1);
    }
}
