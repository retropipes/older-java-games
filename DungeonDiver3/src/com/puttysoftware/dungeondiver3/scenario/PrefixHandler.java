/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.scenario;

import java.io.IOException;

import com.puttysoftware.dungeondiver3.support.map.PrefixIO;
import com.puttysoftware.dungeondiver3.support.scenario.FormatConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class PrefixHandler implements PrefixIO {
    private static final byte FORMAT_VERSION = (byte) FormatConstants.LATEST_SCENARIO_FORMAT;

    @Override
    public int readPrefix(final XDataReader reader) throws IOException {
        final byte formatVer = PrefixHandler.readFormatVersion(reader);
        final boolean res = PrefixHandler.checkFormatVersion(formatVer);
        if (!res) {
            throw new IOException("Unsupported scenario format version.");
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
        return version <= PrefixHandler.FORMAT_VERSION;
    }

    private static void writeFormatVersion(final XDataWriter writer)
            throws IOException {
        writer.writeByte(PrefixHandler.FORMAT_VERSION);
    }
}
