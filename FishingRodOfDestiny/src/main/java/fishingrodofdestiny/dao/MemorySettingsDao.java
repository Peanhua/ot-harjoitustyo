/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.settings.KeyboardSettings;

/**
 *
 * @author joyr
 */
public class MemorySettingsDao extends SettingsDao {

    @Override
    public void loadKeyboardSettings(KeyboardSettings to) {
    }

    @Override
    public void saveKeyboardSettings(KeyboardSettings from) {
    }

    @Override
    public boolean isLoadable() {
        return false;
    }
}
