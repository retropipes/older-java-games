package com.puttysoftware.mazerunner2.maze.legacy;

import java.io.IOException;

import com.puttysoftware.xio.legacy.XLegacyDataReader;

public class LegacyPrefixHandler implements LegacyPrefixIO {
    private static final byte FORMAT_VERSION = (byte) LegacyFormatConstants.LEGACY_MAZE_FORMAT_LATEST;

    @Override
    public int readPrefix(final XLegacyDataReader reader) throws IOException {
        final byte formatVer = LegacyPrefixHandler.readFormatVersion(reader);
        final boolean res = LegacyPrefixHandler.checkFormatVersion(formatVer);
        if (!res) {
            throw new IOException("Unsupported maze format version.");
        }
        return formatVer;
    }

    private static byte readFormatVersion(final XLegacyDataReader reader)
            throws IOException {
        return reader.readByte();
    }

    private static boolean checkFormatVersion(final byte version) {
        return version <= LegacyPrefixHandler.FORMAT_VERSION;
    }
}
