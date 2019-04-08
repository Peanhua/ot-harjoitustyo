/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.highscores.Highscore;
import fishingrodofdestiny.highscores.ScoreBasedHighscore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author joyr
 */
public class JdbcHighscoreDao implements HighscoreDao {
    
    private HashMap<Highscore.Type, List<Highscore>> highscores;
    private JdbcHelper jdbc;
    
    public JdbcHighscoreDao(String databaseUrl) {
        this.highscores  = new HashMap<>();
        this.jdbc = new JdbcHelper(databaseUrl);
        for (Highscore.Type type : Highscore.Type.values()) {
            this.load(type);
        }
    }
    
    private List<Highscore> getHighscores(Highscore.Type type) {
        List<Highscore> list = this.highscores.get(type);
        if (list == null) {
            list = new ArrayList<>();
            this.highscores.put(type, list);
        }
        return list;
    }
    
    private final void load(Highscore.Type type) {
        List<Highscore> list = this.getHighscores(type);
        list.clear();
        
        CachedRowSet rs = this.jdbc.query(
                "SELECT   highscore_id,"
                + "       name,"
                + "       points,"
                + "       game_ended"
                + "  FROM Highscores"
                + " WHERE highscore_type = '" + type + "'"
        );
        if (rs == null) {
            return;
        }
        
        try {
            while (rs.next()) {
                Integer highscoreId     = rs.getInt("highscore_id");
                String  name            = rs.getString("name");
                int     points          = rs.getInt("points");
                LocalDateTime gameEnded = LocalDateTime.parse(rs.getString("game_ended"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                Highscore hs = null;
                switch (type) {
                    case SCORE:
                        hs = new ScoreBasedHighscore(highscoreId, name, points, gameEnded);
                        break;
                }
                
                if (hs != null) {
                    list.add(hs);
                }
            }
        } catch (Exception e) {
            System.out.println("JdbcHighscoreDao.load(): " + e);
        }
    }
    
    private final void initializeDatabase() {
        this.jdbc.update(
                "CREATE TABLE IF NOT EXISTS Highscores ("
                + "  highscore_id   INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  highscore_type VARCHAR(40) NOT NULl,"
                + "  name           VARCHAR(80) NOT NULL,"
                + "  points         INTEGER NOT NULL,"
                + "  game_ended     DATETIME NOT NULL"
                + ")"
        );
    }


    @Override
    public Highscore create(Highscore.Type type, Highscore highscore) {
        List<Highscore> list = this.getHighscores(type);
        list.add(highscore);
        Collections.sort(list);
        
        this.initializeDatabase();
        Integer id = this.jdbc.insert("INSERT INTO Highscores ( highscore_type, name, points, game_ended ) VALUES ( ?, ?, ?, ? )", (stmt) -> {
            stmt.setString(1, type.toString());
            stmt.setString(2, highscore.getName());
            stmt.setInt(3,    highscore.getPoints());
            stmt.setString(4, highscore.getEndTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        });
        highscore.setId(id);
        
        return highscore;
    }

    @Override
    public List<Highscore> getByType(Highscore.Type type) {
        return this.getHighscores(type);
    }

    @Override
    public void delete(Highscore.Type type, Highscore highscore) {
        List<Highscore> list = this.getHighscores(type);
        list.remove(highscore);
        
        this.jdbc.update("DELETE FROM Highscores WHERE highscore_id = " + highscore.getId());
    }
    
}
