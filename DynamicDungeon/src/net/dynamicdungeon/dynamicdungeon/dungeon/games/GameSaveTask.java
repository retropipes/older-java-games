/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.games;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.Extension;
import net.dynamicdungeon.dynamicdungeon.dungeon.PrefixHandler;
import net.dynamicdungeon.dynamicdungeon.dungeon.SuffixHandler;
import net.dynamicdungeon.fileutils.ZipUtilities;

public class GameSaveTask extends Thread {
    // Fields
    private String filename;

    // Constructors
    public GameSaveTask(final String file) {
        this.filename = file;
        this.setName("Game Writer");
    }

    @Override
    public void run() {
        boolean success = true;
        final String sg = "Game";
        try {
            final Application app = DynamicDungeon.getApplication();
            // filename check
            final boolean hasExtension = GameSaveTask
                    .hasExtension(this.filename);
            if (!hasExtension) {
                this.filename += Extension.getGameExtensionWithPeriod();
            }
            final File mazeFile = new File(this.filename);
            final File tempLock = new File(
                    Dungeon.getDungeonTempFolder() + "lock.tmp");
            // Set prefix handler
            app.getDungeonManager().getDungeon()
                    .setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            app.getDungeonManager().getDungeon()
                    .setSuffixHandler(new SuffixHandler());
            app.getDungeonManager().getDungeon().writeDungeon();
            ZipUtilities.zipDirectory(
                    new File(
                            app.getDungeonManager().getDungeon().getBasePath()),
                    tempLock);
            // Lock the file
            GameFileManager.save(tempLock, mazeFile);
            final boolean delSuccess = tempLock.delete();
            if (!delSuccess) {
                throw new IOException("Failed to delete temporary file!");
            }
            app.showMessage(sg + " saved.");
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Writing the " + sg.toLowerCase()
                    + " failed, probably due to illegal characters in the file name.");
            success = false;
        } catch (final Exception ex) {
            DynamicDungeon.getErrorLogger().logError(ex);
        }
        DynamicDungeon.getApplication().getDungeonManager()
                .handleDeferredSuccess(success, false, null);
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
