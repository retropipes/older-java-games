/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.world;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.WorldObject;

public class WorldManager {
    // Fields
    private World gameWorld;
    private boolean loaded, isDirty;
    private String lastUsedWorldFile;
    private String lastUsedGameFile;

    // Constructors
    public WorldManager() {
        this.loaded = false;
        this.isDirty = false;
        this.lastUsedWorldFile = "";
        this.lastUsedGameFile = "";
    }

    // Methods
    public World getWorld() {
        return this.gameWorld;
    }

    public void setWorld(final World newWorld) {
        this.gameWorld = newWorld;
    }

    public void handleDeferredSuccess(final boolean value) {
        if (value) {
            this.setLoaded(true);
        }
        this.setDirty(false);
        Worldz.getApplication().getGameManager().stateChanged();
        Worldz.getApplication().getMenuManager().checkFlags();
    }

    public WorldObject getWorldObject(final int x, final int y, final int z,
            final int e) {
        try {
            return this.gameWorld.getCell(x, y, z, e);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return null;
        }
    }

    public boolean quitHandler() {
        if (Worldz.inWorldz()) {
            boolean saved = true;
            int status = JOptionPane.DEFAULT_OPTION;
            if (this.getDirty()) {
                status = this.showSaveDialog();
                if (status == JOptionPane.YES_OPTION) {
                    saved = this.saveWorld();
                } else if (status == JOptionPane.CANCEL_OPTION) {
                    saved = false;
                } else {
                    this.setDirty(false);
                }
            }
            if (saved) {
                Worldz.getApplication().getPrefsManager().writePrefs();
                // Run cleanup task
                new TempDirCleanup().start();
            }
            return saved;
        } else {
            return true;
        }
    }

    public int showSaveDialog() {
        String type, source;
        final Application app = Worldz.getApplication();
        final int mode = app.getMode();
        if (mode == Application.STATUS_EDITOR) {
            type = "world";
            source = "Editor";
        } else if (mode == Application.STATUS_GAME
                || mode == Application.STATUS_BATTLE) {
            type = "game";
            source = "Worldz";
        } else {
            // Not in the game, battle, or editor, so abort
            return JOptionPane.NO_OPTION;
        }
        int status = JOptionPane.DEFAULT_OPTION;
        status = Messager.showYNCConfirmDialog(
                "Do you want to save your " + type + "?", source);
        return status;
    }

    public boolean getLoaded() {
        return this.loaded;
    }

    public void setLoaded(final boolean status) {
        final Application app = Worldz.getApplication();
        this.loaded = status;
        app.getMenuManager().checkFlags();
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        final Application app = Worldz.getApplication();
        this.isDirty = newDirty;
        final JFrame frame = app.getOutputFrame();
        if (frame != null) {
            frame.getRootPane().putClientProperty("Window.documentModified",
                    Boolean.valueOf(newDirty));
        }
        app.getMenuManager().checkFlags();
    }

    public void clearLastUsedFilenames() {
        this.lastUsedWorldFile = "";
        this.lastUsedGameFile = "";
    }

    public String getLastUsedWorld() {
        return this.lastUsedWorldFile;
    }

    public String getLastUsedGame() {
        return this.lastUsedGameFile;
    }

    public void setLastUsedWorld(final String newFile) {
        this.lastUsedWorldFile = newFile;
    }

    public void setLastUsedGame(final String newFile) {
        this.lastUsedGameFile = newFile;
    }

