/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
