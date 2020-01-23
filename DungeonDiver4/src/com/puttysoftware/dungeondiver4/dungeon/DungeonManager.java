/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.games.GameFilter;
import com.puttysoftware.dungeondiver4.dungeon.games.GameFinder;
import com.puttysoftware.dungeondiver4.dungeon.games.GameLoadTask;
import com.puttysoftware.dungeondiver4.dungeon.games.GameSaveTask;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;
import com.puttysoftware.xio.DirectoryUtilities;
import com.puttysoftware.xio.FilenameChecker;

public class DungeonManager {
    // Fields
    private Dungeon gameDungeon;
    private boolean loaded, isDirty;
    private String scoresFileName;
    private String lastUsedDungeonFile;
    private String lastUsedGameFile;
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/DungeonDiver4/Games/";
    private static final String WIN_DIR = "\\Putty Software\\DungeonDiver4\\Games\\";
    private static final String UNIX_DIR = "/.puttysoftware/dungeondiver4/games/";

    // Constructors
    public DungeonManager() {
        this.loaded = false;
        this.isDirty = false;
        this.lastUsedDungeonFile = "";
        this.lastUsedGameFile = "";
        this.scoresFileName = "";
        this.gameDungeon = new Dungeon();
        this.gameDungeon.addLevel(4, 4, 1);
    }

    // Methods
    public Dungeon getDungeon() {
        return this.gameDungeon;
    }

    public void setDungeon(Dungeon newDungeon) {
        this.gameDungeon = newDungeon;
    }

    public void generateRandomDungeon() {
        RandomDungeonGenerator rdg = new RandomDungeonGenerator();
        rdg.start();
    }

    public void handleDeferredSuccess(boolean value) {
        if (value) {
            this.setLoaded(true);
        }
        this.setDirty(false);
        DungeonDiver4.getApplication().getGameManager().stateChanged();
        DungeonDiver4.getApplication().getEditor().dungeonChanged();
        DungeonDiver4.getApplication().getMenuManager().checkFlags();
    }

    public AbstractDungeonObject getDungeonObject(final int x, final int y,
            final int z, final int e) {
        try {
            return this.gameDungeon.getCell(x, y, z, e);
        } catch (ArrayIndexOutOfBoundsException ae) {
            return null;
        }
    }

    public int showSaveDialog() {
        String type, source;
        Application app = DungeonDiver4.getApplication();
        int mode = app.getMode();
        if (mode == Application.STATUS_EDITOR) {
            type = "dungeon";
            source = "Editor";
        } else if (mode == Application.STATUS_GAME) {
            type = "game";
            source = "DungeonDiver4";
        } else {
            // Not in the game or editor, so abort
            return JOptionPane.NO_OPTION;
        }
        return CommonDialogs.showYNCConfirmDialog("Do you want to save your "
                + type + "?", source);
    }

    public boolean getLoaded() {
        return this.loaded;
    }

