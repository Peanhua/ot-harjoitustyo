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
