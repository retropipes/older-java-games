package com.puttysoftware.rulemazer.maze;

import java.io.File;
import java.io.IOException;

import com.puttysoftware.io.DirectoryUtilities;

public class TempDirCleanup extends Thread {
    @Override
    public void run() {
        final File dirToDelete = new File(Maze.getMazeTempFolder());
        try {
            DirectoryUtilities.removeDirectory(dirToDelete);
        } catch (final IOException io) {
            // Ignore
        }
    }
}
