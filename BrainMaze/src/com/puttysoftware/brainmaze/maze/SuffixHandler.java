package com.puttysoftware.brainmaze.maze;

import java.io.IOException;

import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        BrainMaze.getApplication().getGameManager()
                .loadGameHook(reader, formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        BrainMaze.getApplication().getGameManager().saveGameHook(writer);
    }
}
