/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.maze;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.puttysoftware.mazemode.Application;
import com.puttysoftware.mazemode.CommonDialogs;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.generic.MazeObject;
import com.puttysoftware.mazemode.prefs.PreferencesManager;

public class MazeManager {
    // Fields
    private Maze gameMaze;
    private boolean loaded, isDirty;
    private String scoresFileName;
    private String lastUsedMazeFile;
    private String lastUsedGameFile;

    // Constructors
    public MazeManager() {
        this.loaded = false;
        this.isDirty = false;
        this.lastUsedMazeFile = "";
        this.lastUsedGameFile = "";
        this.scoresFileName = "";
    }

    // Methods
    public Maze getMaze() {
        return this.gameMaze;
    }

    public void setMaze(final Maze newMaze) {
        this.gameMaze = newMaze;
    }

    public void handleDeferredSuccess(final boolean value) {
        if (value) {
            this.setLoaded(true);
        }
        this.setDirty(false);
        MazeMode.getApplication().getGameManager().stateChanged();
        MazeMode.getApplication().getEditor().mazeChanged();
        MazeMode.getApplication().getMenuManager().checkFlags();
    }

    public MazeObject getMazeObject(final int x, final int y, final int z,
            final int e) {
        try {
            return this.gameMaze.getCell(x, y, z, e);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return null;
        }
    }

    public int showSaveDialog() {
        String type, source;
        final Application app = MazeMode.getApplication();
        final int mode = app.getMode();
        if (mode == Application.STATUS_EDITOR) {
            type = "maze";
            source = "Editor";
        } else if (mode == Application.STATUS_GAME) {
            type = "game";
            source = MazeMode.getProgramName();
        } else {
            // Not in the game or editor, so abort
            return JOptionPane.NO_OPTION;
        }
        int status = JOptionPane.DEFAULT_OPTION;
        status = CommonDialogs.showYNCConfirmDialog(
                "Do you want to save your " + type + "?", source);
        return status;
    }

    public boolean getLoaded() {
        return this.loaded;
    }

    public void setLoaded(final boolean status) {
        final Application app = MazeMode.getApplication();
        this.loaded = status;
        app.getMenuManager().checkFlags();
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        final Application app = MazeMode.getApplication();
        this.isDirty = newDirty;
        final JFrame frame = app.getOutputFrame();
        if (frame != null) {
            frame.getRootPane().putClientProperty("Window.documentModified",
                    Boolean.valueOf(newDirty));
        }
        app.getMenuManager().checkFlags();
    }

    public void clearLastUsedFilenames() {
        this.lastUsedMazeFile = "";
        this.lastUsedGameFile = "";
    }

    public String getLastUsedMaze() {
        return this.lastUsedMazeFile;
    }

    public String getLastUsedGame() {
        return this.lastUsedGameFile;
    }

    public void setLastUsedMaze(final String newFile) {
        this.lastUsedMazeFile = newFile;
    }

    public void setLastUsedGame(final String newFile) {
        this.lastUsedGameFile = newFile;
    }

    public String getScoresFileName() {
        return this.scoresFileName;
    }

    public void setScoresFileName(final String filename) {
        this.scoresFileName = filename;
    }

    public void loadFromOSHandler(final String infilename) { // NO_UCD
        final Application app = MazeMode.getApplication();
        if (!this.loaded) {
            String extension;
            final File file = new File(infilename);
            final String filename = file.getAbsolutePath();
            extension = MazeManager.getExtension(file);
            app.getGameManager().resetObjectInventory();
            if (extension.equals(Extension.getMazeExtension())) {
                this.lastUsedMazeFile = filename;
                this.scoresFileName = MazeManager
                        .getNameWithoutExtension(file.getName());
                MazeManager.loadFile(filename, false);
            } else if (extension.equals(Extension.getGameExtension())) {
                this.lastUsedGameFile = filename;
                MazeManager.loadFile(filename, true);
            } else if (extension.equals(Extension.getScoresExtension())) {
                CommonDialogs.showDialog(
                        "You double-clicked a scores file. These are automatically loaded when their associated maze is loaded, and need not be double-clicked.");
            } else if (extension.equals(Extension.getPreferencesExtension())) {
                CommonDialogs.showDialog(
                        "You double-clicked a preferences file. These are automatically loaded when the program is loaded, and need not be double-clicked.");
            } else if (extension.equals(Extension.getModeExtension())) {
                CommonDialogs.showDialog(
                        "You double-clicked a plugin file. These are automatically loaded when needed, and need not be double-clicked.");
            } else if (extension.equals(Extension.getRuleSetExtension())) {
                CommonDialogs.showDialog(
                        "You double-clicked a rule set file. These are loaded by the Rule Set Picker, and need not be double-clicked.");
            }
        }
    }

