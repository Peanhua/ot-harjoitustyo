/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.highscores;

import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.Player;
import javafx.embed.swing.JFXPanel;
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
public class ScoreBasedHighscoreTest {

    private JFXPanel jfxPanel;
        
    public ScoreBasedHighscoreTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.jfxPanel = new JFXPanel();
    }
    
    @After
    public void tearDown() {
        this.jfxPanel = null;
    }

    @Test
    public void freshGameGivesZeroPoints() {
        Player p = new Player();
        Game g = new Game(0, p, Game.RescueTarget.PRINCESS);
        Highscore hs = new ScoreBasedHighscore(g);
        assertEquals(0, hs.getPoints());
    }
    
    @Test
    public void experienceGivesPoints() {
        Player p = new Player();
        p.adjustExperiencePoints(123);
        Game g = new Game(0, p, Game.RescueTarget.PRINCESS);
        Highscore hs = new ScoreBasedHighscore(g);
        assertTrue(hs.getPoints() > 0);
    }
}
