package com.puttysoftware.tallertower.resourcemanagers;

import com.puttysoftware.tallertower.creatures.party.PartyManager;
import com.puttysoftware.tallertower.datamanagers.MusicDataManager;
import com.puttysoftware.tallertower.prefs.PreferencesManager;

public class MusicConstants {
    // Public Music Constants
    public static final int MUSIC_BATTLE = -2;
    public static final int MUSIC_EXPLORING = -1;
    private static final int OFFSET_EXPLORING = 0;
    private static final int OFFSET_MAP_BATTLE = 20;
    private static final int OFFSET_WINDOW_BATTLE = 40;
    private static final int DIVIDE_BASE = 3;
    private static final String[] MUSIC_NAMES = MusicDataManager.getMusicData();

    // Private constructor
    private MusicConstants() {
        // Do nothing
    }

    public static int getMusicID(final int ID) {
        if (ID == MUSIC_EXPLORING) {
            final int nID = PartyManager.getParty().getTowerLevel()
                    / DIVIDE_BASE;
            return nID + OFFSET_EXPLORING;
        } else if (ID == MUSIC_BATTLE) {
            final int nID = PartyManager.getParty().getTowerLevel()
                    / DIVIDE_BASE;
            if (PreferencesManager.useMapBattleEngine()) {
                return nID + OFFSET_MAP_BATTLE;
            } else {
                return nID + OFFSET_WINDOW_BATTLE;
            }
        } else {
            return ID;
        }
    }

    static String getMusicName(final int ID) {
        if (ID == MUSIC_EXPLORING) {
            final int nID = PartyManager.getParty().getTowerLevel()
                    / DIVIDE_BASE;
            return MUSIC_NAMES[nID + OFFSET_EXPLORING];
        } else if (ID == MUSIC_BATTLE) {
            final int nID = PartyManager.getParty().getTowerLevel()
                    / DIVIDE_BASE;
            if (PreferencesManager.useMapBattleEngine()) {
                return MUSIC_NAMES[nID + OFFSET_MAP_BATTLE];
            } else {
                return MUSIC_NAMES[nID + OFFSET_WINDOW_BATTLE];
            }
        } else {
            return MUSIC_NAMES[ID];
        }
    }
}