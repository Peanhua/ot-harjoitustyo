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

import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.Player;
import javafx.embed.swing.JFXPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class ScoreBasedHighscoreTest {

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
