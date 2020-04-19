package com.puttysoftware.mazer5d.compatibility.files.xml;

import java.io.IOException;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.files.io.XDataReader;
import com.puttysoftware.mazer5d.files.io.XDataWriter;

public class XMLSuffixHandler implements XMLSuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        Mazer5D.getBagOStuff().getGameManager().loadGameHookXML(reader,
                formatVersion);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        Mazer5D.getBagOStuff().getGameManager().saveGameHookXML(writer);
    }
}
