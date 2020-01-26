package net.dynamicdungeon.dynamicdungeon.dungeon;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.game.FileHooks;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final DatabaseReader reader, final int formatVersion)
            throws IOException {
        DynamicDungeon.getApplication().getGameManager();
        FileHooks.loadGameHook(reader);
    }

    @Override
    public void writeSuffix(final DatabaseWriter writer) throws IOException {
        DynamicDungeon.getApplication().getGameManager();
        FileHooks.saveGameHook(writer);
    }
}
