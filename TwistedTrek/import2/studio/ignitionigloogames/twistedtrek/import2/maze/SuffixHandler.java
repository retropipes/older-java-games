/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze;

import java.io.IOException;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.game.FileHooks;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion) throws IOException {
        Import2.getApplication().getGameManager();
        FileHooks.loadGameHook(reader);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        Import2.getApplication().getGameManager();
        FileHooks.saveGameHook(writer);
    }
}
