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
package fishingrodofdestiny.resources;

import fishingrodofdestiny.dao.FileHighscoreDao;
import fishingrodofdestiny.dao.HighscoreDao;
import fishingrodofdestiny.dao.JdbcHighscoreDao;
import fishingrodofdestiny.dao.MemoryHighscoreDao;
import fishingrodofdestiny.savedata.highscores.Highscore;
import fishingrodofdestiny.savedata.highscores.HighscoreList;
import java.util.HashMap;

/**
 *
 * @author joyr
 */
public class HighscoreListCache {
    private static HighscoreListCache instance = null;
    
    public static HighscoreListCache getInstance() {
        if (HighscoreListCache.instance == null) {
            HighscoreListCache.instance = new HighscoreListCache();
        }
        return HighscoreListCache.instance;
    }
    
    
    private final HashMap<Highscore.Type, HighscoreList> highscoreLists;
    private final HighscoreDao dao;
  
    private HighscoreListCache() {
        this.highscoreLists = new HashMap<>();
        
        String defaultUri = "jdbc:sqlite:FishingRodOfDestiny.db";
        String uri = System.getenv("FISHINGRODOFDESTINY_HIGHSCORES");
        if (uri == null) {
            uri = defaultUri;
        }
        
        String fileStart = "file:";
        if (uri.startsWith("jdbc:")) {
            this.dao = new JdbcHighscoreDao(uri);
        } else if (uri.startsWith(fileStart)) {
            this.dao = new FileHighscoreDao(uri.substring(fileStart.length()));
        } else {
            this.dao = new MemoryHighscoreDao();
        }
    }
    
    public HighscoreList get(Highscore.Type type) {
        HighscoreList list = this.highscoreLists.get(type);
        if (list != null) {
            return list;
        }
        
        if (this.highscoreLists.containsKey(type)) {
            return null;
        }
        
        list = new HighscoreList(this.dao, type);
        this.highscoreLists.put(type, list);
        
        return list;
    }
    
}
