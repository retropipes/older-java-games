package net.dynamicdungeon.dynamicdungeon.dungeon;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;

public class PrefixHandler implements PrefixIO {
    private static final byte FORMAT_VERSION = (byte) FormatConstants.MAZE_FORMAT_LATEST;

    @Override
    public int readPrefix(final DatabaseReader reader) throws IOException {
        final byte formatVer = PrefixHandler.readFormatVersion(reader);
        final boolean res = PrefixHandler.checkFormatVersion(formatVer);
        if (!res) {
            throw new IOException(
                    "Unsupported maze format version: " + formatVer);
        }
        return formatVer;
    }

    @Override
    public void writePrefix(final DatabaseWriter writer) throws IOException {
        PrefixHandler.writeFormatVersion(writer);
    }

    private static byte readFormatVersion(final DatabaseReader reader)
            throws IOException {
        return (byte) reader.readByte();
    }

    private static boolean checkFormatVersion(final byte version) {
        return version <= PrefixHandler.FORMAT_VERSION;
    }

    private static void writeFormatVersion(final DatabaseWriter writer)
            throws IOException {
        writer.writeByte(PrefixHandler.FORMAT_VERSION);
    }
}
