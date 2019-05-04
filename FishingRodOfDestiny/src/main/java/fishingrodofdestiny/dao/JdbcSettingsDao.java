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

import fishingrodofdestiny.savedata.settings.KeyboardSettings;
import java.util.List;
import javafx.scene.input.KeyCode;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author joyr
 */
public class JdbcSettingsDao extends SettingsDao {

    private final JdbcHelper jdbc;
    
    public JdbcSettingsDao(String databaseUrl) {
        super();
        this.jdbc = new JdbcHelper(databaseUrl);
    }
    
    @Override
    public void loadKeyboardSettings(KeyboardSettings to) {
        this.initializeDatabase();
        CachedRowSet rs = this.jdbc.query("SELECT * FROM Keybindings");
        if (rs == null) {
            return;
        }
        try {
            while (rs.next()) {
                to.addKeybinding(rs.getString("key"), rs.getString("action"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void saveKeyboardSettings(KeyboardSettings from) {
        this.initializeDatabase();
        this.jdbc.update("DELETE FROM Keybindings");
        List<KeyCode> keys = from.getConfiguredKeys();
        keys.forEach(key -> {
            final String actionStr = from.getActionStringForKey(key);
            if (actionStr != null) {
                jdbc.update("INSERT INTO Keybindings ( key, action ) VALUES (?, ?)", ((stmt) -> {
                    stmt.setString(1, key.toString());
                    stmt.setString(2, actionStr);
                }));
            }
        });
    }
    
    @Override
    public boolean isLoadable() {
        CachedRowSet rs = this.jdbc.query("SELECT COUNT(*) AS bindingcount FROM Keybindings");
        if (rs == null) {
            return false;
        }
        try {
            if (!rs.next()) {
                return false;
            }
            if (rs.getInt("bindingcount") > 0) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    
    private void initializeDatabase() {
        this.jdbc.update(""
                + "CREATE TABLE IF NOT EXISTS Keybindings ("
                + "  key VARCHAR(32) NOT NULL,"
                + "  action VARCHAR(64) NOT NULL"
                + ")"
        );
    }
}
