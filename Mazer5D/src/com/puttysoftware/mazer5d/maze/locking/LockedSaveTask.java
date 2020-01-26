/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.maze.locking;

import java.io.File;
import java.io.FileNotFoundException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fileutils.ZipUtilities;
import com.puttysoftware.mazer5d.Application;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.maze.Extension;
import com.puttysoftware.mazer5d.maze.Maze;
import com.puttysoftware.mazer5d.maze.xml.XMLPrefixHandler;

public class LockedSaveTask extends Thread {
    // Fields
    private String filename;

    // Constructors
    public LockedSaveTask(final String file) {
        this.filename = file;
        this.setName("Locked File Writer");
    }

    @Override
    public void run() {
        final Application app = Mazer5D.getApplication();
        boolean success = true;
        final String sg = "Maze";
        // filename check
        final boolean hasExtension = LockedSaveTask.hasExtension(this.filename);
        if (!hasExtension) {
            this.filename += Extension.getLockedMazeExtensionWithPeriod();
        }
        final File mazeFile = new File(this.filename);
        final File tempLock = new File(Maze.getMazeTempFolder() + "lock.tmp");
        try {
            // Set prefix handler
            app.getMazeManager().getMaze()
                    .setXMLPrefixHandler(new XMLPrefixHandler());
            // Set suffix handler
            app.getMazeManager().getMaze().setXMLSuffixHandler(null);
            app.getMazeManager().getMaze().writeMazeXML();
            ZipUtilities.zipDirectory(
                    new File(app.getMazeManager().getMaze().getBasePath()),
                    tempLock);
            // Lock the file
            LockedWrapper.lock(tempLock, mazeFile);
            tempLock.delete();
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Writing the locked " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
            success = false;
        } catch (final Exception ex) {
            Mazer5D.getErrorLogger().logError(ex);
        }
        Mazer5D.getApplication().showMessage("Locked " + sg + " file saved.");
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
