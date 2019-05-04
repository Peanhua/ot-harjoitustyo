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

import fishingrodofdestiny.savedata.highscores.Highscore;
import fishingrodofdestiny.savedata.highscores.HighscoreList;
import fishingrodofdestiny.savedata.highscores.ScoreBasedHighscore;
import java.io.File;
import java.time.LocalDateTime;
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
public class JdbcHighscoreDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();    
    private File folder;
    
    @Before
    public void setUp() throws Exception {
        this.folder = testFolder.newFolder();
    }

    private String getURL(String id) {
        return "jdbc:sqlite:" + this.folder.getAbsolutePath() + "/test" + id + ".db";
    }
    
    private HighscoreList fillRandomScores(String id, int n) {
        HighscoreDao dao = new JdbcHighscoreDao(this.getURL(id));
        HighscoreList hslist = new HighscoreList(dao, Highscore.Type.SCORE);
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            Highscore hs = new ScoreBasedHighscore(null, "entry-" + i, r.nextInt(999), LocalDateTime.now());
            hslist.add(hs);
        }
        return hslist;
    }
    
    @Test
    public void savingThenLoadingReturnsSame() {
        int n = 5;
        String id = "savingTheNloadingRetunsSame";

        HighscoreList hslist = this.fillRandomScores(id, n);
        
        HighscoreDao dao2 = new JdbcHighscoreDao(this.getURL(id));
        HighscoreList hslist2 = new HighscoreList(dao2, Highscore.Type.SCORE);
        for (int i = 0; i < n; i++) {
            assertEquals(0, hslist.get(i).compareTo(hslist2.get(i)));
            assertTrue(hslist.get(i).getName().equals(hslist2.get(i).getName()));
        }
    }
    
    @Test
    public void savingMoreThanFitsReturnsOnlyTheFit() {
        String id = "savingMoreThanFitsReturnsOnlyTheFit";
        int n = 100;
        this.fillRandomScores(id, n);
        
        HighscoreDao dao = new JdbcHighscoreDao(this.getURL(id));
        assertTrue(dao.getByType(Highscore.Type.SCORE).size() < n);
    }
}
