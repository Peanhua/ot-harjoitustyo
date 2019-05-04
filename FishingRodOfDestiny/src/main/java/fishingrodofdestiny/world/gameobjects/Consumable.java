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

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.tiles.Tile;

/**
 *
 * @author joyr
 */
public class Consumable extends Item {
    private String  useVerb;
    private int     healOnUse;
    private boolean healOnUsePercentage;
    private boolean revealsMapOnUse;
    private boolean givesAntivenomOnUse;
    
    public Consumable(String objectType) {
        super(objectType);
        this.useVerb             = "use";
        this.healOnUse           = 0;
        this.healOnUsePercentage = false;
        this.revealsMapOnUse     = false;
        this.givesAntivenomOnUse = false;
    }
    
    public final void setUseVerb(String useVerb) {
        this.useVerb = useVerb;
    }
    
    public final void setHealOnUse(int amount) {
        this.healOnUse = amount;
    }
    
    public final void setHealOnUsePercentage(boolean usePercentage) {
        this.healOnUsePercentage = usePercentage;
    }
    
    public final void setRevealsMap() {
        this.revealsMapOnUse = true;
    }
    
    public final void setAntivenom() {
        this.givesAntivenomOnUse = true;
    }

    @Override
    public void useItem(GameObject instigator, GameObject user) {
        super.useItem(instigator, user);
        user.addMessage("You " + this.useVerb + " " + this.getName() + ".");
        this.doHeal(user);
        this.doRevealMap(user);
        this.doAntivenom(user);
        this.destroy(instigator);
    }
    
    private void doHeal(GameObject user) {
        if (this.healOnUse != 0) {
            double amount;
            if (this.healOnUsePercentage) {
                amount = (double) this.healOnUse * (double) user.getMaxHitpoints() / 100.0;
            } else {
                amount = this.healOnUse;
            }
            user.adjustHitpoints((int) amount);
        }
    }
    
    private void doRevealMap(GameObject user) {
        if (this.revealsMapOnUse && user instanceof Player) {
            this.revealMap((Player) user);
        }
    }
    
    private void doAntivenom(GameObject user) {
        if (this.givesAntivenomOnUse && user instanceof Character) {
            this.antivenom((Character) user);
        }
    }

    
    private void antivenom(Character character) {
        boolean hadPoison = false;
        for (Buff buff : ((Character) character).getBuffs()) {
            double amount = buff.getBonus(Buff.Type.POISON);
            if (amount > 0.01) {
                hadPoison = true;
                amount *= 0.5;
                buff.setBonus(Buff.Type.POISON, amount);
            }
        }
        if (hadPoison) {
            character.addMessage("You feel better as the poison in your veins weakens.");
        }
    }
    
    private void revealMap(Player player) {
        Tile tile = player.getLocation().getContainerTile();
        if (tile == null) {
            return;
        }
        Level level = tile.getLevel();
        LevelMemory memory = player.getLevelMemory(level);
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                memory.remember(x, y);
            }
        }
        player.addMessage("You learn the layout of the whole level!");
    }
}
