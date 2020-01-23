package com.puttysoftware.rulemazer.maze.xml;

import java.io.IOException;

import com.puttysoftware.xmlio.XMLDataReader;
import com.puttysoftware.xmlio.XMLDataWriter;

public class XMLPrefixHandler implements XMLPrefixIO {
    private static final byte FORMAT_VERSION_MAJOR = (byte) 4;
    private static final byte FORMAT_VERSION_MINOR = (byte) 0;

    @Override
    public int readPrefix(final XMLDataReader reader) throws IOException {
        final byte[] formatVer = XMLPrefixHandler.readFormatVersion(reader);
        final boolean res = XMLPrefixHandler.checkFormatVersion(formatVer);
        if (!res) {
            throw new IOException("Unsupported XML maze format version.");
        }
        return formatVer[0];
    }

    @Override
    public void writePrefix(final XMLDataWriter writer) throws IOException {
        XMLPrefixHandler.writeFormatVersion(writer);
    }

    private static byte[] readFormatVersion(final XMLDataReader reader)
            throws IOException {
        final byte major = reader.readByte();
        final byte minor = reader.readByte();
        return new byte[] { major, minor };
    }

    private static boolean checkFormatVersion(final byte[] version) {
        final byte major = version[0];
        final byte minor = version[1];
        if (major > XMLPrefixHandler.FORMAT_VERSION_MAJOR) {
            return false;
        } else {
            if (minor > XMLPrefixHandler.FORMAT_VERSION_MINOR) {
                return false;
            } else {
                return true;
            }
        }
    }

    private static void writeFormatVersion(final XMLDataWriter writer)
            throws IOException {
        writer.writeByte(XMLPrefixHandler.FORMAT_VERSION_MAJOR);
        writer.writeByte(XMLPrefixHandler.FORMAT_VERSION_MINOR);
    }
}
