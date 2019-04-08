/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.highscores;

import fishingrodofdestiny.world.Game;
import java.time.LocalDateTime;

/**
 *
 * @author joyr
 */
public abstract class Highscore implements Comparable<Highscore> {
    
    public enum Type {
        SCORE;
        
        @Override
        public String toString() {
            switch (this) {
                case SCORE: return "Score";
                default:    return "Unknown";
            }
        }
    };
    
    private Integer       highscoreId;
    private String        name;
    private int           points;
    private LocalDateTime endTimestamp;
    
    public Highscore(Game fromGame) {
        this.highscoreId  = null;
        this.name         = fromGame.getPlayer().getCapitalizedName();
        this.points       = -1;
        this.endTimestamp = LocalDateTime.now();
    }
    
    public Highscore(Integer highscoreId, String name, int points, LocalDateTime endTimestamp) {
        this.highscoreId  = highscoreId;
        this.name         = name;
        this.points       = points;
        this.endTimestamp = endTimestamp;
    }
    
    protected abstract int calculatePoints(Game fromGame);
    
    public Integer getId() {
        return this.highscoreId;
    }
    
    public void setId(Integer id) {
        this.highscoreId = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getPoints() {
        return this.points;
    }
    
    protected final void setPoints(int points) {
        this.points = points;
    }
    
    public LocalDateTime getEndTimestamp() {
        return this.endTimestamp;
    }

    @Override
    public int compareTo(Highscore t) {
        return t.points - this.points;
    }
    
    @Override
    public String toString() {
        return this.name + ":" + this.points + ":" + this.endTimestamp.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
