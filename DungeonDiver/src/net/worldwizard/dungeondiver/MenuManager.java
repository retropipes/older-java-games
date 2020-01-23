package net.worldwizard.dungeondiver;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import net.worldwizard.dungeondiver.items.ShopTypes;

public class MenuManager implements ShopTypes {
    // Fields
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu gameMenu;
    private JMenu helpMenu;
    private JMenuItem fileLoadState;
    JMenuItem fileSaveState;
    JMenuItem gameNewGame;
    JMenuItem gameEndGame;
    JMenuItem gameExploreDungeon;
    JMenuItem gameNewDungeon;
    JMenuItem gamePreferences;
    private JMenuItem helpShowHelp;
    private JMenuItem fileExit;
    private KeyStroke fileLoadStateAccel;
    private KeyStroke fileSaveStateAccel;
    private KeyStroke gameNewGameAccel;
    private KeyStroke gameEndGameAccel;
    private KeyStroke gameExploreDungeonAccel;
    private KeyStroke gameNewDungeonAccel;
    private KeyStroke gamePreferencesAccel;
    private EventHandler handler;

    // Constructors
    public MenuManager() {
        this.createAccelerators();
        this.createMenus();
        this.createEventHandlers();
    }

    // Methods
    public JMenuBar getMenuBar() {
        return this.menuBar;
    }

    private void createAccelerators() {
        int modKey;
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            modKey = InputEvent.META_DOWN_MASK;
        } else {
            modKey = InputEvent.CTRL_DOWN_MASK;
        }
        this.fileLoadStateAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O, modKey);
        this.fileSaveStateAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey);
        this.gameNewGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N, modKey);
        this.gameEndGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E, modKey
                + InputEvent.SHIFT_DOWN_MASK);
        this.gameExploreDungeonAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E,
                modKey);
        this.gameNewDungeonAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N, modKey
                + InputEvent.SHIFT_DOWN_MASK);
        this.gamePreferencesAccel = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,
                modKey);
    }

    private void createMenus() {
        // Initialize Menu Bar
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.gameMenu = new JMenu("Game");
        this.helpMenu = new JMenu("Help");
        this.fileLoadState = new JMenuItem("Load...");
        this.fileLoadState.setAccelerator(this.fileLoadStateAccel);
        this.fileSaveState = new JMenuItem("Save...");
        this.fileSaveState.setAccelerator(this.fileSaveStateAccel);
        this.fileSaveState.setEnabled(false);
        this.gameNewGame = new JMenuItem("New Game...");
        this.gameNewGame.setAccelerator(this.gameNewGameAccel);
        this.gameEndGame = new JMenuItem("End Game");
        this.gameEndGame.setAccelerator(this.gameEndGameAccel);
        this.gameEndGame.setEnabled(false);
        this.gameExploreDungeon = new JMenuItem("Explore");
        this.gameExploreDungeon.setAccelerator(this.gameExploreDungeonAccel);
        this.gameExploreDungeon.setEnabled(false);
        this.gameNewDungeon = new JMenuItem("New Dungeon");
        this.gameNewDungeon.setAccelerator(this.gameNewDungeonAccel);
        this.gameNewDungeon.setEnabled(false);
        this.gamePreferences = new JMenuItem("Preferences...");
        this.gamePreferences.setAccelerator(this.gamePreferencesAccel);
        this.gamePreferences.setEnabled(false);
        this.helpShowHelp = new JMenuItem("Help...");
        this.fileExit = new JMenuItem("Exit");
        this.fileMenu.add(this.fileLoadState);
        this.fileMenu.add(this.fileSaveState);
        if (!System.getProperty("os.name").startsWith("Mac OS X")) {
            this.fileMenu.add(this.fileExit);
        }
        this.gameMenu.add(this.gameNewGame);
        this.gameMenu.add(this.gameEndGame);
        this.gameMenu.addSeparator();
        this.gameMenu.add(this.gameExploreDungeon);
        this.gameMenu.add(this.gameNewDungeon);
        this.gameMenu.add(this.gamePreferences);
        this.helpMenu.add(this.helpShowHelp);
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.gameMenu);
        this.menuBar.add(this.helpMenu);
    }

    private void createEventHandlers() {
        // Initialize Event Handlers
        this.handler = new EventHandler();
        this.fileLoadState.addActionListener(this.handler);
        this.fileSaveState.addActionListener(this.handler);
        this.gameNewGame.addActionListener(this.handler);
        this.gameEndGame.addActionListener(this.handler);
        this.gameExploreDungeon.addActionListener(this.handler);
        this.gameNewDungeon.addActionListener(this.handler);
        this.gamePreferences.addActionListener(this.handler);
        this.helpShowHelp.addActionListener(this.handler);
        this.fileExit.addActionListener(this.handler);
    }

    private class EventHandler extends AbstractAction {
        // Serialization
        private static final long serialVersionUID = 23525303603L;

        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final String cmd = e.getActionCommand();
                final HoldingBag m = DungeonDiver.getHoldingBag();
                if (cmd.equals("Explore")) {
                    // Explore
                    m.exploreDungeon();
                } else if (cmd.equals("Load...")) {
                    // Load State
                    final boolean b = m.loadState();
                    if (b) {
                        this.handleNewGame();
                    }
                } else if (cmd.equals("Save...")) {
                    // Save State
                    m.saveState();
                } else if (cmd.equals("New Game...")) {
                    // New Game
                    final boolean b = m.getGameManager().newGame();
                    if (b) {
                        this.handleNewGame();
                    }
                } else if (cmd.equals("End Game")) {
                    // End Game
                    final boolean b = m.getGameManager().endGame();
                    if (b) {
                        this.handleEndGame();
                    }
                } else if (cmd.equals("New Dungeon")) {
                    // New Dungeon
                    m.newDungeon();
                } else if (cmd.equals("Preferences...")) {
                    // Preferences
                    m.getPrefs().showPrefs();
                } else if (cmd.equals("Help...")) {
                    // Help
                    m.showHelp();
                } else if (cmd.equals("Exit")) {
                    // Quit
                    m.quit();
                }
            } catch (final Throwable t) {
                DungeonDiver.getDebug().debug(t);
            }
        }

        private void handleNewGame() {
            // Handle New Game
            MenuManager.this.gameExploreDungeon.setEnabled(true);
            MenuManager.this.gameNewDungeon.setEnabled(true);
            MenuManager.this.gameNewGame.setEnabled(false);
            MenuManager.this.gameEndGame.setEnabled(true);
            MenuManager.this.gamePreferences.setEnabled(true);
            MenuManager.this.fileSaveState.setEnabled(true);
        }

        private void handleEndGame() {
            // Handle End Game
            MenuManager.this.gameExploreDungeon.setEnabled(false);
            MenuManager.this.gameNewDungeon.setEnabled(false);
            MenuManager.this.gameNewGame.setEnabled(true);
            MenuManager.this.gameEndGame.setEnabled(false);
            MenuManager.this.gamePreferences.setEnabled(false);
            MenuManager.this.fileSaveState.setEnabled(false);
        }
    }
}
