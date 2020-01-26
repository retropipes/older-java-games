/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import net.worldwizard.worldz.battle.Battle;
import net.worldwizard.worldz.creatures.CharacterRegistration;
import net.worldwizard.worldz.creatures.PartyManager;

public class MenuManager {
    // Fields
    private JMenuBar mainMenuBar;
    private JMenu fileMenu, editMenu, playMenu, gameMenu, battleMenu,
            editorMenu, debugMenu, pluginMenu, helpMenu;
    private JMenu editorFillSubMenu;
    private JMenuItem fileNew, fileOpen, fileClose, fileSave, fileSaveAs,
            fileExit;
    private JMenuItem editUndo, editRedo, editCutLevel, editCopyLevel,
            editPasteLevel, editInsertLevelFromClipboard, editPreferences,
            editClearHistory;
    private JMenuItem playPlay, playEdit, playRegisterCharacter,
            playUnregisterCharacter;
    private JMenuItem gameEquipment, gameItemInventory, gameObjectInventory,
            gameUse, gamePickLeader;
    private JMenuItem battleItem, battleSpell, battleSteal, battleDrain,
            battleEndTurn;
    private JMenuItem editorGoTo, editorUpOneFloor, editorDownOneFloor,
            editorUpOneLevel, editorDownOneLevel, editorAddLevel,
            editorRemoveLevel, editorResizeLevel, editorToggleLayer,
            editorLevelPreferences, editorWorldPreferences, editorSetStartPoint;
    private JCheckBoxMenuItem editorToggleScript;
    private JMenuItem editorFillFloor, editorFillLevel, editorFillFloorRandomly,
            editorFillLevelRandomly, editorFillRuleSets;
    private JCheckBoxMenuItem editorFillUseRuleSets;
    JMenuItem debugToggleAI;
    private JMenuItem pluginRegister, pluginUnregister;
    private JMenuItem helpAbout, helpGeneralHelp, helpObjectHelp;
    private KeyStroke fileNewAccel, fileOpenAccel, fileCloseAccel,
            fileSaveAccel, fileSaveAsAccel;
    private KeyStroke editUndoAccel, editRedoAccel, editCutLevelAccel,
            editCopyLevelAccel, editPasteLevelAccel,
            editInsertLevelFromClipboardAccel, editPreferencesAccel,
            editClearHistoryAccel;
    private KeyStroke playPlayWorldAccel, playEditWorldAccel;
    private KeyStroke gameObjectInventoryAccel, gameUseAccel;
    private KeyStroke editorGoToAccel, editorUpOneFloorAccel,
            editorDownOneFloorAccel, editorUpOneLevelAccel,
            editorDownOneLevelAccel, editorToggleLayerAccel;
    private final EventHandler handler;
    private final PluginEventHandler phandler;

    // Constructors
    public MenuManager() {
        this.handler = new EventHandler();
        this.phandler = new PluginEventHandler();
        this.createAccelerators();
        this.createMenus();
        this.setInitialMenuState();
    }

    // Methods
    public JMenuBar getMainMenuBar() {
        return this.mainMenuBar;
    }

