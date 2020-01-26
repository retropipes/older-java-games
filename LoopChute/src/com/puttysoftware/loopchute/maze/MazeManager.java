/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.maze;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.generic.MazeObject;
import com.puttysoftware.loopchute.maze.games.GameFilter;
import com.puttysoftware.loopchute.maze.games.GameFinder;
import com.puttysoftware.loopchute.maze.games.GameLoadTask;
import com.puttysoftware.loopchute.maze.games.GameSaveTask;
import com.puttysoftware.loopchute.prefs.PreferencesManager;
import com.puttysoftware.xio.DirectoryUtilities;
import com.puttysoftware.xio.FilenameChecker;

public class MazeManager {
    // Fields
    private Maze gameMaze;
    private boolean loaded, isDirty;
    private String scoresFileName;
    private String lastUsedMazeFile;
    private String lastUsedGameFile;
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/loopchute/Games/";
    private static final String WIN_DIR = "\\Putty Software\\loopchute\\Games\\";
    private static final String UNIX_DIR = "/.puttysoftware/loopchute/games/";

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
        LoopChute.getApplication().getGameManager().stateChanged();
        LoopChute.getApplication().getEditor().mazeChanged();
        LoopChute.getApplication().getMenuManager().checkFlags();
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
        final Application app = LoopChute.getApplication();
        final int mode = app.getMode();
        if (mode == Application.STATUS_EDITOR) {
            type = "maze";
            source = "Editor";
        } else if (mode == Application.STATUS_GAME) {
            type = "game";
            source = "loopchute";
        } else {
            // Not in the game or editor, so abort
            return JOptionPane.NO_OPTION;
        }
        return CommonDialogs.showYNCConfirmDialog(
                "Do you want to save your " + type + "?", source);
    }

    public boolean getLoaded() {
        return this.loaded;
    }

    public void setLoaded(final boolean status) {
        final Application app = LoopChute.getApplication();
        this.loaded = status;
        app.getMenuManager().checkFlags();
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        final Application app = LoopChute.getApplication();
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
        final Application app = LoopChute.getApplication();
        if (!this.loaded) {
            String extension;
            final File file = new File(filename);
            String loadFile;
            loadFile = file.getAbsolutePath();
            extension = MazeManager.getExtension(file);
            app.getGameManager().resetObjectInventory();
            if (extension.equals(Extension.getMazeExtension())) {
                this.lastUsedMazeFile = loadFile;
                this.scoresFileName = MazeManager
                        .getNameWithoutExtension(file.getName());
                MazeManager.loadFile(loadFile, false, false);
            } else if (extension.equals(Extension.getGameExtension())) {
                this.lastUsedMazeFile = loadFile;
                this.scoresFileName = MazeManager
                        .getNameWithoutExtension(file.getName());
                MazeManager.loadFile(loadFile, false, true);
            } else if (extension.equals(Extension.getSavedGameExtension())) {
                this.lastUsedGameFile = loadFile;
                MazeManager.loadFile(loadFile, true, false);
            } else if (extension.equals(Extension.getPreferencesExtension())) {
                CommonDialogs.showDialog(
                        "You double-clicked a preferences file. These are automatically loaded when the program is loaded, and need not be double-clicked.");
            } else if (extension.equals(Extension.getRuleSetExtension())) {
                CommonDialogs.showDialog(
                        "You double-clicked a rule set file. These are loaded by the Rule Set Picker, and need not be double-clicked.");
            }
        }
    }

    public boolean loadMaze() {
        final Application app = LoopChute.getApplication();
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
        final SavedGameFilter xgf = new SavedGameFilter();
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
            if (filter == PreferencesManager.FILTER_MAZE) {
                fc.setFileFilter(xmf);
            } else {
                fc.setFileFilter(xgf);
            }
            final int returnVal = fc.showOpenDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                final FileFilter ff = fc.getFileFilter();
                if (ff.getDescription().equals(xmf.getDescription())) {
                    PreferencesManager.setLastFilterUsedOpen(
                            PreferencesManager.FILTER_MAZE);
                } else {
                    PreferencesManager.setLastFilterUsedOpen(
                            PreferencesManager.FILTER_GAME);
                }
                PreferencesManager.setLastDirOpen(
                        fc.getCurrentDirectory().getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(Extension.getMazeExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(filename, false, false);
                } else if (extension
                        .equals(Extension.getSavedGameExtension())) {
                    this.lastUsedGameFile = filename;
                    MazeManager.loadFile(filename, true, false);
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

    public boolean loadGame() {
        final Application app = LoopChute.getApplication();
        int status = 0;
        boolean saved = true;
        String filename;
        final GameFinder gf = new GameFinder();
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
            final String gameDir = MazeManager.getGameDirectory();
            final String[] rawChoices = new File(gameDir).list(gf);
            if (rawChoices != null) {
                final String[] choices = new String[rawChoices.length];
                // Strip extension
                for (int x = 0; x < choices.length; x++) {
                    choices[x] = MazeManager
                            .getNameWithoutExtension(rawChoices[x]);
                }
                final String returnVal = CommonDialogs.showInputDialog(
                        "Select a Game", "Load Game", choices, choices[0]);
                if (returnVal != null) {
                    int index = -1;
                    for (int x = 0; x < choices.length; x++) {
                        if (returnVal.equals(choices[x])) {
                            index = x;
                            break;
                        }
                    }
                    if (index != -1) {
                        final File file = new File(gameDir + rawChoices[index]);
                        filename = file.getAbsolutePath();
                        app.getGameManager().resetObjectInventory();
                        this.lastUsedMazeFile = filename;
                        this.scoresFileName = MazeManager
                                .getNameWithoutExtension(file.getName());
                        MazeManager.loadFile(filename, false, true);
                    } else {
                        // Result not found
                        if (this.loaded) {
                            return true;
                        }
                    }
                } else {
                    // User cancelled
                    if (this.loaded) {
                        return true;
                    }
                }
            } else {
                CommonDialogs.showErrorDialog("No Games Found!", "Load Game");
                if (this.loaded) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean importGame() {
        final Application app = LoopChute.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = PreferencesManager.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final GameFilter gf = new GameFilter();
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
            fc.addChoosableFileFilter(gf);
            fc.setFileFilter(gf);
            final int returnVal = fc.showOpenDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                PreferencesManager.setLastDirOpen(
                        fc.getCurrentDirectory().getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(Extension.getGameExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    // Make sure folder exists
                    if (!file.getParentFile().exists()) {
                        final boolean okay = file.getParentFile().mkdirs();
                        if (!okay) {
                            LoopChute.getErrorLogger().logError(new IOException(
                                    "Cannot create game folder!"));
                        }
                    }
                    try {
                        DirectoryUtilities.copyFile(file,
                                new File(MazeManager.getGameDirectory()
                                        + file.getName()));
                    } catch (final IOException ioe) {
                        LoopChute.getErrorLogger().logError(ioe);
                    }
                    MazeManager.loadFile(filename, false, true);
                } else {
                    CommonDialogs.showDialog(
                            "You opened something other than a game file. Select a game file, and try again.");
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
        if (!FilenameChecker.isFilenameOK(MazeManager.getNameWithoutExtension(
                MazeManager.getFileNameOnly(filename)))) {
            CommonDialogs.showErrorDialog(
                    "The file you selected contains illegal characters in its\n"
                            + "name. These characters are not allowed: /?<>\\:|\"\n"
                            + "Files named con, nul, or prn are illegal, as are files\n"
                            + "named com1 through com9 and lpt1 through lpt9.",
                    "Load");
        } else {
            if (locked) {
                final GameLoadTask llt = new GameLoadTask(filename);
                llt.start();
            } else {
                final LoadTask xlt = new LoadTask(filename, isSavedGame);
                xlt.start();
            }
        }
    }

    public boolean saveMaze() {
        final Application app = LoopChute.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            if (this.lastUsedGameFile != null
                    && !this.lastUsedGameFile.equals("")) {
                final String extension = MazeManager
                        .getExtension(this.lastUsedGameFile);
                if (extension != null) {
                    if (!extension.equals(Extension.getSavedGameExtension())) {
                        this.lastUsedGameFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedGameFile)
                                + Extension.getSavedGameExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedGameFile += Extension
                            .getSavedGameExtensionWithPeriod();
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
                    if (!extension.equals(Extension.getMazeExtension())) {
                        this.lastUsedMazeFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedMazeFile)
                                + Extension.getMazeExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedMazeFile += Extension
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
        final Application app = LoopChute.getApplication();
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
        final SavedGameFilter xgf = new SavedGameFilter();
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
                    CommonDialogs.showErrorDialog(
                            "The file name you entered contains illegal characters.\n"
                                    + "These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            "Save");
                } else {
                    PreferencesManager.setLastDirSave(
                            fc.getCurrentDirectory().getAbsolutePath());
                    if (app.getMode() == Application.STATUS_GAME) {
                        if (extension != null) {
                            if (!extension.equals(
                                    Extension.getSavedGameExtension())) {
                                filename = MazeManager
                                        .getNameWithoutExtension(file)
                                        + Extension
                                                .getSavedGameExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension
                                    .getSavedGameExtensionWithPeriod();
                        }
                        this.lastUsedGameFile = filename;
                        MazeManager.saveFile(filename, true, false);
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
                        MazeManager.saveFile(filename, false, false);
                    }
                }
            } else {
                break;
            }
        }
        return false;
    }

    public boolean saveGame() {
        String filename = "";
        String extension;
        String returnVal = "\\";
        while (!FilenameChecker.isFilenameOK(returnVal)) {
            returnVal = CommonDialogs.showTextInputDialog("Name?", "Save Game");
            if (returnVal != null) {
                extension = Extension.getGameExtensionWithPeriod();
                final File file = new File(
                        MazeManager.getGameDirectory() + returnVal + extension);
                filename = file.getAbsolutePath();
                if (!FilenameChecker.isFilenameOK(returnVal)) {
                    CommonDialogs.showErrorDialog(
                            "The file name you entered contains illegal characters.\n"
                                    + "These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            "Save Game");
                } else {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    // Make sure folder exists
                    if (!file.getParentFile().exists()) {
                        final boolean okay = file.getParentFile().mkdirs();
                        if (!okay) {
                            LoopChute.getErrorLogger().logError(new IOException(
                                    "Cannot create game folder!"));
                        }
                    }
                    MazeManager.saveFile(filename, false, true);
                }
            } else {
                break;
            }
        }
        return false;
    }

    public boolean exportGame() {
        final Application app = LoopChute.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        final String lastSave = PreferencesManager.getLastDirSave();
        File lastSaveDir = null;
        if (lastSave != null) {
            lastSaveDir = new File(lastSave);
        }
        final JFileChooser fc = new JFileChooser(lastSaveDir);
        final GameFilter lf = new GameFilter();
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
                    CommonDialogs.showErrorDialog(
                            "The file name you entered contains illegal characters.\n"
                                    + "These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            "Save");
                } else {
                    PreferencesManager.setLastDirSave(
                            fc.getCurrentDirectory().getAbsolutePath());
                    if (extension != null) {
                        if (!extension.equals(Extension.getGameExtension())) {
                            filename = MazeManager.getNameWithoutExtension(file)
                                    + Extension.getGameExtensionWithPeriod();
                        }
                    } else {
                        filename += Extension.getGameExtensionWithPeriod();
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
        LoopChute.getApplication().showMessage("Saving " + sg + " file...");
        if (locked) {
            final GameSaveTask lst = new GameSaveTask(filename);
            lst.start();
        } else {
            final SaveTask xst = new SaveTask(filename, isSavedGame);
            xst.start();
        }
    }

    private static String getGameDirectoryPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(MazeManager.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(MazeManager.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(MazeManager.UNIX_PREFIX);
        }
    }

    private static String getGameDirectoryName() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return MazeManager.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return MazeManager.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return MazeManager.UNIX_DIR;
        }
    }

    private static String getGameDirectory() {
        final StringBuilder b = new StringBuilder();
        b.append(MazeManager.getGameDirectoryPrefix());
        b.append(MazeManager.getGameDirectoryName());
        return b.toString();
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
