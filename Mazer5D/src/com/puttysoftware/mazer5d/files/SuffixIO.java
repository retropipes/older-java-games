package com.puttysoftware.mazer5d.files;

import java.io.IOException;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.files.io.XDataReader;
import com.puttysoftware.mazer5d.files.io.XDataWriter;
import com.puttysoftware.mazer5d.files.versions.MazeVersion;

public class SuffixIO {
    public void writeSuffix(final XDataWriter writer) throws IOException {
        Mazer5D.getBagOStuff().getGameManager().saveGameHookXML(writer);
    }

    public void readSuffix(final XDataReader reader,
            final MazeVersion formatVersion) throws IOException {
        Mazer5D.getBagOStuff().getGameManager().loadGameHookXML(reader,
                formatVersion.ordinal());
    }
}
