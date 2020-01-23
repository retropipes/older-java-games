package com.puttysoftware.widgetwarren.security;

import java.io.File;

final class SandboxManagerWin extends SandboxManager {
    // Fields
    private static final String FALLBACK_PREFIX = "APPDATA";
    private static final String LIBRARY_FALLBACK_DIR = "Sandboxes\\com.puttysoftware.widgetwarren";
    private static final String APP_SUPPORT_FALLBACK_DIR = "Support";
    private static final String DOCUMENTS_FALLBACK_DIR = "Documents";
    private static final String CACHES_FALLBACK_DIR = "Caches";

    // Constructor
    SandboxManagerWin() {
        super();
    }

    // Methods
    @Override
    public String getLibraryDirectory() {
        return SandboxManagerWin.getLibraryFallbackDirectory();
    }

    @Override
    public String getDocumentsDirectory() {
        return SandboxManagerWin.getLibraryFallbackDirectory() + File.separator
                + SandboxManagerWin.DOCUMENTS_FALLBACK_DIR;
    }

    @Override
    public String getCachesDirectory() {
        return SandboxManagerWin.getLibraryFallbackDirectory() + File.separator
                + SandboxManagerWin.CACHES_FALLBACK_DIR;
    }

    @Override
    public String getSupportDirectory() {
        return SandboxManagerWin.getLibraryFallbackDirectory() + File.separator
                + SandboxManagerWin.APP_SUPPORT_FALLBACK_DIR;
    }

    private static String getLibraryFallbackDirectory() {
        return System.getenv(SandboxManagerWin.FALLBACK_PREFIX)
                + File.separator + SandboxManagerWin.LIBRARY_FALLBACK_DIR;
    }
}
