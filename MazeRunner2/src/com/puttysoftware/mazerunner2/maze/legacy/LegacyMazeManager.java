/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.legacy;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.legacy.games.LegacyGameFilter;
import com.puttysoftware.mazerunner2.maze.legacy.games.LegacyGameFinder;
import com.puttysoftware.mazerunner2.maze.legacy.games.LegacyGameLoadTask;
import com.puttysoftware.mazerunner2.prefs.PreferencesManager;
import com.puttysoftware.xio.DirectoryUtilities;
import com.puttysoftware.xio.FilenameChecker;

public class LegacyMazeManager {
    // Fields
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/MazeRunnerII/Games/";
    private static final String WIN_DIR = "\\Putty Software\\MazeRunnerII\\Games\\";
    private static final String UNIX_DIR = "/.puttysoftware/mazerunner2/games/";

    // Constructors
    public LegacyMazeManager() {
        // Do nothing
    }

    // Methods
    private static boolean getLoaded() {
        final Application app = MazeRunnerII.getApplication();
        return app.getMazeManager().getLoaded();
    }

    private static boolean getDirty() {
        final Application app = MazeRunnerII.getApplication();
        return app.getMazeManager().getDirty();
    }

    private static void setDirty(final boolean newDirty) {
        final Application app = MazeRunnerII.getApplication();
        app.getMazeManager().setDirty(newDirty);
    }

    private static int showSaveDialog() {
        String type, source;
        final Application app = MazeRunnerII.getApplication();
        final int mode = app.getMode();
        if (mode == Application.STATUS_EDITOR) {
            type = "maze";
            source = "Editor";
        } else if (mode == Application.STATUS_GAME) {
            type = "game";
            source = "MazeRunnerII";
        } else {
            // Not in the game or editor, so abort
            return JOptionPane.NO_OPTION;
        }
        return CommonDialogs.showYNCConfirmDialog(
                "Do you want to save your " + type + "?", source);
    }

    public void legacyLoadFromOSHandler(final String filename) {
        final Application app = MazeRunnerII.getApplication();
        String extension;
        final File file = new File(filename);
        String loadFile;
        loadFile = file.getAbsolutePath();
        extension = LegacyMazeManager.getExtension(file);
        app.getGameManager().resetObjectInventory();
        if (extension.equals(LegacyExtension.getLegacyMazeExtension())) {
            LegacyMazeManager.loadLegacyFile(loadFile, false, false);
        } else if (extension.equals(LegacyExtension.getLegacyGameExtension())) {
            LegacyMazeManager.loadLegacyFile(loadFile, false, true);
        } else if (extension
                .equals(LegacyExtension.getLegacySavedGameExtension())) {
            LegacyMazeManager.loadLegacyFile(loadFile, true, false);
        }
    }

