package com.puttysoftware.fantastlereboot.loaders;

import java.io.IOException;
import java.util.Properties;

import com.puttysoftware.diane.loaders.SoundLoader;
import com.puttysoftware.fantastlereboot.FantastleReboot;
import com.puttysoftware.fantastlereboot.assets.SoundGroup;
import com.puttysoftware.fantastlereboot.assets.SoundIndex;
import com.puttysoftware.fantastlereboot.gui.Prefs;
import com.puttysoftware.randomrange.RandomRange;

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
      try {
        SoundPlayer.fileExtensions = new Properties();
        SoundPlayer.fileExtensions.load(SoundPlayer.class.getResourceAsStream(
            "/assets/data/extensions/extensions.properties"));
      } catch (final IOException e) {
        FantastleReboot.exception(e);
      }
    }
    final String soundExt = SoundPlayer.fileExtensions.getProperty("sounds");
    if (sound == SoundIndex.WALK || sound == SoundIndex.WALK_2
        || sound == SoundIndex.WALK_3 || sound == SoundIndex.WALK_4
        || sound == SoundIndex.WALK_5 || sound == SoundIndex.WALK_6) {
      // Pick a random walk sound and play it
      final int base = SoundIndex.WALK.ordinal();
      final int offset = RandomRange.generate(0, 5);
      return SoundPlayer.allFilenames[base + offset] + soundExt;
    }
    return SoundPlayer.allFilenames[sound.ordinal()] + soundExt;
  }

  public static void playSound(final SoundIndex sound, final SoundGroup group) {
    if (Prefs.isSoundGroupEnabled(group)) {
      if (sound != null && sound != SoundIndex._NONE) {
        final String filename = SoundPlayer.getSoundFilename(sound);
        SoundLoader
            .play(SoundPlayer.class.getResource("/assets/sounds/" + filename));
      }
    }
  }
}