/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.resourcemanagers;

import com.puttysoftware.dungeondiver3.support.prefs.LocalPreferencesManager;
import com.puttysoftware.media.Media;
import com.puttysoftware.randomrange.RandomRange;

public class SoundManager {
    private static final String INTERNAL_LOAD_PATH = "/com/puttysoftware/dungeondiver3/support/resources/sounds/";
    private final static Class<?> LOAD_CLASS = SoundManager.class;

    private static Media getSound(final String filename) {
        try {
            return Media.getNonLoopingResource(SoundManager.LOAD_CLASS
                    .getResource(SoundManager.INTERNAL_LOAD_PATH
                            + filename.toLowerCase() + ".wav"));
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playSound(final int soundID) {
        if (LocalPreferencesManager.getSoundsEnabled()) {
            try {
                int offset;
                RandomRange rr;
                switch (soundID) {
                case GameSoundConstants.SOUND_STEP:
                    rr = new RandomRange(0, 1);
                    offset = rr.generate();
                    break;
                default:
                    offset = 0;
                    break;
                }
                final String soundName = SoundNames.getSoundNames()[soundID
                        + offset];
                final Media snd = SoundManager.getSound(soundName);
                if (snd != null) {
                    snd.start();
                }
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }
}