package com.puttysoftware.mazemode.maze;

import java.io.IOException;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        MazeMode.getApplication().getGameManager().loadGameHookX(reader,
                formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        MazeMode.getApplication().getGameManager().saveGameHookX(writer);
    }
}
