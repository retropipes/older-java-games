package com.puttysoftware.mazer5d.files.versions;

public class MazeVersions {
    private static final MazeVersion MINIMUM = MazeVersion.V1;
    public static final MazeVersion LATEST = MazeVersion.V6;

    private MazeVersions() {
        // Do nothing
    }

    public static boolean isCompatible(final MazeVersion version) {
        return version.ordinal() >= MazeVersions.MINIMUM.ordinal() && version
                .ordinal() <= MazeVersions.LATEST.ordinal();
    }
}