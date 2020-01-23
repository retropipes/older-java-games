package net.worldwizard.worldz.world;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.Worldz;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final DataReader reader) throws IOException {
        Worldz.getApplication().getGameManager()
                .loadGameHook(reader, FormatConstants.WORLD_FORMAT_1);
    }

    @Override
    public void writeSuffix(final DataWriter writer) throws IOException {
        Worldz.getApplication().getGameManager().saveGameHook(writer);
    }
}
