package com.puttysoftware.fantastlex.maze;

import java.io.IOException;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        FantastleX.getApplication().getGameManager().loadGameHook(reader,
                formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        FantastleX.getApplication().getGameManager().saveGameHook(writer);
    }
}
