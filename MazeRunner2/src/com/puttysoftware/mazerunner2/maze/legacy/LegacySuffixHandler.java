package com.puttysoftware.mazerunner2.maze.legacy;

import java.io.IOException;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.xio.legacy.XLegacyDataReader;

public class LegacySuffixHandler implements LegacySuffixIO {
    @Override
    public void readSuffix(XLegacyDataReader reader, int formatVersion)
            throws IOException {
        MazeRunnerII.getApplication().getGameManager()
                .loadLegacyGameHook(reader, formatVersion);
    }
}
