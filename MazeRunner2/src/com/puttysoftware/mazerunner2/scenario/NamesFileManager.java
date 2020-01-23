/*  MazeRunnerII: A Names Editor
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.scenario;

import java.io.File;
import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.datamanagers.NamesDataManager;
import com.puttysoftware.mazerunner2.names.NamesManager;

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

    public void setNames(String[][] newNames) {
        this.gameNames = newNames;
    }

    public void loadNames() {
        NamesFileManager.loadFile();
    }

    private static void loadFile() {
        try {
            Application app = MazeRunnerII.getApplication();
            app.getScenarioManager().getNamesFileManager()
                    .setNames(NamesManager.getNamesCache());
            // Final cleanup
            app.getNamesEditor().objectChanged();
        } catch (final Exception ex) {
            MazeRunnerII.getErrorLogger().logError(ex);
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
            File parent = new File(lastUsedNamesFile).getParentFile();
            if (!parent.exists()) {
                boolean success = parent.mkdirs();
                if (!success) {
                    MazeRunnerII.getErrorLogger().logError(
                            new IOException("Creating names folder failed!"));
                }
            }
            String[] data = NamesManager.convertCacheToArray();
            NamesFileManager.saveFile(lastUsedNamesFile, data);
        } else {
            CommonDialogs.showErrorDialog("No Names Opened!", "Names Editor");
        }
    }

    private static void saveFile(String filename, String[] data) {
        NamesSaveTask xst = new NamesSaveTask(filename, data);
        xst.start();
    }
}
