/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.savedata.highscores;

import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.Player;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ActionCountBasedHighscoreTest {
    
    private JFXPanel jfxPanel;
        
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
        Highscore hs = new ActionCountBasedHighscore(g);
        assertEquals(0, hs.getPoints());
    }
    
    @Test
    public void gameWithLessMovesIsSortedHigher() {
        List<Highscore> scores = new ArrayList<>();

        scores.add(new ActionCountBasedHighscore(4, "first", 3, LocalDateTime.now()));
        scores.add(new ActionCountBasedHighscore(4, "first", 1, LocalDateTime.now()));

        Collections.sort(scores);
        assertTrue(scores.get(0).getPoints() < scores.get(1).getPoints());
    }
}
