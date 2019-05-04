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
package fishingrodofdestiny.dao;

import fishingrodofdestiny.savedata.highscores.ActionCountBasedHighscore;
import fishingrodofdestiny.savedata.highscores.Highscore;
import fishingrodofdestiny.savedata.highscores.ScoreBasedHighscore;
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
    private final HashMap<Highscore.Type, List<Highscore>> highscores;
    private boolean loaded;
    
    
    public HighscoreDao() {
        this.highscores  = new HashMap<>();
        this.loaded = false;
    }

    
    protected abstract void load(Highscore.Type type);

    protected Highscore createFromData(Integer highscoreId, Highscore.Type type, String name, long points, LocalDateTime timestamp) {
        Highscore hs = null;
        switch (type) {
            case SCORE:
                hs = new ScoreBasedHighscore(highscoreId, name, points, timestamp);
                break;
            case ACTION_COUNT:
                hs = new ActionCountBasedHighscore(highscoreId, name, points, timestamp);
                break;
        }
        return hs;
    }    

    
    private void loadAll() {
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
