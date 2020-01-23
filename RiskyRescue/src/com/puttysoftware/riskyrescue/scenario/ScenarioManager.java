/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.scenario;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fileutils.FilenameChecker;
import com.puttysoftware.riskyrescue.Application;
import com.puttysoftware.riskyrescue.RiskyRescue;
import com.puttysoftware.riskyrescue.Support;
import com.puttysoftware.riskyrescue.map.Map;

public class ScenarioManager {
    // Fields
    private Map gameMap;
    private boolean isDirty;
    private boolean isLoaded;
    private String lastUsedGameFile;

    // Constructors
    public ScenarioManager() {
        this.isDirty = false;
        this.isLoaded = false;
        this.lastUsedGameFile = "";
    }

    // Methods
    public Map getMap() {
        return this.gameMap;
    }

    public void setMap(final Map newMap) {
        this.gameMap = newMap;
    }

    void handleDeferredSuccess() {
        this.setDirty(false);
        this.isLoaded = true;
        RiskyRescue.getApplication().getGameManager().stateChanged();
        RiskyRescue.getApplication().getMenuManager().checkFlags();
    }

    public static int showSaveDialog() {
        String type, source;
        final Application app = RiskyRescue.getApplication();
        final int mode = app.getMode();
        if (mode == Application.STATUS_GAME) {
            type = "game";
            source = RiskyRescue.getProgramName();
        } else {
            // Not in the game, so abort
            return JOptionPane.NO_OPTION;
        }
        return CommonDialogs.showYNCConfirmDialog(
                "Do you want to save your " + type + "?", source);
    }

    public boolean getLoaded() {
        return this.isLoaded;
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        final Application app = RiskyRescue.getApplication();
        this.isDirty = newDirty;
        final JFrame frame = app.getOutputFrame();
        if (frame != null) {
            frame.getRootPane().putClientProperty("Window.documentModified",
                    Boolean.valueOf(this.isDirty));
        }
        app.getMenuManager().checkFlags();
    }

    public void loadSavedGame() {
        String title;
        if (Support.inDebugMode()) {
            title = "Open Saved Game (DEBUG)";
        } else {
            title = "Open Saved Game";
        }
        int status;
        boolean saved = true;
        String filename;
        if (this.getDirty()) {
            status = ScenarioManager.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = this.saveGame();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                this.setDirty(false);
            }
        }
        if (saved) {
            final String[] saveList = SaveRegistration.getSaveList();
            if (saveList != null && saveList.length > 0) {
                final String save = CommonDialogs.showInputDialog(
                        "Open Which Saved Game?", title, saveList, saveList[0]);
                if (save != null) {
                    final File file = new File(
                            SaveRegistration.getSaveBasePath() + File.separator
                                    + save
                                    + Extension.getGameExtensionWithPeriod());
                    filename = file.getAbsolutePath();
                    this.lastUsedGameFile = filename;
                    ScenarioManager.loadFile(filename);
                }
            } else {
                CommonDialogs.showDialog("No Saved Games Found!");
            }
        }
    }

    private static void loadFile(final String filename) {
        if (!FilenameChecker
                .isFilenameOK(ScenarioManager.getNameWithoutExtension(
                        ScenarioManager.getFileNameOnly(filename)))) {
            CommonDialogs.showErrorDialog(
                    "The file you selected contains illegal characters in its\n"
                            + "name. These characters are not allowed: /?<>\\:|\"\n"
                            + "Files named con, nul, or prn are illegal, as are files\n"
                            + "named com1 through com9 and lpt1 through lpt9.",
                    "Open Saved Game");
        } else {
            final LoadTask xlt = new LoadTask(filename);
            xlt.start();
        }
    }

    public boolean saveGame() {
        final Application app = RiskyRescue.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            if (this.lastUsedGameFile != null
                    && !this.lastUsedGameFile.equals("")) {
                final String extension = ScenarioManager
                        .getExtension(this.lastUsedGameFile);
                if (extension != null) {
                    if (!extension.equals(Extension.getGameExtension())) {
                        this.lastUsedGameFile = ScenarioManager
                                .getNameWithoutExtension(this.lastUsedGameFile)
                                + Extension.getGameExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedGameFile += Extension
                            .getGameExtensionWithPeriod();
                }
                ScenarioManager.saveFile(this.lastUsedGameFile);
            } else {
                return this.saveGameAs();
            }
        }
        return false;
    }

    public boolean saveGameAs() {
        String title;
        if (Support.inDebugMode()) {
            title = "Save Game (DEBUG)";
        } else {
            title = "Save Game";
        }
        final Application app = RiskyRescue.getApplication();
        String filename = "\\";
        String extension;
        while (!FilenameChecker.isFilenameOK(filename)) {
            filename = CommonDialogs.showTextInputDialog("Saved Game Name:",
                    title);
            if (filename != null) {
                if (!FilenameChecker.isFilenameOK(filename)) {
                    CommonDialogs.showErrorDialog(
                            "The file name you entered contains illegal characters.\n"
                                    + "These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            title);
                } else {
                    SaveRegistration.autoregisterSave(filename);
                    final String dir = SaveRegistration.getSaveBasePath()
                            + File.separator;
                    extension = ScenarioManager.getExtension(filename);
                    if (app.getMode() == Application.STATUS_GAME) {
                        if (extension != null) {
                            if (!extension
                                    .equals(Extension.getGameExtension())) {
                                filename = ScenarioManager
                                        .getNameWithoutExtension(filename)
                                        + Extension
                                                .getGameExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension.getGameExtensionWithPeriod();
                        }
                        this.lastUsedGameFile = dir + filename;
                        ScenarioManager.saveFile(dir + filename);
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
        RiskyRescue.getApplication().showMessage("Saving " + sg + " file...");
        final SaveTask xst = new SaveTask(filename);
        xst.start();
    }

    private static String getExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
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
