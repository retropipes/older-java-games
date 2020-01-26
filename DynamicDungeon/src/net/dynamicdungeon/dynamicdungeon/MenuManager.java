/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dynamicdungeon.creatures.characterfiles.CharacterRegistration;
import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.GenerateTask;
import net.dynamicdungeon.dynamicdungeon.game.InventoryViewer;
import net.dynamicdungeon.dynamicdungeon.game.NoteManager;
import net.dynamicdungeon.dynamicdungeon.game.StatisticsViewer;
import net.dynamicdungeon.dynamicdungeon.prefs.PreferencesManager;

public class MenuManager {
    // Fields
    JMenuBar mainMenuBar;
    private JMenuItem fileOpenGame, fileClose, fileSaveGame;
    private JMenuItem gameNewGame, gameEquipment, gameInventory,
            gameRegisterCharacter, gameUnregisterCharacter, gameRemoveCharacter,
            gameEditNote, gameViewStats;
    private JMenuItem helpGeneralHelp, helpObjectHelp;
    private KeyStroke fileOpenGameAccel, fileCloseAccel, fileSaveGameAccel;
    private KeyStroke gameNewGameAccel, gameEquipmentAccel, gameInventoryAccel,
            gameEditNoteAccel, gameViewStatsAccel;
    private final EventHandler handler;

    // Constructors
    public MenuManager() {
        this.handler = new EventHandler();
        this.createAccelerators();
        this.createMenus();
        this.setInitialMenuState();
    }

    // Methods
    public JMenuBar getMainMenuBar() {
        return this.mainMenuBar;
    }

    public void setGameMenus() {
        this.fileOpenGame.setEnabled(false);
        this.gameNewGame.setEnabled(false);
        this.enableGameMenus();
        this.checkFlags();
    }

    public void setPrefMenus() {
        this.fileOpenGame.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.gameNewGame.setEnabled(false);
        this.disableGameMenus();
    }

    public void setHelpMenus() {
        this.fileOpenGame.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.gameNewGame.setEnabled(false);
        this.disableGameMenus();
    }

    public void setMainMenus() {
        this.fileOpenGame.setEnabled(true);
        this.gameNewGame.setEnabled(true);
        this.disableGameMenus();
        this.checkFlags();
    }

    private void enableGameMenus() {
        this.gameEquipment.setEnabled(true);
        this.gameInventory.setEnabled(true);
        this.gameEditNote.setEnabled(true);
        this.gameViewStats.setEnabled(true);
    }

    private void disableGameMenus() {
        this.gameEquipment.setEnabled(false);
        this.gameInventory.setEnabled(false);
        this.gameEditNote.setEnabled(false);
        this.gameViewStats.setEnabled(false);
    }

    public void checkFlags() {
        final Application app = DynamicDungeon.getApplication();
        if (app.getDungeonManager().getDirty()) {
            this.setMenusDirtyOn();
        } else {
            this.setMenusDirtyOff();
        }
        if (app.getDungeonManager().getLoaded()) {
            this.setMenusLoadedOn();
        } else {
            this.setMenusLoadedOff();
        }
    }

    private void setMenusDirtyOn() {
        this.fileSaveGame.setEnabled(true);
    }

    private void setMenusDirtyOff() {
        this.fileSaveGame.setEnabled(false);
    }

    private void setMenusLoadedOn() {
        final Application app = DynamicDungeon.getApplication();
        if (app.getMode() == Application.STATUS_GUI) {
            this.fileClose.setEnabled(false);
        } else {
            this.fileClose.setEnabled(true);
        }
    }

    private void setMenusLoadedOff() {
        this.fileClose.setEnabled(false);
    }

