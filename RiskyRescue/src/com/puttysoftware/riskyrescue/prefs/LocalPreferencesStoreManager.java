/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.prefs;

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
    private String getString(final String key, final String defaultValue) {
        return this.store.getProperty(key, defaultValue);
    }

    private void setString(final String key, final String newValue) {
        this.store.setProperty(key, newValue);
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        final String strVal = this.getString(key,
                Boolean.toString(defaultValue));
        return Boolean.parseBoolean(strVal);
    }

    public void setBoolean(final String key, final boolean newValue) {
        this.setString(key, Boolean.toString(newValue));
    }

    public void loadStore(final InputStream source) throws IOException {
        this.store.loadFromXML(source);
    }

    public void saveStore(final OutputStream dest) throws IOException {
        this.store.storeToXML(dest, null);
    }
}
