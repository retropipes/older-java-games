package com.puttysoftware.widgetwarren.security;

import java.io.File;

final class SandboxManagerAny extends SandboxManager {
    // Fields
    private static final String FALLBACK_PREFIX = "HOME";
    private static final String LIBRARY_FALLBACK_DIR = ".sandboxes/com.puttysoftware.widgetwarren";
    private static final String APP_SUPPORT_FALLBACK_DIR = "Support";
    private static final String DOCUMENTS_FALLBACK_DIR = "Documents";
    private static final String CACHES_FALLBACK_DIR = "Caches";

    // Constructor
    SandboxManagerAny() {
        super();
    }

    // Methods
    @Override
    public String getLibraryDirectory() {
        return SandboxManagerAny.getLibraryFallbackDirectory();
    }

    @Override
    public String getDocumentsDirectory() {
        return SandboxManagerAny.getLibraryFallbackDirectory() + File.separator
                + SandboxManagerAny.DOCUMENTS_FALLBACK_DIR;
    }

    @Override
    public String getCachesDirectory() {
        return SandboxManagerAny.getLibraryFallbackDirectory() + File.separator
                + SandboxManagerAny.CACHES_FALLBACK_DIR;
    }

    @Override
    public String getSupportDirectory() {
        return SandboxManagerAny.getLibraryFallbackDirectory() + File.separator
                + SandboxManagerAny.APP_SUPPORT_FALLBACK_DIR;
    }

    private static String getLibraryFallbackDirectory() {
        return System.getenv(SandboxManagerAny.FALLBACK_PREFIX)
                + File.separator + SandboxManagerAny.LIBRARY_FALLBACK_DIR;
    }
}
