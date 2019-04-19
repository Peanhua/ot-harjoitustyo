/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.BloodSplatter;
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
        this.container1 = new BloodSplatter();
        this.container2 = new BloodSplatter();
        this.attacker = new NonPlayerCharacter();
        this.defender = new NonPlayerCharacter();
    }
    
    @Test
    public void attackingHitsTarget() {
        Character targ = spy(new NonPlayerCharacter());
        this.attacker.getLocation().moveTo(this.container1);
        targ.getLocation().moveTo(this.container1);
        Action a = new ActionAttack(targ);
        a.act(this.attacker);
        verify(targ, atLeastOnce()).hit(notNull(), anyInt());
    }
    
    @Test
    public void cantAttackTargetInDifferentContainer() {
        Character targ = spy(new NonPlayerCharacter());
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
