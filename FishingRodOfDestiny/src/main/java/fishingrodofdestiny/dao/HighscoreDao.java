/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.highscores.Highscore;
import fishingrodofdestiny.highscores.ScoreBasedHighscore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author joyr
 */
public abstract class HighscoreDao {
    private HashMap<Highscore.Type, List<Highscore>> highscores;
    private boolean loaded;
    
    
    public HighscoreDao() {
        this.highscores  = new HashMap<>();
        this.loaded = false;
    }

    
    protected abstract void load(Highscore.Type type);

    protected Highscore createFromData(Integer highscoreId, Highscore.Type type, String name, int points, LocalDateTime timestamp) {
        Highscore hs = null;
        switch (type) {
            case SCORE:
                hs = new ScoreBasedHighscore(highscoreId, name, points, timestamp);
                break;
        }
        return hs;
    }    

    
    private final void loadAll() {
        if (this.loaded) {
            return;
        }
        this.loaded = true;
        for (Highscore.Type type : Highscore.Type.values()) {
            this.load(type);
        }
    }
    

    protected final List<Highscore> getHighscores(Highscore.Type type) {
        this.loadAll();
        List<Highscore> list = this.highscores.get(type);
        if (list == null) {
            list = new ArrayList<>();
            this.highscores.put(type, list);
        }
        return list;
    }
    

    public Highscore create(Highscore.Type type, Highscore highscore) {
        this.loadAll();
        List<Highscore> list = this.getHighscores(type);
        list.add(highscore);
        Collections.sort(list);
        return highscore;
    }
    
    
    public final List<Highscore> getByType(Highscore.Type type) {
        this.loadAll();
        return this.getHighscores(type);
    }
    
    
    public void delete(Highscore.Type type, Highscore highscore) {
        List<Highscore> list = this.getHighscores(type);
        list.remove(highscore);
    }        
}
