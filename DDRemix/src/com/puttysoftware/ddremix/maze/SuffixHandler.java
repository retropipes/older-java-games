package com.puttysoftware.ddremix.maze;

import java.io.IOException;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.game.FileHooks;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        DDRemix.getApplication().getGameManager();
        FileHooks.loadGameHook(reader);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        DDRemix.getApplication().getGameManager();
        FileHooks.saveGameHook(writer);
    }
}
