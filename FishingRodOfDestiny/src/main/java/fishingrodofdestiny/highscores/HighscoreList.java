/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.highscores;

import fishingrodofdestiny.dao.HighscoreDao;
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
    
    public void add(Highscore highscore) {
        this.dao.create(this.type, highscore);

        while (true) {
            List<Highscore> list = this.dao.getByType(this.type);
            if (list.size() < this.max) {
                break;
            }
            // Too many highscores, remove the one with lowest score:
            Highscore hs = list.get(list.size() - 1);
            this.dao.delete(this.type, hs);
        }
    }
    
    public Highscore get(int index) {
        List<Highscore> list = this.dao.getByType(this.type);
        if (index < 0 || index >= list.size()) {
            return null;
        }
        
        return list.get(index);
    }
    
    public int getMaximumNumberOfEntries() {
        return this.max;
    }
}
