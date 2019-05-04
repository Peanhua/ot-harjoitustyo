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
import java.time.LocalDateTime;

/**
 *
 * @author joyr
 */
public class ActionCountBasedHighscore extends Highscore {

    public ActionCountBasedHighscore(Game fromGame) {
        super(fromGame);
        this.setPoints(this.calculatePoints(fromGame));
    }
    
    public ActionCountBasedHighscore(Integer highscoreId, String name, long points, LocalDateTime endTimestamp) {
        super(highscoreId, name, points, endTimestamp);
    }


    @Override
    protected final long calculatePoints(Game fromGame) {
        return fromGame.getPlayer().getActionsTaken();
    }
    
    @Override
    public int compareTo(Highscore t) {
        long diff = this.getPoints() - t.getPoints();
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        } else {
            return 0;
        }
    }

}
