package com.puttysoftware.gemma.game;

import java.io.IOException;

import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class FileHooks {
    private FileHooks() {
        // Do nothing
    }

    public static void loadGameHook(XDataReader mapFile) throws IOException {
        PartyManager.loadGameHook(mapFile);
    }

    public static void saveGameHook(XDataWriter mapFile) throws IOException {
        PartyManager.saveGameHook(mapFile);
    }
}
