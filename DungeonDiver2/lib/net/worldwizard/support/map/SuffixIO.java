package net.worldwizard.support.map;

import java.io.IOException;

import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public interface SuffixIO {
    void writeSuffix(XDataWriter writer) throws IOException;

    void readSuffix(XDataReader reader, int formatVersion) throws IOException;
}
