/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fantastlex.battle.AbstractBattle;
import com.puttysoftware.fantastlex.creatures.characterfiles.CharacterRegistration;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.creatures.party.PartyMember;
import com.puttysoftware.fantastlex.editor.MazeEditorLogic;
import com.puttysoftware.fantastlex.editor.namer.abc.AbstractEditor;
import com.puttysoftware.fantastlex.game.InventoryViewer;
import com.puttysoftware.fantastlex.game.NoteManager;
import com.puttysoftware.fantastlex.game.PrestigeViewer;
import com.puttysoftware.fantastlex.game.StatisticsViewer;
import com.puttysoftware.fantastlex.prefs.PreferencesManager;

public class MenuManager {
    // Fields
    JMenuBar mainMenuBar;
    private JMenuItem fileNew, fileOpen, fileOpenGame, fileImportGame,
            fileClose, fileSave, fileSaveAs, fileSaveGame, fileExportGame,
            fileExit;
    private JMenuItem editUndo, editRedo, editCutLevel, editCopyLevel,
            editPasteLevel, editInsertLevelFromClipboard, editPreferences,
            editClearHistory;
    private JMenuItem playPlay, playEdit;
    private JMenuItem gameObjectInventory, gameReset, gameEquipment,
            gameInventory, gamePickLeader, gameRegisterCharacter,
            gameUnregisterCharacter, gameRemoveCharacter, gameEditNote,
            gameViewPrestige, gameViewScore, gameViewStats, gameUseAnItem,
            gameSwitchBow;
    private JMenuItem editorGoToLocation, editorGoToDestination,
            editorUpOneFloor, editorDownOneFloor, editorUpOneLevel,
            editorDownOneLevel, editorAddLevel, editorRemoveLevel,
            editorResizeLevel, editorToggleLayer, editorLevelPreferences,
            editorMazePreferences, editorSetStartPoint;
    private JMenuItem editorFillFloor, editorFillLevel,
            editorFillFloorRandomly, editorFillLevelRandomly,
            editorFillFloorLayer, editorFillLevelLayer,
            editorFillFloorLayerRandomly, editorFillLevelLayerRandomly,
            editorFillRuleSets, editorSetFirstMovingFinish;
    private JCheckBoxMenuItem editorFillUseRuleSets;
    private JMenuItem battleItem, battleSpell, battleSteal, battleDrain,
            battleEndTurn;
    private JMenuItem helpAbout, helpObjectHelp;
    private KeyStroke fileNewAccel, fileOpenAccel, fileCloseAccel,
            fileSaveAccel, fileSaveAsAccel;
    private KeyStroke editUndoAccel, editRedoAccel, editCutLevelAccel,
            editCopyLevelAccel, editPasteLevelAccel,
            editInsertLevelFromClipboardAccel, editPreferencesAccel,
            editClearHistoryAccel;
    private KeyStroke playPlayMazeAccel, playEditMazeAccel;
    private KeyStroke gameObjectInventoryAccel, gameResetAccel;
    private KeyStroke editorGoToLocationAccel, editorUpOneFloorAccel,
            editorDownOneFloorAccel, editorUpOneLevelAccel,
            editorDownOneLevelAccel, editorToggleLayerAccel;
    private KeyStroke battleItemAccel, battleSpellAccel, battleStealAccel,
            battleDrainAccel, battleEndTurnAccel;
    private final EventHandler handler;
    private boolean gameFlag;

    // Constructors
    public MenuManager() {
        this.handler = new EventHandler();
        this.createAccelerators();
        this.createMenus();
        this.setInitialMenuState();
        this.gameFlag = false;
    }

    // Methods
    public JMenuBar getMainMenuBar() {
        return this.mainMenuBar;
    }

