/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class FileStatisticsDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();    
    private File folder;

    @Before
    public void setUp() throws Exception {
        this.folder = testFolder.newFolder();
    }

    private String getFilename(String id) {
        return this.folder.getAbsolutePath() + "/test" + id + ".txt";
    }
    
    private Statistics createRandomStatistics(String id) {
        StatisticsDao dao = new FileStatisticsDao(this.getFilename(id));
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
        
        StatisticsDao dao = new FileStatisticsDao(this.getFilename(id));
        Statistics s2 = new Statistics(dao);
        s2.load();
        
        assertEquals(s1.getEnemiesKilled(), s2.getEnemiesKilled());
        assertEquals(s1.getGamesCompleted(), s2.getGamesCompleted());
        assertEquals(s1.getGamesPlayed(), s2.getGamesPlayed());
        assertEquals(s1.getGoldCoinsCollected(), s2.getGoldCoinsCollected());
    }
}
