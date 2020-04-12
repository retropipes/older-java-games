package com.puttysoftware.mazer5d.files.versions;

public class PrefsVersions {
    public static final int V2 = 2;
    public static final int LATEST = 2;
    private static final int MINIMUM = 1;

    private PrefsVersions() {
        // Do nothing
    }

    public static boolean isCompatible(final int version) {
        return version >= PrefsVersions.MINIMUM
                && version <= PrefsVersions.LATEST;
    }
}