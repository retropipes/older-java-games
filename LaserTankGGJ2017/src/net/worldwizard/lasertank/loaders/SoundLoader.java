package net.worldwizard.lasertank.loaders;

import java.net.URL;

import net.worldwizard.lasertank.assets.GameSound;

public class SoundLoader {
    // Private constructor
    private SoundLoader() {
	// Do nothing
    }

    public static GameSound loadSound(final String soundName) {
	final URL u = SoundLoader.class.getResource("/net/worldwizard/lasertank/assets/sounds/" + soundName + ".wav");
	return new GameSound(u);
    }
}
