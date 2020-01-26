/*  DungeonDiver4: A Names Editor
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.scenario;

import java.io.File;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.datamanagers.NamesDataManager;
import com.puttysoftware.dungeondiver4.names.NamesManager;

public class NamesFileManager {
    // Fields
    private String[][] gameNames;

    // Constructors
    public NamesFileManager() {
        // Do nothing
    }

    // Methods
    public String[][] getNames() {
        return this.gameNames;
    }

    public void setNames(final String[][] newNames) {
        this.gameNames = newNames;
    }

    public void loadNames() {
        NamesFileManager.loadFile();
    }

    private static void loadFile() {
        try {
            final Application app = DungeonDiver4.getApplication();
            app.getScenarioManager().getNamesFileManager()
                    .setNames(NamesManager.getNamesCache());
            // Final cleanup
            app.getNamesEditor().objectChanged();
        } catch (final Exception ex) {
            DungeonDiver4.getErrorLogger().logError(ex);
        }
    }

    public void saveNames() {
        String lastUsedNamesFile = "";
        if (this.gameNames != null) {
            lastUsedNamesFile = NamesDataManager.getNamesOverrideFile()
                    .toString();
        } else {
            lastUsedNamesFile = null;
        }
        if (lastUsedNamesFile != null) {
            final File parent = new File(lastUsedNamesFile).getParentFile();
            if (!parent.exists()) {
                final boolean success = parent.mkdirs();
                if (!success) {
                    DungeonDiver4.getErrorLogger().logError(
                            new IOException("Creating names folder failed!"));
                }
            }
            final String[] data = NamesManager.convertCacheToArray();
            NamesFileManager.saveFile(lastUsedNamesFile, data);
        } else {
            CommonDialogs.showErrorDialog("No Names Opened!", "Names Editor");
        }
    }

    private static void saveFile(final String filename, final String[] data) {
        final NamesSaveTask xst = new NamesSaveTask(filename, data);
        xst.start();
    }
}
