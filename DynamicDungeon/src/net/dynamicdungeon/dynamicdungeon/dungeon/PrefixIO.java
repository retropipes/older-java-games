package net.dynamicdungeon.dynamicdungeon.dungeon;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;

public interface PrefixIO {
    void writePrefix(DatabaseWriter writer) throws IOException;

    int readPrefix(DatabaseReader reader) throws IOException;
}
