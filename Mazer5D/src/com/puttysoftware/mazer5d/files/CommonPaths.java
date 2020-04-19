package com.puttysoftware.mazer5d.files;

import java.io.File;

public class CommonPaths {
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "XDG_CONFIG_HOME";
    private static final String UNIX_FALLBACK_PREFIX = "HOME";
    private static final String MAC_APP_DIR = "/Library/Application Support/com.puttysoftware.mazer5d/";
    private static final String WIN_APP_DIR = "\\com.puttysoftware.mazer5d\\";
    private static final String UNIX_APP_DIR = "/com.puttysoftware.mazer5d/";
    private static final String UNIX_FALLBACK_APP_DIR = "/.config/com.puttysoftware.mazer5d/";
    private static final String MAC_PREFS_DIR = "/Library/Preferences/com.puttysoftware.mazer5d.versioned";
    private static final String WIN_PREFS_DIR = "\\com.puttysoftware.mazer5d\\prefs.versioned";
    private static final String UNIX_PREFS_DIR = "/com.puttysoftware.mazer5d/prefs.versioned";
    private static final String UNIX_FALLBACK_PREFS_DIR = "/.config/com.puttysoftware.mazer5d/prefs.versioned";
    private static final String MAC_LEGACY_PREFS_PREFIX = "HOME";
    private static final String WIN_LEGACY_PREFS_PREFIX = "APPDATA";
    private static final String UNIX_LEGACY_PREFS_PREFIX = "HOME";
    private static final String MAC_LEGACY_PREFS_DIR = "/Library/Preferences/";
    private static final String WIN_LEGACY_PREFS_DIR = "\\Mazer5D\\";
    private static final String UNIX_LEGACY_PREFS_DIR = "/.mazer5d/";
    private static final String MAC_LEGACY_PREFS_FILE = "com.puttysoftware.mazer5d.preferences";
    private static final String WIN_LEGACY_PREFS_FILE = "Mazer5DPreferences";
    private static final String UNIX_LEGACY_PREFS_FILE = "Mazer5DPreferences";
    private static final String LEGACY_PREFS_FILE_EXTENSION = ".5dp";

    private CommonPaths() {
        super();
    }

    private static boolean useFallback() {
        return System.getenv(CommonPaths.UNIX_PREFIX) == null;
    }

    private static String getDirectoryPrefix(final boolean fallback) {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(CommonPaths.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(CommonPaths.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            if (fallback) {
                return System.getenv(CommonPaths.UNIX_FALLBACK_PREFIX);
            }
            return System.getenv(CommonPaths.UNIX_PREFIX);
        }
    }

    private static String getAppDirectoryName(final boolean fallback) {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return CommonPaths.MAC_APP_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return CommonPaths.WIN_APP_DIR;
        } else {
            // Other - assume UNIX-like
            if (fallback) {
                return System.getenv(CommonPaths.UNIX_FALLBACK_APP_DIR);
            }
            return CommonPaths.UNIX_APP_DIR;
        }
    }

    private static String getPrefsDirectoryName(final boolean fallback) {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return CommonPaths.MAC_PREFS_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return CommonPaths.WIN_PREFS_DIR;
        } else {
            // Other - assume UNIX-like
            if (fallback) {
                return System.getenv(CommonPaths.UNIX_FALLBACK_PREFS_DIR);
            }
            return CommonPaths.UNIX_PREFS_DIR;
        }
    }

    public static String getAppDirectoryFor(final String suffix) {
        final boolean fallback = CommonPaths.useFallback();
        final StringBuilder b = new StringBuilder();
        b.append(CommonPaths.getDirectoryPrefix(fallback));
        b.append(CommonPaths.getAppDirectoryName(fallback));
        b.append(suffix);
        final String dirname = b.toString();
        new File(dirname).mkdirs();
        return dirname;
    }

    public static File getPrefsFile() {
        final boolean fallback = CommonPaths.useFallback();
        final StringBuilder b = new StringBuilder();
        b.append(CommonPaths.getDirectoryPrefix(fallback));
        b.append(CommonPaths.getPrefsDirectoryName(fallback));
        b.append(FileExtensions.getPrefsExtensionWithPeriod());
        return new File(b.toString());
    }

    private static String getLegacyPrefsDirPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(CommonPaths.MAC_LEGACY_PREFS_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(CommonPaths.WIN_LEGACY_PREFS_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(CommonPaths.UNIX_LEGACY_PREFS_PREFIX);
        }
    }

    private static String getLegacyPrefsDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return CommonPaths.MAC_LEGACY_PREFS_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return CommonPaths.WIN_LEGACY_PREFS_DIR;
        } else {
            // Other - assume UNIX-like
            return CommonPaths.UNIX_LEGACY_PREFS_DIR;
        }
    }

    private static String getLegacyPrefsFileExtension() {
        return CommonPaths.LEGACY_PREFS_FILE_EXTENSION;
    }

    private static String getLegacyPrefsFileName() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return CommonPaths.MAC_LEGACY_PREFS_FILE;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return CommonPaths.WIN_LEGACY_PREFS_FILE;
        } else {
            // Other - assume UNIX-like
            return CommonPaths.UNIX_LEGACY_PREFS_FILE;
        }
    }

    public static String getLegacyPrefsFile() {
        final StringBuilder b = new StringBuilder();
        b.append(CommonPaths.getLegacyPrefsDirPrefix());
        b.append(CommonPaths.getLegacyPrefsDirectory());
        b.append(CommonPaths.getLegacyPrefsFileName());
        b.append(CommonPaths.getLegacyPrefsFileExtension());
        return b.toString();
    }
}
