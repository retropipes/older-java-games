/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.puttysoftware.riskyrescue.battle.Battle;
import com.puttysoftware.riskyrescue.battle.BattleLogic;
import com.puttysoftware.riskyrescue.game.InventoryViewer;
import com.puttysoftware.riskyrescue.game.NoteManager;
import com.puttysoftware.riskyrescue.game.PrestigeViewer;
import com.puttysoftware.riskyrescue.map.Map;
import com.puttysoftware.riskyrescue.prefs.PreferencesManager;
import com.puttysoftware.riskyrescue.scenario.ScenarioManager;
import com.puttysoftware.riskyrescue.utilities.BoardPrinter;

public class MenuManager {
    // Fields
    private JMenuBar mainMenuBar;
    private JMenuItem fileOpenSavedGame, fileClose, fileSave, fileSaveAs,
            filePrint, filePreferences, fileExit;
    private JMenuItem gamePlay, gameEquipment, gameEditNote, gameViewPrestige,
            gameViewScore, gameInstantBattle;
    private JMenuItem battleSpell, battleSteal, battleDrain, battleEndTurn;
    private JMenuItem helpAbout, helpManual;
    private KeyStroke fileOpenSavedGameAccel, fileCloseAccel, fileSaveAccel,
            fileSaveAsAccel, filePrintAccel, filePreferencesAccel;
    private KeyStroke gamePlayMapAccel, gameEditNoteAccel;
    private KeyStroke battleSpellAccel, battleStealAccel, battleDrainAccel,
            battleEndTurnAccel;
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

    public void setBattleMenus() {
        this.fileOpenSavedGame.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.filePreferences.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.disableGameMenus();
        this.enableBattleMenus();
    }

    public void setGameMenus() {
        this.fileOpenSavedGame.setEnabled(false);
        this.filePreferences.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.enableGameMenus();
        this.disableBattleMenus();
        this.checkFlags();
    }

    public void setPrefMenus() {
        this.fileOpenSavedGame.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.filePreferences.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.disableGameMenus();
        this.disableBattleMenus();
    }

    void setMainMenus() {
        this.fileOpenSavedGame.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.disableGameMenus();
        this.disableBattleMenus();
        this.checkFlags();
    }

    public void enableBattleMenus() {
        this.battleSpell.setEnabled(true);
        this.battleSteal.setEnabled(true);
        this.battleDrain.setEnabled(true);
        this.battleEndTurn.setEnabled(true);
    }

    public void disableBattleMenus() {
        this.battleSpell.setEnabled(false);
        this.battleSteal.setEnabled(false);
        this.battleDrain.setEnabled(false);
        this.battleEndTurn.setEnabled(false);
    }

    private void enableGameMenus() {
        this.gameEquipment.setEnabled(true);
        this.gameEditNote.setEnabled(true);
        this.gameViewPrestige.setEnabled(true);
        this.gameViewScore.setEnabled(true);
        this.gameInstantBattle.setEnabled(true);
    }

    private void disableGameMenus() {
        this.gameEquipment.setEnabled(false);
        this.gameEditNote.setEnabled(false);
        this.gameViewPrestige.setEnabled(false);
        this.gameViewScore.setEnabled(false);
        this.gameInstantBattle.setEnabled(false);
    }

    public void checkFlags() {
        final Application app = RiskyRescue.getApplication();
        if (app.getScenarioManager().getDirty()) {
            this.setMenusDirtyOn();
        } else {
            this.setMenusDirtyOff();
        }
        if (app.getMode() == Application.STATUS_GUI) {
            this.fileClose.setEnabled(false);
            this.fileSaveAs.setEnabled(false);
            this.gamePlay.setEnabled(true);
        } else {
            this.fileClose.setEnabled(true);
            this.fileSaveAs.setEnabled(true);
            this.gamePlay.setEnabled(false);
        }
    }

    private void setMenusDirtyOn() {
        this.fileSave.setEnabled(true);
    }

    private void setMenusDirtyOff() {
        this.fileSave.setEnabled(false);
    }

