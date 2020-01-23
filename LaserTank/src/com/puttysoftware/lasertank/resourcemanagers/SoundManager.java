/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.resourcemanagers;

import java.net.URL;

import com.puttysoftware.lasertank.prefs.PreferencesManager;
import com.puttysoftware.lasertank.stringmanagers.StringConstants;
import com.puttysoftware.lasertank.stringmanagers.StringLoader;
import com.puttysoftware.wavplayer.WAVFactory;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = StringLoader.loadString(
            StringConstants.NOTL_STRINGS_FILE,
            StringConstants.NOTL_STRING_SOUND_PATH);
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private SoundManager() {
        // Do nothing
    }

    private static WAVFactory getSound(final int soundID) {
        try {
            final String filename = SoundConstants.SOUND_NAMES[soundID];
            final URL url = SoundManager.LOAD_CLASS
                    .getResource(SoundManager.LOAD_PATH + filename.toLowerCase()
                            + StringLoader.loadString(
                                    StringConstants.NOTL_STRINGS_FILE,
                                    StringConstants.NOTL_STRING_SOUND_EXTENSION));
            return WAVFactory.getNonLoopingResource(url);
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playSound(final int soundID) {
        if (PreferencesManager.getSoundsEnabled()) {
            SoundManager.getSound(soundID).start();
        }
    }
}