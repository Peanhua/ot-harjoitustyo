/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.savedata.highscores;

import fishingrodofdestiny.world.Game;
import java.time.LocalDateTime;

/**
 *
 * @author joyr
 */
public class ActionCountBasedHighscore extends Highscore {

    public ActionCountBasedHighscore(Game fromGame) {
        super(fromGame);
        this.setPoints(this.calculatePoints(fromGame));
    }
    
    public ActionCountBasedHighscore(Integer highscoreId, String name, long points, LocalDateTime endTimestamp) {
        super(highscoreId, name, points, endTimestamp);
    }


    @Override
    protected final long calculatePoints(Game fromGame) {
        return fromGame.getPlayer().getActionsTaken();
    }
    
    @Override
    public int compareTo(Highscore t) {
        long diff = this.getPoints() - t.getPoints();
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        } else {
            return 0;
        }
    }

}
