/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.resourcemanagers;

import java.nio.BufferUnderflowException;

import net.worldwizard.audio.Music;
import net.worldwizard.worldz.Worldz;

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
            this.mus.play();
        } catch (final BufferUnderflowException bue) {
            // Ignore
        } catch (final NullPointerException np) {
            // Ignore
        } catch (final Throwable t) {
            Worldz.getDebug().debug(t);
        }
    }
}
