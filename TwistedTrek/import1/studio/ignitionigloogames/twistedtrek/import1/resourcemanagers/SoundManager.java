/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers;

import java.nio.BufferUnderflowException;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.sound.Sound;

public class SoundManager {
	@Deprecated
	static Sound getUncachedSound(final String filename) {
		return null;
	}

	public static void play(final String soundName) {
		// Play the sound asynchronously
		try {
			Sound.play(soundName);
		} catch (final BufferUnderflowException bue) {
			// Ignore
		} catch (final NullPointerException np) {
			// Ignore
		} catch (final Throwable t) {
			Import1.debug(t);
		}
	}
}