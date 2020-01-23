/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.maze;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fileutils.FilenameChecker;
import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.maze.locking.LockedFilter;
import com.puttysoftware.widgetwarren.maze.locking.LockedLoadTask;
import com.puttysoftware.widgetwarren.maze.locking.LockedSaveTask;
import com.puttysoftware.widgetwarren.maze.xml.XMLExtension;
import com.puttysoftware.widgetwarren.maze.xml.XMLGameFilter;
import com.puttysoftware.widgetwarren.maze.xml.XMLLoadTask;
import com.puttysoftware.widgetwarren.maze.xml.XMLMazeFilter;
import com.puttysoftware.widgetwarren.maze.xml.XMLSaveTask;
import com.puttysoftware.widgetwarren.security.SandboxManager;

public class MazeManager {
    // Fields
    private Maze gameMaze;
    private boolean loaded, isDirty;
    private String scoresFileName;
    private String lastUsedMazeFile;
    private String lastUsedGameFile;
    private boolean mazeXML1Compatible;
    private boolean mazeXML2Compatible;
    private boolean mazeXML4Compatible;

    // Constructors
    public MazeManager() {
        this.loaded = false;
        this.isDirty = false;
        this.lastUsedMazeFile = "";
        this.lastUsedGameFile = "";
        this.scoresFileName = "";
        this.mazeXML1Compatible = false;
        this.mazeXML2Compatible = false;
        this.mazeXML4Compatible = false;
    }

    // Methods
    public Maze getMaze() {
        return this.gameMaze;
    }

    public void setMaze(final Maze newMaze) {
        this.gameMaze = newMaze;
    }

    public boolean isMazeXML1Compatible() {
        return this.mazeXML1Compatible;
    }

    void setMazeXML1Compatible(final boolean value) {
        this.mazeXML1Compatible = value;
    }

    public boolean isMazeXML2Compatible() {
        return this.mazeXML2Compatible;
    }

    void setMazeXML2Compatible(final boolean value) {
        this.mazeXML2Compatible = value;
    }

    public boolean isMazeXML4Compatible() {
        return this.mazeXML4Compatible;
    }

    void setMazeXML4Compatible(final boolean value) {
        this.mazeXML4Compatible = value;
    }

