package net.dynamicdungeon.dynamicdungeon.utilities;

import java.io.File;

public class DynamicProperties {
    // Fields
    private static final String FALLBACK_PREFIX = "HOME";
    private static final String LIBRARY_FALLBACK_DIR = "Library";
    private static final String APP_SUPPORT_FALLBACK_DIR = "Application Support";
    private static final String DOCUMENTS_FALLBACK_DIR = "Documents";
    private static final String CACHES_FALLBACK_DIR = "Caches";

    // Constructor
    private DynamicProperties() {
        // Do nothing
    }

    // Methods
    public static String getLibraryDirectory() {
        String library = System.getProperty("LibraryDirectory");
        if (library == null) {
            library = DynamicProperties.getLibraryFallbackDirectory();
        }
        return library;
    }

    public static String getDocumentsDirectory() {
        String docs = System.getProperty("DocumentsDirectory");
        if (docs == null) {
            docs = DynamicProperties.getDocumentsFallbackDirectory();
        }
        return docs;
    }

    public static String getCachesDirectory() {
        String cache = System.getProperty("CachesDirectory");
        if (cache == null) {
            cache = DynamicProperties.getLibraryFallbackDirectory()
                    + File.separator + DynamicProperties.CACHES_FALLBACK_DIR;
        }
        return cache;
    }

    public static String getApplicationSupportDirectory() {
        String appsupport = System.getProperty("ApplicationSupportDirectory");
        if (appsupport == null) {
            appsupport = DynamicProperties.getLibraryFallbackDirectory()
                    + File.separator
                    + DynamicProperties.APP_SUPPORT_FALLBACK_DIR;
        }
        return appsupport;
    }

    private static String getLibraryFallbackDirectory() {
        return System.getenv(DynamicProperties.FALLBACK_PREFIX) + File.separator
                + DynamicProperties.LIBRARY_FALLBACK_DIR;
    }

    private static String getDocumentsFallbackDirectory() {
        return System.getenv(DynamicProperties.FALLBACK_PREFIX) + File.separator
                + DynamicProperties.DOCUMENTS_FALLBACK_DIR;
    }
}