    public void setBattleMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.editorGoTo.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorToggleScript.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorWorldPreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.playRegisterCharacter.setEnabled(false);
        this.playUnregisterCharacter.setEnabled(false);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.battleSpell.setEnabled(true);
        this.battleItem.setEnabled(true);
        this.battleSteal.setEnabled(true);
        this.battleDrain.setEnabled(true);
        this.battleEndTurn.setEnabled(true);
        this.checkFlags();
    }

    public void setGameMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.editorGoTo.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorToggleScript.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorWorldPreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.playRegisterCharacter.setEnabled(false);
        this.playUnregisterCharacter.setEnabled(false);
        this.gameEquipment.setEnabled(true);
        this.gameItemInventory.setEnabled(true);
        this.gameObjectInventory.setEnabled(true);
        this.gameUse.setEnabled(true);
        this.gamePickLeader.setEnabled(true);
        this.battleSpell.setEnabled(false);
        this.battleItem.setEnabled(false);
        this.battleSteal.setEnabled(false);
        this.battleDrain.setEnabled(false);
        this.battleEndTurn.setEnabled(false);
        this.checkFlags();
    }

    public void setEditorMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editCutLevel.setEnabled(true);
        this.editCopyLevel.setEnabled(true);
        this.editPasteLevel.setEnabled(true);
        this.editInsertLevelFromClipboard.setEnabled(true);
        this.editPreferences.setEnabled(true);
        this.editorGoTo.setEnabled(true);
        this.editorResizeLevel.setEnabled(true);
        this.editorFillFloor.setEnabled(true);
        this.editorFillLevel.setEnabled(true);
        this.editorFillFloorRandomly.setEnabled(true);
        this.editorFillLevelRandomly.setEnabled(true);
        this.editorFillRuleSets.setEnabled(true);
        this.editorFillUseRuleSets.setEnabled(true);
        this.editorToggleLayer.setEnabled(true);
        this.editorToggleScript.setEnabled(true);
        this.editorLevelPreferences.setEnabled(true);
        this.editorWorldPreferences.setEnabled(true);
        this.editorSetStartPoint.setEnabled(true);
        this.playRegisterCharacter.setEnabled(false);
        this.playUnregisterCharacter.setEnabled(false);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.battleSpell.setEnabled(false);
        this.battleItem.setEnabled(false);
        this.battleSteal.setEnabled(false);
        this.battleDrain.setEnabled(false);
        this.battleEndTurn.setEnabled(false);
        this.checkFlags();
    }

    public void setPrefMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(false);
        this.editClearHistory.setEnabled(false);
        this.editorGoTo.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorToggleScript.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorWorldPreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.playRegisterCharacter.setEnabled(false);
        this.playUnregisterCharacter.setEnabled(false);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.battleSpell.setEnabled(false);
        this.battleItem.setEnabled(false);
        this.battleSteal.setEnabled(false);
        this.battleDrain.setEnabled(false);
        this.battleEndTurn.setEnabled(false);
    }

    public void setMainMenus() {
        this.fileNew.setEnabled(true);
        this.fileOpen.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.editorGoTo.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorToggleScript.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorWorldPreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.playRegisterCharacter.setEnabled(true);
        this.playUnregisterCharacter.setEnabled(true);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.battleSpell.setEnabled(false);
        this.battleItem.setEnabled(false);
        this.battleSteal.setEnabled(false);
        this.battleDrain.setEnabled(false);
        this.battleEndTurn.setEnabled(false);
        this.checkFlags();
    }

    public void disableBattleMenus() {
        this.battleSpell.setEnabled(false);
        this.battleItem.setEnabled(false);
        this.battleSteal.setEnabled(false);
        this.battleDrain.setEnabled(false);
        this.battleEndTurn.setEnabled(false);
    }

    public void enableBattleMenus() {
        this.battleSpell.setEnabled(true);
        this.battleItem.setEnabled(true);
        this.battleSteal.setEnabled(true);
        this.battleDrain.setEnabled(true);
        this.battleEndTurn.setEnabled(true);
    }

    public void enableUpOneFloor() {
        this.editorUpOneFloor.setEnabled(true);
    }

    public void disableUpOneFloor() {
        this.editorUpOneFloor.setEnabled(false);
    }

    public void enableDownOneFloor() {
        this.editorDownOneFloor.setEnabled(true);
    }

    public void disableDownOneFloor() {
        this.editorDownOneFloor.setEnabled(false);
    }

    public void enableUpOneLevel() {
        this.editorUpOneLevel.setEnabled(true);
    }

    public void disableUpOneLevel() {
        this.editorUpOneLevel.setEnabled(false);
    }

    public void enableDownOneLevel() {
        this.editorDownOneLevel.setEnabled(true);
    }

    public void disableDownOneLevel() {
        this.editorDownOneLevel.setEnabled(false);
    }

    public void enableAddLevel() {
        this.editorAddLevel.setEnabled(true);
    }

    public void disableAddLevel() {
        this.editorAddLevel.setEnabled(false);
    }

    public void enableRemoveLevel() {
        this.editorRemoveLevel.setEnabled(true);
    }

    public void disableRemoveLevel() {
        this.editorRemoveLevel.setEnabled(false);
    }

    public void enableUndo() {
        this.editUndo.setEnabled(true);
    }

    public void disableUndo() {
        this.editUndo.setEnabled(false);
    }

    public void enableRedo() {
        this.editRedo.setEnabled(true);
    }

    public void disableRedo() {
        this.editRedo.setEnabled(false);
    }

    public void enableClearHistory() {
        this.editClearHistory.setEnabled(true);
    }

    public void disableClearHistory() {
        this.editClearHistory.setEnabled(false);
    }

    public void enableCutLevel() {
        this.editCutLevel.setEnabled(true);
    }

    public void disableCutLevel() {
        this.editCutLevel.setEnabled(false);
    }

    public void enablePasteLevel() {
        this.editPasteLevel.setEnabled(true);
    }

    public void disablePasteLevel() {
        this.editPasteLevel.setEnabled(false);
    }

    public void enableInsertLevelFromClipboard() {
        this.editInsertLevelFromClipboard.setEnabled(true);
    }

    public void disableInsertLevelFromClipboard() {
        this.editInsertLevelFromClipboard.setEnabled(false);
    }

    public void enableSetStartPoint() {
        this.editorSetStartPoint.setEnabled(true);
    }

    public void disableSetStartPoint() {
        this.editorSetStartPoint.setEnabled(false);
    }

    public void enableToggleScript() {
        this.editorToggleScript.setEnabled(true);
    }

    public void disableToggleScript() {
        this.editorToggleScript.setEnabled(false);
    }

    public void toggleToggleScript() {
        this.editorToggleScript
                .setSelected(!this.editorToggleScript.isSelected());
    }

    public void checkFlags() {
        final Application app = Worldz.getApplication();
        if (app.getWorldManager().getDirty()) {
            this.setMenusDirtyOn();
        } else {
            this.setMenusDirtyOff();
        }
        if (app.getWorldManager().getLoaded()) {
            this.setMenusLoadedOn();
        } else {
            this.setMenusLoadedOff();
        }
        if (app.getMode() == Application.STATUS_EDITOR) {
            if (app.getWorldManager().getWorld().isPasteBlocked()) {
                this.disablePasteLevel();
                this.disableInsertLevelFromClipboard();
            } else {
                this.enablePasteLevel();
                this.enableInsertLevelFromClipboard();
            }
            if (app.getWorldManager().getWorld().isCutBlocked()) {
                this.disableCutLevel();
            } else {
                this.enableCutLevel();
            }
        }
    }

    public boolean useFillRuleSets() {
        return this.editorFillUseRuleSets.getState();
    }

    private void setMenusDirtyOn() {
        this.fileSave.setEnabled(true);
    }

    private void setMenusDirtyOff() {
        this.fileSave.setEnabled(false);
    }

    private void setMenusLoadedOn() {
        final Application app = Worldz.getApplication();
        if (app.getMode() == Application.STATUS_GUI) {
            this.fileClose.setEnabled(false);
            this.fileSaveAs.setEnabled(false);
            if (app.getWorldManager().getWorld().doesPlayerExist()) {
                this.playPlay.setEnabled(true);
            } else {
                this.playPlay.setEnabled(false);
            }
            this.playEdit.setEnabled(true);
        } else {
            this.fileClose.setEnabled(true);
            this.fileSaveAs.setEnabled(true);
            this.playPlay.setEnabled(false);
            this.playEdit.setEnabled(false);
        }
    }

    private void setMenusLoadedOff() {
        this.fileClose.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.playPlay.setEnabled(false);
        this.playEdit.setEnabled(false);
    }

    private void createAccelerators() {
        int modKey;
        if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            modKey = InputEvent.META_DOWN_MASK;
        } else {
            modKey = InputEvent.CTRL_DOWN_MASK;
        }
        this.fileNewAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N, modKey);
        this.fileOpenAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O, modKey);
        this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W, modKey);
        this.fileSaveAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey);
        this.fileSaveAsAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.editUndoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z, modKey);
        this.editRedoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.editCutLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_X, modKey);
        this.editCopyLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_C, modKey);
        this.editPasteLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                modKey);
        this.editInsertLevelFromClipboardAccel = KeyStroke
                .getKeyStroke(KeyEvent.VK_F, modKey);
        this.editPreferencesAccel = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,
                modKey);
        this.editClearHistoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                modKey);
        this.editorGoToAccel = KeyStroke.getKeyStroke(KeyEvent.VK_G,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.playPlayWorldAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P, modKey);
        this.playEditWorldAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E, modKey);
        this.gameObjectInventoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_I,
                modKey);
        this.gameUseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_U, modKey);
        this.editorUpOneFloorAccel = KeyStroke.getKeyStroke(KeyEvent.VK_UP,
                modKey);
        this.editorDownOneFloorAccel = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
                modKey);
        this.editorUpOneLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_UP,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.editorDownOneLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.editorToggleLayerAccel = KeyStroke.getKeyStroke(KeyEvent.VK_L,
                modKey);
    }

    private void createMenus() {
        this.mainMenuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.editMenu = new JMenu("Edit");
        this.playMenu = new JMenu("Play");
        this.gameMenu = new JMenu("Game");
        this.battleMenu = new JMenu("Battle");
        this.editorMenu = new JMenu("Editor");
        this.debugMenu = new JMenu("Debug");
        this.pluginMenu = new JMenu("Plugin");
        this.helpMenu = new JMenu("Help");
        this.editorFillSubMenu = new JMenu("Fill");
        this.fileNew = new JMenuItem("New...");
        this.fileNew.setAccelerator(this.fileNewAccel);
        this.fileOpen = new JMenuItem("Open...");
        this.fileOpen.setAccelerator(this.fileOpenAccel);
        this.fileClose = new JMenuItem("Close");
        this.fileClose.setAccelerator(this.fileCloseAccel);
        this.fileSave = new JMenuItem("Save");
        this.fileSave.setAccelerator(this.fileSaveAccel);
        this.fileSaveAs = new JMenuItem("Save As...");
        this.fileSaveAs.setAccelerator(this.fileSaveAsAccel);
        this.fileExit = new JMenuItem("Exit");
        this.editUndo = new JMenuItem("Undo");
        this.editUndo.setAccelerator(this.editUndoAccel);
        this.editRedo = new JMenuItem("Redo");
        this.editRedo.setAccelerator(this.editRedoAccel);
        this.editCutLevel = new JMenuItem("Cut Level");
        this.editCutLevel.setAccelerator(this.editCutLevelAccel);
        this.editCopyLevel = new JMenuItem("Copy Level");
        this.editCopyLevel.setAccelerator(this.editCopyLevelAccel);
        this.editPasteLevel = new JMenuItem("Paste Level");
        this.editPasteLevel.setAccelerator(this.editPasteLevelAccel);
        this.editInsertLevelFromClipboard = new JMenuItem(
                "Insert Level From Clipboard");
        this.editInsertLevelFromClipboard
                .setAccelerator(this.editInsertLevelFromClipboardAccel);
        this.editPreferences = new JMenuItem("Preferences...");
        this.editPreferences.setAccelerator(this.editPreferencesAccel);
        this.editClearHistory = new JMenuItem("Clear History");
        this.editClearHistory.setAccelerator(this.editClearHistoryAccel);
        this.playPlay = new JMenuItem("Play");
        this.playPlay.setAccelerator(this.playPlayWorldAccel);
        this.playEdit = new JMenuItem("Edit");
        this.playEdit.setAccelerator(this.playEditWorldAccel);
        this.playRegisterCharacter = new JMenuItem("Register Character...");
        this.playUnregisterCharacter = new JMenuItem("Unregister Character...");
        this.gameEquipment = new JMenuItem("Show Equipment...");
        this.gameItemInventory = new JMenuItem("Show Item Inventory...");
        this.gameObjectInventory = new JMenuItem("Show Object Inventory...");
        this.gameObjectInventory.setAccelerator(this.gameObjectInventoryAccel);
        this.gameUse = new JMenuItem("Use an Item...");
        this.gameUse.setAccelerator(this.gameUseAccel);
        this.gamePickLeader = new JMenuItem("Pick Leader...");
        this.battleSpell = new JMenuItem("Cast Spell");
        this.battleItem = new JMenuItem("Use an Item");
        this.battleSteal = new JMenuItem("Steal Stuff");
        this.battleDrain = new JMenuItem("Drain MP");
        this.battleEndTurn = new JMenuItem("End Turn");
        this.editorGoTo = new JMenuItem("Go To...");
        this.editorGoTo.setAccelerator(this.editorGoToAccel);
        this.editorUpOneFloor = new JMenuItem("Up One Floor");
        this.editorUpOneFloor.setAccelerator(this.editorUpOneFloorAccel);
        this.editorDownOneFloor = new JMenuItem("Down One Floor");
        this.editorDownOneFloor.setAccelerator(this.editorDownOneFloorAccel);
        this.editorUpOneLevel = new JMenuItem("Up One Level");
        this.editorUpOneLevel.setAccelerator(this.editorUpOneLevelAccel);
        this.editorDownOneLevel = new JMenuItem("Down One Level");
        this.editorDownOneLevel.setAccelerator(this.editorDownOneLevelAccel);
        this.editorAddLevel = new JMenuItem("Add a Level...");
        this.editorRemoveLevel = new JMenuItem("Remove a Level...");
        this.editorResizeLevel = new JMenuItem("Resize Current Level...");
        this.editorFillFloor = new JMenuItem("Fill Current Floor");
        this.editorFillLevel = new JMenuItem("Fill Current Level");
        this.editorFillFloorRandomly = new JMenuItem(
                "Fill Current Floor Randomly");
        this.editorFillLevelRandomly = new JMenuItem(
                "Fill Current Level Randomly");
        this.editorFillRuleSets = new JMenuItem("Fill Rule Sets...");
        this.editorFillUseRuleSets = new JCheckBoxMenuItem(
                "Use Fill Rule Sets");
        this.editorToggleLayer = new JMenuItem("Toggle Layer");
        this.editorToggleScript = new JCheckBoxMenuItem("Toggle Script");
        this.editorToggleLayer.setAccelerator(this.editorToggleLayerAccel);
        this.editorLevelPreferences = new JMenuItem("Level Preferences...");
        this.editorWorldPreferences = new JMenuItem("World Preferences...");
        this.editorSetStartPoint = new JMenuItem("Set Start Point...");
        this.debugToggleAI = new JMenuItem("Turn AI Off");
        this.pluginRegister = new JMenuItem("Register Plugin...");
        this.pluginUnregister = new JMenuItem("Unregister Plugin...");
        this.helpAbout = new JMenuItem("About Worldz...");
        this.helpGeneralHelp = new JMenuItem("Worldz Help");
        this.helpObjectHelp = new JMenuItem("Worldz Object Help");
        this.fileNew.addActionListener(this.handler);
        this.fileOpen.addActionListener(this.handler);
        this.fileClose.addActionListener(this.handler);
        this.fileSave.addActionListener(this.handler);
        this.fileSaveAs.addActionListener(this.handler);
        this.fileExit.addActionListener(this.handler);
        this.editUndo.addActionListener(this.handler);
        this.editRedo.addActionListener(this.handler);
        this.editCutLevel.addActionListener(this.handler);
        this.editCopyLevel.addActionListener(this.handler);
        this.editPasteLevel.addActionListener(this.handler);
        this.editInsertLevelFromClipboard.addActionListener(this.handler);
        this.editPreferences.addActionListener(this.handler);
        this.editClearHistory.addActionListener(this.handler);
        this.playPlay.addActionListener(this.handler);
        this.playEdit.addActionListener(this.handler);
        this.playRegisterCharacter.addActionListener(this.handler);
        this.playUnregisterCharacter.addActionListener(this.handler);
        this.gameEquipment.addActionListener(this.handler);
        this.gameItemInventory.addActionListener(this.handler);
        this.gameObjectInventory.addActionListener(this.handler);
        this.gameUse.addActionListener(this.handler);
        this.gamePickLeader.addActionListener(this.handler);
        this.battleSpell.addActionListener(this.handler);
        this.battleItem.addActionListener(this.handler);
        this.battleSteal.addActionListener(this.handler);
        this.battleDrain.addActionListener(this.handler);
        this.battleEndTurn.addActionListener(this.handler);
        this.editorGoTo.addActionListener(this.handler);
        this.editorUpOneFloor.addActionListener(this.handler);
        this.editorDownOneFloor.addActionListener(this.handler);
        this.editorUpOneLevel.addActionListener(this.handler);
        this.editorDownOneLevel.addActionListener(this.handler);
        this.editorAddLevel.addActionListener(this.handler);
        this.editorRemoveLevel.addActionListener(this.handler);
        this.editorResizeLevel.addActionListener(this.handler);
        this.editorFillFloor.addActionListener(this.handler);
        this.editorFillLevel.addActionListener(this.handler);
        this.editorFillFloorRandomly.addActionListener(this.handler);
        this.editorFillLevelRandomly.addActionListener(this.handler);
        this.editorFillRuleSets.addActionListener(this.handler);
        this.editorToggleLayer.addActionListener(this.handler);
        this.editorToggleScript.addActionListener(this.handler);
        this.editorLevelPreferences.addActionListener(this.handler);
        this.editorWorldPreferences.addActionListener(this.handler);
        this.editorSetStartPoint.addActionListener(this.handler);
        this.debugToggleAI.addActionListener(this.handler);
        this.pluginRegister.addActionListener(this.handler);
        this.pluginUnregister.addActionListener(this.handler);
        this.helpAbout.addActionListener(this.handler);
        this.helpGeneralHelp.addActionListener(this.handler);
        this.helpObjectHelp.addActionListener(this.handler);
        this.editorFillSubMenu.add(this.editorFillFloor);
        this.editorFillSubMenu.add(this.editorFillLevel);
        this.editorFillSubMenu.add(this.editorFillFloorRandomly);
        this.editorFillSubMenu.add(this.editorFillLevelRandomly);
        this.editorFillSubMenu.add(this.editorFillRuleSets);
        this.editorFillSubMenu.add(this.editorFillUseRuleSets);
        this.fileMenu.add(this.fileNew);
        this.fileMenu.add(this.fileOpen);
        this.fileMenu.add(this.fileClose);
        this.fileMenu.add(this.fileSave);
        this.fileMenu.add(this.fileSaveAs);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.fileMenu.add(this.fileExit);
        }
        this.editMenu.add(this.editUndo);
        this.editMenu.add(this.editRedo);
        this.editMenu.add(this.editCutLevel);
        this.editMenu.add(this.editCopyLevel);
        this.editMenu.add(this.editPasteLevel);
        this.editMenu.add(this.editInsertLevelFromClipboard);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.editMenu.add(this.editPreferences);
        }
        this.editMenu.add(this.editClearHistory);
        this.playMenu.add(this.playPlay);
        this.playMenu.add(this.playEdit);
        this.playMenu.add(this.playRegisterCharacter);
        this.playMenu.add(this.playUnregisterCharacter);
        this.gameMenu.add(this.gameEquipment);
        this.gameMenu.add(this.gameItemInventory);
        this.gameMenu.add(this.gameObjectInventory);
        this.gameMenu.add(this.gameUse);
        this.gameMenu.add(this.gamePickLeader);
        this.battleMenu.add(this.battleSpell);
        this.battleMenu.add(this.battleItem);
        this.battleMenu.add(this.battleSteal);
        this.battleMenu.add(this.battleDrain);
        this.battleMenu.add(this.battleEndTurn);
        this.editorMenu.add(this.editorGoTo);
        this.editorMenu.add(this.editorUpOneFloor);
        this.editorMenu.add(this.editorDownOneFloor);
        this.editorMenu.add(this.editorUpOneLevel);
        this.editorMenu.add(this.editorDownOneLevel);
        this.editorMenu.add(this.editorAddLevel);
        this.editorMenu.add(this.editorRemoveLevel);
        this.editorMenu.add(this.editorResizeLevel);
        this.editorMenu.add(this.editorFillSubMenu);
        this.editorMenu.add(this.editorToggleLayer);
        this.editorMenu.add(this.editorToggleScript);
        this.editorMenu.add(this.editorLevelPreferences);
        this.editorMenu.add(this.editorWorldPreferences);
        this.editorMenu.add(this.editorSetStartPoint);
        this.debugMenu.add(this.debugToggleAI);
        this.pluginMenu.add(this.pluginRegister);
        this.pluginMenu.add(this.pluginUnregister);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.helpMenu.add(this.helpAbout);
        }
        this.helpMenu.add(this.helpGeneralHelp);
        this.helpMenu.add(this.helpObjectHelp);
        this.mainMenuBar.add(this.fileMenu);
        this.mainMenuBar.add(this.editMenu);
        this.mainMenuBar.add(this.playMenu);
        this.mainMenuBar.add(this.gameMenu);
        this.mainMenuBar.add(this.battleMenu);
        this.mainMenuBar.add(this.editorMenu);
        this.mainMenuBar.add(this.debugMenu);
        this.mainMenuBar.add(this.pluginMenu);
        this.mainMenuBar.add(this.helpMenu);
    }

    public void dynAddMenuItems(final JMenuItem[] items, final String menuName,
            final boolean flag) {
        // Get Menu to add items to
        JMenu menuToAddTo;
        final boolean menuExists = this.doesMenuExist(menuName);
        if (menuExists) {
            menuToAddTo = this.getMenuWithName(menuName);
        } else {
            menuToAddTo = this.createNewMenuWithName(menuName);
        }
        // Add items to menu, along with action listener
        for (final JMenuItem item : items) {
            if (flag) {
                item.addActionListener(this.phandler);
            }
            menuToAddTo.add(item);
        }
    }

    private JMenu createNewMenuWithName(final String menuName) {
        // Create the new menu
        final JMenu menu = new JMenu(menuName);
        // Add it to the menu bar before the Help menu
        this.mainMenuBar.add(menu, this.mainMenuBar.getMenuCount() - 1);
        // Rebuild the menu
        this.mainMenuBar.validate();
        // Return newly created menu
        return menu;
    }

    private JMenu getMenuWithName(final String menuName) {
        final int menuCount = this.mainMenuBar.getMenuCount();
        for (int x = 0; x < menuCount; x++) {
            if (this.mainMenuBar.getMenu(x).getText().equals(menuName)) {
                return this.mainMenuBar.getMenu(x);
            }
        }
        return null;
    }

    private boolean doesMenuExist(final String menuName) {
        final int menuCount = this.mainMenuBar.getMenuCount();
        for (int x = 0; x < menuCount; x++) {
            final JMenu menu = this.mainMenuBar.getMenu(x);
            if (menu != null) {
                final String text = menu.getText();
                if (text != null) {
                    if (text.equals(menuName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void setInitialMenuState() {
        this.fileNew.setEnabled(true);
        this.fileOpen.setEnabled(true);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.editorGoTo.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorToggleScript.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorWorldPreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.playPlay.setEnabled(false);
        this.playEdit.setEnabled(false);
        this.playRegisterCharacter.setEnabled(false);
        this.playUnregisterCharacter.setEnabled(false);
        this.gameEquipment.setEnabled(false);
        this.gameItemInventory.setEnabled(false);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.battleSpell.setEnabled(false);
        this.battleItem.setEnabled(false);
        this.battleSteal.setEnabled(false);
        this.battleDrain.setEnabled(false);
        this.battleEndTurn.setEnabled(false);
        this.debugToggleAI.setEnabled(true);
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
                final Application app = Worldz.getApplication();
                final Battle ba = app.getBattle();
                boolean loaded = false;
                final String cmd = e.getActionCommand();
                if (cmd.equals("New...")) {
                    loaded = app.getEditor().newWorld();
                    app.getWorldManager().setLoaded(loaded);
                } else if (cmd.equals("Open...")) {
                    loaded = app.getWorldManager().loadWorld();
                    app.getWorldManager().setLoaded(loaded);
                } else if (cmd.equals("Close")) {
                    // Close the window
                    if (app.getMode() == Application.STATUS_EDITOR) {
                        app.getEditor().handleCloseWindow();
                    } else if (app.getMode() == Application.STATUS_GAME) {
                        boolean saved = true;
                        int status = 0;
                        if (app.getWorldManager().getDirty()) {
                            status = app.getWorldManager().showSaveDialog();
                            if (status == JOptionPane.YES_OPTION) {
                                saved = app.getWorldManager().saveWorld();
                            } else if (status == JOptionPane.CANCEL_OPTION) {
                                saved = false;
                            } else {
                                app.getWorldManager().setDirty(false);
                            }
                        }
                        if (saved) {
                            app.getGameManager().hideOutput();
                            app.getGUIManager().showGUI();
                        }
                    }
                } else if (cmd.equals("Save")) {
                    if (app.getWorldManager().getLoaded()) {
                        app.getWorldManager().saveWorld();
                    } else {
                        Messager.showDialog("No World Opened");
                    }
                } else if (cmd.equals("Save As...")) {
                    if (app.getWorldManager().getLoaded()) {
                        app.getWorldManager().saveWorldAs();
                    } else {
                        Messager.showDialog("No World Opened");
                    }
                } else if (cmd.equals("Exit")) {
                    // Exit program
                    if (app.getWorldManager().quitHandler()) {
                        System.exit(0);
                    }
                } else if (cmd.equals("Undo")) {
                    // Undo most recent action
                    app.getEditor().undo();
                } else if (cmd.equals("Redo")) {
                    // Redo most recent undone action
                    app.getEditor().redo();
                } else if (cmd.equals("Cut Level")) {
                    // Cut Level
                    final int level = app.getEditor().getLocationManager()
                            .getEditorLocationW();
                    app.getWorldManager().getWorld().cutLevel();
                    app.getEditor().fixLimits();
                    app.getEditor().updateEditorLevelAbsolute(level);
                } else if (cmd.equals("Copy Level")) {
                    // Copy Level
                    app.getWorldManager().getWorld().copyLevel();
                } else if (cmd.equals("Paste Level")) {
                    // Paste Level
                    app.getWorldManager().getWorld().pasteLevel();
                    app.getEditor().fixLimits();
                    app.getEditor().redrawEditor();
                } else if (cmd.equals("Insert Level From Clipboard")) {
                    // Insert Level From Clipboard
                    app.getWorldManager().getWorld().insertLevelFromClipboard();
                    app.getEditor().fixLimits();
                } else if (cmd.equals("Preferences...")) {
                    // Show preferences dialog
                    app.getPrefsManager().showPrefs();
                } else if (cmd.equals("Clear History")) {
                    // Clear undo/redo history, confirm first
                    final int res = Messager.showConfirmDialog(
                            "Are you sure you want to clear the history?",
                            "Editor");
                    if (res == JOptionPane.YES_OPTION) {
                        app.getEditor().clearHistory();
                    }
                } else if (cmd.equals("Go To...")) {
                    // Go To
                    app.getEditor().goToHandler();
                } else if (cmd.equals("Up One Floor")) {
                    // Go up one floor
                    app.getEditor().updateEditorPosition(0, 0, 1, 0);
                } else if (cmd.equals("Down One Floor")) {
                    // Go down one floor
                    app.getEditor().updateEditorPosition(0, 0, -1, 0);
                } else if (cmd.equals("Up One Level")) {
                    // Go up one level
                    app.getEditor().updateEditorPosition(0, 0, 0, 1);
                } else if (cmd.equals("Down One Level")) {
                    // Go down one level
                    app.getEditor().updateEditorPosition(0, 0, 0, -1);
                } else if (cmd.equals("Add a Level...")) {
                    // Add a level
                    app.getEditor().addLevel();
                } else if (cmd.equals("Remove a Level...")) {
                    // Remove a level
                    app.getEditor().removeLevel();
                } else if (cmd.equals("Resize Current Level...")) {
                    // Resize level
                    app.getEditor().resizeLevel();
                } else if (cmd.equals("Fill Current Floor")) {
                    // Fill floor
                    app.getEditor().fillFloor();
                } else if (cmd.equals("Fill Current Level")) {
                    // Fill level
                    app.getEditor().fillLevel();
                } else if (cmd.equals("Fill Current Floor Randomly")) {
                    // Fill floor randomly
                    app.getEditor().fillFloorRandomly();
                } else if (cmd.equals("Fill Current Level Randomly")) {
                    // Fill level randomly
                    app.getEditor().fillLevelRandomly();
                } else if (cmd.equals("Fill Rule Sets...")) {
                    // Fill Rule Sets
                    app.getRuleSetPicker().editRuleSets();
                } else if (cmd.equals("Toggle Layer")) {
                    // Toggle current layer
                    app.getEditor().toggleLayer();
                } else if (cmd.equals("Toggle Script")) {
                    // Toggle script mode
                    app.getEditor().toggleScript();
                } else if (cmd.equals("Level Preferences...")) {
                    // Set Level Preferences
                    app.getEditor().setLevelPrefs();
                } else if (cmd.equals("World Preferences...")) {
                    // Set World Preferences
                    app.getEditor().setWorldPrefs();
                } else if (cmd.equals("Set Start Point...")) {
                    // Set Start Point
                    app.getEditor().editPlayerLocation();
                } else if (cmd.equals("Play")) {
                    // Play the current world
                    final boolean proceed = app.getGameManager().newGame();
                    if (proceed) {
                        app.getGameManager().playWorld();
                    }
                } else if (cmd.equals("Edit")) {
                    // Edit the current world
                    app.getEditor().editWorld();
                } else if (cmd.equals("Register Character...")) {
                    // Register Character
                    CharacterRegistration.registerCharacter();
                } else if (cmd.equals("Unregister Character...")) {
                    // Unregister Character
                    CharacterRegistration.unregisterCharacter();
                } else if (cmd.equals("Show Equipment...")) {
                    if (!app.getGameManager().usingAnItem()) {
                        app.getGameManager().showEquipmentDialog();
                    }
                } else if (cmd.equals("Show Item Inventory...")) {
                    if (!app.getGameManager().usingAnItem()) {
                        app.getGameManager().showItemInventoryDialog();
                    }
                } else if (cmd.equals("Show Object Inventory...")) {
                    if (!app.getGameManager().usingAnItem()) {
                        app.getGameManager().showObjectInventoryDialog();
                    }
                } else if (cmd.equals("Use an Item...")) {
                    if (!app.getGameManager().usingAnItem()) {
                        app.getGameManager().setUsingAnItem(true);
                        app.getGameManager().showUseDialog();
                    }
                } else if (cmd.equals("Pick Leader...")) {
                    if (!app.getGameManager().usingAnItem()) {
                        PartyManager.getParty().setLeader(PartyManager
                                .getParty().pickOnePartyMember().getName());
                        Worldz.getApplication().getGameManager().updateStats();
                    }
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
                } else if (cmd.equals("Turn AI Off")) {
                    MenuManager.this.debugToggleAI.setText("Turn AI On");
                    ba.turnAIOff();
                } else if (cmd.equals("Turn AI On")) {
                    MenuManager.this.debugToggleAI.setText("Turn AI Off");
                    ba.turnAIOn();
                } else if (cmd.equals("End Turn")) {
                    ba.endTurn();
                } else if (cmd.equals("Register Plugin...")) {
                    PluginRegistration.registerPlugin();
                } else if (cmd.equals("Unregister Plugin...")) {
                    PluginRegistration.unregisterPlugin();
                } else if (cmd.equals("About Worldz...")) {
                    app.getAboutDialog().showAboutDialog();
                } else if (cmd.equals("Worldz Help")) {
                    app.getGeneralHelpManager().showHelp();
                } else if (cmd.equals("Worldz Object Help")) {
                    app.getObjectHelpManager().showHelp();
                }
                MenuManager.this.checkFlags();
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }
    }

    private static class PluginEventHandler implements ActionListener {
        public PluginEventHandler() {
            // TODO Auto-generated constructor stub
        }

        // Handle dynamically added menus
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                // Leave whatever we're in
                Worldz.leaveWorldz();
            } catch (final Exception ex) {
                Worldz.getDebug().debug(ex);
            }
        }
    }
}