    public boolean loadWorld() {
        final Application app = Worldz.getApplication();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = app.getPrefsManager().getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final WorldFilter mmf = new WorldFilter();
        final SavedGameFilter mgf = new SavedGameFilter();
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == JOptionPane.YES_OPTION) {
                saved = this.saveWorld();
            } else if (status == JOptionPane.CANCEL_OPTION) {
                saved = false;
            } else {
                this.setDirty(false);
            }
        }
        if (saved) {
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(mmf);
            fc.addChoosableFileFilter(mgf);
            final int filter = app.getPrefsManager().getLastFilterUsedIndex();
            if (filter == PreferencesManager.FILTER_WORLD_V1) {
                fc.setFileFilter(mmf);
            } else {
                fc.setFileFilter(mgf);
            }
            final int returnVal = fc.showOpenDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                final FileFilter ff = fc.getFileFilter();
                if (ff.getDescription().equals(mmf.getDescription())) {
                    app.getPrefsManager().setLastFilterUsedIndex(
                            PreferencesManager.FILTER_WORLD_V1);
                } else {
                    app.getPrefsManager().setLastFilterUsedIndex(
                            PreferencesManager.FILTER_GAME);
                }
                app.getPrefsManager().setLastDirOpen(
                        fc.getCurrentDirectory().getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = WorldManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(Extension.getGameExtension())) {
                    this.lastUsedGameFile = filename;
                    WorldManager.loadFile(filename, true,
                            FormatConstants.WORLD_FORMAT_1);
                } else if (extension.equals(Extension.getWorldExtension())) {
                    this.lastUsedWorldFile = filename;
                    WorldManager.loadFile(filename, false,
                            FormatConstants.WORLD_FORMAT_1);
                } else if (extension.equals(Extension.getWorldExtension())) {
                    this.lastUsedWorldFile = filename;
                    WorldManager.loadFile(filename, false,
                            FormatConstants.WORLD_FORMAT_1);
                } else if (extension.equals(Extension.getGameExtension())) {
                    this.lastUsedGameFile = filename;
                    WorldManager.loadFile(filename, true,
                            FormatConstants.WORLD_FORMAT_1);
                } else {
                    Messager.showDialog(
                            "You opened something other than a world file. Select a world file, and try again.");
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

    public void loadFromOSHandler(final String infilename) {
        final Application app = Worldz.getApplication();
        if (!this.loaded) {
            String extension;
            final File file = new File(infilename);
            final String filename = file.getAbsolutePath();
            extension = WorldManager.getExtension(file);
            app.getGameManager().resetObjectInventory();
            if (extension.equals(Extension.getGameExtension())) {
                this.lastUsedGameFile = filename;
                WorldManager.loadFile(filename, true,
                        FormatConstants.WORLD_FORMAT_1);
            } else if (extension.equals(Extension.getWorldExtension())) {
                this.lastUsedWorldFile = filename;
                WorldManager.loadFile(filename, false,
                        FormatConstants.WORLD_FORMAT_1);
            } else if (extension.equals(Extension.getWorldExtension())) {
                this.lastUsedWorldFile = filename;
                WorldManager.loadFile(filename, false,
                        FormatConstants.WORLD_FORMAT_1);
            } else if (extension.equals(Extension.getGameExtension())) {
                this.lastUsedGameFile = filename;
                WorldManager.loadFile(filename, true,
                        FormatConstants.WORLD_FORMAT_1);
            } else if (extension.equals(Extension.getPreferencesExtension())) {
                Messager.showDialog(
                        "You double-clicked a preferences file. These are automatically loaded when the program is loaded, and need not be double-clicked.");
            } else if (extension.equals(Extension.getPluginExtension())) {
                Messager.showDialog(
                        "You double-clicked a plugin file. These are automatically loaded when needed, and need not be double-clicked.");
            } else if (extension.equals(Extension.getMusicExtension())) {
                Messager.showDialog(
                        "You double-clicked a music file. These are automatically loaded when needed, and need not be double-clicked.");
            }
        }
    }

    private static void loadFile(final String filename,
            final boolean isSavedGame, final int formatVersion) {
        if (!FilenameChecker.isFilenameOK(WorldManager.getNameWithoutExtension(
                WorldManager.getFileNameOnly(filename)))) {
            Messager.showErrorDialog(
                    "The file you selected contains illegal characters in its\n"
                            + "name. These characters are not allowed: /?<>\\:|\"\n"
                            + "Files named con, nul, or prn are illegal, as are files\n"
                            + "named com1 through com9 and lpt1 through lpt9.",
                    "Load");
        } else {
            if (formatVersion == FormatConstants.WORLD_FORMAT_1) {
                final LoadTask lt = new LoadTask(filename, isSavedGame);
                lt.start();
            }
        }
    }

    public boolean saveWorld() {
        final Application app = Worldz.getApplication();
        if (app.getMode() == Application.STATUS_GAME) {
            if (this.lastUsedGameFile != null
                    && !this.lastUsedGameFile.equals("")) {
                final String extension = WorldManager
                        .getExtension(this.lastUsedGameFile);
                if (extension != null) {
                    if (!extension.equals(Extension.getGameExtension())) {
                        this.lastUsedGameFile = WorldManager
                                .getNameWithoutExtension(this.lastUsedGameFile)
                                + Extension.getGameExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedGameFile += Extension
                            .getGameExtensionWithPeriod();
                }
                WorldManager.saveFile(this.lastUsedGameFile, true);
            } else {
                return this.saveWorldAs();
            }
        } else {
            if (this.lastUsedWorldFile != null
                    && !this.lastUsedWorldFile.equals("")) {
                final String extension = WorldManager
                        .getExtension(this.lastUsedWorldFile);
                if (extension != null) {
                    if (!extension.equals(Extension.getWorldExtension())) {
                        this.lastUsedWorldFile = WorldManager
                                .getNameWithoutExtension(this.lastUsedWorldFile)
                                + Extension.getWorldExtensionWithPeriod();
                    }
                } else {
                    this.lastUsedWorldFile += Extension
                            .getWorldExtensionWithPeriod();
                }
                WorldManager.saveFile(this.lastUsedWorldFile, false);
            } else {
                return this.saveWorldAs();
            }
        }
        return false;
    }

    public boolean saveWorldAs() {
        final Application app = Worldz.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        final String lastSave = app.getPrefsManager().getLastDirSave();
        File lastSaveDir = null;
        if (lastSave != null) {
            lastSaveDir = new File(lastSave);
        }
        final JFileChooser fc = new JFileChooser(lastSaveDir);
        final WorldFilter mmf = new WorldFilter();
        final SavedGameFilter gf = new SavedGameFilter();
        fc.setAcceptAllFileFilterUsed(false);
        if (app.getMode() == Application.STATUS_GAME) {
            fc.addChoosableFileFilter(gf);
            fc.setFileFilter(gf);
        } else {
            fc.addChoosableFileFilter(mmf);
            fc.setFileFilter(mmf);
        }
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            final int returnVal = fc.showSaveDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                extension = WorldManager.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = fc.getCurrentDirectory()
                        .getAbsolutePath();
                fileOnly = filename.substring(dirOnly.length() + 1);
                if (!FilenameChecker.isFilenameOK(fileOnly)) {
                    Messager.showErrorDialog(
                            "The file name you entered contains illegal characters.\n"
                                    + "These characters are not allowed: /?<>\\:|\"\n"
                                    + "Files named con, nul, or prn are illegal, as are files\n"
                                    + "named com1 through com9 and lpt1 through lpt9.",
                            "Save");
                } else {
                    app.getPrefsManager().setLastDirSave(
                            fc.getCurrentDirectory().getAbsolutePath());
                    if (app.getMode() == Application.STATUS_GAME) {
                        if (extension != null) {
                            if (!extension
                                    .equals(Extension.getGameExtension())) {
                                filename = WorldManager
                                        .getNameWithoutExtension(file)
                                        + Extension
                                                .getGameExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension.getGameExtensionWithPeriod();
                        }
                        this.lastUsedGameFile = filename;
                        WorldManager.saveFile(filename, true);
                    } else {
                        if (extension != null) {
                            if (!extension
                                    .equals(Extension.getWorldExtension())) {
                                filename = WorldManager
                                        .getNameWithoutExtension(file)
                                        + Extension
                                                .getWorldExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension.getWorldExtensionWithPeriod();
                        }
                        this.lastUsedWorldFile = filename;
                        WorldManager.saveFile(filename, false);
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
            sg = "World";
        }
        Messager.showMessage("Saving " + sg + " file...");
        final SaveTask st = new SaveTask(filename, isSavedGame);
        st.start();
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
