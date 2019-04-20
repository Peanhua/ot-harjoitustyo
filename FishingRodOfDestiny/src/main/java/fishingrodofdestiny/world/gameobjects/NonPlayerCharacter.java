/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.GameObjectSpawner;
import fishingrodofdestiny.world.controllers.SimpleAiController;
import fishingrodofdestiny.world.TileGfx;

/**
 *
 * @author joyr
 */
public abstract class NonPlayerCharacter extends Character {
    private final GameObjectSpawner gameObjectSpawner;
    
    public NonPlayerCharacter() {
        super();
        this.setController(new SimpleAiController(this));
        this.setName("monster");
        this.setGraphics(new TileGfx("rltiles/nh32", 192, 0, 32, 32));
        this.gameObjectSpawner = new GameObjectSpawner();
    }
    
    protected final GameObjectSpawner getGameObjectSpawner() {
        return this.gameObjectSpawner;
    }
    
    protected final void spawnInventoryItems() {
        while (true) {
            GameObjectFactory.Type type = this.gameObjectSpawner.getNext(this.getRandom(), this);
            GameObject item = null;
            if (type != null) {
                item = GameObjectFactory.create(type);
            } else {
                String objectId = this.gameObjectSpawner.getNextObjectId(this.getRandom(), this);
                if (objectId != null) {
                    item = GameObjectFactory.create(objectId);
                }
            }
            if (item == null) {
                break;
            }
            item.getLocation().moveTo(this);
        }
    }
    
    @Override
    public String toString() {
        return "NonPlayerCharacter(" + super.toString() + ")";
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
