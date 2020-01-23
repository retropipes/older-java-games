package com.puttysoftware.mastermaze.maze;

import java.io.IOException;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        MasterMaze.getApplication().getGameManager()
                .loadGameHook(reader, formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        MasterMaze.getApplication().getGameManager().saveGameHook(writer);
    }
}
