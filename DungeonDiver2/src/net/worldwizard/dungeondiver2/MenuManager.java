/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import net.worldwizard.dungeondiver2.battle.BattleGUI;
import net.worldwizard.dungeondiver2.prefs.PreferencesManager;
import net.worldwizard.support.BoardPrinter;
import net.worldwizard.support.ai.AIRegistration;
import net.worldwizard.support.creatures.PartyManager;
import net.worldwizard.support.creatures.PartyMember;
import net.worldwizard.support.creatures.characterfiles.CharacterRegistration;

public class MenuManager {
    // Fields
    private JMenuBar mainMenuBar;
    private JMenu fileMenu, gameMenu, battleMenu, aiMenu, helpMenu;
    private JMenuItem fileOpen, fileClose, fileSave, fileSaveAs, filePrint,
            filePreferences, fileExit;
    private JMenuItem gamePlay, gameEquipment, gameItemInventory, gameShowScore,
            gamePickLeader, gameRegisterCharacter, gameUnregisterCharacter;
    private JMenuItem battleItem, battleSpell, battleSteal, battleDrain,
            battleEndTurn;
    private JMenuItem aiRegister, aiUnregister, aiImport;
    private JMenuItem helpAbout, helpObjectHelp;
    private KeyStroke fileOpenAccel, fileCloseAccel, fileSaveAccel,
            fileSaveAsAccel, filePrintAccel, filePreferencesAccel;
    private KeyStroke gamePlayMapAccel;
    private KeyStroke battleItemAccel, battleSpellAccel, battleStealAccel,
            battleDrainAccel, battleEndTurnAccel;
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
        this.fileOpen.setEnabled(false);
        this.filePreferences.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.gameRegisterCharacter.setEnabled(false);
        this.gameUnregisterCharacter.setEnabled(false);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.enableBattleMenus();
    }

    public void setGameMenus() {
        this.fileOpen.setEnabled(false);
        this.filePreferences.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.gameEquipment.setEnabled(true);
        this.gameItemInventory.setEnabled(true);
        this.gameShowScore.setEnabled(true);
        this.gamePickLeader.setEnabled(true);
        this.disableBattleMenus();
        this.checkFlags();
    }

    public void setPrefMenus() {
        this.fileOpen.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.filePreferences.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.disableBattleMenus();
    }

    public void setHelpMenus() {
        this.fileOpen.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.filePreferences.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.disableBattleMenus();
    }

    public void setMainMenus() {
        this.fileOpen.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.disableBattleMenus();
        this.checkFlags();
    }

    public void enableBattleMenus() {
        this.battleSpell.setEnabled(true);
        this.battleItem.setEnabled(true);
        this.battleSteal.setEnabled(true);
        this.battleDrain.setEnabled(true);
        this.battleEndTurn.setEnabled(true);
    }

    public void disableBattleMenus() {
        this.battleSpell.setEnabled(false);
        this.battleItem.setEnabled(false);
        this.battleSteal.setEnabled(false);
        this.battleDrain.setEnabled(false);
        this.battleEndTurn.setEnabled(false);
    }

    public void checkFlags() {
        final Application app = DungeonDiverII.getApplication();
        if (app.getVariablesManager().getDirty()) {
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
        this.fileOpenAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O, modKey);
        this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W, modKey);
        this.fileSaveAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey);
        this.fileSaveAsAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.filePrintAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P, modKey);
        this.filePreferencesAccel = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,
                modKey);
        this.gamePlayMapAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.battleItemAccel = KeyStroke.getKeyStroke(KeyEvent.VK_I, modKey);
        this.battleSpellAccel = KeyStroke.getKeyStroke(KeyEvent.VK_L, modKey);
        this.battleStealAccel = KeyStroke.getKeyStroke(KeyEvent.VK_T, modKey);
        this.battleDrainAccel = KeyStroke.getKeyStroke(KeyEvent.VK_D, modKey);
        this.battleEndTurnAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E, modKey);
    }

    private void createMenus() {
        this.mainMenuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.gameMenu = new JMenu("Game");
        this.battleMenu = new JMenu("Battle");
        this.aiMenu = new JMenu("AI");
        this.helpMenu = new JMenu("Help");
        this.fileOpen = new JMenuItem("Open...");
        this.fileOpen.setAccelerator(this.fileOpenAccel);
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
        this.gameRegisterCharacter = new JMenuItem("Register Character...");
        this.gameUnregisterCharacter = new JMenuItem("Unregister Character...");
        this.gameEquipment = new JMenuItem("Show Equipment...");
        this.gameItemInventory = new JMenuItem("Show Item Inventory...");
        this.gameShowScore = new JMenuItem("Show Score...");
        this.gamePickLeader = new JMenuItem("Pick Leader...");
        this.battleSpell = new JMenuItem("Cast Spell");
        this.battleSpell.setAccelerator(this.battleSpellAccel);
        this.battleItem = new JMenuItem("Use an Item");
        this.battleItem.setAccelerator(this.battleItemAccel);
        this.battleSteal = new JMenuItem("Steal Stuff");
        this.battleSteal.setAccelerator(this.battleStealAccel);
        this.battleDrain = new JMenuItem("Drain MP");
        this.battleDrain.setAccelerator(this.battleDrainAccel);
        this.battleEndTurn = new JMenuItem("End Turn");
        this.battleEndTurn.setAccelerator(this.battleEndTurnAccel);
        this.aiRegister = new JMenuItem("Register AI...");
        this.aiUnregister = new JMenuItem("Unregister AI...");
        this.aiImport = new JMenuItem("Import AI...");
        this.helpAbout = new JMenuItem("About DungeonDiverII...");
        this.helpObjectHelp = new JMenuItem("DungeonDiverII Help");
        this.fileOpen.addActionListener(this.handler);
        this.fileClose.addActionListener(this.handler);
        this.fileSave.addActionListener(this.handler);
        this.fileSaveAs.addActionListener(this.handler);
        this.filePrint.addActionListener(this.handler);
        this.filePreferences.addActionListener(this.handler);
        this.fileExit.addActionListener(this.handler);
        this.gamePlay.addActionListener(this.handler);
        this.gameRegisterCharacter.addActionListener(this.handler);
        this.gameUnregisterCharacter.addActionListener(this.handler);
        this.gameEquipment.addActionListener(this.handler);
        this.gameItemInventory.addActionListener(this.handler);
        this.gameShowScore.addActionListener(this.handler);
        this.gamePickLeader.addActionListener(this.handler);
        this.battleSpell.addActionListener(this.handler);
        this.battleItem.addActionListener(this.handler);
        this.battleSteal.addActionListener(this.handler);
        this.battleDrain.addActionListener(this.handler);
        this.battleEndTurn.addActionListener(this.handler);
        this.aiRegister.addActionListener(this.handler);
        this.aiUnregister.addActionListener(this.handler);
        this.aiImport.addActionListener(this.handler);
        this.helpAbout.addActionListener(this.handler);
        this.helpObjectHelp.addActionListener(this.handler);
        this.fileMenu.add(this.fileOpen);
        this.fileMenu.add(this.fileClose);
        this.fileMenu.add(this.fileSave);
        this.fileMenu.add(this.fileSaveAs);
        this.fileMenu.add(this.filePrint);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.fileMenu.add(this.filePreferences);
            this.fileMenu.add(this.fileExit);
        }
        this.gameMenu.add(this.gamePlay);
        this.gameMenu.add(this.gameEquipment);
        this.gameMenu.add(this.gameItemInventory);
        this.gameMenu.add(this.gameShowScore);
        this.gameMenu.add(this.gamePickLeader);
        this.gameMenu.add(this.gameRegisterCharacter);
        this.gameMenu.add(this.gameUnregisterCharacter);
        this.battleMenu.add(this.battleSpell);
        this.battleMenu.add(this.battleItem);
        this.battleMenu.add(this.battleSteal);
        this.battleMenu.add(this.battleDrain);
        this.battleMenu.add(this.battleEndTurn);
        this.aiMenu.add(this.aiRegister);
        this.aiMenu.add(this.aiUnregister);
        this.aiMenu.add(this.aiImport);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.helpMenu.add(this.helpAbout);
        }
        this.helpMenu.add(this.helpObjectHelp);
        this.mainMenuBar.add(this.fileMenu);
        this.mainMenuBar.add(this.gameMenu);
        this.mainMenuBar.add(this.battleMenu);
        this.mainMenuBar.add(this.aiMenu);
        this.mainMenuBar.add(this.helpMenu);
    }

    private void setInitialMenuState() {
        this.fileOpen.setEnabled(true);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.filePrint.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.gamePlay.setEnabled(false);
        this.gameRegisterCharacter.setEnabled(true);
        this.gameUnregisterCharacter.setEnabled(true);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.battleSpell.setEnabled(false);
        this.battleItem.setEnabled(false);
        this.battleSteal.setEnabled(false);
        this.battleDrain.setEnabled(false);
        this.battleEndTurn.setEnabled(false);
        this.aiRegister.setEnabled(true);
        this.aiUnregister.setEnabled(true);
        this.aiImport.setEnabled(true);
        this.helpAbout.setEnabled(true);
        this.helpObjectHelp.setEnabled(true);
    }

    private class EventHandler implements ActionListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        // Handle menus
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final Application app = DungeonDiverII.getApplication();
                final BattleGUI ba = app.getBattle();
                final String cmd = e.getActionCommand();
                if (cmd.equals("Open...")) {
                    app.getVariablesManager().loadSavedGame();
                } else if (cmd.equals("Close")) {
                    // Close the window
                    boolean saved = true;
                    int status = 0;
                    if (app.getVariablesManager().getDirty()) {
                        status = app.getVariablesManager().showSaveDialog();
                        if (status == JOptionPane.YES_OPTION) {
                            saved = app.getVariablesManager().saveGame();
                        } else if (status == JOptionPane.CANCEL_OPTION) {
                            saved = false;
                        } else {
                            app.getVariablesManager().setDirty(false);
                        }
                    }
                    if (saved) {
                        app.getGameManager().exitGame();
                    }
                } else if (cmd.equals("Save")) {
                    app.getVariablesManager().saveGame();
                } else if (cmd.equals("Save As...")) {
                    app.getVariablesManager().saveGameAs();
                } else if (cmd.equals("Print...")) {
                    BoardPrinter.printBoard(app.getOutputFrame());
                } else if (cmd.equals("Exit")) {
                    // Exit program
                    if (app.getGUIManager().quitHandler()) {
                        System.exit(0);
                    }
                } else if (cmd.equals("Preferences...")) {
                    // Show preferences dialog
                    PreferencesManager.showPrefs();
                } else if (cmd.equals("Play")) {
                    // Play the current map
                    final boolean proceed = app.getGameManager().newGame();
                    if (proceed) {
                        app.getGameManager().playMap();
                    }
                } else if (cmd.equals("Register Character...")) {
                    // Register Character
                    CharacterRegistration.registerCharacter();
                } else if (cmd.equals("Unregister Character...")) {
                    // Unregister Character
                    CharacterRegistration.unregisterCharacter();
                } else if (cmd.equals("Show Equipment...")) {
                    app.getGameManager().showEquipmentDialog();
                } else if (cmd.equals("Show Item Inventory...")) {
                    app.getGameManager().showItemInventoryDialog();
                } else if (cmd.equals("Show Score...")) {
                    app.getGameManager().showCurrentScore();
                } else if (cmd.equals("Pick Leader...")) {
                    final PartyMember pm = PartyManager.getParty()
                            .pickOnePartyMember();
                    if (pm != null) {
                        PartyManager.getParty().setLeader(pm.getName());
                    }
                    app.getGameManager().updateStats();
                } else if (cmd.equals("Cast Spell")) {
                    ba.castSpell();
                } else if (cmd.equals("Use an Item")) {
                    ba.useItem();
                } else if (cmd.equals("Steal Stuff")) {
                    ba.steal();
                } else if (cmd.equals("Drain MP")) {
                    ba.drain();
                } else if (cmd.equals("End Turn")) {
                    ba.endTurn();
                } else if (cmd.equals("End Turn")) {
                    ba.endTurn();
                } else if (cmd.equals("Register AI...")) {
                    AIRegistration.registerAI();
                } else if (cmd.equals("Unregister AI...")) {
                    AIRegistration.unregisterAI();
                } else if (cmd.equals("Import AI...")) {
                    AIRegistration.importAI();
                } else if (cmd.equals("About DungeonDiverII...")) {
                    app.getAboutDialog().showAboutDialog();
                } else if (cmd.equals("DungeonDiverII Help")) {
                    app.getHelpManager().showHelp();
                }
                MenuManager.this.checkFlags();
            } catch (final Exception ex) {
                DungeonDiverII.getErrorLogger().logError(ex);
            }
        }
    }
}