    public void handleDeferredSuccess(final boolean value) {
        if (value) {
            this.setLoaded(true);
        }
        this.setDirty(false);
        WidgetWarren.getApplication().getGameManager().stateChanged();
        WidgetWarren.getApplication().getEditor().mazeChanged();
        WidgetWarren.getApplication().getMenuManager().checkFlags();
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
        final Application app = WidgetWarren.getApplication();
        final int mode = app.getMode();
        if (mode == Application.STATUS_EDITOR) {
            type = "maze";
            source = "Editor";
        } else if (mode == Application.STATUS_GAME) {
            type = "game";
            source = "WidgetWarren";
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
        final Application app = WidgetWarren.getApplication();
        this.loaded = status;
        app.getMenuManager().checkFlags();
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        final Application app = WidgetWarren.getApplication();
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

    private boolean loadMaze() {
        final Application app = WidgetWarren.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final XMLMazeFilter xmf = new XMLMazeFilter();
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
            final File file = CommonDialogs.showFileOpenDialog(
                    new File(SandboxManager.getSandboxManager()
                            .getDocumentsDirectory()), xmf, "Open Which Maze?");
            if (file != null) {
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(XMLExtension.getXMLMazeExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(filename, false, false);
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

    public boolean load() {
        final Application app = WidgetWarren.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            return this.loadGame();
        } else {
            return this.loadMaze();
        }
    }

    private boolean loadGame() {
        final Application app = WidgetWarren.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final XMLGameFilter xgf = new XMLGameFilter();
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
            final File file = CommonDialogs.showFileOpenDialog(
                    new File(SandboxManager.getSandboxManager()
                            .getDocumentsDirectory()), xgf, "Open Which Game?");
            if (file != null) {
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(XMLExtension.getXMLGameExtension())) {
                    this.lastUsedGameFile = filename;
                    MazeManager.loadFile(filename, true, false);
                } else {
                    CommonDialogs
                            .showDialog("You opened something other than a game file. Select a game file, and try again.");
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
        final Application app = WidgetWarren.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
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
            final File file = CommonDialogs.showFileOpenDialog(
                    new File(SandboxManager.getSandboxManager()
                            .getDocumentsDirectory()), lf,
                    "Open Which Locked Maze?");
            if (file != null) {
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(XMLExtension.getXMLMazeExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(filename, false, false);
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
                final XMLLoadTask xlt = new XMLLoadTask(filename, isSavedGame);
                xlt.start();
            }
        }
    }

    public boolean saveMaze() {
        this.setMazeXML1Compatible(false);
        this.setMazeXML2Compatible(false);
        this.setMazeXML4Compatible(false);
        final Application app = WidgetWarren.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            if (this.lastUsedGameFile != null
                    && !this.lastUsedGameFile.equals("")) {
                final String extension = MazeManager
                        .getExtension(this.lastUsedGameFile);
                if (extension != null) {
                    if (!(extension.equals(XMLExtension.getXMLGameExtension()))) {
                        this.lastUsedGameFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedGameFile)
                                + XMLExtension.getXMLGameExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedGameFile += XMLExtension
                            .getXMLGameExtensionWithPeriod();
                }
                MazeManager.saveFile(this.lastUsedGameFile, true, false);
            } else {
                return this.saveGameAs();
            }
        } else {
            if (this.lastUsedMazeFile != null
                    && !this.lastUsedMazeFile.equals("")) {
                final String extension = MazeManager
                        .getExtension(this.lastUsedMazeFile);
                if (extension != null) {
                    if (!(extension.equals(XMLExtension.getXMLMazeExtension()))) {
                        this.lastUsedMazeFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedMazeFile)
                                + XMLExtension.getXMLMazeExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedMazeFile += XMLExtension
                            .getXMLMazeExtensionWithPeriod();
                }
                MazeManager.saveFile(this.lastUsedMazeFile, false, false);
            } else {
                return this.saveMazeAs();
            }
        }
        return false;
    }

    public boolean saveAs() {
        final Application app = WidgetWarren.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            return this.saveGameAs();
        } else {
            return this.saveMazeAs();
        }
    }

    private boolean saveMazeAs() {
        this.setMazeXML1Compatible(false);
        this.setMazeXML2Compatible(false);
        this.setMazeXML4Compatible(false);
        String filename = "";
        String fileOnly = "\\";
        String extension;
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            final File file = CommonDialogs.showFileSaveDialog(
                    new File(SandboxManager.getSandboxManager()
                            .getDocumentsDirectory()), "Save Maze As...");
            if (file != null) {
                extension = MazeManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = SandboxManager.getSandboxManager()
                        .getDocumentsDirectory();
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
                    if (extension != null) {
                        if (!(extension.equals(XMLExtension
                                .getXMLMazeExtension()))) {
                            filename = MazeManager
                                    .getNameWithoutExtension(file)
                                    + XMLExtension
                                            .getXMLMazeExtensionWithPeriod();
                        }
                    } else {
                        filename += XMLExtension
                                .getXMLMazeExtensionWithPeriod();
                    }
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.saveFile(filename, false, false);
                }
            } else {
                break;
            }
        }
        return false;
    }

    private boolean saveGameAs() {
        this.setMazeXML1Compatible(false);
        this.setMazeXML2Compatible(false);
        this.setMazeXML4Compatible(false);
        String filename = "";
        String fileOnly = "\\";
        String extension;
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            final File file = CommonDialogs.showFileSaveDialog(
                    new File(SandboxManager.getSandboxManager()
                            .getDocumentsDirectory()), "Save Game As...");
            if (file != null) {
                extension = MazeManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = SandboxManager.getSandboxManager()
                        .getDocumentsDirectory();
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
                    if (extension != null) {
                        if (!(extension.equals(XMLExtension
                                .getXMLGameExtension()))) {
                            filename = MazeManager
                                    .getNameWithoutExtension(file)
                                    + XMLExtension
                                            .getXMLGameExtensionWithPeriod();
                        }
                    } else {
                        filename += XMLExtension
                                .getXMLGameExtensionWithPeriod();
                    }
                    this.lastUsedGameFile = filename;
                    MazeManager.saveFile(filename, true, false);
                }
            } else {
                break;
            }
        }
        return false;
    }

    public boolean saveLockedMaze() {
        this.setMazeXML1Compatible(false);
        this.setMazeXML2Compatible(false);
        this.setMazeXML4Compatible(false);
        String filename = "";
        String fileOnly = "\\";
        String extension;
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            final File file = CommonDialogs
                    .showFileSaveDialog(new File(SandboxManager
                            .getSandboxManager().getDocumentsDirectory()),
                            "Save Locked Maze As...");
            if (file != null) {
                extension = MazeManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = SandboxManager.getSandboxManager()
                        .getDocumentsDirectory();
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
                    if (extension != null) {
                        if (!(extension.equals(Extension
                                .getLockedMazeExtension()))) {
                            filename = MazeManager
                                    .getNameWithoutExtension(file)
                                    + Extension
                                            .getLockedMazeExtensionWithPeriod();
                        }
                    } else {
                        filename += Extension
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
        WidgetWarren.getApplication().showMessage("Saving " + sg + " file...");
        if (locked) {
            final LockedSaveTask lst = new LockedSaveTask(filename);
            lst.start();
        } else {
            final XMLSaveTask xst = new XMLSaveTask(filename, isSavedGame);
            xst.start();
        }
    }

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if ((i > 0) && (i < s.length() - 1)) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if ((i > 0) && (i < s.length() - 1)) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getNameWithoutExtension(final File f) {
        String ext = null;
        final String s = f.getAbsolutePath();
        final int i = s.lastIndexOf('.');
        if ((i > 0) && (i < s.length() - 1)) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getNameWithoutExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if ((i > 0) && (i < s.length() - 1)) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getFileNameOnly(final String s) {
        String fno = null;
        final int i = s.lastIndexOf(File.separatorChar);
        if ((i > 0) && (i < s.length() - 1)) {
            fno = s.substring(i + 1);
        } else {
            fno = s;
        }
        return fno;
    }
}
