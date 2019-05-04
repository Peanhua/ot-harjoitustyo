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

import fishingrodofdestiny.savedata.highscores.Highscore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author joyr
 */
public class JdbcHighscoreDao extends HighscoreDao {
    
    private final JdbcHelper jdbc;
    
    public JdbcHighscoreDao(String databaseUrl) {
        super();
        this.jdbc = new JdbcHelper(databaseUrl);
    }

    private String getLoadSql(Highscore.Type type) {
        // This method exists only because of checkstyle.
        return
            "SELECT highscore_id," +
            "       name," +
            "       points," +
            "       game_ended" +
            "  FROM Highscores" +
            " WHERE highscore_type = '" + type + "'"
            ;
    }
    
    @Override
    protected void load(Highscore.Type type) {
        this.initializeDatabase();
        
        List<Highscore> list = this.getHighscores(type);
        list.clear();
        
        try {
            CachedRowSet rs = this.jdbc.query(getLoadSql(type));
            while (rs != null && rs.next()) {
                Integer highscoreId     = rs.getInt("highscore_id");
                String  name            = rs.getString("name");
                long    points          = rs.getLong("points");
                LocalDateTime gameEnded = LocalDateTime.parse(rs.getString("game_ended"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                Highscore hs = this.createFromData(highscoreId, type, name, points, gameEnded);
                if (hs != null) {
                    list.add(hs);
                }
            }
        } catch (Exception e) {
            System.out.println("JdbcHighscoreDao.load(): " + e);
        }
    }
    
    private void initializeDatabase() {
        this.jdbc.update(
                "CREATE TABLE IF NOT EXISTS Highscores ("
                + "  highscore_id   INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  highscore_type VARCHAR(40) NOT NULL,"
                + "  name           VARCHAR(80) NOT NULL,"
                + "  points         INTEGER NOT NULL,"
                + "  game_ended     DATETIME NOT NULL"
                + ")"
        );
    }


    @Override
    public Highscore create(Highscore.Type type, Highscore highscore) {
        super.create(type, highscore);
        this.initializeDatabase();
        Integer id = this.jdbc.insert("INSERT INTO Highscores ( highscore_type, name, points, game_ended ) VALUES ( ?, ?, ?, ? )", (stmt) -> {
            stmt.setString(1, type.toString());
            stmt.setString(2, highscore.getName());
            stmt.setLong(3,   highscore.getPoints());
            stmt.setString(4, highscore.getEndTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        });
        highscore.setId(id);
        
        return highscore;
    }


    @Override
    public void delete(Highscore.Type type, Highscore highscore) {
        super.delete(type, highscore);
        this.jdbc.update("DELETE FROM Highscores WHERE highscore_id = " + highscore.getId());
    }
}
