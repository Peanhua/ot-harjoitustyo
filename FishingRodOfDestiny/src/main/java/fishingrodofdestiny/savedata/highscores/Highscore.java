/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
