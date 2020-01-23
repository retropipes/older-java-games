package com.puttysoftware.mazerunner2.game;

import java.io.IOException;

import com.puttysoftware.mazerunner2.creatures.party.PartyManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;
import com.puttysoftware.xio.legacy.XLegacyDataReader;

public class FileHooks {
    private FileHooks() {
        // Do nothing
    }

    public static void loadLegacyGameHook(XLegacyDataReader mapFile)
            throws IOException {
        PartyManager.loadLegacyGameHook(mapFile);
    }

    public static void loadGameHook(XDataReader mapFile) throws IOException {
        PartyManager.loadGameHook(mapFile);
    }

    public static void saveGameHook(XDataWriter mapFile) throws IOException {
        PartyManager.saveGameHook(mapFile);
    }
}
