package com.puttysoftware.loopchute.maze;

import java.io.IOException;

import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        LoopChute.getApplication().getGameManager()
                .loadGameHook(reader, formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        LoopChute.getApplication().getGameManager().saveGameHook(writer);
    }
}
