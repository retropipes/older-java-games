/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.prefs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

class LocalPreferencesStoreManager {
    // Fields
    private final Properties store;

    // Constructors
    LocalPreferencesStoreManager() {
        this.store = new Properties();
    }

    // Methods
    private String getString(String key, String defaultValue) {
        return this.store.getProperty(key, defaultValue);
    }

    private void setString(String key, String newValue) {
        this.store.setProperty(key, newValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String strVal = this.getString(key, Boolean.toString(defaultValue));
        return Boolean.parseBoolean(strVal);
    }

    public void setBoolean(String key, boolean newValue) {
        this.setString(key, Boolean.toString(newValue));
    }

    public void loadStore(InputStream source) throws IOException {
        this.store.loadFromXML(source);
    }

    public void saveStore(OutputStream dest) throws IOException {
        this.store.storeToXML(dest, null);
    }
}
