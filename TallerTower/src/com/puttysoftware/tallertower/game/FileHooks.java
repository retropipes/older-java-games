package com.puttysoftware.tallertower.game;

import java.io.IOException;

import com.puttysoftware.tallertower.creatures.party.PartyManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class FileHooks {
    private FileHooks() {
        // Do nothing
    }

    public static void loadGameHook(final XDataReader mapFile)
            throws IOException {
        PartyManager.loadGameHook(mapFile);
    }

    public static void saveGameHook(final XDataWriter mapFile)
            throws IOException {
        PartyManager.saveGameHook(mapFile);
    }
}
