package com.puttysoftware.mazemode.maze;

import java.io.IOException;

import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class PrefixHandler implements PrefixIO {
    private static final byte FORMAT_VERSION = (byte) FormatConstants.LATEST_MAZE_FORMAT;

    @Override
    public int readPrefix(final XDataReader reader) throws IOException {
        final byte formatVer = PrefixHandler.readFormatVersion(reader);
        final boolean res = PrefixHandler.checkFormatVersion(formatVer);
        if (!res) {
            throw new IOException("Unsupported maze format version.");
        }
        return formatVer;
    }

    @Override
    public void writePrefix(final XDataWriter writer) throws IOException {
        PrefixHandler.writeFormatVersion(writer);
    }

    private static byte readFormatVersion(final XDataReader reader) throws IOException {
        return reader.readByte();
    }

    private static boolean checkFormatVersion(final byte version) {
        if (version > PrefixHandler.FORMAT_VERSION) {
            return false;
        } else {
            return true;
        }
    }

    private static void writeFormatVersion(final XDataWriter writer)
            throws IOException {
        writer.writeByte(PrefixHandler.FORMAT_VERSION);
    }
}
