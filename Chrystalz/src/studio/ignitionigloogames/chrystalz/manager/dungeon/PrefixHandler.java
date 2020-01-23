/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.dungeon;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class PrefixHandler implements PrefixIO {
    private static final byte FORMAT_VERSION = (byte) FormatConstants.MAZE_FORMAT_LATEST;

    @Override
    public int readPrefix(final FileIOReader reader) throws IOException {
        final byte formatVer = PrefixHandler.readFormatVersion(reader);
        final boolean res = PrefixHandler.checkFormatVersion(formatVer);
        if (!res) {
            throw new IOException(
                    "Unsupported maze format version: " + formatVer);
        }
        return formatVer;
    }

    @Override
    public void writePrefix(final FileIOWriter writer) throws IOException {
        PrefixHandler.writeFormatVersion(writer);
    }

    private static byte readFormatVersion(final FileIOReader reader)
            throws IOException {
        return reader.readByte();
    }

    private static boolean checkFormatVersion(final byte version) {
        return version <= PrefixHandler.FORMAT_VERSION;
    }

    private static void writeFormatVersion(final FileIOWriter writer)
            throws IOException {
        writer.writeByte(PrefixHandler.FORMAT_VERSION);
    }
}
