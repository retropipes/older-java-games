package com.puttysoftware.mazerunner2.maze.legacy;

import java.io.IOException;

import com.puttysoftware.xio.legacy.XLegacyDataReader;

public interface LegacySuffixIO {
    void readSuffix(XLegacyDataReader reader, int formatVersion)
            throws IOException;
}
