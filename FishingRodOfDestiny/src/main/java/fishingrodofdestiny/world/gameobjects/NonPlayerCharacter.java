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
import fishingrodofdestiny.world.GameObjectSpawner;
import fishingrodofdestiny.world.controllers.SimpleAiController;
import fishingrodofdestiny.world.TileGfx;

/**
 * The non-player character.
 * 
 * @author joyr
 */
public class NonPlayerCharacter extends Character {
    private final GameObjectSpawner gameObjectSpawner;
    
    public NonPlayerCharacter(String objectType) {
        super(objectType);
        this.setController(new SimpleAiController(this));
        this.setGraphics(new TileGfx("rltiles/nh32", 192, 0, 32, 32));
        this.gameObjectSpawner = new GameObjectSpawner();
    }
    
    protected final GameObjectSpawner getGameObjectSpawner() {
        return this.gameObjectSpawner;
    }
    
    protected final void spawnInventoryItems() {
        while (true) {
            GameObject item = GameObjectFactory.create(this.gameObjectSpawner.getNextObjectType(this.getRandom(), this));
            if (item == null) {
                break;
            }
            item.getLocation().moveTo(this);
        }
    }
    
    @Override
    public boolean isValidAttackTarget(GameObject target) {
        if (!super.isValidAttackTarget(target)) {
            return false;
        }
        
        return target instanceof Player;
    }

    @Override
    public void addMessage(String message) {
        // Do nothing here, because NPCs don't have any use for messages.
    }
}
