/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionUse;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class CharacterTest {
    
    class CharacterSubclass extends Character {
        public CharacterSubclass() {
            super("test character");
        }
    };
    
    private Character character;
    
    @Before
    public void setUp() {
        this.character = new CharacterSubclass();
    }

    @Test
    public void adjustingCarryingCapacityWorks() {
        int initialCapacity = this.character.getCarryingCapacity();
        int n = 42;
        for(int i = 0; i < n; i++) {
            this.character.adjustCarryingCapacity(1);
            assertEquals(initialCapacity + i + 1, this.character.getCarryingCapacity());
        }
        this.character.adjustCarryingCapacity(10);
        assertEquals(initialCapacity + n + 10, this.character.getCarryingCapacity());
        this.character.adjustCarryingCapacity(-5);
        assertEquals(initialCapacity + n + 10 - 5, this.character.getCarryingCapacity());
    }
        

    @Test
    public void carryingCapacityDoesntGoBelowZero() {
        int initialCapacity = this.character.getCarryingCapacity();
        this.character.adjustCarryingCapacity(-initialCapacity - 4);
        assertEquals(0, this.character.getCarryingCapacity());
    }
    
    
    @Test
    public void experiencePointsForNextLevelIsGreaterThanForPreviousLevel() {
        for (int i = 1; i < 1000; i++) {
            assertTrue(this.character.getExperiencePointsForCharacterLevel(i - 1) < this.character.getExperiencePointsForCharacterLevel(i));
        }
    }
    
    @Test
    public void characterWithHighDefenceIsMoreLikelyToIncreaseDefenceWhenLevellingUp() {
        int defcount = 0;
        int n = 1000;
        this.character.setDefence(1000);
        int curdef = this.character.getDefence();
        for (int i = 0; i < n; i++) {
            this.character.increaseCharacterLevel();
            int newdef = this.character.getDefence();
            if (newdef > curdef) {
                curdef = newdef;
                defcount++;
            }
        }
        assertTrue(defcount >= n / 2);
    }
    
    @Test
    public void addingDefenceBuffGivesBonus() {
        int initialDefence = this.character.getDefence();
        
        Buff b = new Buff(100);
        b.setBonus(Buff.Type.DEFENCE, 10);
        this.character.addBuff(b);
        
        assertTrue(initialDefence < this.character.getDefence());
    }
    
    @Test
    public void deadBuffDoesntGiveBonus() {
        int initialDefence = this.character.getDefence();
        
        Buff b = new Buff(0);
        b.setBonus(Buff.Type.DEFENCE, 10);
        this.character.addBuff(b);
        
        while (b.isAlive()) {
            b.tick(1);
        }
        
        assertEquals(initialDefence, this.character.getDefence());
    }

    @Test
    public void deadAndAliveBuffGiveBonusOnlyOnce() {
        int initialDefence = this.character.getDefence();

        Buff b1 = new Buff(10);
        b1.setBonus(Buff.Type.DEFENCE, 10);
        Buff b2 = new Buff(b1);
        
        this.character.addBuff(b1);
        while (b1.isAlive()) {
            b1.tick(1);
        }

        this.character.addBuff(b2);
        
        assertEquals(initialDefence + 10, this.character.getDefence());
    }
    
    @Test
    public void attackBuffIsGivenRandomlyWhenChanceIsBelow1() {
        // Assumes cobra's attack buff has chance below 1.0
        Character cobra = (Character) GameObjectFactory.create("cobra");
        int buffCount = 0;
        int noBuffCount = 0;
        int n = 1000;
        for (int i = 0; i < n; i++) {
            if (cobra.getRandomAttackBuff() == null) {
                noBuffCount++;
            } else {
                buffCount++;
            }
        }
        
        assertNotEquals(0, buffCount);
        assertNotEquals(0, noBuffCount);
    }
    
    @Test
    public void validPickupTargetsDontContainCharacters() {
        Tile floor = new FloorTile(null, 0, 0);
        
        this.character.getLocation().moveTo(floor);
        
        GameObject rat = GameObjectFactory.create("rat");
        rat.getLocation().moveTo(floor);
        
        GameObject coin = GameObjectFactory.create("gold coin");
        coin.getLocation().moveTo(floor);
        
        List<GameObject> objects = this.character.getValidPickUpTargets();
        assertTrue(objects.contains(coin));
        assertFalse(objects.contains(rat));
    }
    
    @Test
    public void getBuffsDoesNotReturnDeadBuffs() {
        Buff buff = new Buff(1, Buff.Type.ATTACK, 1);
        this.character.addBuff(buff);
        for (int i = 0; i < 10; i++) {
            buff.tick(1.0);
        }
        List<Buff> buffs = this.character.getBuffs();
        assertFalse(buffs.contains(buff));
    }
    
    @Test
    public void wieldWeaponIncreasesDamage() {
        int damageAtStart = this.character.getDamage();
        
        GameObject weapon = GameObjectFactory.create("short sword");
        weapon.getLocation().moveTo(this.character);
        Action action = new ActionUse(weapon);
        action.act(this.character);
        
        assertTrue(this.character.getDamage() > damageAtStart);
    }
    
}