    private void createAccelerators() {
        final int modKey = InputEvent.META_DOWN_MASK;
        this.fileOpenGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O, modKey);
        this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W, modKey);
        this.fileSaveGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey);
        this.gameNewGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N, modKey);
        this.gameEquipmentAccel = KeyStroke.getKeyStroke(KeyEvent.VK_G, modKey);
        this.gameInventoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_I, modKey);
        this.gameEditNoteAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E, modKey);
        this.gameViewStatsAccel = KeyStroke.getKeyStroke(KeyEvent.VK_T, modKey);
    }

    private void createMenus() {
        this.mainMenuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        final JMenu gameMenu = new JMenu("Game");
        final JMenu helpMenu = new JMenu("Help");
        this.fileOpenGame = new JMenuItem("Open Game...");
        this.fileOpenGame.setAccelerator(this.fileOpenGameAccel);
        this.fileClose = new JMenuItem("Close");
        this.fileClose.setAccelerator(this.fileCloseAccel);
        this.fileSaveGame = new JMenuItem("Save Game...");
        this.fileSaveGame.setAccelerator(this.fileSaveGameAccel);
        this.gameNewGame = new JMenuItem("New Game");
        this.gameNewGame.setAccelerator(this.gameNewGameAccel);
        this.gameRegisterCharacter = new JMenuItem("Register Character...");
        this.gameUnregisterCharacter = new JMenuItem("Unregister Character...");
        this.gameRemoveCharacter = new JMenuItem("Remove Character...");
        this.gameEquipment = new JMenuItem("Show Equipment...");
        this.gameEquipment.setAccelerator(this.gameEquipmentAccel);
        this.gameInventory = new JMenuItem("Show Inventory...");
        this.gameInventory.setAccelerator(this.gameInventoryAccel);
        this.gameEditNote = new JMenuItem("Edit Note...");
        this.gameEditNote.setAccelerator(this.gameEditNoteAccel);
        this.gameViewStats = new JMenuItem("View Statistics...");
        this.gameViewStats.setAccelerator(this.gameViewStatsAccel);
        this.helpGeneralHelp = new JMenuItem("DynamicDungeon Help");
        this.helpObjectHelp = new JMenuItem("DynamicDungeon Object Help");
        this.fileOpenGame.addActionListener(this.handler);
        this.fileClose.addActionListener(this.handler);
        this.fileSaveGame.addActionListener(this.handler);
        this.gameNewGame.addActionListener(this.handler);
        this.gameRegisterCharacter.addActionListener(this.handler);
        this.gameUnregisterCharacter.addActionListener(this.handler);
        this.gameRemoveCharacter.addActionListener(this.handler);
        this.gameEquipment.addActionListener(this.handler);
        this.gameInventory.addActionListener(this.handler);
        this.gameEditNote.addActionListener(this.handler);
        this.gameViewStats.addActionListener(this.handler);
        this.helpGeneralHelp.addActionListener(this.handler);
        this.helpObjectHelp.addActionListener(this.handler);
        fileMenu.add(this.fileOpenGame);
        fileMenu.add(this.fileClose);
        fileMenu.add(this.fileSaveGame);
        gameMenu.add(this.gameNewGame);
        gameMenu.add(this.gameEquipment);
        gameMenu.add(this.gameInventory);
        gameMenu.add(this.gameRegisterCharacter);
        gameMenu.add(this.gameUnregisterCharacter);
        gameMenu.add(this.gameRemoveCharacter);
        gameMenu.add(this.gameEditNote);
        gameMenu.add(this.gameViewStats);
        helpMenu.add(this.helpGeneralHelp);
        helpMenu.add(this.helpObjectHelp);
        this.mainMenuBar.add(fileMenu);
        this.mainMenuBar.add(gameMenu);
        this.mainMenuBar.add(helpMenu);
    }

    private void setInitialMenuState() {
        this.fileOpenGame.setEnabled(true);
        this.fileClose.setEnabled(false);
        this.fileSaveGame.setEnabled(false);
        this.gameNewGame.setEnabled(true);
        this.disableGameMenus();
        this.gameRegisterCharacter.setEnabled(true);
        this.gameUnregisterCharacter.setEnabled(true);
        this.gameRemoveCharacter.setEnabled(true);
        this.helpObjectHelp.setEnabled(true);
    }

    private class EventHandler implements ActionListener {
        public EventHandler() {
            // Do nothing
        }

        // Handle menus
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final String cmd = e.getActionCommand();
                final Application app = DynamicDungeon.getApplication();
                boolean loaded = false;
                if (cmd.equals("Open Game...")) {
                    loaded = app.getDungeonManager().loadGame();
                    app.getDungeonManager().setLoaded(loaded);
                } else if (cmd.equals("Close")) {
                    // Close the window
                    if (app.getMode() == Application.STATUS_GAME) {
                        boolean saved = true;
                        int status = 0;
                        if (app.getDungeonManager().getDirty()) {
                            app.getDungeonManager();
                            status = DungeonManager.showSaveDialog();
                            if (status == JOptionPane.YES_OPTION) {
                                app.getDungeonManager();
                                saved = DungeonManager.saveGame();
                            } else if (status == JOptionPane.CANCEL_OPTION) {
                                saved = false;
                            } else {
                                app.getDungeonManager().setDirty(false);
                            }
                        }
                        if (saved) {
                            app.getGameManager().exitGame();
                        }
                    }
                } else if (cmd.equals("Save Game...")) {
                    if (app.getDungeonManager().getLoaded()) {
                        app.getDungeonManager();
                        DungeonManager.saveGame();
                    } else {
                        CommonDialogs.showDialog("No Dungeon Opened");
                    }
                } else if (cmd.equals("Exit")) {
                    // Exit program
                    if (app.getGUIManager().quitHandler()) {
                        System.exit(0);
                    }
                } else if (cmd.equals("Preferences...")) {
                    // Show preferences dialog
                    PreferencesManager.showPrefs();
                } else if (cmd.equals("New Game")) {
                    // Start a new game
                    final boolean proceed = app.getGameManager().newGame();
                    if (proceed) {
                        new GenerateTask(true).start();
                    }
                } else if (cmd.equals("Register Character...")) {
                    // Register Character
                    CharacterRegistration.registerCharacter();
                } else if (cmd.equals("Unregister Character...")) {
                    // Unregister Character
                    CharacterRegistration.unregisterCharacter();
                } else if (cmd.equals("Remove Character...")) {
                    // Confirm
                    final int confirm = CommonDialogs.showConfirmDialog(
                            "WARNING: This will DELETE the character from disk,\n"
                                    + "and CANNOT be undone! Proceed anyway?",
                            "Remove Character");
                    if (confirm == CommonDialogs.YES_OPTION) {
                        // Remove Character
                        CharacterRegistration.removeCharacter();
                    }
                } else if (cmd.equals("Show Equipment...")) {
                    InventoryViewer.showEquipmentDialog();
                } else if (cmd.equals("Show Inventory...")) {
                    InventoryViewer.showItemInventoryDialog();
                } else if (cmd.equals("Edit Note...")) {
                    // Edit Note
                    NoteManager.editNote();
                } else if (cmd.equals("View Statistics...")) {
                    // View Statistics
                    StatisticsViewer.viewStatistics();
                } else if (cmd.equals("About DynamicDungeon...")) {
                    app.getAboutDialog().showAboutDialog();
                } else if (cmd.equals("DynamicDungeon Help")) {
                    app.getGeneralHelpManager().showHelp();
                } else if (cmd.equals("DynamicDungeon Object Help")) {
                    app.getObjectHelpManager().showHelp();
                }
                MenuManager.this.checkFlags();
            } catch (final Throwable t) {
                DynamicDungeon.getErrorLogger().logError(t);
            }
        }
    }
}