    public void setLoaded(final boolean status) {
        Application app = DungeonDiver4.getApplication();
        this.loaded = status;
        app.getMenuManager().checkFlags();
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(boolean newDirty) {
        Application app = DungeonDiver4.getApplication();
        this.isDirty = newDirty;
        JFrame frame = app.getOutputFrame();
        if (frame != null) {
            frame.getRootPane().putClientProperty("Window.documentModified",
                    Boolean.valueOf(newDirty));
        }
        app.getMenuManager().checkFlags();
    }

    public void clearLastUsedFilenames() {
        this.lastUsedDungeonFile = "";
        this.lastUsedGameFile = "";
    }

    public String getLastUsedDungeon() {
        return this.lastUsedDungeonFile;
    }

    public String getLastUsedGame() {
        return this.lastUsedGameFile;
    }

    public void setLastUsedDungeon(String newFile) {
        this.lastUsedDungeonFile = newFile;
    }

    public void setLastUsedGame(String newFile) {
        this.lastUsedGameFile = newFile;
    }

    public String getScoresFileName() {
        return this.scoresFileName;
    }

    public void setScoresFileName(String filename) {
        this.scoresFileName = filename;
    }

    public void loadFromOSHandler(String filename) { // NO_UCD
        Application app = DungeonDiver4.getApplication();
        if (!this.loaded) {
            String extension;
            final File file = new File(filename);
            String loadFile;
            loadFile = file.getAbsolutePath();
            extension = DungeonManager.getExtension(file);
            app.getGameManager().resetObjectInventory();
            if (extension.equals(Extension.getDungeonExtension())) {
                this.lastUsedDungeonFile = loadFile;
                this.scoresFileName = DungeonManager
                        .getNameWithoutExtension(file.getName());
                DungeonManager.loadFile(loadFile, false, false);
            } else if (extension.equals(Extension.getGameExtension())) {
                this.lastUsedDungeonFile = loadFile;
                this.scoresFileName = DungeonManager
                        .getNameWithoutExtension(file.getName());
                DungeonManager.loadFile(loadFile, false, true);
            } else if (extension.equals(Extension.getSavedGameExtension())) {
                this.lastUsedGameFile = loadFile;
                DungeonManager.loadFile(loadFile, true, false);
            } else if (extension.equals(Extension.getPreferencesExtension())) {
                CommonDialogs
                        .showDialog("You double-clicked a preferences file. These are automatically loaded when the program is loaded, and need not be double-clicked.");
            } else if (extension.equals(Extension.getRuleSetExtension())) {
                CommonDialogs
                        .showDialog("You double-clicked a rule set file. These are loaded by the Rule Set Picker, and need not be double-clicked.");
            }
        }
    }

    public boolean loadDungeon() {
        Application app = DungeonDiver4.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        String lastOpen = PreferencesManager.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final DungeonFilter xmf = new DungeonFilter();
        final SavedGameFilter xgf = new SavedGameFilter();
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = this.saveDungeon();
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
            int filter = PreferencesManager.getLastFilterUsedOpen();
            if (filter == PreferencesManager.FILTER_DUNGEON) {
                fc.setFileFilter(xmf);
            } else {
                fc.setFileFilter(xgf);
            }
            final int returnVal = fc.showOpenDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                FileFilter ff = fc.getFileFilter();
                if (ff.getDescription().equals(xmf.getDescription())) {
                    PreferencesManager
                            .setLastFilterUsedOpen(PreferencesManager.FILTER_DUNGEON);
                } else {
                    PreferencesManager
                            .setLastFilterUsedOpen(PreferencesManager.FILTER_GAME);
                }
                PreferencesManager.setLastDirOpen(fc.getCurrentDirectory()
                        .getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = DungeonManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(Extension.getDungeonExtension())) {
                    this.lastUsedDungeonFile = filename;
                    this.scoresFileName = DungeonManager
                            .getNameWithoutExtension(file.getName());
                    DungeonManager.loadFile(filename, false, false);
                } else if (extension.equals(Extension.getSavedGameExtension())) {
                    this.lastUsedGameFile = filename;
                    DungeonManager.loadFile(filename, true, false);
                } else {
                    CommonDialogs
                            .showDialog("You opened something other than a dungeon file. Select a dungeon file, and try again.");
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
        Application app = DungeonDiver4.getApplication();
        int status = 0;
        boolean saved = true;
        String filename;
        final GameFinder gf = new GameFinder();
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = this.saveDungeon();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                this.setDirty(false);
            }
        }
        if (saved) {
            String gameDir = DungeonManager.getGameDirectory();
            String[] rawChoices = new File(gameDir).list(gf);
            if (rawChoices != null) {
                String[] choices = new String[rawChoices.length];
                // Strip extension
                for (int x = 0; x < choices.length; x++) {
                    choices[x] = DungeonManager
                            .getNameWithoutExtension(rawChoices[x]);
                }
                String returnVal = CommonDialogs.showInputDialog(
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
                        this.lastUsedDungeonFile = filename;
                        this.scoresFileName = DungeonManager
                                .getNameWithoutExtension(file.getName());
                        DungeonManager.loadFile(filename, false, true);
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
        Application app = DungeonDiver4.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        String lastOpen = PreferencesManager.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final GameFilter gf = new GameFilter();
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = this.saveDungeon();
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
                PreferencesManager.setLastDirOpen(fc.getCurrentDirectory()
                        .getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = DungeonManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(Extension.getGameExtension())) {
                    this.lastUsedDungeonFile = filename;
                    this.scoresFileName = DungeonManager
                            .getNameWithoutExtension(file.getName());
                    // Make sure folder exists
                    if (!file.getParentFile().exists()) {
                        boolean okay = file.getParentFile().mkdirs();
                        if (!okay) {
                            DungeonDiver4.getErrorLogger().logError(
                                    new IOException(
                                            "Cannot create game folder!"));
                        }
                    }
                    try {
                        DirectoryUtilities.copyFile(file,
                                new File(DungeonManager.getGameDirectory()
                                        + file.getName()));
                    } catch (IOException ioe) {
                        DungeonDiver4.getErrorLogger().logError(ioe);
                    }
                    DungeonManager.loadFile(filename, false, true);
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

    private static void loadFile(String filename, final boolean isSavedGame,
            final boolean locked) {
        if (!FilenameChecker.isFilenameOK(DungeonManager
                .getNameWithoutExtension(DungeonManager
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
                GameLoadTask llt = new GameLoadTask(filename);
                llt.start();
            } else {
                LoadTask xlt = new LoadTask(filename, isSavedGame);
                xlt.start();
            }
        }
    }

    public boolean saveDungeon() {
        Application app = DungeonDiver4.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            if (this.lastUsedGameFile != null
                    && !this.lastUsedGameFile.equals("")) {
                String extension = DungeonManager
                        .getExtension(this.lastUsedGameFile);
                if (extension != null) {
                    if (!(extension.equals(Extension.getSavedGameExtension()))) {
                        this.lastUsedGameFile = DungeonManager
                                .getNameWithoutExtension(this.lastUsedGameFile)
                                + Extension.getSavedGameExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedGameFile += Extension
                            .getSavedGameExtensionWithPeriod();
                }
                DungeonManager.saveFile(this.lastUsedGameFile, true, false);
            } else {
                return this.saveDungeonAs();
            }
        } else {
            if (this.lastUsedDungeonFile != null
                    && !this.lastUsedDungeonFile.equals("")) {
                String extension = DungeonManager
                        .getExtension(this.lastUsedDungeonFile);
                if (extension != null) {
                    if (!(extension.equals(Extension.getDungeonExtension()))) {
                        this.lastUsedDungeonFile = DungeonManager
                                .getNameWithoutExtension(this.lastUsedDungeonFile)
                                + Extension.getDungeonExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedDungeonFile += Extension
                            .getDungeonExtensionWithPeriod();
                }
                DungeonManager.saveFile(this.lastUsedDungeonFile, false, false);
            } else {
                return this.saveDungeonAs();
            }
        }
        return false;
    }

    public boolean saveDungeonAs() {
        Application app = DungeonDiver4.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        String lastSave = PreferencesManager.getLastDirSave();
        File lastSaveDir = null;
        if (lastSave != null) {
            lastSaveDir = new File(lastSave);
        }
        final JFileChooser fc = new JFileChooser(lastSaveDir);
        final DungeonFilter xmf = new DungeonFilter();
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
                extension = DungeonManager.getExtension(file);
                filename = file.getAbsolutePath();
                String dirOnly = fc.getCurrentDirectory().getAbsolutePath();
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
                            if (!(extension.equals(Extension
                                    .getSavedGameExtension()))) {
                                filename = DungeonManager
                                        .getNameWithoutExtension(file)
                                        + Extension
                                                .getSavedGameExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension
                                    .getSavedGameExtensionWithPeriod();
                        }
                        this.lastUsedGameFile = filename;
                        DungeonManager.saveFile(filename, true, false);
                    } else {
                        if (extension != null) {
                            if (!(extension.equals(Extension
                                    .getDungeonExtension()))) {
                                filename = DungeonManager
                                        .getNameWithoutExtension(file)
                                        + Extension
                                                .getDungeonExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension
                                    .getDungeonExtensionWithPeriod();
                        }
                        this.lastUsedDungeonFile = filename;
                        this.scoresFileName = DungeonManager
                                .getNameWithoutExtension(file.getName());
                        DungeonManager.saveFile(filename, false, false);
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
                final File file = new File(DungeonManager.getGameDirectory()
                        + returnVal + extension);
                filename = file.getAbsolutePath();
                if (!FilenameChecker.isFilenameOK(returnVal)) {
                    CommonDialogs
                            .showErrorDialog(
                                    "The file name you entered contains illegal characters.\n"
                                            + "These characters are not allowed: /?<>\\:|\"\n"
                                            + "Files named con, nul, or prn are illegal, as are files\n"
                                            + "named com1 through com9 and lpt1 through lpt9.",
                                    "Save Game");
                } else {
                    this.lastUsedDungeonFile = filename;
                    this.scoresFileName = DungeonManager
                            .getNameWithoutExtension(file.getName());
                    // Make sure folder exists
                    if (!file.getParentFile().exists()) {
                        boolean okay = file.getParentFile().mkdirs();
                        if (!okay) {
                            DungeonDiver4.getErrorLogger().logError(
                                    new IOException(
                                            "Cannot create game folder!"));
                        }
                    }
                    DungeonManager.saveFile(filename, false, true);
                }
            } else {
                break;
            }
        }
        return false;
    }

    public boolean exportGame() {
        Application app = DungeonDiver4.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        String lastSave = PreferencesManager.getLastDirSave();
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
                extension = DungeonManager.getExtension(file);
                filename = file.getAbsolutePath();
                String dirOnly = fc.getCurrentDirectory().getAbsolutePath();
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
                        if (!(extension.equals(Extension.getGameExtension()))) {
                            filename = DungeonManager
                                    .getNameWithoutExtension(file)
                                    + Extension.getGameExtensionWithPeriod();
                        }
                    } else {
                        filename += Extension.getGameExtensionWithPeriod();
                    }
                    this.lastUsedDungeonFile = filename;
                    this.scoresFileName = DungeonManager
                            .getNameWithoutExtension(file.getName());
                    DungeonManager.saveFile(filename, false, true);
                }
            } else {
                break;
            }
        }
        return false;
    }

    private static void saveFile(String filename, boolean isSavedGame,
            boolean locked) {
        final String sg;
        if (isSavedGame) {
            sg = "Saved Game";
        } else {
            if (locked) {
                sg = "Locked Dungeon";
            } else {
                sg = "Dungeon";
            }
        }
        DungeonDiver4.getApplication().showMessage("Saving " + sg + " file...");
        if (locked) {
            GameSaveTask lst = new GameSaveTask(filename);
            lst.start();
        } else {
            SaveTask xst = new SaveTask(filename, isSavedGame);
            xst.start();
        }
    }

    private static String getGameDirectoryPrefix() {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(DungeonManager.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(DungeonManager.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(DungeonManager.UNIX_PREFIX);
        }
    }

    private static String getGameDirectoryName() {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return DungeonManager.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return DungeonManager.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return DungeonManager.UNIX_DIR;
        }
    }

    private static String getGameDirectory() {
        StringBuilder b = new StringBuilder();
        b.append(DungeonManager.getGameDirectoryPrefix());
        b.append(DungeonManager.getGameDirectoryName());
        return b.toString();
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
