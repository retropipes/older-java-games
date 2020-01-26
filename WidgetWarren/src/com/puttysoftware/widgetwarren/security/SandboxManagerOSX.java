package com.puttysoftware.widgetwarren.security;

import java.io.File;

final class SandboxManagerOSX extends SandboxManager {
    // Fields
    private static final String FALLBACK_PREFIX = "HOME";
    private static final String LIBRARY_FALLBACK_DIR = "Library/Application Support/Sandboxes/com.puttysoftware.widgetwarren";
    private static final String APP_SUPPORT_FALLBACK_DIR = "Application Support";
    private static final String DOCUMENTS_FALLBACK_DIR = "Documents";
    private static final String CACHES_FALLBACK_DIR = "Caches";

    // Constructor
    SandboxManagerOSX() {
        super();
    }

    // Methods
    @Override
    public String getLibraryDirectory() {
        String library = System.getProperty("LibraryDirectory");
        if (library == null) {
            library = SandboxManagerOSX.getLibraryFallbackDirectory();
        }
        return library;
    }

    @Override
    public String getDocumentsDirectory() {
        String docs = System.getProperty("DocumentsDirectory");
        if (docs == null) {
            docs = SandboxManagerOSX.getLibraryFallbackDirectory()
                    + File.separator + SandboxManagerOSX.DOCUMENTS_FALLBACK_DIR;
        }
        return docs;
    }

    @Override
    public String getCachesDirectory() {
        String cache = System.getProperty("CachesDirectory");
        if (cache == null) {
            cache = SandboxManagerOSX.getLibraryFallbackDirectory()
                    + File.separator + SandboxManagerOSX.CACHES_FALLBACK_DIR;
        }
        return cache;
    }

    @Override
    public String getSupportDirectory() {
        String appsupport = System.getProperty("ApplicationSupportDirectory");
        if (appsupport == null) {
            appsupport = SandboxManagerOSX.getLibraryFallbackDirectory()
                    + File.separator
                    + SandboxManagerOSX.APP_SUPPORT_FALLBACK_DIR;
        }
        return appsupport;
    }

    private static String getLibraryFallbackDirectory() {
        return System.getenv(SandboxManagerOSX.FALLBACK_PREFIX) + File.separator
                + SandboxManagerOSX.LIBRARY_FALLBACK_DIR;
    }
}
