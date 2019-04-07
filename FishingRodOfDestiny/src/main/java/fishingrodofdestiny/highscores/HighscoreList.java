/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.highscores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author joyr
 */
public class HighscoreList {
    String          name;
    List<Highscore> highscores;
    int             max;
    
    public HighscoreList(String name) {
        this.name       = name;
        this.highscores = new ArrayList<>();
        this.max        = 10;
    }
    
    public void add(Highscore highscore) {
        this.highscores.add(highscore);
        Collections.sort(this.highscores);

        while (this.highscores.size() >= this.max) {
            // Too many highscores, remove the one with lowest score:
            this.highscores.remove(this.highscores.size() - 1);
        }
    }
    
    public Highscore get(int index) {
        if (index < 0 || index >= this.highscores.size()) {
            return null;
        }
        
        return this.highscores.get(index);
    }
    
    public int getMaximumNumberOfEntries() {
        return this.max;
    }
}
