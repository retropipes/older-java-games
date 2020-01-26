package com.puttysoftware.rulemazer.maze.xml;

import java.io.IOException;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.xmlio.XMLDataReader;
import com.puttysoftware.xmlio.XMLDataWriter;

public class XMLSuffixHandler implements XMLSuffixIO {
    @Override
    public void readSuffix(final XMLDataReader reader, final int formatVersion)
            throws IOException {
        Main.getApplication().getGameManager().loadGameHookXML(reader,
                formatVersion);
    }

    @Override
    public void writeSuffix(final XMLDataWriter writer) throws IOException {
        Main.getApplication().getGameManager().saveGameHookXML(writer);
    }
}
