package com.puttysoftware.dungeondiver4.dungeon;

import java.io.IOException;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        DungeonDiver4.getApplication().getGameManager().loadGameHook(reader,
                formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        DungeonDiver4.getApplication().getGameManager().saveGameHook(writer);
    }
}
