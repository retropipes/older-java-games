package net.dynamicdungeon.dynamicdungeon.dungeon;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;

public interface SuffixIO {
    void writeSuffix(DatabaseWriter writer) throws IOException;

    void readSuffix(DatabaseReader reader, int formatVersion)
            throws IOException;
}
