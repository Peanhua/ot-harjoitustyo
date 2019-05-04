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

import fishingrodofdestiny.dao.HighscoreDao;
import java.util.Collections;
import java.util.List;

/**
 * A list containing one type of highscores.
 * 
 * @author joyr
 */
public class HighscoreList {
    HighscoreDao    dao;
    Highscore.Type  type;
    int             max;
    
    public HighscoreList(HighscoreDao fromDao, Highscore.Type type) {
        this.dao  = fromDao;
        this.type = type;
        this.max  = 10;
    }
    
    public Highscore.Type getType() {
        return this.type;
    }

    /**
     * Add a new highscore to this list.
     * <p>
     * Makes sure the list size does not exceed the maximum size, and keeps the list sorted.
     * 
     * @param highscore The highscore to add
     */
    public void add(Highscore highscore) {
        this.dao.create(this.type, highscore);

        while (true) {
            List<Highscore> list = this.dao.getHighscores(this.type);
            if (list.size() <= this.max) {
                break;
            }
            // Too many highscores, remove the one with lowest score:
            Collections.sort(list);
            Highscore hs = list.get(list.size() - 1);
            this.dao.delete(this.type, hs);
        }
    }
    
    /**
     * Return a highscore from the given index.
     * 
     * @param index The index, in the range of [0, max]
     * @return The highscore entry, or null
     */
    public Highscore get(int index) {
        List<Highscore> list = this.dao.getHighscores(this.type);
        Collections.sort(list);
        if (index < 0 || index >= list.size()) {
            return null;
        }
        
        return list.get(index);
    }
    
    /**
     * Return the maximum number of highscore entries this list keeps.
     * 
     * @return The maximum number of highscore entries.
     */
    public int getMaximumNumberOfEntries() {
        return this.max;
    }
}
