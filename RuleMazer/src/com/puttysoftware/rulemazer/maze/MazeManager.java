/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.maze;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.CommonDialogs;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.generic.MazeObject;
import com.puttysoftware.rulemazer.maze.xml.XMLExtension;
import com.puttysoftware.rulemazer.maze.xml.XMLGameFilter;
import com.puttysoftware.rulemazer.maze.xml.XMLLoadTask;
import com.puttysoftware.rulemazer.maze.xml.XMLMazeFilter;
import com.puttysoftware.rulemazer.maze.xml.XMLSaveTask;
import com.puttysoftware.rulemazer.prefs.PreferencesManager;

public class MazeManager {
    // Fields
    private Maze gameMaze;
    private boolean loaded, isDirty;
    private String scoresFileName;
    private String lastUsedMazeFile;
    private String lastUsedGameFile;
    private boolean mazeXML1Compatible;
    private boolean mazeXML2Compatible;

    // Constructors
    public MazeManager() {
        this.loaded = false;
        this.isDirty = false;
        this.lastUsedMazeFile = "";
        this.lastUsedGameFile = "";
        this.scoresFileName = "";
        this.mazeXML1Compatible = false;
        this.mazeXML2Compatible = false;
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

    public void handleDeferredSuccess(final boolean value) {
        if (value) {
            this.setLoaded(true);
        }
        this.setDirty(false);
        Main.getApplication().getGameManager().stateChanged();
        Main.getApplication().getEditor().mazeChanged();
        Main.getApplication().getMenuManager().checkFlags();
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
        final Application app = Main.getApplication();
        final int mode = app.getMode();
        if (mode == Application.STATUS_EDITOR) {
            type = "maze";
            source = "Editor";
        } else if (mode == Application.STATUS_GAME) {
            type = "game";
            source = "RuleMazer";
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
        final Application app = Main.getApplication();
        this.loaded = status;
        app.getMenuManager().checkFlags();
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        final Application app = Main.getApplication();
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
        final Application app = Main.getApplication();
        if (!this.loaded) {
            String extension;
            final File file = new File(infilename);
            final String filename = file.getAbsolutePath();
            extension = MazeManager.getExtension(file);
            app.getGameManager().resetObjectInventory();
            if (extension.equals(XMLExtension.getXMLMazeExtension())) {
                this.lastUsedMazeFile = filename;
                this.scoresFileName = MazeManager
                        .getNameWithoutExtension(file.getName());
                MazeManager.loadFile(filename, false);
            } else if (extension.equals(XMLExtension.getXMLGameExtension())) {
                this.lastUsedGameFile = filename;
                MazeManager.loadFile(filename, true);
            } else if (extension.equals(XMLExtension.getXMLScoresExtension())) {
                CommonDialogs.showDialog(
                        "You double-clicked a scores file. These are automatically loaded when their associated maze is loaded, and need not be double-clicked.");
            } else if (extension.equals(Extension.getPreferencesExtension())) {
                CommonDialogs.showDialog(
                        "You double-clicked a preferences file. These are automatically loaded when the program is loaded, and need not be double-clicked.");
            } else if (extension
                    .equals(XMLExtension.getXMLRuleSetExtension())) {
                CommonDialogs.showDialog(
                        "You double-clicked a rule set file. These are loaded by the Rule Set Picker, and need not be double-clicked.");
            }
        }
    }

    public boolean loadMaze() {
        final Application app = Main.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = PreferencesManager.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final XMLMazeFilter xmf = new XMLMazeFilter();
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
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(xmf);
            fc.addChoosableFileFilter(xgf);
            final int filter = PreferencesManager.getLastFilterUsedOpen();
            if (filter == PreferencesManager.FILTER_XML_MAZE) {
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
                            PreferencesManager.FILTER_XML_MAZE);
                } else {
                    PreferencesManager.setLastFilterUsedOpen(
                            PreferencesManager.FILTER_XML_GAME);
                }
                PreferencesManager.setLastDirOpen(
                        fc.getCurrentDirectory().getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(XMLExtension.getXMLMazeExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(filename, false);
                } else if (extension
                        .equals(XMLExtension.getXMLGameExtension())) {
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
            final XMLLoadTask xlt = new XMLLoadTask(filename, isSavedGame);
            xlt.start();
        }
    }

    public boolean saveMaze() {
        this.setMazeXML1Compatible(false);
        this.setMazeXML2Compatible(false);
        final Application app = Main.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            if (this.lastUsedGameFile != null
                    && !this.lastUsedGameFile.equals("")) {
                final String extension = MazeManager
                        .getExtension(this.lastUsedGameFile);
                if (extension != null) {
                    if (!extension.equals(XMLExtension.getXMLGameExtension())) {
                        this.lastUsedGameFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedGameFile)
                                + XMLExtension.getXMLGameExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedGameFile += XMLExtension
                            .getXMLGameExtensionWithPeriod();
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
                    if (!extension.equals(XMLExtension.getXMLMazeExtension())) {
                        this.lastUsedMazeFile = MazeManager
                                .getNameWithoutExtension(this.lastUsedMazeFile)
                                + XMLExtension.getXMLMazeExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedMazeFile += XMLExtension
                            .getXMLMazeExtensionWithPeriod();
                }
                MazeManager.saveFile(this.lastUsedMazeFile, false);
            } else {
                return this.saveMazeAs();
            }
        }
        return false;
    }

    public boolean saveMazeAs() {
        this.setMazeXML1Compatible(false);
        final Application app = Main.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        final String lastSave = PreferencesManager.getLastDirSave();
        File lastSaveDir = null;
        if (lastSave != null) {
            lastSaveDir = new File(lastSave);
        }
        final JFileChooser fc = new JFileChooser(lastSaveDir);
        final XMLMazeFilter xmf = new XMLMazeFilter();
        final XMLGameFilter xgf = new XMLGameFilter();
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
                                    XMLExtension.getXMLGameExtension())) {
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
                        MazeManager.saveFile(filename, true);
                    } else {
                        if (extension != null) {
                            if (!extension.equals(
                                    XMLExtension.getXMLMazeExtension())) {
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
        Main.getApplication().showMessage("Saving " + sg + " file...");
        final XMLSaveTask xst = new XMLSaveTask(filename, isSavedGame);
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
