/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.world;

import java.io.File;
import java.io.FileNotFoundException;

import net.worldwizard.io.DirectoryUtilities;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;

public class SaveTask extends Thread {
    // Fields
    private String filename;
    private final boolean isSavedGame;
    private int savedLevel;

    // Constructors
    public SaveTask(final String file, final boolean saved) {
        this.filename = file;
        this.isSavedGame = saved;
        this.setName("File Writer");
    }

    @Override
    public void run() {
        final Application app = Worldz.getApplication();
        boolean success = true;
        final String sg;
        if (this.isSavedGame) {
            sg = "Saved Game";
        } else {
            sg = "World";
        }
        // filename check
        final boolean hasExtension = SaveTask.hasExtension(this.filename);
        if (!hasExtension) {
            if (this.isSavedGame) {
                this.filename += Extension.getGameExtensionWithPeriod();
            } else {
                this.filename += Extension.getWorldExtensionWithPeriod();
            }
        }
        final File worldFile = new File(this.filename);
        try {
            // Set prefix handler
            app.getWorldManager().getWorld()
                    .setPrefixHandler(new PrefixHandler());
            // Set suffix handler
            if (this.isSavedGame) {
                app.getWorldManager().getWorld()
                        .setSuffixHandler(new SuffixHandler());
            } else {
                app.getWorldManager().getWorld().setSuffixHandler(null);
            }
            if (this.isSavedGame) {
                // Save start location
                app.getWorldManager().getWorld().saveStart();
                // Save active level
                this.savedLevel = app.getWorldManager().getWorld()
                        .getActiveLevelNumber();
                // Update start location
                final int currW = app.getGameManager().getPlayerManager()
                        .getPlayerLocationW();
                app.getWorldManager().getWorld().setStartLevel(currW);
                app.getWorldManager().getWorld().switchLevel(currW);
                app.getWorldManager().getWorld().findStart();
            }
            app.getWorldManager().getWorld().writeWorld();
            if (this.isSavedGame) {
                // Restore active level
                app.getWorldManager().getWorld().switchLevel(this.savedLevel);
                // Restore start location
                app.getWorldManager().getWorld().restoreStart();
            }
            DirectoryUtilities.zipDirectory(new File(app.getWorldManager()
                    .getWorld().getBasePath()), worldFile);
        } catch (final FileNotFoundException fnfe) {
            Messager.showDialog("Writing the "
                    + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
            success = false;
        } catch (final Exception ex) {
            Worldz.getDebug().debug(ex);
        }
        Messager.showMessage(sg + " file saved.");
        app.getWorldManager().handleDeferredSuccess(success);
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