    private void createAccelerators() {
        int modKey;
        if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            modKey = InputEvent.META_DOWN_MASK;
        } else {
            modKey = InputEvent.CTRL_DOWN_MASK;
        }
        this.fileOpenSavedGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O,
                modKey);
        this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W, modKey);
        this.fileSaveAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey);
        this.fileSaveAsAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.filePrintAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P, modKey);
        this.filePreferencesAccel = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,
                modKey);
        this.gamePlayMapAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.gameEditNoteAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.battleSpellAccel = KeyStroke.getKeyStroke(KeyEvent.VK_L, modKey);
        this.battleStealAccel = KeyStroke.getKeyStroke(KeyEvent.VK_T, modKey);
        this.battleDrainAccel = KeyStroke.getKeyStroke(KeyEvent.VK_D, modKey);
        this.battleEndTurnAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E, modKey);
    }

    private void createMenus() {
        this.mainMenuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        final JMenu gameMenu = new JMenu("Game");
        final JMenu battleMenu = new JMenu("Battle");
        final JMenu editorMenu = new JMenu("Editor");
        final JMenu helpMenu = new JMenu("Help");
        this.fileOpenSavedGame = new JMenuItem("Open Saved Game...");
        this.fileOpenSavedGame.setAccelerator(this.fileOpenSavedGameAccel);
        this.fileClose = new JMenuItem("Close");
        this.fileClose.setAccelerator(this.fileCloseAccel);
        this.fileSave = new JMenuItem("Save");
        this.fileSave.setAccelerator(this.fileSaveAccel);
        this.fileSaveAs = new JMenuItem("Save As...");
        this.fileSaveAs.setAccelerator(this.fileSaveAsAccel);
        this.filePrint = new JMenuItem("Print...");
        this.filePrint.setAccelerator(this.filePrintAccel);
        this.filePreferences = new JMenuItem("Preferences...");
        this.filePreferences.setAccelerator(this.filePreferencesAccel);
        this.fileExit = new JMenuItem("Exit");
        this.gamePlay = new JMenuItem("Play");
        this.gamePlay.setAccelerator(this.gamePlayMapAccel);
        this.gameEquipment = new JMenuItem("Show Equipment...");
        this.gameEditNote = new JMenuItem("Edit Note...");
        this.gameEditNote.setAccelerator(this.gameEditNoteAccel);
        this.gameViewPrestige = new JMenuItem("View Prestige...");
        this.gameViewScore = new JMenuItem("View Current Score...");
        this.gameInstantBattle = new JMenuItem("[DEBUG] Enter Battle Now");
        this.battleSpell = new JMenuItem("Cast Spell");
        this.battleSpell.setAccelerator(this.battleSpellAccel);
        this.battleSteal = new JMenuItem("Steal Stuff");
        this.battleSteal.setAccelerator(this.battleStealAccel);
        this.battleDrain = new JMenuItem("Drain MP");
        this.battleDrain.setAccelerator(this.battleDrainAccel);
        this.battleEndTurn = new JMenuItem("End Turn");
        this.battleEndTurn.setAccelerator(this.battleEndTurnAccel);
        this.helpAbout = new JMenuItem("About RiskyRescue...");
        this.helpManual = new JMenuItem("Manual");
        this.fileOpenSavedGame.addActionListener(this.handler);
        this.fileClose.addActionListener(this.handler);
        this.fileSave.addActionListener(this.handler);
        this.fileSaveAs.addActionListener(this.handler);
        this.filePrint.addActionListener(this.handler);
        this.filePreferences.addActionListener(this.handler);
        this.fileExit.addActionListener(this.handler);
        this.gamePlay.addActionListener(this.handler);
        this.gameEquipment.addActionListener(this.handler);
        this.gameEditNote.addActionListener(this.handler);
        this.gameViewPrestige.addActionListener(this.handler);
        this.gameViewScore.addActionListener(this.handler);
        this.gameInstantBattle.addActionListener(this.handler);
        this.battleSpell.addActionListener(this.handler);
        this.battleSteal.addActionListener(this.handler);
        this.battleDrain.addActionListener(this.handler);
        this.battleEndTurn.addActionListener(this.handler);
        this.helpAbout.addActionListener(this.handler);
        this.helpManual.addActionListener(this.handler);
        fileMenu.add(this.fileOpenSavedGame);
        fileMenu.add(this.fileClose);
        fileMenu.add(this.fileSave);
        fileMenu.add(this.fileSaveAs);
        fileMenu.add(this.filePrint);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            fileMenu.add(this.filePreferences);
            fileMenu.add(this.fileExit);
        }
        gameMenu.add(this.gamePlay);
        gameMenu.add(this.gameEquipment);
        gameMenu.add(this.gameEditNote);
        gameMenu.add(this.gameViewPrestige);
        gameMenu.add(this.gameViewScore);
        if (Support.inDebugMode()) {
            gameMenu.add(this.gameInstantBattle);
        }
        battleMenu.add(this.battleSpell);
        battleMenu.add(this.battleSteal);
        battleMenu.add(this.battleDrain);
        battleMenu.add(this.battleEndTurn);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            helpMenu.add(this.helpAbout);
        }
        helpMenu.add(this.helpManual);
        this.mainMenuBar.add(fileMenu);
        this.mainMenuBar.add(gameMenu);
        this.mainMenuBar.add(battleMenu);
        this.mainMenuBar.add(editorMenu);
        this.mainMenuBar.add(helpMenu);
    }

    private void setInitialMenuState() {
        this.fileOpenSavedGame.setEnabled(true);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.filePrint.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.gamePlay.setEnabled(true);
        this.disableGameMenus();
        this.disableBattleMenus();
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
                final Application app = RiskyRescue.getApplication();
                final BattleLogic ba = app.getBattle();
                final String cmd = e.getActionCommand();
                switch (cmd) {
                    case "Open Saved Game...":
                        app.getScenarioManager().loadSavedGame();
                        break;
                    case "Close":
                        // Close the window
                        boolean saved = true;
                        int status;
                        if (app.getScenarioManager().getDirty()) {
                            status = ScenarioManager.showSaveDialog();
                            if (status == JOptionPane.YES_OPTION) {
                                saved = app.getScenarioManager().saveGame();
                            } else if (status == JOptionPane.CANCEL_OPTION) {
                                saved = false;
                            } else {
                                app.getScenarioManager().setDirty(false);
                            }
                        }
                        if (saved) {
                            if (app.getMode() == Application.STATUS_GAME) {
                                app.getGameManager().exitGame();
                            }
                        }
                        break;
                    case "Save":
                        app.getScenarioManager().saveGame();
                        break;
                    case "Save As...":
                        app.getScenarioManager().saveGameAs();
                        break;
                    case "Print...":
                        BoardPrinter.printBoard(app.getOutputFrame());
                        break;
                    case "Exit":
                        // Exit program
                        if (app.getGUIManager().quitHandler()) {
                            System.exit(0);
                        }
                        break;
                    case "Preferences...":
                        // Show preferences dialog
                        PreferencesManager.showPrefs();
                        break;
                    case "Play":
                        // Play the current scenario
                        final boolean proceed = app.getGameManager().newGame();
                        if (proceed) {
                            app.getGameManager().playMap();
                        }
                        break;
                    case "Show Equipment...":
                        InventoryViewer.showEquipmentDialog();
                        break;
                    case "Edit Note...":
                        // Edit Note
                        NoteManager.editNote();
                        break;
                    case "View Prestige...":
                        // View Prestige
                        PrestigeViewer.viewPrestige();
                        break;
                    case "View Current Score...":
                        // View Score
                        app.getGameManager().showCurrentScore();
                        break;
                    case "[DEBUG] Enter Battle Now":
                        // Hide the game
                        RiskyRescue.getApplication().getGameManager().hideOutput();
                        // Enter Battle
                        final Battle battle = new Battle();
                        new Thread("Debug Battle") {
                            @Override
                            public void run() {
                                try {
                                    RiskyRescue.getApplication().getGameManager();
                                    RiskyRescue.getApplication().getBattle()
                                            .doFixedBattle(
                                                    Map.getTemporaryBattleCopy(),
                                                    battle);
                                } catch (final Exception e1) {
                                    // Something went wrong in the battle
                                    RiskyRescue.logError(e1);
                                }
                            }
                        }.start();
                        break;
                    case "Cast Spell":
                        ba.castSpell();
                        break;
                    case "Steal Stuff":
                        ba.steal();
                        break;
                    case "Drain MP":
                        ba.drain();
                        break;
                    case "End Turn":
                        ba.endTurn();
                        break;
                    case "About RiskyRescue...":
                        app.getAboutDialog().showAboutDialog();
                        break;
                    default:
                        break;
                }
                MenuManager.this.checkFlags();
            } catch (final Exception ex) {
                RiskyRescue.logError(ex);
            }
        }
    }
}
