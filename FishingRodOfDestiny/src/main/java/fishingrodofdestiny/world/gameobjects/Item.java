/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joyr
 */
public class Item extends GameObject {
    private final List<Buff> buffs;
    
    public Item(String name) {
        super(name);
        this.buffs = new ArrayList<>();
        this.setCanBePickedUp(true);
    }
    
    @Override
    public int getFallDamage(int height) {
        return 0;
    }
    
    
    protected final void addBuff(Buff buff) {
        this.buffs.add(buff);
    }

    
    public final int getBuffBonuses(Buff.Type forType) {
        int bonuses = 0;
        for(Buff buff : this.buffs) {
            bonuses += buff.getBonus(forType);
        }
        return bonuses;
    }

    
    public void useItem(GameObject instigator, GameObject user) {
    }
}
