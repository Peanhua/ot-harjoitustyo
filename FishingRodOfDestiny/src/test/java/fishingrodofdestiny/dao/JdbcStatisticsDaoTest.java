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
package fishingrodofdestiny.dao;

import fishingrodofdestiny.savedata.statistics.Statistics;
import java.io.File;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author joyr
 */
public class JdbcStatisticsDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();    
    private File folder;

    @Before
    public void setUp() throws Exception {
        this.folder = testFolder.newFolder();
    }

    private String getURL(String id) {
        return "jdbc:sqlite:" + this.folder.getAbsolutePath() + "/test" + id + ".txt";
    }
    
    private Statistics createRandomStatistics(String id) {
        StatisticsDao dao = new JdbcStatisticsDao(this.getURL(id));
        Statistics s = new Statistics(dao);
        
        Random r = new Random();
        s.setEnemiesKilled(r.nextLong());
        s.setGamesCompleted(r.nextLong());
        s.setGamesPlayed(r.nextLong());
        s.setGoldCoinsCollected(r.nextLong());
        
        dao.save(s);
        
        return s;
    }
    
    @Test
    public void savingThenLoadingReturnsSame() {
        String id = "savingThenLoadingReturnsSame";
        
        Statistics s1 = this.createRandomStatistics(id);
        
        StatisticsDao dao = new JdbcStatisticsDao(this.getURL(id));
        Statistics s2 = new Statistics(dao);
        s2.load();
        
        assertEquals(s1.getEnemiesKilled(), s2.getEnemiesKilled());
        assertEquals(s1.getGamesCompleted(), s2.getGamesCompleted());
        assertEquals(s1.getGamesPlayed(), s2.getGamesPlayed());
        assertEquals(s1.getGoldCoinsCollected(), s2.getGoldCoinsCollected());
    }
}
