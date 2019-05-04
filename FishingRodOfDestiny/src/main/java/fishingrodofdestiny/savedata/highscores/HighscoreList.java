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
    
    public void add(Highscore highscore) {
        this.dao.create(this.type, highscore);

        while (true) {
            List<Highscore> list = this.dao.getByType(this.type);
            if (list.size() <= this.max) {
                break;
            }
            // Too many highscores, remove the one with lowest score:
            Collections.sort(list);
            Highscore hs = list.get(list.size() - 1);
            this.dao.delete(this.type, hs);
        }
    }
    
    public Highscore get(int index) {
        List<Highscore> list = this.dao.getByType(this.type);
        Collections.sort(list);
        if (index < 0 || index >= list.size()) {
            return null;
        }
        
        return list.get(index);
    }
    
    public int getMaximumNumberOfEntries() {
        return this.max;
    }
}
