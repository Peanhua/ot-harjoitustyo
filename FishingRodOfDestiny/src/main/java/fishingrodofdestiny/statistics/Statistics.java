/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.statistics;

import fishingrodofdestiny.dao.StatisticsDao;
import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.GoldCoin;

/**
 *
 * @author joyr
 */
public class Statistics {
    private long gamesPlayed;
    private long gamesCompleted;
    private long goldCoinsCollected;
    private long enemiesKilled;
    private final StatisticsDao dao;
    
    public Statistics(StatisticsDao dao) {
        this.gamesPlayed        = 0;
        this.gamesCompleted     = 0;
        this.goldCoinsCollected = 0;
        this.enemiesKilled      = 0;
        this.dao                = dao;
    }
    
    @Override
    public String toString() {
        return "games=" + this.gamesPlayed + ", completed=" + this.gamesCompleted + ", gold=" + this.goldCoinsCollected + ", enemies=" + this.enemiesKilled;
    }
    
    public final long getGamesPlayed() {
        return this.gamesPlayed;
    }
    
    public final void setGamesPlayed(long count) {
        this.gamesPlayed = count;
    }
    
    public final long getGamesCompleted() {
        return this.gamesCompleted;
    }
    
    public final void setGamesCompleted(long count) {
        this.gamesCompleted = count;
    }
    
    public final long getGoldCoinsCollected() {
        return this.goldCoinsCollected;
    }
    
    public final void setGoldCoinsCollected(long count) {
        this.goldCoinsCollected = count;
    }
    
    public final long getEnemiesKilled() {
        return this.enemiesKilled;
    }
    
    public final void setEnemiesKilled(long count) {
        this.enemiesKilled = count;
    }
    
    public void load() {
        this.dao.load(this);
    }
    
    public void addFromGame(Game game) {
        this.gamesPlayed++;
        if (game.getPlayer().getGameCompleted()) {
            this.gamesCompleted++;
        }
        game.getPlayer().getInventory().getObjects().forEach(object -> {
            if (object.getClass() == GoldCoin.class) {
                this.goldCoinsCollected++;
            }
        });
        this.enemiesKilled += game.getPlayer().getEnemiesKilled();
        this.save();
    }
    
    private void save() {
        this.dao.save(this);
    }
}