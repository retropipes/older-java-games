package com.puttysoftware.mazer5d.files.versions;

public class MazeVersionException extends VersionException {
    /**
     *
     */
    private static final long serialVersionUID = -9019684655962854211L;

    public MazeVersionException(final int actualVersion) {
        super("Unsupported maze version found: " + actualVersion
                + " (expected " + MazeVersions.LATEST + " or earlier)");
    }
}
