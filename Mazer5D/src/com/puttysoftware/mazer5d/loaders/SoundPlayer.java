package com.puttysoftware.mazer5d.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.prefs.Prefs;

public class SoundPlayer {
    private SoundPlayer() {
        // Do nothing
    }

    private static String[] allFilenames;
    private static Properties fileExtensions;

    private static String getSoundFilename(final SoundIndex sound) {
        if (SoundPlayer.allFilenames == null
                && SoundPlayer.fileExtensions == null) {
            SoundPlayer.allFilenames = DataLoader.loadSoundData();
            try (final InputStream stream = SoundPlayer.class
                    .getResourceAsStream(
                            "/assets/data/extension/extension.properties")) {
                SoundPlayer.fileExtensions = new Properties();
                SoundPlayer.fileExtensions.load(stream);
            } catch (final IOException e) {
                Mazer5D.logError(e);
            }
        }
        final String soundExt = SoundPlayer.fileExtensions
                .getProperty("sounds");
        return SoundPlayer.allFilenames[sound.ordinal()] + soundExt;
    }

    public static void playSound(final SoundIndex sound,
            final SoundGroup group) {
        if (Prefs.isSoundGroupEnabled(group)) {
            if (sound != null && sound != SoundIndex._NONE) {
                final String filename = SoundPlayer.getSoundFilename(sound);
                SoundLoader.play(SoundPlayer.class
                        .getResource("/assets/sound/" + filename));
            }
        }
    }
}