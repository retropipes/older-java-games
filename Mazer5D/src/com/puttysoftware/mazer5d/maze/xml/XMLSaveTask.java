/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.maze.xml;

import java.io.File;
import java.io.IOException;

import com.puttysoftware.fileutils.ZipUtilities;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.gui.Application;

public class XMLSaveTask extends Thread {
    // Fields
    private String filename;
    private final boolean isSavedGame;
    private int savedLevel;

    // Constructors
    public XMLSaveTask(final String file, final boolean saved) {
        this.filename = file;
        this.isSavedGame = saved;
        this.setName("XML File Writer");
    }

    @Override
    public void run() {
        final Application app = Mazer5D.getApplication();
        boolean success = true;
        final String sg;
        if (this.isSavedGame) {
            sg = "Saved Game";
        } else {
            sg = "Maze";
        }
        // filename check
        final boolean hasExtension = XMLSaveTask.hasExtension(this.filename);
        if (!hasExtension) {
            if (this.isSavedGame) {
                this.filename += XMLExtension.getXMLGameExtensionWithPeriod();
            } else {
                this.filename += XMLExtension.getXMLMazeExtensionWithPeriod();
            }
        }
        final File mazeFile = new File(this.filename);
        try {
            // Set prefix handler
            app.getMazeManager().getMaze()
                    .setXMLPrefixHandler(new XMLPrefixHandler());
            // Set suffix handler
            if (this.isSavedGame) {
                app.getMazeManager().getMaze()
                        .setXMLSuffixHandler(new XMLSuffixHandler());
            } else {
                app.getMazeManager().getMaze().setXMLSuffixHandler(null);
            }
            if (this.isSavedGame) {
                // Save start location
                app.getMazeManager().getMaze().saveStart();
                // Save active level
                this.savedLevel = app.getMazeManager().getMaze()
                        .getActiveLevelNumber();
                // Update start location
                final int currW = app.getGameManager().getPlayerManager()
                        .getPlayerLocationW();
                app.getMazeManager().getMaze().setStartLevel(currW);
                app.getMazeManager().getMaze().switchLevel(currW);
                app.getMazeManager().getMaze().findStart();
            }
            app.getMazeManager().getMaze().writeMazeXML();
            if (this.isSavedGame) {
                // Restore active level
                app.getMazeManager().getMaze().switchLevel(this.savedLevel);
                // Restore start location
                app.getMazeManager().getMaze().restoreStart();
            }
            ZipUtilities.zipDirectory(
                    new File(app.getMazeManager().getMaze().getBasePath()),
                    mazeFile);
        } catch (final IOException e) {
            Mazer5D.logError(e);
        }
        Mazer5D.getApplication().showMessage(sg + " file saved.");
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
