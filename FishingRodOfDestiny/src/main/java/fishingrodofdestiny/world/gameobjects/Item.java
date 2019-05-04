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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joyr
 */
public class Item extends GameObject {
    private final List<Buff> buffs;
    private final List<Buff> useBuffs;
    
    public Item(String objectType) {
        super(objectType);
        this.buffs    = new ArrayList<>();
        this.useBuffs = new ArrayList<>();
        this.setCanBePickedUp(true);
    }
    
    @Override
    public int getFallDamage(int height) {
        return 0;
    }
    
    
    public final void addBuff(Buff buff) {
        this.buffs.add(buff);
    }
    
    
    public final void addUseBuff(Buff buff) {
        this.useBuffs.add(buff);
    }

    
    public final double getBuffBonuses(Buff.Type forType) {
        double bonuses = 0;
        for (Buff buff : this.buffs) {
            bonuses += buff.getBonus(forType);
        }
        return bonuses;
    }

    
    public void useItem(GameObject instigator, GameObject user) {
        if (user instanceof Character) {
            Character userCharacter = (Character) user;
            this.useBuffs.forEach(buff -> userCharacter.addBuff(new Buff(buff)));
        }
    }
}
