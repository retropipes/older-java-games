package com.puttysoftware.mazerunner2.prefs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

class PreferencesStoreManager {
    // Fields
    private Properties store;

    // Constructors
    public PreferencesStoreManager() {
        this.store = new Properties();
    }

    // Methods
    public String getString(String key, String defaultValue) {
        return this.store.getProperty(key, defaultValue);
    }

    public void setString(String key, String newValue) {
        this.store.setProperty(key, newValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String strVal = this.getString(key, Boolean.toString(defaultValue));
        return Boolean.parseBoolean(strVal);
    }

    public void setBoolean(String key, boolean newValue) {
        this.setString(key, Boolean.toString(newValue));
    }

    public int getInteger(String key, int defaultValue) {
        String strVal = this.getString(key, Integer.toString(defaultValue));
        return Integer.parseInt(strVal);
    }

    public void setInteger(String key, int newValue) {
        this.setString(key, Integer.toString(newValue));
    }

    public void loadStore(InputStream source) throws IOException {
        this.store.loadFromXML(source);
    }

    public void saveStore(OutputStream dest) throws IOException {
        this.store.storeToXML(dest, null);
    }
}
