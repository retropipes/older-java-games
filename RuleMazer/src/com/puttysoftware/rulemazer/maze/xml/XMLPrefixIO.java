package com.puttysoftware.rulemazer.maze.xml;

import java.io.IOException;

import com.puttysoftware.xmlio.XMLDataReader;
import com.puttysoftware.xmlio.XMLDataWriter;

public interface XMLPrefixIO {
    void writePrefix(XMLDataWriter writer) throws IOException;

    int readPrefix(XMLDataReader reader) throws IOException;
}
