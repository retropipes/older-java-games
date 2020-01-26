package com.puttysoftware.weaselweb.maze;

import java.io.IOException;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        WeaselWeb.getApplication().getGameManager().loadGameHook(reader,
                formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        WeaselWeb.getApplication().getGameManager().saveGameHook(writer);
    }
}
