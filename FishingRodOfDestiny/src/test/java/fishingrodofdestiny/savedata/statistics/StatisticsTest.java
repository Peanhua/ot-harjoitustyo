/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.savedata.statistics;

import fishingrodofdestiny.dao.MemoryStatisticsDao;
import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.Player;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class StatisticsTest {
    @Test
    public void addingFromGameIncreasesGamesPlayed() {
        Game game = new Game(0, new Player(), Game.RescueTarget.PRINCESS);
        Statistics s = new Statistics(new MemoryStatisticsDao());
        long initial = s.getGamesPlayed();
        s.addFromGame(game);
        assertEquals(initial + 1, s.getGamesPlayed());
    }
}
