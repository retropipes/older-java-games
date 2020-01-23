package com.puttysoftware.rulemazer.maze.xml;

import java.io.IOException;

import com.puttysoftware.xmlio.XMLDataReader;
import com.puttysoftware.xmlio.XMLDataWriter;

public interface XMLSuffixIO {
    void writeSuffix(XMLDataWriter writer) throws IOException;

    void readSuffix(XMLDataReader reader, int formatVersion) throws IOException;
}
