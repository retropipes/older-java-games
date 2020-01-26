package com.puttysoftware.widgetwarren.security;

import java.io.File;

public abstract class SandboxManager {
    // Constructor
    protected SandboxManager() {
        new File(this.getDocumentsDirectory()).mkdirs();
        new File(this.getCachesDirectory()).mkdirs();
        new File(this.getSupportDirectory()).mkdirs();
    }

    // Static methods
    public static SandboxManager getSandboxManager() {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            return new SandboxManagerOSX();
        } else if (System.getProperty("os.name").startsWith("Windows")) {
            return new SandboxManagerWin();
        } else {
            return new SandboxManagerAny();
        }
    }

    // Methods
    public abstract String getLibraryDirectory();

    public abstract String getDocumentsDirectory();

    public abstract String getCachesDirectory();

    public abstract String getSupportDirectory();
}
