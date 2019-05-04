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
package fishingrodofdestiny.savedata.statistics;

import fishingrodofdestiny.dao.StatisticsDao;
import fishingrodofdestiny.world.Game;

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
        this.goldCoinsCollected += game.getPlayer().getGoldCollected();
        this.enemiesKilled += game.getPlayer().getEnemiesKilled();
        this.save();
    }
    
    private void save() {
        this.dao.save(this);
    }
}
