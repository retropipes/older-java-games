package net.worldwizard.worldz.world;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;

public interface SuffixIO {
    void writeSuffix(DataWriter writer) throws IOException;

    void readSuffix(DataReader reader) throws IOException;
}
