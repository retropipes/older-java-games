package com.puttysoftware.mazerunner2.maze.legacy;

import java.io.IOException;

import com.puttysoftware.xio.legacy.XLegacyDataReader;

public interface LegacyPrefixIO {
    int readPrefix(XLegacyDataReader reader) throws IOException;
}
