/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.tiles;

import fishingrodofdestiny.world.GameObjectFactory;
import fishingrodofdestiny.world.gameobjects.GameObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class PitTrapTileTest {
    
    @Test
    public void objectGetsTeleportedWhenEntersTheTrapTile() {
        PitTrapTile trap = new PitTrapTile(null, 0, 0);
        Tile target = new FloorTile(null, 0, 0);
        trap.setTarget(target);
        
        GameObject obj = GameObjectFactory.create("gold coin");
        obj.getLocation().moveTo(trap);
        assertEquals(target, obj.getLocation().getContainerTile());
    }
}
