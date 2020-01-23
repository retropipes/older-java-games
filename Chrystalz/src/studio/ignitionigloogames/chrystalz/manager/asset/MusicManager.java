/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import java.net.URL;
import java.nio.BufferUnderflowException;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.manager.file.Extension;
import studio.ignitionigloogames.common.oggplayer.OggPlayer;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;
    private static OggPlayer CURRENT_MUSIC;

    private static OggPlayer getMusic(final String filename) {
        final URL modFile = MusicManager.LOAD_CLASS
                .getResource(MusicManager.LOAD_PATH + filename
                        + Extension.getMusicExtensionWithPeriod());
        return new OggPlayer(modFile);
    }

    public static void playMusic(final int musicID) {
        MusicManager.CURRENT_MUSIC = MusicManager
                .getMusic(MusicConstants.getMusicName(musicID));
        if (MusicManager.CURRENT_MUSIC != null) {
            // Play the music
            MusicManager.CURRENT_MUSIC.playLoop();
        }
    }

    public static void stopMusic() {
        if (MusicManager.CURRENT_MUSIC != null) {
            // Stop the music
            try {
                MusicManager.CURRENT_MUSIC.stopLoop();
            } catch (final BufferUnderflowException bue) {
                // Ignore
            } catch (final Throwable t) {
                Chrystalz.getErrorLogger().logError(t);
            }
        }
    }

    public static boolean isMusicPlaying() {
        if (MusicManager.CURRENT_MUSIC != null) {
            return MusicManager.CURRENT_MUSIC.isAlive();
        }
        return false;
    }
}