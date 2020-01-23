/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.generic.MazeObject;
import com.puttysoftware.weaselweb.maze.locking.LockedFilter;
import com.puttysoftware.weaselweb.maze.locking.LockedLoadTask;
import com.puttysoftware.weaselweb.maze.locking.LockedSaveTask;
import com.puttysoftware.weaselweb.prefs.PreferencesManager;

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
        WeaselWeb.getApplication().getGameManager().stateChanged();
        WeaselWeb.getApplication().getEditor().mazeChanged();
        WeaselWeb.getApplication().getMenuManager().checkFlags();
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
        final Application app = WeaselWeb.getApplication();
        final int mode = app.getMode();
        if (mode == Application.STATUS_EDITOR) {
            type = "maze";
            source = "Editor";
        } else if (mode == Application.STATUS_GAME) {
            type = "game";
            source = "WeaselWeb";
        } else {
            // Not in the game or editor, so abort
            return JOptionPane.NO_OPTION;
        }
        int status = JOptionPane.DEFAULT_OPTION;
        status = CommonDialogs.showYNCConfirmDialog("Do you want to save your "
                + type + "?", source);
        return status;
    }

    public boolean getLoaded() {
        return this.loaded;
    }

    public void setLoaded(final boolean status) {
        final Application app = WeaselWeb.getApplication();
        this.loaded = status;
        app.getMenuManager().checkFlags();
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        final Application app = WeaselWeb.getApplication();
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

    public void loadFromOSHandler(final String filename) { // NO_UCD
        final Application app = WeaselWeb.getApplication();
        if (!this.loaded) {
            String extension;
            final File file = new File(filename);
            String loadFile;
            loadFile = file.getAbsolutePath();
            extension = MazeManager.getExtension(file);
            app.getGameManager().resetObjectInventory();
            if (extension.equals(FileExtension.getMazeExtension())) {
                this.lastUsedMazeFile = loadFile;
                this.scoresFileName = MazeManager.getNameWithoutExtension(file
                        .getName());
                MazeManager.loadFile(loadFile, false, false);
            } else if (extension.equals(FileExtension.getLockedMazeExtension())) {
                this.lastUsedMazeFile = loadFile;
                this.scoresFileName = MazeManager.getNameWithoutExtension(file
                        .getName());
                MazeManager.loadFile(loadFile, false, true);
            } else if (extension.equals(FileExtension.getGameExtension())) {
                this.lastUsedGameFile = loadFile;
                MazeManager.loadFile(loadFile, true, false);
            } else if (extension.equals(FileExtension.getScoresExtension())) {
                CommonDialogs
                        .showDialog("You double-clicked a scores file. These are automatically loaded when their associated maze is loaded, and need not be double-clicked.");
            } else if (extension
                    .equals(FileExtension.getPreferencesExtension())) {
                CommonDialogs
                        .showDialog("You double-clicked a preferences file. These are automatically loaded when the program is loaded, and need not be double-clicked.");
            } else if (extension.equals(FileExtension.getPluginExtension())) {
                CommonDialogs
                        .showDialog("You double-clicked a plugin file. These are automatically loaded when needed, and need not be double-clicked.");
            } else if (extension.equals(FileExtension.getRuleSetExtension())) {
                CommonDialogs
                        .showDialog("You double-clicked a rule set file. These are loaded by the Rule Set Picker, and need not be double-clicked.");
            }
        }
    }

    public boolean loadMaze() {
        final Application app = WeaselWeb.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = PreferencesManager.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final MazeFilter xmf = new MazeFilter();
        final GameFilter xgf = new GameFilter();
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
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(xmf);
            fc.addChoosableFileFilter(xgf);
            final int filter = PreferencesManager.getLastFilterUsedOpen();
            if (filter == PreferencesManager.FILTER__MAZE) {
                fc.setFileFilter(xmf);
            } else {
                fc.setFileFilter(xgf);
            }
            final int returnVal = fc.showOpenDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                final FileFilter ff = fc.getFileFilter();
                if (ff.getDescription().equals(xmf.getDescription())) {
                    PreferencesManager
                            .setLastFilterUsedOpen(PreferencesManager.FILTER__MAZE);
                } else {
                    PreferencesManager
                            .setLastFilterUsedOpen(PreferencesManager.FILTER__GAME);
                }
                PreferencesManager.setLastDirOpen(fc.getCurrentDirectory()
                        .getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(FileExtension.getMazeExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(filename, false, false);
                } else if (extension.equals(FileExtension.getGameExtension())) {
                    this.lastUsedGameFile = filename;
                    MazeManager.loadFile(filename, true, false);
                } else {
                    CommonDialogs
                            .showDialog("You opened something other than a maze file. Select a maze file, and try again.");
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

    public boolean loadLockedMaze() {
        final Application app = WeaselWeb.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = PreferencesManager.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final LockedFilter lf = new LockedFilter();
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
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(lf);
            fc.setFileFilter(lf);
            final int returnVal = fc.showOpenDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                PreferencesManager.setLastDirOpen(fc.getCurrentDirectory()
                        .getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(FileExtension.getLockedMazeExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(filename, false, true);
                } else {
                    CommonDialogs
                            .showDialog("You opened something other than a locked maze file. Select a locked maze file, and try again.");
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
            final boolean isSavedGame, final boolean locked) {
        if (!FilenameChecker
                .isFilenameOK(MazeManager.getNameWithoutExtension(MazeManager
                        .getFileNameOnly(filename)))) {
            CommonDialogs
                    .showErrorDialog(
                            "The file you selected contains illegal characters in its\n"
                                    + "name. These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            "Load");
        } else {
            if (locked) {
                final LockedLoadTask llt = new LockedLoadTask(filename);
                llt.start();
            } else {
                final LoadTask xlt = new LoadTask(filename, isSavedGame);
                xlt.start();
            }
        }
    }

    public boolean saveMaze() {
        final Application app = WeaselWeb.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            if (this.lastUsedGameFile != null
                    && !this.lastUsedGameFile.equals("")) {
                final String extension = MazeManager
                        .getExtension(this.lastUsedGameFile);
                if (extension != null) {
                    if (!extension.equals(FileExtension.getGameExtension())) {
                        this.lastUsedGameFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedGameFile)
                                + FileExtension.getGameExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedGameFile += FileExtension
                            .getGameExtensionWithPeriod();
                }
                MazeManager.saveFile(this.lastUsedGameFile, true, false);
            } else {
                return this.saveMazeAs();
            }
        } else {
            if (this.lastUsedMazeFile != null
                    && !this.lastUsedMazeFile.equals("")) {
                final String extension = MazeManager
                        .getExtension(this.lastUsedMazeFile);
                if (extension != null) {
                    if (!extension.equals(FileExtension.getMazeExtension())) {
                        this.lastUsedMazeFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedMazeFile)
                                + FileExtension.getMazeExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedMazeFile += FileExtension
                            .getMazeExtensionWithPeriod();
                }
                MazeManager.saveFile(this.lastUsedMazeFile, false, false);
            } else {
                return this.saveMazeAs();
            }
        }
        return false;
    }

    public boolean saveMazeAs() {
        final Application app = WeaselWeb.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        final String lastSave = PreferencesManager.getLastDirSave();
        File lastSaveDir = null;
        if (lastSave != null) {
            lastSaveDir = new File(lastSave);
        }
        final JFileChooser fc = new JFileChooser(lastSaveDir);
        final MazeFilter xmf = new MazeFilter();
        final GameFilter xgf = new GameFilter();
        fc.setAcceptAllFileFilterUsed(false);
        if (app.getMode() == Application.STATUS_GAME) {
            fc.addChoosableFileFilter(xgf);
            fc.setFileFilter(xgf);
        } else {
            fc.addChoosableFileFilter(xmf);
            fc.setFileFilter(xmf);
        }
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            final int returnVal = fc.showSaveDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                extension = MazeManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = fc.getCurrentDirectory()
                        .getAbsolutePath();
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
                    PreferencesManager.setLastDirSave(fc.getCurrentDirectory()
                            .getAbsolutePath());
                    if (app.getMode() == Application.STATUS_GAME) {
                        if (extension != null) {
                            if (!extension.equals(FileExtension
                                    .getGameExtension())) {
                                filename = MazeManager
                                        .getNameWithoutExtension(file)
                                        + FileExtension
                                                .getGameExtensionWithPeriod();
                            }
                        } else {
                            filename += FileExtension
                                    .getGameExtensionWithPeriod();
                        }
                        this.lastUsedGameFile = filename;
                        MazeManager.saveFile(filename, true, false);
                    } else {
                        if (extension != null) {
                            if (!extension.equals(FileExtension
                                    .getMazeExtension())) {
                                filename = MazeManager
                                        .getNameWithoutExtension(file)
                                        + FileExtension
                                                .getMazeExtensionWithPeriod();
                            }
                        } else {
                            filename += FileExtension
                                    .getMazeExtensionWithPeriod();
                        }
                        this.lastUsedMazeFile = filename;
                        this.scoresFileName = MazeManager
                                .getNameWithoutExtension(file.getName());
                        MazeManager.saveFile(filename, false, false);
                    }
                }
            } else {
                break;
            }
        }
        return false;
    }

    public boolean saveLockedMaze() {
        final Application app = WeaselWeb.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        final String lastSave = PreferencesManager.getLastDirSave();
        File lastSaveDir = null;
        if (lastSave != null) {
            lastSaveDir = new File(lastSave);
        }
        final JFileChooser fc = new JFileChooser(lastSaveDir);
        final LockedFilter lf = new LockedFilter();
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(lf);
        fc.setFileFilter(lf);
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            final int returnVal = fc.showSaveDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                extension = MazeManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = fc.getCurrentDirectory()
                        .getAbsolutePath();
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
                    PreferencesManager.setLastDirSave(fc.getCurrentDirectory()
                            .getAbsolutePath());
                    if (extension != null) {
                        if (!extension.equals(FileExtension
                                .getLockedMazeExtension())) {
                            filename = MazeManager
                                    .getNameWithoutExtension(file)
                                    + FileExtension
                                            .getLockedMazeExtensionWithPeriod();
                        }
                    } else {
                        filename += FileExtension
                                .getLockedMazeExtensionWithPeriod();
                    }
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.saveFile(filename, false, true);
                }
            } else {
                break;
            }
        }
        return false;
    }

    private static void saveFile(final String filename,
            final boolean isSavedGame, final boolean locked) {
        final String sg;
        if (isSavedGame) {
            sg = "Saved Game";
        } else {
            if (locked) {
                sg = "Locked Maze";
            } else {
                sg = "Maze";
            }
        }
        WeaselWeb.getApplication().showMessage("Saving " + sg + " file...");
        if (locked) {
            final LockedSaveTask lst = new LockedSaveTask(filename);
            lst.start();
        } else {
            final SaveTask xst = new SaveTask(filename, isSavedGame);
            xst.start();
        }
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
