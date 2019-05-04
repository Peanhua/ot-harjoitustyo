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
package fishingrodofdestiny.savedata.highscores;

import fishingrodofdestiny.world.Game;
import java.time.LocalDateTime;

/**
 *
 * @author joyr
 */
public abstract class Highscore implements Comparable<Highscore> {
    
    public enum Type {
        SCORE,
        ACTION_COUNT;
        
        @Override
        public String toString() {
            switch (this) {
                case SCORE:        return "Score";
                case ACTION_COUNT: return "Actions";
                default:           return "Unknown";
            }
        }
    };
    
    private Integer             highscoreId;
    private final String        name;
    private long                points;
    private final LocalDateTime endTimestamp;
    
    public Highscore(Game fromGame) {
        this.highscoreId  = null;
        this.name         = fromGame.getPlayer().getCapitalizedName();
        this.points       = -1;
        this.endTimestamp = LocalDateTime.now();
    }
    
    public Highscore(Integer highscoreId, String name, long points, LocalDateTime endTimestamp) {
        this.highscoreId  = highscoreId;
        this.name         = name;
        this.points       = points;
        this.endTimestamp = endTimestamp;
    }
    
    protected abstract long calculatePoints(Game fromGame);
    
    public Integer getId() {
        return this.highscoreId;
    }
    
    public void setId(Integer id) {
        this.highscoreId = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public long getPoints() {
        return this.points;
    }
    
    protected final void setPoints(long points) {
        this.points = points;
    }
    
    public LocalDateTime getEndTimestamp() {
        return this.endTimestamp;
    }

    @Override
    public int compareTo(Highscore t) {
        long diff = t.points - this.points;
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        } else {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        return this.name + ":" + this.points + ":" + this.endTimestamp.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
