/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.scenario;

import java.io.IOException;

import com.puttysoftware.gemma.support.map.PrefixIO;
import com.puttysoftware.gemma.support.scenario.FormatConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class PrefixHandler implements PrefixIO {
    private static final byte FORMAT_VERSION = (byte) FormatConstants.LATEST_SCENARIO_FORMAT;

    @Override
    public int readPrefix(XDataReader reader) throws IOException {
        byte formatVer = PrefixHandler.readFormatVersion(reader);
        boolean res = PrefixHandler.checkFormatVersion(formatVer);
        if (!res) {
            throw new IOException("Unsupported scenario format version.");
        }
        return formatVer;
    }

    @Override
    public void writePrefix(XDataWriter writer) throws IOException {
        PrefixHandler.writeFormatVersion(writer);
    }

    private static byte readFormatVersion(XDataReader reader)
            throws IOException {
        return reader.readByte();
    }

    private static boolean checkFormatVersion(byte version) {
        return (version <= PrefixHandler.FORMAT_VERSION);
    }

    private static void writeFormatVersion(XDataWriter writer)
            throws IOException {
        writer.writeByte(PrefixHandler.FORMAT_VERSION);
    }
}
