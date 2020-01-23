/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.variables;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.dungeondiver2.Application;
import net.worldwizard.dungeondiver2.DungeonDiverII;
import net.worldwizard.dungeondiver2.prefs.PreferencesManager;
import net.worldwizard.support.map.Map;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.FilenameChecker;

public class VariablesManager {
    // Fields
    private Map gameMap;
    private boolean isDirty;
    private String lastUsedVariablesFile;
    private String lastUsedGameFile;

    // Constructors
    public VariablesManager() {
        this.isDirty = false;
        this.lastUsedVariablesFile = "";
        this.lastUsedGameFile = "";
    }

    // Methods
    public Map getMap() {
        return this.gameMap;
    }

    public void setMap(final Map newMap) {
        this.gameMap = newMap;
    }

    public void handleDeferredSuccess() {
        this.setDirty(false);
        DungeonDiverII.getApplication().getGameManager().stateChanged();
        DungeonDiverII.getApplication().getMenuManager().checkFlags();
    }

    public int showSaveDialog() {
        String type, source;
        final Application app = DungeonDiverII.getApplication();
        final int mode = app.getMode();
        if (mode == Application.STATUS_GAME) {
            type = "game";
            source = DungeonDiverII.getProgramName();
        } else {
            // Not in the game, so abort
            return JOptionPane.NO_OPTION;
        }
        int status = JOptionPane.DEFAULT_OPTION;
        status = CommonDialogs.showYNCConfirmDialog("Do you want to save your "
                + type + "?", source);
        return status;
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        final Application app = DungeonDiverII.getApplication();
        this.isDirty = newDirty;
        final JFrame frame = app.getOutputFrame();
        if (frame != null) {
            frame.getRootPane().putClientProperty("Window.documentModified",
                    Boolean.valueOf(newDirty));
        }
        app.getMenuManager().checkFlags();
    }

    public void clearLastUsedFilenames() {
        this.lastUsedVariablesFile = "";
        this.lastUsedGameFile = "";
    }

    public String getLastUsedVariables() {
        return this.lastUsedVariablesFile;
    }

    public String getLastUsedGame() {
        return this.lastUsedGameFile;
    }

    public void setLastUsedVariables(final String newFile) {
        this.lastUsedVariablesFile = newFile;
    }

    public void setLastUsedGame(final String newFile) {
        this.lastUsedGameFile = newFile;
    }

    public void loadFromOSHandler(final String infilename) { // NO_UCD
        String extension;
        final File file = new File(infilename);
        final String filename = file.getAbsolutePath();
        extension = VariablesManager.getExtension(file);
        if (extension.equals(Extension.getVariablesExtension())) {
            this.lastUsedVariablesFile = filename;
            VariablesManager.loadFile(filename, false);
        } else if (extension.equals(Extension.getGameExtension())) {
            this.lastUsedGameFile = filename;
            VariablesManager.loadFile(filename, true);
        }
    }

    public boolean loadSavedGame() {
        final Application app = DungeonDiverII.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = PreferencesManager.getLastDirOpen();
        final FileDialog fc = new FileDialog(app.getOutputFrame(),
                "Load Saved Game", FileDialog.LOAD);
        fc.setDirectory(lastOpen);
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = this.saveGame();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                this.setDirty(false);
            }
        }
        if (saved) {
            fc.setVisible(true);
            if (fc.getFile() != null && fc.getDirectory() != null) {
                final File file = new File(fc.getDirectory() + fc.getFile());
                PreferencesManager.setLastDirOpen(fc.getDirectory());
                filename = file.getAbsolutePath();
                extension = VariablesManager.getExtension(file);
                if (extension.equals(Extension.getGameExtension())) {
                    this.lastUsedGameFile = filename;
                    VariablesManager.loadFile(filename, true);
                } else {
                    CommonDialogs
                            .showDialog("You opened something other than a saved game file. Select a saved game file, and try again.");
                }
            }
        }
        return false;
    }

    private static void loadFile(final String filename,
            final boolean isSavedGame) {
        if (!FilenameChecker.isFilenameOK(VariablesManager
                .getNameWithoutExtension(VariablesManager
                        .getFileNameOnly(filename)))) {
            CommonDialogs
                    .showErrorDialog(
                            "The file you selected contains illegal characters in its\n"
                                    + "name. These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            "Load Variables or Saved Game");
        } else {
            final LoadTask xlt = new LoadTask(filename, isSavedGame);
            xlt.start();
        }
    }

    public boolean saveGame() {
        final Application app = DungeonDiverII.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            if (this.lastUsedGameFile != null
                    && !this.lastUsedGameFile.equals("")) {
                final String extension = VariablesManager
                        .getExtension(this.lastUsedGameFile);
                if (extension != null) {
                    if (!extension.equals(Extension.getGameExtension())) {
                        this.lastUsedGameFile = VariablesManager
                                .getNameWithoutExtension(this.lastUsedGameFile)
                                + Extension.getGameExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedGameFile += Extension
                            .getGameExtensionWithPeriod();
                }
                VariablesManager.saveFile(this.lastUsedGameFile);
            } else {
                return this.saveGameAs();
            }
        }
        return false;
    }

    public boolean saveGameAs() {
        final Application app = DungeonDiverII.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        final String lastSave = PreferencesManager.getLastDirSave();
        final FileDialog fc = new FileDialog(app.getOutputFrame(), "Save Game",
                FileDialog.SAVE);
        fc.setDirectory(lastSave);
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            fc.setVisible(true);
            if (fc.getFile() != null && fc.getDirectory() != null) {
                final File file = new File(fc.getDirectory() + fc.getFile());
                extension = VariablesManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = fc.getDirectory();
                fileOnly = filename.substring(dirOnly.length() + 1);
                if (!FilenameChecker.isFilenameOK(fileOnly)) {
                    CommonDialogs
                            .showErrorDialog(
                                    "The file name you entered contains illegal characters.\n"
                                            + "These characters are not allowed: /?<>\\:|\"\n"
                                            + "Files named con, nul, or prn are illegal, as are files\n"
                                            + "named com1 through com9 and lpt1 through lpt9.",
                                    "Save");
                } else {
                    PreferencesManager.setLastDirSave(fc.getDirectory());
                    if (app.getMode() == Application.STATUS_GAME) {
                        if (extension != null) {
                            if (!extension.equals(Extension.getGameExtension())) {
                                filename = VariablesManager
                                        .getNameWithoutExtension(file)
                                        + Extension
                                                .getGameExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension.getGameExtensionWithPeriod();
                        }
                        this.lastUsedGameFile = filename;
                        VariablesManager.saveFile(filename);
                    }
                }
            } else {
                break;
            }
        }
        return false;
    }

    private static void saveFile(final String filename) {
        final String sg = "Saved Game";
        DungeonDiverII.getApplication()
                .showMessage("Saving " + sg + " file...");
        final SaveTask xst = new SaveTask(filename);
        xst.start();
    }

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getNameWithoutExtension(final File f) {
        String ext = null;
        final String s = f.getAbsolutePath();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getNameWithoutExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getFileNameOnly(final String s) {
        String fno = null;
        final int i = s.lastIndexOf(File.separatorChar);
        if (i > 0 && i < s.length() - 1) {
            fno = s.substring(i + 1);
        } else {
            fno = s;
        }
        return fno;
    }
}
