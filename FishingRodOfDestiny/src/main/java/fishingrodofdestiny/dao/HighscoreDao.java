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
public interface HighscoreDao {
    Highscore       create(Highscore.Type type, Highscore highscore);
    List<Highscore> getByType(Highscore.Type type);
    void            delete(Highscore.Type type, Highscore highscore);
}
