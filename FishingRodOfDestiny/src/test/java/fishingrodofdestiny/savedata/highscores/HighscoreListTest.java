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
package fishingrodofdestiny.savedata.highscores;

import fishingrodofdestiny.dao.HighscoreDao;
import fishingrodofdestiny.dao.MemoryHighscoreDao;
import java.time.LocalDateTime;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class HighscoreListTest {
    
    private HighscoreList list;
    private HighscoreDao  dao;
    
    @Before
    public void setUp() {
        this.dao  = new MemoryHighscoreDao();
        this.list = new HighscoreList(this.dao, Highscore.Type.SCORE);
    }
    
    @Test
    public void listContainsAtMostMaximumNumberOfEntries() {
        Random r = new Random();
        for (int i = 0; i < this.list.getMaximumNumberOfEntries() * 2; i++) {
            Highscore hs = new ScoreBasedHighscore(null, "entry-" + i, r.nextInt(999), LocalDateTime.now());
            this.list.add(hs);
        }
        assertNotNull(this.list.get(this.list.getMaximumNumberOfEntries() - 1));
        assertNull(this.list.get(this.list.getMaximumNumberOfEntries()));
    }
}
