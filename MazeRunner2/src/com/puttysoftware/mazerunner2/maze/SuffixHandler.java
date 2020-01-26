package com.puttysoftware.mazerunner2.maze;

import java.io.IOException;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        MazeRunnerII.getApplication().getGameManager().loadGameHook(reader,
                formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        MazeRunnerII.getApplication().getGameManager().saveGameHook(writer);
    }
}
