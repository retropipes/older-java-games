/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import studio.ignitionigloogames.chrystalz.creatures.characterfiles.CharacterRegistration;
import studio.ignitionigloogames.chrystalz.dungeon.GenerateTask;
import studio.ignitionigloogames.chrystalz.game.InventoryViewer;
import studio.ignitionigloogames.chrystalz.game.StatisticsViewer;
import studio.ignitionigloogames.chrystalz.manager.dungeon.DungeonManager;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;

public class MenuManager {
    // Fields
    JMenuBar mainMenuBar;
    private JMenuItem fileOpenGame, fileClose, fileSaveGame, filePreferences,
            fileExit;
    private JMenuItem gameNewGame, gameEquipment, gameRegisterCharacter,
            gameUnregisterCharacter, gameRemoveCharacter, gameViewStats;
    private JMenuItem helpAbout;
    private KeyStroke fileOpenGameAccel, fileCloseAccel, fileSaveGameAccel,
            filePreferencesAccel;
    private KeyStroke gameNewGameAccel;
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
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.gameNewGame.setEnabled(false);
        this.enableGameMenus();
        this.checkFlags();
    }

    public void setPrefMenus() {
        this.fileOpenGame.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(false);
        this.gameNewGame.setEnabled(false);
        this.disableGameMenus();
    }

    public void setHelpMenus() {
        this.fileOpenGame.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(false);
        this.gameNewGame.setEnabled(false);
        this.disableGameMenus();
    }

    public void setMainMenus() {
        this.fileOpenGame.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.gameNewGame.setEnabled(true);
        this.disableGameMenus();
        this.checkFlags();
    }

    private void enableGameMenus() {
        this.gameEquipment.setEnabled(true);
        this.gameViewStats.setEnabled(true);
    }

    private void disableGameMenus() {
        this.gameEquipment.setEnabled(false);
        this.gameViewStats.setEnabled(false);
    }

    public void checkFlags() {
        final Application app = Chrystalz.getApplication();
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
        final Application app = Chrystalz.getApplication();
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
        int modKey;
        if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            modKey = InputEvent.META_DOWN_MASK;
        } else {
            modKey = InputEvent.CTRL_DOWN_MASK;
        }
        this.fileOpenGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O, modKey);
        this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W, modKey);
        this.fileSaveGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey);
        this.filePreferencesAccel = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,
                modKey);
        this.gameNewGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N, modKey);
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
        this.fileExit = new JMenuItem("Exit");
        this.filePreferences = new JMenuItem("Preferences...");
        this.filePreferences.setAccelerator(this.filePreferencesAccel);
        this.gameNewGame = new JMenuItem("New Game");
        this.gameNewGame.setAccelerator(this.gameNewGameAccel);
        this.gameRegisterCharacter = new JMenuItem("Register Character...");
        this.gameUnregisterCharacter = new JMenuItem("Unregister Character...");
        this.gameRemoveCharacter = new JMenuItem("Remove Character...");
        this.gameEquipment = new JMenuItem("Show Equipment...");
        this.gameViewStats = new JMenuItem("View Statistics...");
        this.helpAbout = new JMenuItem("About Chrystalz...");
        this.fileOpenGame.addActionListener(this.handler);
        this.fileClose.addActionListener(this.handler);
        this.fileSaveGame.addActionListener(this.handler);
        this.fileExit.addActionListener(this.handler);
        this.filePreferences.addActionListener(this.handler);
        this.gameNewGame.addActionListener(this.handler);
        this.gameRegisterCharacter.addActionListener(this.handler);
        this.gameUnregisterCharacter.addActionListener(this.handler);
        this.gameRemoveCharacter.addActionListener(this.handler);
        this.gameEquipment.addActionListener(this.handler);
        this.gameViewStats.addActionListener(this.handler);
        this.helpAbout.addActionListener(this.handler);
        fileMenu.add(this.fileOpenGame);
        fileMenu.add(this.fileClose);
        fileMenu.add(this.fileSaveGame);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            fileMenu.add(this.filePreferences);
            fileMenu.add(this.fileExit);
        }
        gameMenu.add(this.gameNewGame);
        gameMenu.add(this.gameEquipment);
        gameMenu.add(this.gameRegisterCharacter);
        gameMenu.add(this.gameUnregisterCharacter);
        gameMenu.add(this.gameRemoveCharacter);
        gameMenu.add(this.gameViewStats);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            helpMenu.add(this.helpAbout);
        }
        this.mainMenuBar.add(fileMenu);
        this.mainMenuBar.add(gameMenu);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.mainMenuBar.add(helpMenu);
        }
    }

    private void setInitialMenuState() {
        this.fileOpenGame.setEnabled(true);
        this.fileClose.setEnabled(false);
        this.fileSaveGame.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.gameNewGame.setEnabled(true);
        this.disableGameMenus();
        this.gameRegisterCharacter.setEnabled(true);
        this.gameUnregisterCharacter.setEnabled(true);
        this.gameRemoveCharacter.setEnabled(true);
        this.helpAbout.setEnabled(true);
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
                final Application app = Chrystalz.getApplication();
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
                            app.getGame().exitGame();
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
                    System.exit(0);
                } else if (cmd.equals("Preferences...")) {
                    // Show preferences dialog
                    PreferencesManager.showPrefs();
                } else if (cmd.equals("New Game")) {
                    // Start a new game
                    final boolean proceed = app.getGame().newGame();
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
                } else if (cmd.equals("View Statistics...")) {
                    // View Statistics
                    StatisticsViewer.viewStatistics();
                } else if (cmd.equals("About Chrystalz...")) {
                    app.getAboutDialog().showAboutDialog();
                }
                MenuManager.this.checkFlags();
            } catch (final Throwable t) {
                Chrystalz.getErrorLogger().logError(t);
            }
        }
    }
}
