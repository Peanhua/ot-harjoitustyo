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
import javax.sql.rowset.CachedRowSet;

/**
 * Jdbc based dao for statistics.
 * 
 * @author joyr
 */
public class JdbcStatisticsDao implements StatisticsDao {

    private final JdbcHelper jdbc;
    
    public JdbcStatisticsDao(String databaseUrl) {
        this.jdbc = new JdbcHelper(databaseUrl);
    }
    
    @Override
    public void load(Statistics to) {
        this.initializeDatabase();
        try {
            CachedRowSet rs = this.jdbc.query("SELECT * FROM Statistics");
            if (rs == null) {
                return;
            }
            if (rs.next()) {
                to.setGamesPlayed(rs.getLong("games_played"));
                to.setGamesCompleted(rs.getLong("games_completed"));
                to.setGoldCoinsCollected(rs.getLong("gold_collected"));
                to.setEnemiesKilled(rs.getLong("enemies_killed"));
            }
        } catch (Exception e) {
            System.out.println("JdbcStatisticsDao.load(): " + e);
        }
        
    }

    @Override
    public void save(Statistics from) {
        this.initializeDatabase();
        this.jdbc.update("DELETE FROM Statistics");
        this.jdbc.update("INSERT INTO Statistics ( games_played, games_completed, gold_collected, enemies_killed) VALUES (?, ?, ?, ?)", ((stmt) -> {
                stmt.setLong(1, from.getGamesPlayed());
                stmt.setLong(2, from.getGamesCompleted());
                stmt.setLong(3, from.getGoldCoinsCollected());
                stmt.setLong(4, from.getEnemiesKilled());
            })
        );
    }

    private void initializeDatabase() {
        this.jdbc.update(
                "CREATE TABLE IF NOT EXISTS Statistics ("
                + "  games_played    INTEGER(8) NOT NULL,"
                + "  games_completed INTEGER(8) NOT NULL,"
                + "  gold_collected  INTEGER(8) NOT NULL,"
                + "  enemies_killed  INTEGER(8) NOT NULL"
                + ")"
        );
    }
}
