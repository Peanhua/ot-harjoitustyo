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
public abstract class SettingsDao {
    private boolean readOnly;
    
    public SettingsDao() {
        this.readOnly = false;
    }

    public abstract void loadKeyboardSettings(KeyboardSettings to);
    public abstract void saveKeyboardSettings(KeyboardSettings from);

    
    /**
     * Return whether the settings exists and can be loaded (for example if the file exists).
     * 
     * @return True if the settings can be loaded.
     */
    public abstract boolean isLoadable();
    
    /**
     * Set this DAO object to be in read only or in read-write -mode.
     * <p>
     * In read only mode, calling the save methods do nothing.
     * 
     * @param readOnly True to set read only mode, false to set read-write mode.
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
    
    protected boolean isReadOnly() {
        return this.readOnly;
    }
}
