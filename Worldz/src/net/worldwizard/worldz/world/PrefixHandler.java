package net.worldwizard.worldz.world;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;

public class PrefixHandler implements PrefixIO {
    private static final byte FORMAT_VERSION_MAJOR = (byte) 1;
    private static final byte FORMAT_VERSION_MINOR = (byte) 0;

    @Override
    public void readPrefix(final DataReader reader) throws IOException {
        final boolean res = PrefixHandler
                .checkFormatVersion(PrefixHandler.readFormatVersion(reader));
        if (!res) {
            throw new IOException("Unsupported world format version.");
        }
    }

    @Override
    public void writePrefix(final DataWriter writer) throws IOException {
        PrefixHandler.writeFormatVersion(writer);
    }

    private static byte[] readFormatVersion(final DataReader reader)
            throws IOException {
        final byte major = reader.readByte();
        final byte minor = reader.readByte();
        return new byte[] { major, minor };
    }

    private static boolean checkFormatVersion(final byte[] version) {
        final byte major = version[0];
        final byte minor = version[1];
        if (major != PrefixHandler.FORMAT_VERSION_MAJOR) {
            return false;
        } else {
            if (minor > PrefixHandler.FORMAT_VERSION_MINOR) {
                return false;
            } else {
                return true;
            }
        }
    }

    private static void writeFormatVersion(final DataWriter writer)
            throws IOException {
        writer.writeByte(PrefixHandler.FORMAT_VERSION_MAJOR);
        writer.writeByte(PrefixHandler.FORMAT_VERSION_MINOR);
    }
}
