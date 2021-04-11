/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.resourcemanagers;

import java.net.URL;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.sound.Sound;
import net.worldwizard.support.map.generic.GameSounds;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/net/worldwizard/support/resources/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private static Sound getSound(final String categoryName,
            final String filename) {
        try {
            String sfile;
            if (filename.equalsIgnoreCase("walk")) {
                final RandomRange r = new RandomRange(1, 2);
                sfile = filename + Integer.toString(r.generate());
            } else {
                sfile = filename;
            }
            final URL url = SoundManager.LOAD_CLASS
                    .getResource(SoundManager.LOAD_PATH + categoryName + "/"
                            + sfile.toLowerCase() + ".wav");
            final Sound snd = new Sound(url);
            return snd;
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playSound(final GameSounds soundID) {
        if (soundID != GameSounds._NONE) {
            try {
                final String categoryName = SoundNames.SOUND_CATEGORY_NAMES[SoundNames
                        .getCategoryIndexFromSoundIndex(soundID.ordinal())];
                final String soundName = SoundNames.SOUND_NAMES[soundID
                        .ordinal()];
                final Sound snd = SoundManager.getSound(categoryName,
                        soundName);
                if (snd != null) {
                    new SoundTask(snd).start();
                }
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }
}