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

/**
 * Dao to handle user settings.
 * 
 * @author joyr
 */
public abstract class SettingsDao {
    private boolean readOnly;
    
    public SettingsDao() {
        this.readOnly = false;
    }

    /**
     * Load all the keyboard settings (bindings).
     * 
     * @param to The target KeyboardSettings object
     */
    public abstract void loadKeyboardSettings(KeyboardSettings to);
    /**
     * Save all the keyboard settings (bindings).
     * 
     * @param from The settings to save
     */
    public abstract void saveKeyboardSettings(KeyboardSettings from);

    
    /**
     * Return whether the settings exists and can be loaded (for example if the file exists).
     * 
     * @return True if the settings can be loaded.
     */
    public abstract boolean isLoadable();
    
    /**
     * Set this dao object to be in read only or in read-write -mode.
     * <p>
     * In read only mode, calling the save methods do nothing.
     * 
     * @param readOnly True to set read only mode, false to set read-write mode.
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
    
    /**
     * Return whether this dao is in read-only mode.
     * <p>
     * No writing should be performed when this dao is in read-only mode.
     * 
     * @return True if this dao is in read-only mode
     */
    protected boolean isReadOnly() {
        return this.readOnly;
    }
}
