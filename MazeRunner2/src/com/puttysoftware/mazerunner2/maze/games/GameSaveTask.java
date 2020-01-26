/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.games;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.Extension;
import com.puttysoftware.mazerunner2.maze.Maze;
import com.puttysoftware.mazerunner2.maze.PrefixHandler;
import com.puttysoftware.xio.ZipUtilities;

public class GameSaveTask extends Thread {
    // Fields
    private String filename;

    // Constructors
    public GameSaveTask(final String file) {
        this.filename = file;
        this.setName("Locked File Writer");
    }

    @Override
    public void run() {
        final Application app = MazeRunnerII.getApplication();
        boolean success = true;
        final String sg = "Maze";
        // filename check
        final boolean hasExtension = GameSaveTask.hasExtension(this.filename);
        if (!hasExtension) {
            this.filename += Extension.getGameExtensionWithPeriod();
        }
        final File mazeFile = new File(this.filename);
        final File tempLock = new File(Maze.getMazeTempFolder() + "lock.tmp");
        try {
            // Set prefix handler
            app.getMazeManager().getMaze()
                    .setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            app.getMazeManager().getMaze().setSuffixHandler(null);
            app.getMazeManager().getMaze().writeMaze();
            ZipUtilities.zipDirectory(
                    new File(app.getMazeManager().getMaze().getBasePath()),
                    tempLock);
            // Lock the file
            GameFileManager.save(tempLock, mazeFile);
            final boolean delSuccess = tempLock.delete();
            if (!delSuccess) {
                throw new IOException("Failed to delete temporary file!");
            }
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Writing the locked " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
            success = false;
        } catch (final Exception ex) {
            MazeRunnerII.getErrorLogger().logError(ex);
        }
        MazeRunnerII.getApplication()
                .showMessage("Locked " + sg + " file saved.");
        app.getMazeManager().handleDeferredSuccess(success);
    }

    private static boolean hasExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        if (ext == null) {
            return false;
        } else {
            return true;
        }
    }
}
