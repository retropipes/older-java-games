package net.worldwizard.worldz.world;

import java.io.File;
import java.io.IOException;

import net.worldwizard.io.DirectoryUtilities;

public class TempDirCleanup extends Thread {
    @Override
    public void run() {
        final File dirToDelete = new File(System.getProperty("java.io.tmpdir")
                + File.separator + "Worldz");
        try {
            DirectoryUtilities.removeDirectory(dirToDelete);
        } catch (final IOException io) {
            // Ignore
        }
    }
}
