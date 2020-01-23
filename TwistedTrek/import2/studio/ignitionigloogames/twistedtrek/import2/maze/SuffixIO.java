/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze;

import java.io.IOException;

import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public interface SuffixIO {
    void writeSuffix(XDataWriter writer) throws IOException;

    void readSuffix(XDataReader reader, int formatVersion) throws IOException;
}
