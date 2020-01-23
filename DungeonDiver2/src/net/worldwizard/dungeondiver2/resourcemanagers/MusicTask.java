/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.resourcemanagers;

import java.nio.BufferUnderflowException;

import net.worldwizard.dungeondiver2.DungeonDiverII;
import net.worldwizard.music.Music;

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
            DungeonDiverII.getErrorLogger().logError(t);
        }
    }
}
