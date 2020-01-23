/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.resourcemanagers;

import java.nio.BufferUnderflowException;

import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.media.Music;

class MusicTask extends Thread {
    // Fields
    private final Music mus;

    // Constructors
    public MusicTask(final Music music) {
        this.mus = music;
        this.setName("Music Player");
    }

    @Override
    public void run() {
        try {
            this.mus.playLoop();
        } catch (final BufferUnderflowException bue) {
            // Ignore
        } catch (final NullPointerException np) {
            // Ignore
        } catch (final Throwable t) {
            LoopChute.getErrorLogger().logError(t);
        }
    }
}
