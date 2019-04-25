/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.settings.KeyboardSettings;
import fishingrodofdestiny.world.actions.Action;
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
            final String actionStr = this.getActionStringForKey(from, key);
            if (actionStr != null) {
                jdbc.update("INSERT INTO Keybindings ( key, action ) VALUES (?, ?)", ((stmt) -> {
                    stmt.setString(1, key.toString());
                    stmt.setString(2, actionStr);
                }));
            }
        });
    }
    
    private String getActionStringForKey(KeyboardSettings keyboardSettings, KeyCode key) {
        String actionStr = null;
        Action.Type action = keyboardSettings.getAction(key);
        if (action != null) {
            actionStr = action.toString();
        } else {
            KeyboardSettings.Command command = keyboardSettings.getCommand(key);
            if (command != null) {
                actionStr = command.toString();
            }
        }
        return actionStr;
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
