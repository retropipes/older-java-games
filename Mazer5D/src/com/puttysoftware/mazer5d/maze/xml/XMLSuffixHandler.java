package com.puttysoftware.mazer5d.maze.xml;

import java.io.IOException;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class XMLSuffixHandler implements XMLSuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        Mazer5D.getApplication().getGameManager().loadGameHookXML(reader,
                formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        Mazer5D.getApplication().getGameManager().saveGameHookXML(writer);
    }
}
