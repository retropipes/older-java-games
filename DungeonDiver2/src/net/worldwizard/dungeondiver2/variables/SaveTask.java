/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.variables;

import java.io.File;
import java.io.FileNotFoundException;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.dungeondiver2.Application;
import net.worldwizard.dungeondiver2.DungeonDiver2;
import net.worldwizard.support.Support;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.ZipUtilities;

public class SaveTask extends Thread {
    // Fields
    private String filename;
    private int savedLevel;

    // Constructors
    public SaveTask(final String file) {
        this.filename = file;
        this.setName("Saved Game File Writer");
    }

    @Override
    public void run() {
        final Application app = DungeonDiver2.getApplication();
        final String sg = "Saved Game";
        // filename check
        final boolean hasExtension = SaveTask.hasExtension(this.filename);
        if (!hasExtension) {
            this.filename += Extension.getGameExtensionWithPeriod();
        }
        final File mapFile = new File(this.filename);
        try {
            // Write variables data
            Support.getVariables().write();
            // Set prefix handler
            app.getVariablesManager().getMap()
                    .setXPrefixHandler(new PrefixHandler());
            // Set suffix handler
            app.getVariablesManager().getMap()
                    .setXSuffixHandler(new SuffixHandler());
            // Save start location
            app.getVariablesManager().getMap().saveStart();
            // Save active level
            this.savedLevel = app.getVariablesManager().getMap()
                    .getActiveLevelNumber();
            // Update start location
            final int currW = app.getGameManager().getPlayerManager()
                    .getPlayerLocationW();
            app.getVariablesManager().getMap().setStartLevel(currW);
            app.getVariablesManager().getMap().switchLevel(currW);
            app.getVariablesManager().getMap().findStart();
            app.getVariablesManager().getMap().writeMapX();
            // Restore active level
            app.getVariablesManager().getMap().switchLevel(this.savedLevel);
            // Restore start location
            app.getVariablesManager().getMap().restoreStart();
            ZipUtilities.zipDirectory(
                    new File(Support.getVariables().getBasePath()), mapFile);
        } catch (final FileNotFoundException fnfe) {
            CommonDialogs.showDialog("Writing the " + sg.toLowerCase()
                    + " file failed, probably due to illegal characters in the file name.");
        } catch (final Exception ex) {
            DungeonDiver2.getErrorLogger().logError(ex);
        }
        DungeonDiver2.getApplication().showMessage(sg + " file saved.");
        app.getVariablesManager().handleDeferredSuccess();
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
