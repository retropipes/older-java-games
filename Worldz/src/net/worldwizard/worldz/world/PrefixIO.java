package net.worldwizard.worldz.world;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;

public interface PrefixIO {
    void writePrefix(DataWriter writer) throws IOException;

    void readPrefix(DataReader reader) throws IOException;
}
