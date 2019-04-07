/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.resources;

import fishingrodofdestiny.dao.FileHighscoreDao;
import fishingrodofdestiny.dao.HighscoreDao;
import fishingrodofdestiny.highscores.Highscore;
import fishingrodofdestiny.highscores.HighscoreList;
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
    
    
    private HashMap<Highscore.Type, HighscoreList> highscoreLists;
    private HighscoreDao dao;
  
    private HighscoreListCache() {
        this.highscoreLists = new HashMap<>();
        this.dao = new FileHighscoreDao("./highscores");
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
