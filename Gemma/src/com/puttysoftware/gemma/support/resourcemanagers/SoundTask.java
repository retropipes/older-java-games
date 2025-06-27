package com.puttysoftware.gemma.support.resourcemanagers;

import java.nio.BufferUnderflowException;

import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.media.Sound;

public class SoundTask extends Thread {
    // Fields
    private final Sound snd;

    // Constructor
    public SoundTask(final Sound newSound) {
        this.snd = newSound;
    }

    @Override
    public void run() {
        if (this.snd != null) {
            // Play the sound
            try {
                this.snd.play();
            } catch (final BufferUnderflowException bue) {
                // Ignore
            } catch (final NullPointerException np) {
                // Ignore
            } catch (final Throwable t) {
                Gemma.getErrorLogger().logError(t);
            }
        }
    }
}
