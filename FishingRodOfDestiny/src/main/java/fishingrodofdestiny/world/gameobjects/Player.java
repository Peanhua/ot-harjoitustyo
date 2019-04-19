/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.controllers.PlayerController;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ShadowCasting;

/**
 *
 * @author joyr
 */
public class Player extends Character {
    private boolean                 gameCompleted;
    private final List<LevelMemory> levelMemories;
    private final IFovAlgorithm     fovAlgorithm;

    
    public Player() {
        super();
        this.gameCompleted = false;
        this.levelMemories = new ArrayList<>();
        this.fovAlgorithm  = new ShadowCasting();
        this.setNaturalRegeneration(0.1);
        this.setController(new PlayerController(this));
        this.setGraphics(new TileGfx("rltiles/nh32", 160, 352, 32, 32));
        this.getLocation().listenOnChange(() -> this.explore());
    }
    
    public Player(String name, int attack, int defence, int maxHitpoints, int weightLimit) {
        this();
        this.setName(name);
        this.setAttack(attack);
        this.setDefence(defence);
        this.setMaxHitpoints(maxHitpoints);
        this.getInventory().setWeightLimit(weightLimit);
    }
    
    @Override
    public String toString() {
        return "Player(" + super.toString()
                + ")";
    }
    
    @Override
    public boolean isValidAttackTarget(GameObject target) {
        if (!super.isValidAttackTarget(target)) {
            return false;
        }
        
        return target instanceof Character;
    }

    
    public void setGameCompleted() {
        this.gameCompleted = true;
    }
    
    public boolean getGameCompleted() {
        return this.gameCompleted;
    }
    
    
    private int getFovRadius() {
        return 5;
    }
    
    
    public LevelMemory getLevelMemory(Level level) {
        while (this.levelMemories.size() <= level.getDepth()) {
            this.levelMemories.add(null);
        }
        LevelMemory memory = this.levelMemories.get(level.getDepth());
        if (memory == null) {
            memory = new LevelMemory(level);
            this.levelMemories.set(level.getDepth(), memory);
        }
        return memory;
    }
    
    private void explore() {
        Tile tile = this.getLocation().getContainerTile();
        if (tile == null) {
            return;
        }
        Level level = tile.getLevel();
        if (level == null) {
            return;
        }
        LevelMemory memory = this.getLevelMemory(level);
        memory.setCurrentVisionCenter(tile.getX(), tile.getY());
        this.fovAlgorithm.visitFieldOfView(memory, tile.getX(), tile.getY(), this.getFovRadius());
    }
}
