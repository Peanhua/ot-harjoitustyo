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
import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author joyr
 */
public class ActionAttackTest {
    
    private GameObject container1;
    private GameObject container2;
    private Character attacker;
    private Character defender;
    
    @Before
    public void setUp() {
        class MyAttacker extends NonPlayerCharacter {
            public MyAttacker() {
                super("my attacker");
                this.setAttack(99999);
            }
        }
        this.container1 = GameObjectFactory.create("pool of blood");
        this.container2 = GameObjectFactory.create("pool of blood");
        this.attacker = new MyAttacker();
        this.defender = (Character) GameObjectFactory.create("rat");
    }
    
    @Test
    public void attackingHitsTarget() {
        Character targ = spy((Character) GameObjectFactory.create("rat"));
        this.attacker.getLocation().moveTo(this.container1);
        targ.getLocation().moveTo(this.container1);
        Action a = new ActionAttack(targ);
        a.act(this.attacker);
        verify(targ, atLeastOnce()).hit(notNull(), anyInt());
    }
    
    @Test
    public void cantAttackTargetInDifferentContainer() {
        Character targ = spy((Character) GameObjectFactory.create("rat"));
        this.attacker.getLocation().moveTo(this.container1);
        targ.getLocation().moveTo(this.container2);
        Action a = new ActionAttack(targ);
        a.act(this.attacker);
        verify(targ, never()).hit(notNull(), anyInt());
    }
    
    @Test
    public void onHitIsCalled() {
        class TmpNPC extends NonPlayerCharacter {
            public boolean wasCalled = false;
            public TmpNPC() {
                super("tmp npc");
            }
            @Override
            public void onHit(GameObject instigator, int damage) {
                this.wasCalled = true;
            }
        }
        TmpNPC targ = new TmpNPC();
        this.attacker.getLocation().moveTo(this.container1);
        targ.getLocation().moveTo(this.container1);
        Action a = new ActionAttack(targ);
        a.act(this.attacker);
        assertTrue(targ.wasCalled);
    }
}
