/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.resourcemanagers;

import java.nio.BufferUnderflowException;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.sound.Sound;

class SoundTask extends Thread {
    // Fields
    private final Sound snd;

    // Constructors
    public SoundTask(final Sound sound) {
        this.snd = sound;
        this.setName("Sound Player");
    }

    @Override
    public void run() {
        try {
            this.snd.play();
        } catch (final BufferUnderflowException bue) {
            // Ignore
        } catch (final NullPointerException np) {
            // Ignore
        } catch (final Throwable t) {
            MazeMode.getErrorLogger().logError(t);
        }
    }
}
