package net.worldwizard.support.map;

import java.io.IOException;

import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public interface PrefixIO {
    void writePrefix(XDataWriter writer) throws IOException;

    int readPrefix(XDataReader reader) throws IOException;
}
