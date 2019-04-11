/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.highscores.Highscore;
import java.util.List;

/**
 *
 * @author joyr
 */
public class MemoryHighscoreDao extends HighscoreDao {
    public MemoryHighscoreDao() {
        super();
    }
    
    @Override
    protected void load(Highscore.Type type) {
        List<Highscore> list = this.getHighscores(type);
        list.clear();
    }
    

    @Override
    public Highscore create(Highscore.Type type, Highscore highscore) {
        super.create(type, highscore);
        return highscore;
    }

    @Override
    public void delete(Highscore.Type type, Highscore highscore) {
        super.delete(type, highscore);
    }
}