    public void setGameMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileOpenGame.setEnabled(false);
        this.fileImportGame.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.disableEditorMenus();
        this.disableBattleMenus();
        this.enableGameMenus();
        final ArrayList<AbstractEditor> allEditors = FantastleX
                .getApplication().getAllEditors();
        for (final AbstractEditor ge : allEditors) {
            ge.disableEditorCommands();
        }
        this.checkFlags();
    }

    public void setBattleMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileOpenGame.setEnabled(false);
        this.fileImportGame.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.disableGameMenus();
        this.enableBattleMenus();
        final ArrayList<AbstractEditor> allEditors = FantastleX
                .getApplication().getAllEditors();
        for (final AbstractEditor ge : allEditors) {
            ge.disableEditorCommands();
        }
    }

    public void setEditorMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileOpenGame.setEnabled(false);
        this.fileImportGame.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editCutLevel.setEnabled(true);
        this.editCopyLevel.setEnabled(true);
        this.editPasteLevel.setEnabled(true);
        this.editInsertLevelFromClipboard.setEnabled(true);
        this.editPreferences.setEnabled(true);
        this.enableEditorMenus();
        this.disableBattleMenus();
        this.disableGameMenus();
        final ArrayList<AbstractEditor> allEditors = FantastleX
                .getApplication().getAllEditors();
        for (final AbstractEditor ge : allEditors) {
            ge.enableEditorCommands();
        }
        this.checkFlags();
    }

    public void setPrefMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileOpenGame.setEnabled(false);
        this.fileImportGame.setEnabled(false);
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
        this.disableEditorMenus();
        this.disableBattleMenus();
        this.disableGameMenus();
        final ArrayList<AbstractEditor> allEditors = FantastleX
                .getApplication().getAllEditors();
        for (final AbstractEditor ge : allEditors) {
            ge.disableEditorCommands();
        }
    }

    public void setHelpMenus() {
        this.fileNew.setEnabled(false);
        this.fileOpen.setEnabled(false);
        this.fileOpenGame.setEnabled(false);
        this.fileImportGame.setEnabled(false);
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
        this.disableEditorMenus();
        this.disableBattleMenus();
        this.disableGameMenus();
        final ArrayList<AbstractEditor> allEditors = FantastleX
                .getApplication().getAllEditors();
        for (final AbstractEditor ge : allEditors) {
            ge.disableEditorCommands();
        }
    }

    public void setMainMenus() {
        this.fileNew.setEnabled(true);
        this.fileOpen.setEnabled(true);
        this.fileOpenGame.setEnabled(true);
        this.fileImportGame.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.disableEditorMenus();
        this.disableBattleMenus();
        this.disableGameMenus();
        final ArrayList<AbstractEditor> allEditors = FantastleX
                .getApplication().getAllEditors();
        for (final AbstractEditor ge : allEditors) {
            ge.disableEditorCommands();
        }
        this.checkFlags();
    }

    private void disableEditorMenus() {
        this.editorGoToLocation.setEnabled(false);
        this.editorGoToDestination.setEnabled(false);
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
        this.editorFillFloorLayer.setEnabled(false);
        this.editorFillLevelLayer.setEnabled(false);
        this.editorFillFloorLayerRandomly.setEnabled(false);
        this.editorFillLevelLayerRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorMazePreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.editorSetFirstMovingFinish.setEnabled(false);
    }

    private void enableEditorMenus() {
        this.editorGoToLocation.setEnabled(true);
        this.editorGoToDestination.setEnabled(true);
        this.editorUpOneFloor.setEnabled(true);
        this.editorDownOneFloor.setEnabled(true);
        this.editorUpOneLevel.setEnabled(true);
        this.editorDownOneLevel.setEnabled(true);
        this.editorAddLevel.setEnabled(true);
        this.editorRemoveLevel.setEnabled(true);
        this.editorResizeLevel.setEnabled(true);
        this.editorFillFloor.setEnabled(true);
        this.editorFillLevel.setEnabled(true);
        this.editorFillFloorRandomly.setEnabled(true);
        this.editorFillLevelRandomly.setEnabled(true);
        this.editorFillFloorLayer.setEnabled(true);
        this.editorFillLevelLayer.setEnabled(true);
        this.editorFillFloorLayerRandomly.setEnabled(true);
        this.editorFillLevelLayerRandomly.setEnabled(true);
        this.editorFillRuleSets.setEnabled(true);
        this.editorFillUseRuleSets.setEnabled(true);
        this.editorToggleLayer.setEnabled(true);
        this.editorLevelPreferences.setEnabled(true);
        this.editorMazePreferences.setEnabled(true);
        this.editorSetStartPoint.setEnabled(true);
        this.editorSetFirstMovingFinish.setEnabled(true);
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

    private void enableGameMenus() {
        this.gameObjectInventory.setEnabled(true);
        this.gameReset.setEnabled(true);
        this.gameEquipment.setEnabled(true);
        this.gameInventory.setEnabled(true);
        this.gamePickLeader.setEnabled(true);
        this.gameEditNote.setEnabled(true);
        this.gameViewPrestige.setEnabled(true);
        this.gameViewScore.setEnabled(true);
        this.gameViewStats.setEnabled(true);
        this.gameUseAnItem.setEnabled(true);
        this.gameSwitchBow.setEnabled(true);
    }

    private void disableGameMenus() {
        this.gameObjectInventory.setEnabled(false);
        this.gameReset.setEnabled(false);
        this.gameEquipment.setEnabled(false);
        this.gameInventory.setEnabled(false);
        this.gamePickLeader.setEnabled(false);
        this.gameEditNote.setEnabled(false);
        this.gameViewPrestige.setEnabled(false);
        this.gameViewScore.setEnabled(false);
        this.gameViewStats.setEnabled(false);
        this.gameUseAnItem.setEnabled(false);
        this.gameSwitchBow.setEnabled(false);
    }

    public void setGameFlag() {
        this.gameFlag = true;
    }

    public void clearGameFlag() {
        this.gameFlag = false;
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

    public void checkFlags() {
        final Application app = FantastleX.getApplication();
        if (app.getMazeManager().getDirty()) {
            this.setMenusDirtyOn();
        } else {
            this.setMenusDirtyOff();
        }
        if (app.getMazeManager().getLoaded()) {
            this.setMenusLoadedOn();
        } else {
            this.setMenusLoadedOff();
        }
        if (app.getMode() == Application.STATUS_EDITOR) {
            if (app.getMazeManager().getMaze().isPasteBlocked()) {
                this.disablePasteLevel();
                this.disableInsertLevelFromClipboard();
            } else {
                this.enablePasteLevel();
                this.enableInsertLevelFromClipboard();
            }
            if (app.getMazeManager().getMaze().isCutBlocked()) {
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
        final Application app = FantastleX.getApplication();
        if (app.getMode() == Application.STATUS_GUI) {
            this.fileClose.setEnabled(false);
            this.fileSaveAs.setEnabled(false);
            this.fileSaveGame.setEnabled(false);
            this.fileExportGame.setEnabled(false);
            if (app.getMazeManager().getMaze().doesPlayerExist()) {
                this.playPlay.setEnabled(true);
            } else {
                this.playPlay.setEnabled(false);
            }
            if (this.gameFlag) {
                this.playEdit.setEnabled(false);
            } else {
                this.playEdit.setEnabled(true);
            }
        } else {
            this.fileClose.setEnabled(true);
            this.fileSaveAs.setEnabled(true);
            this.fileSaveGame.setEnabled(true);
            this.fileExportGame.setEnabled(true);
            this.playPlay.setEnabled(false);
            this.playEdit.setEnabled(false);
        }
    }

    private void setMenusLoadedOff() {
        this.fileClose.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.fileSaveGame.setEnabled(false);
        this.fileExportGame.setEnabled(false);
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
        this.fileSaveAsAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey
                | InputEvent.SHIFT_DOWN_MASK);
        this.editUndoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z, modKey);
        this.editRedoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z, modKey
                | InputEvent.SHIFT_DOWN_MASK);
        this.editCutLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_X, modKey);
        this.editCopyLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_C, modKey);
        this.editPasteLevelAccel = KeyStroke
                .getKeyStroke(KeyEvent.VK_V, modKey);
        this.editInsertLevelFromClipboardAccel = KeyStroke.getKeyStroke(
                KeyEvent.VK_F, modKey);
        this.editPreferencesAccel = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,
                modKey);
        this.editClearHistoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                modKey);
        this.editorGoToLocationAccel = KeyStroke.getKeyStroke(KeyEvent.VK_G,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.playPlayMazeAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P, modKey);
        this.playEditMazeAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E, modKey);
        this.gameObjectInventoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_I,
                modKey);
        this.gameResetAccel = KeyStroke.getKeyStroke(KeyEvent.VK_R, modKey);
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
        this.battleItemAccel = KeyStroke.getKeyStroke(KeyEvent.VK_M, modKey);
        this.battleSpellAccel = KeyStroke.getKeyStroke(KeyEvent.VK_L, modKey
                | InputEvent.SHIFT_DOWN_MASK);
        this.battleStealAccel = KeyStroke.getKeyStroke(KeyEvent.VK_T, modKey);
        this.battleDrainAccel = KeyStroke.getKeyStroke(KeyEvent.VK_D, modKey);
        this.battleEndTurnAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E, modKey
                | InputEvent.SHIFT_DOWN_MASK);
    }

    private void createMenus() {
        this.mainMenuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        final JMenu editMenu = new JMenu("Edit");
        final JMenu playMenu = new JMenu("Play");
        final JMenu gameMenu = new JMenu("Game");
        final JMenu editorMenu = new JMenu("Editor");
        final JMenu battleMenu = new JMenu("Battle");
        final JMenu helpMenu = new JMenu("Help");
        final JMenu editorFillSubMenu = new JMenu("Fill");
        this.fileNew = new JMenuItem("New...");
        this.fileNew.setAccelerator(this.fileNewAccel);
        this.fileOpen = new JMenuItem("Open...");
        this.fileOpen.setAccelerator(this.fileOpenAccel);
        this.fileOpenGame = new JMenuItem("Open Game...");
        this.fileImportGame = new JMenuItem("Import Game...");
        this.fileClose = new JMenuItem("Close");
        this.fileClose.setAccelerator(this.fileCloseAccel);
        this.fileSave = new JMenuItem("Save");
        this.fileSave.setAccelerator(this.fileSaveAccel);
        this.fileSaveAs = new JMenuItem("Save As...");
        this.fileSaveAs.setAccelerator(this.fileSaveAsAccel);
        this.fileSaveGame = new JMenuItem("Save As Game...");
        this.fileExportGame = new JMenuItem("Export As Game...");
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
        this.playPlay.setAccelerator(this.playPlayMazeAccel);
        this.playEdit = new JMenuItem("Edit");
        this.playEdit.setAccelerator(this.playEditMazeAccel);
        this.gameObjectInventory = new JMenuItem("Show Inventory...");
        this.gameObjectInventory.setAccelerator(this.gameObjectInventoryAccel);
        this.gameReset = new JMenuItem("Reset Current Level");
        this.gameReset.setAccelerator(this.gameResetAccel);
        this.gameRegisterCharacter = new JMenuItem("Register Character...");
        this.gameUnregisterCharacter = new JMenuItem("Unregister Character...");
        this.gameRemoveCharacter = new JMenuItem("Remove Character...");
        this.gameEquipment = new JMenuItem("Show Equipment...");
        this.gameInventory = new JMenuItem("Show Inventory...");
        this.gamePickLeader = new JMenuItem("Pick Leader...");
        this.gameEditNote = new JMenuItem("Edit Note...");
        this.gameViewPrestige = new JMenuItem("View Prestige...");
        this.gameViewScore = new JMenuItem("View Current Score...");
        this.gameViewStats = new JMenuItem("View Statistics...");
        this.gameUseAnItem = new JMenuItem("Use an Item...");
        this.gameSwitchBow = new JMenuItem("Switch Bow...");
        this.editorGoToLocation = new JMenuItem("Go To Location...");
        this.editorGoToLocation.setAccelerator(this.editorGoToLocationAccel);
        this.editorGoToDestination = new JMenuItem("Go To Destination...");
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
        this.editorFillFloorLayer = new JMenuItem(
                "Fill Current Floor and Layer");
        this.editorFillLevelLayer = new JMenuItem(
                "Fill Current Level and Layer");
        this.editorFillFloorLayerRandomly = new JMenuItem(
                "Fill Current Floor and Layer Randomly");
        this.editorFillLevelLayerRandomly = new JMenuItem(
                "Fill Current Level and Layer Randomly");
        this.editorFillRuleSets = new JMenuItem("Fill Rule Sets...");
        this.editorFillUseRuleSets = new JCheckBoxMenuItem("Use Fill Rule Sets");
        this.editorToggleLayer = new JMenuItem("Toggle Layer");
        this.editorToggleLayer.setAccelerator(this.editorToggleLayerAccel);
        this.editorLevelPreferences = new JMenuItem("Level Preferences...");
        this.editorMazePreferences = new JMenuItem("Maze Preferences...");
        this.editorSetStartPoint = new JMenuItem("Set Start Point...");
        this.editorSetFirstMovingFinish = new JMenuItem(
                "Set First Moving Finish...");
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
        this.helpAbout = new JMenuItem("About FantastleX...");
        this.helpObjectHelp = new JMenuItem("FantastleX Object Help");
        this.fileNew.addActionListener(this.handler);
        this.fileOpen.addActionListener(this.handler);
        this.fileOpenGame.addActionListener(this.handler);
        this.fileImportGame.addActionListener(this.handler);
        this.fileClose.addActionListener(this.handler);
        this.fileSave.addActionListener(this.handler);
        this.fileSaveAs.addActionListener(this.handler);
        this.fileSaveGame.addActionListener(this.handler);
        this.fileExportGame.addActionListener(this.handler);
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
        this.gameObjectInventory.addActionListener(this.handler);
        this.gameReset.addActionListener(this.handler);
        this.gameRegisterCharacter.addActionListener(this.handler);
        this.gameUnregisterCharacter.addActionListener(this.handler);
        this.gameRemoveCharacter.addActionListener(this.handler);
        this.gameEquipment.addActionListener(this.handler);
        this.gameInventory.addActionListener(this.handler);
        this.gamePickLeader.addActionListener(this.handler);
        this.gameEditNote.addActionListener(this.handler);
        this.gameViewPrestige.addActionListener(this.handler);
        this.gameViewScore.addActionListener(this.handler);
        this.gameViewStats.addActionListener(this.handler);
        this.gameUseAnItem.addActionListener(this.handler);
        this.gameSwitchBow.addActionListener(this.handler);
        this.editorGoToLocation.addActionListener(this.handler);
        this.editorGoToDestination.addActionListener(this.handler);
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
        this.editorFillFloorLayer.addActionListener(this.handler);
        this.editorFillLevelLayer.addActionListener(this.handler);
        this.editorFillFloorLayerRandomly.addActionListener(this.handler);
        this.editorFillLevelLayerRandomly.addActionListener(this.handler);
        this.editorFillRuleSets.addActionListener(this.handler);
        this.editorToggleLayer.addActionListener(this.handler);
        this.editorLevelPreferences.addActionListener(this.handler);
        this.editorMazePreferences.addActionListener(this.handler);
        this.editorSetStartPoint.addActionListener(this.handler);
        this.editorSetFirstMovingFinish.addActionListener(this.handler);
        this.battleSpell.addActionListener(this.handler);
        this.battleItem.addActionListener(this.handler);
        this.battleSteal.addActionListener(this.handler);
        this.battleDrain.addActionListener(this.handler);
        this.battleEndTurn.addActionListener(this.handler);
        this.helpAbout.addActionListener(this.handler);
        this.helpObjectHelp.addActionListener(this.handler);
        editorFillSubMenu.add(this.editorFillFloor);
        editorFillSubMenu.add(this.editorFillLevel);
        editorFillSubMenu.add(this.editorFillFloorRandomly);
        editorFillSubMenu.add(this.editorFillLevelRandomly);
        editorFillSubMenu.add(this.editorFillFloorLayer);
        editorFillSubMenu.add(this.editorFillLevelLayer);
        editorFillSubMenu.add(this.editorFillFloorLayerRandomly);
        editorFillSubMenu.add(this.editorFillLevelLayerRandomly);
        editorFillSubMenu.add(this.editorFillRuleSets);
        editorFillSubMenu.add(this.editorFillUseRuleSets);
        fileMenu.add(this.fileNew);
        fileMenu.add(this.fileOpen);
        fileMenu.add(this.fileOpenGame);
        fileMenu.add(this.fileImportGame);
        fileMenu.add(this.fileClose);
        fileMenu.add(this.fileSave);
        fileMenu.add(this.fileSaveAs);
        fileMenu.add(this.fileSaveGame);
        fileMenu.add(this.fileExportGame);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            fileMenu.add(this.fileExit);
        }
        editMenu.add(this.editUndo);
        editMenu.add(this.editRedo);
        editMenu.add(this.editCutLevel);
        editMenu.add(this.editCopyLevel);
        editMenu.add(this.editPasteLevel);
        editMenu.add(this.editInsertLevelFromClipboard);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            editMenu.add(this.editPreferences);
        }
        editMenu.add(this.editClearHistory);
        playMenu.add(this.playPlay);
        playMenu.add(this.playEdit);
        gameMenu.add(this.gameObjectInventory);
        gameMenu.add(this.gameReset);
        gameMenu.add(this.gameEquipment);
        gameMenu.add(this.gameInventory);
        gameMenu.add(this.gamePickLeader);
        gameMenu.add(this.gameRegisterCharacter);
        gameMenu.add(this.gameUnregisterCharacter);
        gameMenu.add(this.gameRemoveCharacter);
        gameMenu.add(this.gameEditNote);
        gameMenu.add(this.gameViewPrestige);
        gameMenu.add(this.gameViewScore);
        gameMenu.add(this.gameViewStats);
        gameMenu.add(this.gameUseAnItem);
        gameMenu.add(this.gameSwitchBow);
        final ArrayList<AbstractEditor> allEditors = FantastleX
                .getApplication().getAllEditors();
        for (final AbstractEditor ge : allEditors) {
            editorMenu.add(ge.createEditorCommandsMenu());
        }
        editorMenu.add(this.editorGoToLocation);
        editorMenu.add(this.editorGoToDestination);
        editorMenu.add(this.editorUpOneFloor);
        editorMenu.add(this.editorDownOneFloor);
        editorMenu.add(this.editorUpOneLevel);
        editorMenu.add(this.editorDownOneLevel);
        editorMenu.add(this.editorAddLevel);
        editorMenu.add(this.editorRemoveLevel);
        editorMenu.add(this.editorResizeLevel);
        editorMenu.add(editorFillSubMenu);
        editorMenu.add(this.editorToggleLayer);
        editorMenu.add(this.editorLevelPreferences);
        editorMenu.add(this.editorMazePreferences);
        editorMenu.add(this.editorSetStartPoint);
        editorMenu.add(this.editorSetFirstMovingFinish);
        battleMenu.add(this.battleSpell);
        battleMenu.add(this.battleItem);
        battleMenu.add(this.battleSteal);
        battleMenu.add(this.battleDrain);
        battleMenu.add(this.battleEndTurn);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            helpMenu.add(this.helpAbout);
        }
        helpMenu.add(this.helpObjectHelp);
        this.mainMenuBar.add(fileMenu);
        this.mainMenuBar.add(editMenu);
        this.mainMenuBar.add(playMenu);
        this.mainMenuBar.add(gameMenu);
        this.mainMenuBar.add(editorMenu);
        this.mainMenuBar.add(battleMenu);
        this.mainMenuBar.add(helpMenu);
    }

    private void setInitialMenuState() {
        this.fileNew.setEnabled(true);
        this.fileOpen.setEnabled(true);
        this.fileOpenGame.setEnabled(true);
        this.fileImportGame.setEnabled(true);
        this.fileClose.setEnabled(false);
        this.fileSave.setEnabled(false);
        this.fileSaveAs.setEnabled(false);
        this.fileSaveGame.setEnabled(false);
        this.fileExportGame.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.disableEditorMenus();
        this.playPlay.setEnabled(false);
        this.playEdit.setEnabled(false);
        this.disableGameMenus();
        this.gameRegisterCharacter.setEnabled(true);
        this.gameUnregisterCharacter.setEnabled(true);
        this.gameRemoveCharacter.setEnabled(true);
        this.disableBattleMenus();
        this.helpAbout.setEnabled(true);
        this.helpObjectHelp.setEnabled(true);
    }

    private class EventHandler implements ActionListener {
        String suffix;

        public EventHandler() {
            if (FantastleX.inDebugMode()) {
                this.suffix = " (DEBUG)";
            } else {
                this.suffix = "";
            }
        }

        // Handle menus
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final String cmd = e.getActionCommand();
                final Application app = FantastleX.getApplication();
                final AbstractBattle ba = app.getBattle();
                boolean loaded = false;
                if (cmd.equals("New...")) {
                    loaded = app.getEditor().newMaze();
                    app.getMazeManager().setLoaded(loaded);
                    if (loaded) {
                        app.getMenuManager().clearGameFlag();
                    }
                } else if (cmd.equals("Open...")) {
                    loaded = app.getMazeManager().loadMaze();
                    app.getMazeManager().setLoaded(loaded);
                } else if (cmd.equals("Open Game...")) {
                    loaded = app.getMazeManager().loadGame();
                    app.getMazeManager().setLoaded(loaded);
                } else if (cmd.equals("Import Game...")) {
                    loaded = app.getMazeManager().importGame();
                    app.getMazeManager().setLoaded(loaded);
                } else if (cmd.equals("Close")) {
                    // Close the window
                    if (app.getMode() == Application.STATUS_EDITOR) {
                        app.getEditor().handleCloseWindow();
                    } else if (app.getMode() == Application.STATUS_GAME) {
                        boolean saved = true;
                        int status = 0;
                        if (app.getMazeManager().getDirty()) {
                            status = app.getMazeManager().showSaveDialog();
                            if (status == JOptionPane.YES_OPTION) {
                                saved = app.getMazeManager().saveMaze();
                            } else if (status == JOptionPane.CANCEL_OPTION) {
                                saved = false;
                            } else {
                                app.getMazeManager().setDirty(false);
                            }
                        }
                        if (saved) {
                            app.getGameManager().exitGame();
                        }
                    }
                } else if (cmd.equals("Save")) {
                    if (app.getMazeManager().getLoaded()) {
                        app.getMazeManager().saveMaze();
                    } else {
                        CommonDialogs.showDialog("No Maze Opened");
                    }
                } else if (cmd.equals("Save As...")) {
                    if (app.getMazeManager().getLoaded()) {
                        app.getMazeManager().saveMazeAs();
                    } else {
                        CommonDialogs.showDialog("No Maze Opened");
                    }
                } else if (cmd.equals("Save As Game...")) {
                    if (app.getMazeManager().getLoaded()) {
                        app.getMazeManager().saveGame();
                    } else {
                        CommonDialogs.showDialog("No Maze Opened");
                    }
                } else if (cmd.equals("Export As Game...")) {
                    if (app.getMazeManager().getLoaded()) {
                        app.getMazeManager().exportGame();
                    } else {
                        CommonDialogs.showDialog("No Maze Opened");
                    }
                } else if (cmd.equals("Exit")) {
                    // Exit program
                    if (app.getGUIManager().quitHandler()) {
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
                    app.getMazeManager().getMaze().cutLevel();
                    app.getEditor().fixLimits();
                    app.getEditor().updateEditorLevelAbsolute(level);
                } else if (cmd.equals("Copy Level")) {
                    // Copy Level
                    app.getMazeManager().getMaze().copyLevel();
                } else if (cmd.equals("Paste Level")) {
                    // Paste Level
                    app.getMazeManager().getMaze().pasteLevel();
                    app.getEditor().fixLimits();
                    app.getEditor().redrawEditor();
                } else if (cmd.equals("Insert Level From Clipboard")) {
                    // Insert Level From Clipboard
                    app.getMazeManager().getMaze().insertLevelFromClipboard();
                    app.getEditor().fixLimits();
                } else if (cmd.equals("Preferences...")) {
                    // Show preferences dialog
                    PreferencesManager.showPrefs();
                } else if (cmd.equals("Clear History")) {
                    // Clear undo/redo history, confirm first
                    final int res = CommonDialogs.showConfirmDialog(
                            "Are you sure you want to clear the history?",
                            "Editor");
                    if (res == JOptionPane.YES_OPTION) {
                        app.getEditor().clearHistory();
                    }
                } else if (cmd.equals("Play")) {
                    // Play the current maze
                    final boolean proceed = app.getGameManager().newGame();
                    if (proceed) {
                        app.getGameManager().playMaze();
                    }
                } else if (cmd.equals("Edit")) {
                    // Edit the current maze
                    app.getEditor().editMaze();
                } else if (cmd.equals("Show Inventory...")) {
                    InventoryViewer.showObjectInventoryDialog(app
                            .getGameManager().getObjectInventory());
                } else if (cmd.equals("Reset Current Level")) {
                    final int result = CommonDialogs
                            .showConfirmDialog(
                                    "Are you sure you want to reset the current level?",
                                    "FantastleX");
                    if (result == JOptionPane.YES_OPTION) {
                        app.getGameManager().resetCurrentLevel();
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
                            "Remove Character" + this.suffix);
                    if (confirm == CommonDialogs.YES_OPTION) {
                        // Remove Character
                        CharacterRegistration.removeCharacter();
                    }
                } else if (cmd.equals("Show Equipment...")) {
                    InventoryViewer.showEquipmentDialog();
                } else if (cmd.equals("Show Inventory...")) {
                    InventoryViewer.showItemInventoryDialog();
                } else if (cmd.equals("Pick Leader...")) {
                    final PartyMember pm = PartyManager.getParty()
                            .pickOnePartyMember();
                    if (pm != null) {
                        PartyManager.getParty().setLeader(pm.getName());
                    }
                    app.getGameManager().updateStats();
                } else if (cmd.equals("Edit Note...")) {
                    // Edit Note
                    NoteManager.editNote();
                } else if (cmd.equals("View Prestige...")) {
                    // View Prestige
                    PrestigeViewer.viewPrestige();
                } else if (cmd.equals("View Current Score...")) {
                    // View Score
                    app.getGameManager().showCurrentScore();
                } else if (cmd.equals("View Statistics...")) {
                    // View Statistics
                    StatisticsViewer.viewStatistics();
                } else if (cmd.equals("Use an Item...")) {
                    // Use an Item
                    app.getGameManager().showUseDialog();
                } else if (cmd.equals("Switch Bow...")) {
                    // Switch Bow
                    app.getGameManager().showSwitchBowDialog();
                } else if (cmd.equals("Go To Location...")) {
                    // Go To Location
                    app.getEditor().goToLocationHandler();
                } else if (cmd.equals("Go To Destination...")) {
                    // Go To Destination
                    app.getEditor().goToDestinationHandler();
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
                } else if (cmd.equals("Fill Current Floor and Layer")) {
                    // Fill floor and layer
                    app.getEditor().fillFloorAndLayer();
                } else if (cmd.equals("Fill Current Level and Layer")) {
                    // Fill level and layer
                    app.getEditor().fillLevelAndLayer();
                } else if (cmd.equals("Fill Current Floor and Layer Randomly")) {
                    // Fill floor and layer randomly
                    app.getEditor().fillFloorAndLayerRandomly();
                } else if (cmd.equals("Fill Current Level and Layer Randomly")) {
                    // Fill level and layer randomly
                    app.getEditor().fillLevelAndLayerRandomly();
                } else if (cmd.equals("Fill Rule Sets...")) {
                    // Fill Rule Sets
                    app.getRuleSetPicker().editRuleSets();
                } else if (cmd.equals("Toggle Layer")) {
                    // Toggle current layer
                    app.getEditor().toggleLayer();
                } else if (cmd.equals("Level Preferences...")) {
                    // Set Level Preferences
                    app.getEditor().setLevelPrefs();
                } else if (cmd.equals("Maze Preferences...")) {
                    // Set Maze Preferences
                    app.getEditor().setMazePrefs();
                } else if (cmd.equals("Set Start Point...")) {
                    // Set Start Point
                    app.getEditor().editPlayerLocation();
                } else if (cmd.equals("Set First Moving Finish...")) {
                    // Set First Moving Finish
                    app.getEditor().editTeleportDestination(
                            MazeEditorLogic.TELEPORT_TYPE_FIRST_MOVING_FINISH);
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
                } else if (cmd.equals("About FantastleX...")) {
                    app.getAboutDialog().showAboutDialog();
                } else if (cmd.equals("FantastleX Object Help")) {
                    app.getObjectHelpManager().showHelp();
                }
                MenuManager.this.checkFlags();
            } catch (final Exception ex) {
                FantastleX.getErrorLogger().logError(ex);
            }
        }
    }
}
