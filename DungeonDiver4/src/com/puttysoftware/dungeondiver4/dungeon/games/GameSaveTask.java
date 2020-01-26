/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.games;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.Extension;
import com.puttysoftware.dungeondiver4.dungeon.PrefixHandler;
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
        final Application app = DungeonDiver4.getApplication();
        boolean success = true;
        final String sg = "Dungeon";
        // filename check
        final boolean hasExtension = GameSaveTask.hasExtension(this.filename);
        if (!hasExtension) {
            this.filename += Extension.getGameExtensionWithPeriod();
        }
        final File dungeonFile = new File(this.filename);
        final File tempLock = new File(
                Dungeon.getDungeonTempFolder() + "lock.tmp");
        try {
            // Set prefix handler
            app.getDungeonManager().getDungeon()
                    .setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            app.getDungeonManager().getDungeon().setSuffixHandler(null);
            app.getDungeonManager().getDungeon().writeDungeon();
            ZipUtilities.zipDirectory(
                    new File(
                            app.getDungeonManager().getDungeon().getBasePath()),
                    tempLock);
            // Lock the file
            GameFileManager.save(tempLock, dungeonFile);
            final boolean delSuccess = tempLock.delete();
            if (!delSuccess) {
                throw new IOException("Failed to delete temporary file!");
            }
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Writing the locked " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
            success = false;
        } catch (final Exception ex) {
            DungeonDiver4.getErrorLogger().logError(ex);
        }
        DungeonDiver4.getApplication()
                .showMessage("Locked " + sg + " file saved.");
        app.getDungeonManager().handleDeferredSuccess(success);
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
