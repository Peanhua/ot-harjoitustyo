/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.statistics.Statistics;
import javax.sql.rowset.CachedRowSet;

/**
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
