package net.worldwizard.dungeondiver2.variables;

import java.io.IOException;

import net.worldwizard.support.map.PrefixIO;
import net.worldwizard.support.variables.FormatConstants;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class PrefixHandler implements PrefixIO {
    private static final byte FORMAT_VERSION = (byte) FormatConstants.LATEST_SCENARIO_FORMAT;

    @Override
    public int readPrefix(final XDataReader reader) throws IOException {
        final byte formatVer = PrefixHandler.readFormatVersion(reader);
        final boolean res = PrefixHandler.checkFormatVersion(formatVer);
        if (!res) {
            throw new IOException("Unsupported variables format version.");
        }
        return formatVer;
    }

    @Override
    public void writePrefix(final XDataWriter writer) throws IOException {
        PrefixHandler.writeFormatVersion(writer);
    }

    private static byte readFormatVersion(final XDataReader reader)
            throws IOException {
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
