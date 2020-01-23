/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon;

import java.io.File;
import java.io.FileNotFoundException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.xio.ZipUtilities;

public class SaveTask extends Thread {
    // Fields
    private String filename;
    private boolean isSavedGame;
    private int savedLevel;

    // Constructors
    public SaveTask(String file, boolean saved) {
        this.filename = file;
        this.isSavedGame = saved;
        this.setName("File Writer");
    }

    @Override
    public void run() {
        Application app = DungeonDiver4.getApplication();
        boolean success = true;
        final String sg;
        if (this.isSavedGame) {
            sg = "Saved Game";
        } else {
            sg = "Dungeon";
        }
        // filename check
        boolean hasExtension = SaveTask.hasExtension(this.filename);
        if (!hasExtension) {
            if (this.isSavedGame) {
                this.filename += Extension.getSavedGameExtensionWithPeriod();
            } else {
                this.filename += Extension.getDungeonExtensionWithPeriod();
            }
        }
        File dungeonFile = new File(this.filename);
        try {
            // Set prefix handler
            app.getDungeonManager().getDungeon()
                    .setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            if (this.isSavedGame) {
                app.getDungeonManager().getDungeon()
                        .setSuffixHandler(new SuffixHandler());
            } else {
                app.getDungeonManager().getDungeon().setSuffixHandler(null);
            }
            if (this.isSavedGame) {
                // Save start location
                app.getDungeonManager().getDungeon().saveStart();
                // Save active level
                this.savedLevel = app.getDungeonManager().getDungeon()
                        .getActiveLevelNumber();
                // Update start location
                int currW = app.getDungeonManager().getDungeon()
                        .getPlayerLocationW();
                app.getDungeonManager().getDungeon().setStartLevel(currW);
                app.getDungeonManager().getDungeon().switchLevel(currW);
            }
            app.getDungeonManager().getDungeon().writeDungeon();
            if (this.isSavedGame) {
                // Restore active level
                app.getDungeonManager().getDungeon()
                        .switchLevel(this.savedLevel);
                // Restore start location
                app.getDungeonManager().getDungeon().restoreStart();
            }
            ZipUtilities.zipDirectory(new File(app.getDungeonManager()
                    .getDungeon().getBasePath()), dungeonFile);
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs
                    .showDialog("Writing the "
                            + sg.toLowerCase()
                            + " file failed, probably due to illegal characters in the file name.");
            success = false;
        } catch (final Exception ex) {
            DungeonDiver4.getErrorLogger().logError(ex);
        }
        DungeonDiver4.getApplication().showMessage(sg + " file saved.");
        app.getDungeonManager().handleDeferredSuccess(success);
    }

    private static boolean hasExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if ((i > 0) && (i < s.length() - 1)) {
            ext = s.substring(i + 1).toLowerCase();
        }
        if (ext == null) {
            return false;
        } else {
            return true;
        }
    }
}
