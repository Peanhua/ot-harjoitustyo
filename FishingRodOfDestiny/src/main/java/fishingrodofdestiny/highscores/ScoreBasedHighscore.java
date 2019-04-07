/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.highscores;

import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.Player;

/**
 *
 * @author joyr
 */
public class ScoreBasedHighscore extends Highscore {
    public ScoreBasedHighscore(Game fromGame) {
        super(fromGame);
        this.setPoints(this.calculatePoints(fromGame));
    }

    @Override
    protected final int calculatePoints(Game fromGame) {
        Player player = fromGame.getPlayer();
        return player.getLevel() * 1000 + player.getExperiencePoints();
    }
}
