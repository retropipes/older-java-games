package net.worldwizard.dungeondiver2.variables;

import java.io.IOException;

import net.worldwizard.dungeondiver2.DungeonDiverII;
import net.worldwizard.support.map.SuffixIO;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        DungeonDiverII.getApplication().getGameManager().loadGameHookX(reader);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        DungeonDiverII.getApplication().getGameManager().saveGameHookX(writer);
    }
}
