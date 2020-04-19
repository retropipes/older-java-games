package com.puttysoftware.mazer5d.files;

import java.io.IOException;

import com.puttysoftware.mazer5d.files.io.XDataReader;
import com.puttysoftware.mazer5d.files.io.XDataWriter;
import com.puttysoftware.mazer5d.files.versions.MazeVersion;
import com.puttysoftware.mazer5d.files.versions.MazeVersionException;
import com.puttysoftware.mazer5d.files.versions.MazeVersions;

public class PrefixIO {
    private static final byte FORMAT_VERSION_MAJOR = (byte) MazeVersions.LATEST
            .ordinal();
    private static final byte FORMAT_VERSION_MINOR = (byte) 0;

    public MazeVersion readPrefix(final XDataReader reader) throws IOException {
        final byte[] formatVer = PrefixIO.readFormatVersion(reader);
        final boolean res = PrefixIO.checkFormatVersion(formatVer);
        if (!res) {
            throw new MazeVersionException(formatVer[0]);
        }
        return PrefixIO.decodeFormatByte(formatVer[0]);
    }

    public void writePrefix(final XDataWriter writer) throws IOException {
        PrefixIO.writeFormatVersion(writer);
    }

    private static byte[] readFormatVersion(final XDataReader reader)
            throws IOException {
        final byte major = reader.readByte();
        final byte minor = reader.readByte();
        return new byte[] { major, minor };
    }

    private static boolean checkFormatVersion(final byte[] version) {
        final byte major = version[0];
        final byte minor = version[1];
        if (major > PrefixIO.FORMAT_VERSION_MAJOR) {
            return false;
        } else {
            if (minor > PrefixIO.FORMAT_VERSION_MINOR) {
                return false;
            } else {
                return true;
            }
        }
    }

    private static void writeFormatVersion(final XDataWriter writer)
            throws IOException {
        writer.writeByte(PrefixIO.FORMAT_VERSION_MAJOR);
        writer.writeByte(PrefixIO.FORMAT_VERSION_MINOR);
    }

    private static MazeVersion decodeFormatByte(final int input) {
        switch (input) {
        case 1:
            return MazeVersion.V1;
        case 2:
            return MazeVersion.V2;
        case 3:
            return MazeVersion.V3;
        case 4:
            return MazeVersion.V4;
        case 5:
            return MazeVersion.V5;
        case 6:
            return MazeVersion.V6;
        default:
            return MazeVersions.LATEST;
        }
    }
}