    public boolean loadLegacyMaze() {
        final Application app = MazeRunnerII.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = PreferencesManager.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final LegacyMazeFilter xmf = new LegacyMazeFilter();
        final LegacySavedGameFilter xgf = new LegacySavedGameFilter();
        if (LegacyMazeManager.getDirty()) {
            status = LegacyMazeManager.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = app.getMazeManager().saveMaze();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                LegacyMazeManager.setDirty(false);
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
                extension = LegacyMazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension
                        .equals(LegacyExtension.getLegacyMazeExtension())) {
                    LegacyMazeManager.loadLegacyFile(filename, false, false);
                } else if (extension.equals(
                        LegacyExtension.getLegacySavedGameExtension())) {
                    LegacyMazeManager.loadLegacyFile(filename, true, false);
                } else {
                    CommonDialogs.showDialog(
                            "You opened something other than a maze file. Select a maze file, and try again.");
                }
            } else {
                // User cancelled
                if (LegacyMazeManager.getLoaded()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean loadLegacyGame() {
        final Application app = MazeRunnerII.getApplication();
        int status = 0;
        boolean saved = true;
        String filename;
        final LegacyGameFinder gf = new LegacyGameFinder();
        if (LegacyMazeManager.getDirty()) {
            status = LegacyMazeManager.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = app.getMazeManager().saveMaze();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                LegacyMazeManager.setDirty(false);
            }
        }
        if (saved) {
            final String gameDir = LegacyMazeManager.getGameDirectory();
            final String[] rawChoices = new File(gameDir).list(gf);
            if (rawChoices != null) {
                final String[] choices = new String[rawChoices.length];
                // Strip extension
                for (int x = 0; x < choices.length; x++) {
                    choices[x] = LegacyMazeManager
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
                        LegacyMazeManager.loadLegacyFile(filename, false, true);
                    } else {
                        // Result not found
                        if (LegacyMazeManager.getLoaded()) {
                            return true;
                        }
                    }
                } else {
                    // User cancelled
                    if (LegacyMazeManager.getLoaded()) {
                        return true;
                    }
                }
            } else {
                CommonDialogs.showErrorDialog("No Games Found!", "Load Game");
                if (LegacyMazeManager.getLoaded()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean importLegacyGame() {
        final Application app = MazeRunnerII.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = PreferencesManager.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final LegacyGameFilter gf = new LegacyGameFilter();
        if (LegacyMazeManager.getDirty()) {
            status = LegacyMazeManager.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = app.getMazeManager().saveMaze();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                LegacyMazeManager.setDirty(false);
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
                extension = LegacyMazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension
                        .equals(LegacyExtension.getLegacyGameExtension())) {
                    // Make sure folder exists
                    if (!file.getParentFile().exists()) {
                        final boolean okay = file.getParentFile().mkdirs();
                        if (!okay) {
                            MazeRunnerII.getErrorLogger()
                                    .logError(new IOException(
                                            "Cannot create game folder!"));
                        }
                    }
                    try {
                        DirectoryUtilities.copyFile(file,
                                new File(LegacyMazeManager.getGameDirectory()
                                        + file.getName()));
                    } catch (final IOException ioe) {
                        MazeRunnerII.getErrorLogger().logError(ioe);
                    }
                    LegacyMazeManager.loadLegacyFile(filename, false, true);
                } else {
                    CommonDialogs.showDialog(
                            "You opened something other than a game file. Select a game file, and try again.");
                }
            } else {
                // User cancelled
                if (LegacyMazeManager.getLoaded()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void loadLegacyFile(final String filename,
            final boolean isSavedGame, final boolean locked) {
        if (!FilenameChecker
                .isFilenameOK(LegacyMazeManager.getNameWithoutExtension(
                        LegacyMazeManager.getFileNameOnly(filename)))) {
            CommonDialogs.showErrorDialog(
                    "The file you selected contains illegal characters in its\n"
                            + "name. These characters are not allowed: /?<>\\:|\"\n"
                            + "Files named con, nul, or prn are illegal, as are files\n"
                            + "named com1 through com9 and lpt1 through lpt9.",
                    "Load");
        } else {
            if (locked) {
                final LegacyGameLoadTask llt = new LegacyGameLoadTask(filename);
                llt.start();
            } else {
                final LegacyLoadTask xlt = new LegacyLoadTask(filename,
                        isSavedGame);
                xlt.start();
            }
        }
    }

    private static String getGameDirectoryPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(LegacyMazeManager.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(LegacyMazeManager.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(LegacyMazeManager.UNIX_PREFIX);
        }
    }

    private static String getGameDirectoryName() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return LegacyMazeManager.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return LegacyMazeManager.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return LegacyMazeManager.UNIX_DIR;
        }
    }

    private static String getGameDirectory() {
        final StringBuilder b = new StringBuilder();
        b.append(LegacyMazeManager.getGameDirectoryPrefix());
        b.append(LegacyMazeManager.getGameDirectoryName());
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
