package com.puttysoftware.mazer5d.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.puttysoftware.audio.mod.MicroMod;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.MusicGroup;
import com.puttysoftware.mazer5d.assets.MusicIndex;
import com.puttysoftware.mazer5d.prefs.Prefs;
import com.puttysoftware.randomrange.RandomRange;

public class MusicPlayer {
    private MusicPlayer() {
        // Do nothing
    }

    private static String[] allFilenames;
    private static Properties fileExtensions;
    private static MicroMod MUSIC;

    private static String getMusicFilename(final MusicIndex music) {
        if (MusicPlayer.allFilenames == null
                && MusicPlayer.fileExtensions == null) {
            MusicPlayer.allFilenames = DataLoader.loadMusicData();
            try (final InputStream stream = MusicPlayer.class
                    .getResourceAsStream(
                            "/assets/data/extensions/extensions.properties")) {
                MusicPlayer.fileExtensions = new Properties();
                MusicPlayer.fileExtensions.load(stream);
            } catch (final IOException e) {
                Mazer5D.logError(e);
            }
        }
        final String musicExt = MusicPlayer.fileExtensions.getProperty("music");
        if (music == MusicIndex.DUNGEON || music == MusicIndex._DUNGEON_RANDOM_2
                || music == MusicIndex._DUNGEON_RANDOM_3
                || music == MusicIndex._DUNGEON_RANDOM_4) {
            // Pick random dungeon music and play it
            final int base = MusicIndex.DUNGEON.ordinal();
            final int offset = RandomRange.generate(0, 3);
            return MusicPlayer.allFilenames[base + offset] + musicExt;
        }
        return MusicPlayer.allFilenames[music.ordinal()] + musicExt;
    }

    public static void playMusic(final MusicIndex music,
            final MusicGroup group) {
        if (Prefs.isMusicGroupEnabled(group)) {
            if (music != null && music != MusicIndex._NONE) {
                final String filename = MusicPlayer.getMusicFilename(music);
                if (MusicPlayer.MUSIC != null
                        && MusicPlayer.MUSIC.isPlaying()) {
                    MusicPlayer.MUSIC.stopLoop();
                }
                try {
                    MusicPlayer.MUSIC = MicroMod.loadResource(MusicPlayer.class
                            .getResource("/assets/music/" + filename));
                    MusicPlayer.MUSIC.play();
                } catch (IOException e) {
                    Mazer5D.logError(e);
                }
            }
        }
    }
}