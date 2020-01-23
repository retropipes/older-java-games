/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze;

import java.io.IOException;

import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public interface PrefixIO {
    void writePrefix(XDataWriter writer) throws IOException;

    int readPrefix(XDataReader reader) throws IOException;
}
