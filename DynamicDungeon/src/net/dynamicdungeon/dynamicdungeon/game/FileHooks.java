package net.dynamicdungeon.dynamicdungeon.game;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;

public class FileHooks {
    private FileHooks() {
        // Do nothing
    }

    public static void loadGameHook(final DatabaseReader mapFile)
            throws IOException {
        PartyManager.loadGameHook(mapFile);
    }

    public static void saveGameHook(final DatabaseWriter mapFile)
            throws IOException {
        PartyManager.saveGameHook(mapFile);
    }
}
