/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.highscores;

import fishingrodofdestiny.dao.HighscoreDao;
import fishingrodofdestiny.dao.MemoryHighscoreDao;
import java.time.LocalDateTime;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class HighscoreListTest {
    
    private HighscoreList list;
    private HighscoreDao  dao;
    
    public HighscoreListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.dao  = new MemoryHighscoreDao();
        this.list = new HighscoreList(this.dao, Highscore.Type.SCORE);
    }
    
    @After
    public void tearDown() {
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
