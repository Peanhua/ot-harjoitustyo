/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.resources;

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
    
    
    private HashMap<String, HighscoreList> highscoreLists;
  
    private HighscoreListCache() {
        this.highscoreLists = new HashMap<>();
    }
    
    public HighscoreList get(String name) {
        HighscoreList list = this.highscoreLists.get(name);
        if (list != null) {
            return list;
        }
        
        if (this.highscoreLists.containsKey(name)) {
            return null;
        }
        
        list = new HighscoreList(name);
        this.highscoreLists.put(name, list);
        
        return list;
    }
    
}
