package com.puttysoftware.weaselweb.maze;

import java.io.File;

import com.puttysoftware.xio.DirectoryUtilities;

public class TempDirCleanup extends Thread {
    @Override
    public void run() {
        try {
            final File dirToDelete = new File(Maze.getMazeTempFolder());
            DirectoryUtilities.removeDirectory(dirToDelete);
        } catch (final Throwable t) {
            // Ignore
        }
    }
}
