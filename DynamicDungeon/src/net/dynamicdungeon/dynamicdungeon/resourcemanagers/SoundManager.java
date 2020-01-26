/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.resourcemanagers;

import java.net.URL;

import net.dynamicdungeon.dynamicdungeon.prefs.PreferencesManager;
import net.dynamicdungeon.randomrange.RandomRange;
import net.dynamicdungeon.wavplayer.WAVPlayer;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/net/dynamicdungeon/dynamicdungeon/resources/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private static WAVPlayer getSound(final String filename) {
        try {
            final URL url = SoundManager.LOAD_CLASS.getResource(
                    SoundManager.LOAD_PATH + filename.toLowerCase() + ".wav");
            return new WAVPlayer(url);
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playSound(final int soundID) {
        try {
            if (PreferencesManager.getSoundsEnabled()) {
                int offset = 0;
                if (soundID == SoundConstants.SOUND_WALK) {
                    final RandomRange rSound = new RandomRange(0, 2);
                    offset = rSound.generate();
                }
                final String soundName = SoundConstants
                        .getSoundName(soundID + offset);
                final WAVPlayer snd = SoundManager.getSound(soundName);
                snd.play();
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }
}