    public boolean loadRegisteredMaze() {
        final Application app = MazeMode.getApplication();
        int status = 0;
        boolean saved = true;
        String filename;
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = this.saveMaze();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                this.setDirty(false);
            }
        }
        if (saved) {
            final String[] choices = MazeRegistration.getAllRegisteredMazes();
            if (choices != null && choices.length > 0) {
                final String retVal = CommonDialogs.showInputDialog(
                        "Load Which Maze?", "Load Maze", choices, choices[0]);
                if (retVal != null) {
                    File file = new File(MazeRegistration.getPerUserBasePath()
                            + File.separator + retVal
                            + Extension.getMazeExtensionWithPeriod());
                    if (!file.exists()) {
                        file = new File(MazeRegistration.getBasePath()
                                + File.separator + retVal
                                + Extension.getMazeExtensionWithPeriod());
                    }
                    filename = file.getAbsolutePath();
                    app.getGameManager().resetObjectInventory();
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(filename, false);
                } else {
                    // User cancelled
                    if (this.loaded) {
                        return true;
                    }
                }
            } else {
                CommonDialogs.showErrorDialog("No Mazes Found!", "Load Maze");
            }
        }
        return false;
    }

    public boolean loadMaze() {
        final Application app = MazeMode.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = PreferencesManager.getLastDirOpen();
        final FileDialog fc = new FileDialog(app.getOutputFrame(), "Load Maze",
                FileDialog.LOAD);
        fc.setDirectory(lastOpen);
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = this.saveMaze();
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
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(Extension.getMazeExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeRegistration.autoRegisterUserMaze(file);
                    MazeManager.loadFile(filename, false);
                } else if (extension.equals(Extension.getGameExtension())) {
                    this.lastUsedGameFile = filename;
                    MazeManager.loadFile(filename, true);
                } else {
                    CommonDialogs.showDialog(
                            "You opened something other than a maze file. Select a maze file, and try again.");
                }
            } else {
                // User cancelled
                if (this.loaded) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void loadFile(final String filename,
            final boolean isSavedGame) {
        if (!FilenameChecker.isFilenameOK(MazeManager.getNameWithoutExtension(
                MazeManager.getFileNameOnly(filename)))) {
            CommonDialogs.showErrorDialog(
                    "The file you selected contains illegal characters in its\n"
                            + "name. These characters are not allowed: /?<>\\:|\"\n"
                            + "Files named con, nul, or prn are illegal, as are files\n"
                            + "named com1 through com9 and lpt1 through lpt9.",
                    "Load");
        } else {
            final LoadTask xlt = new LoadTask(filename, isSavedGame);
            xlt.start();
        }
    }

    public boolean saveMaze() {
        final Application app = MazeMode.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            if (this.lastUsedGameFile != null
                    && !this.lastUsedGameFile.equals("")) {
                final String extension = MazeManager
                        .getExtension(this.lastUsedGameFile);
                if (extension != null) {
                    if (!extension.equals(Extension.getGameExtension())) {
                        this.lastUsedGameFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedGameFile)
                                + Extension.getGameExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedGameFile += Extension
                            .getGameExtensionWithPeriod();
                }
                MazeManager.saveFile(this.lastUsedGameFile, true);
            } else {
                return this.saveMazeAs();
            }
        } else {
            if (this.lastUsedMazeFile != null
                    && !this.lastUsedMazeFile.equals("")) {
                final String extension = MazeManager
                        .getExtension(this.lastUsedMazeFile);
                if (extension != null) {
                    if (!extension.equals(Extension.getMazeExtension())) {
                        this.lastUsedMazeFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedMazeFile)
                                + Extension.getMazeExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedMazeFile += Extension
                            .getMazeExtensionWithPeriod();
                }
                MazeManager.saveFile(this.lastUsedMazeFile, false);
            } else {
                return this.saveMazeAs();
            }
        }
        return false;
    }

    public boolean saveMazeAs() {
        final Application app = MazeMode.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        final String lastSave = PreferencesManager.getLastDirSave();
        final FileDialog fc = new FileDialog(app.getOutputFrame(), "Save Maze",
                FileDialog.SAVE);
        fc.setDirectory(lastSave);
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            fc.setVisible(true);
            if (fc.getFile() != null && fc.getDirectory() != null) {
                final File file = new File(fc.getDirectory() + fc.getFile());
                extension = MazeManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = fc.getDirectory();
                fileOnly = filename.substring(dirOnly.length() + 1);
                if (!FilenameChecker.isFilenameOK(fileOnly)) {
                    CommonDialogs.showErrorDialog(
                            "The file name you entered contains illegal characters.\n"
                                    + "These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            "Save");
                } else {
                    PreferencesManager.setLastDirSave(fc.getDirectory());
                    if (app.getMode() == Application.STATUS_GAME) {
                        if (extension != null) {
                            if (!extension
                                    .equals(Extension.getGameExtension())) {
                                filename = MazeManager
                                        .getNameWithoutExtension(file)
                                        + Extension
                                                .getGameExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension.getGameExtensionWithPeriod();
                        }
                        this.lastUsedGameFile = filename;
                        MazeManager.saveFile(filename, true);
                    } else {
                        if (extension != null) {
                            if (!extension
                                    .equals(Extension.getMazeExtension())) {
                                filename = MazeManager
                                        .getNameWithoutExtension(file)
                                        + Extension
                                                .getMazeExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension.getMazeExtensionWithPeriod();
                        }
                        this.lastUsedMazeFile = filename;
                        this.scoresFileName = MazeManager
                                .getNameWithoutExtension(file.getName());
                        MazeManager.saveFile(filename, false);
                    }
                }
            } else {
                break;
            }
        }
        return false;
    }

    private static void saveFile(final String filename,
            final boolean isSavedGame) {
        final String sg;
        if (isSavedGame) {
            sg = "Saved Game";
        } else {
            sg = "Maze";
        }
        MazeMode.getApplication().showMessage("Saving " + sg + " file...");
        final SaveTask xst = new SaveTask(filename, isSavedGame);
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
