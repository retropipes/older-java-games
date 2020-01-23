package com.puttysoftware.dungeondiver3.game;

import java.io.IOException;

import com.puttysoftware.dungeondiver3.support.creatures.PartyManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class FileHooks {
    private FileHooks() {
        // Do nothing
    }

    public static void loadGameHook(XDataReader mapFile) throws IOException {
        PartyManager.loadGameHook(mapFile);
    }

    public static void loadGameHookV1(XDataReader mapFile) throws IOException {
        PartyManager.loadGameHookV1(mapFile);
    }

    public static void saveGameHook(XDataWriter mapFile) throws IOException {
        PartyManager.saveGameHook(mapFile);
    }
}
