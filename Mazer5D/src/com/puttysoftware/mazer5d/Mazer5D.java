/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d;

import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;
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

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.integration.NativeIntegration;
import com.puttysoftware.mazer5d.editor.MazeEditor;
import com.puttysoftware.mazer5d.editor.rulesets.RuleSetPicker;
import com.puttysoftware.mazer5d.game.GameManager;
import com.puttysoftware.mazer5d.generic.MazeObjectList;
import com.puttysoftware.mazer5d.maze.MazeManager;
import com.puttysoftware.mazer5d.prefs.PreferencesManager;
import com.puttysoftware.mazer5d.resourcemanagers.LogoManager;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public class Mazer5D {
    // Constants
    private static Mazer5D application;
    private static final String PROGRAM_NAME = "Mazer5D";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: Mazer5D Bug Report";
    private static final String ERROR_TITLE = "Mazer5D Error";
    private static final ErrorLogger errorLogger = new ErrorLogger(
            Mazer5D.PROGRAM_NAME);
    private static final int VERSION_MAJOR = 11;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_CODE = VersionData.CODE_PRE_ALPHA;
    private static final int VERSION_PRERELEASE = 1;
    private static final int STATUS_GUI = 0;
    private static final int STATUS_GAME = 1;
    private static final int STATUS_EDITOR = 2;
    private static final int STATUS_PREFS = 3;
    private static final int STATUS_NULL = 4;
    // Fields
    private AboutDialog about;
    private GameManager gameMgr;
    private MazeManager mazeMgr;
    private MenuManager menuMgr;
    private ObjectHelpManager oHelpMgr;
    private MazeEditor editor;
    private RuleSetPicker rsPicker;
    private GUIManager guiMgr;
    private final MazeObjectList objects;
    private final NativeIntegration ni;
    private final VersionData vdat;
    private int mode;
    private int formerMode;

    // Methods
    public static Mazer5D getApplication() {
        return Mazer5D.application;
    }

    public static ErrorLogger getErrorLogger() {
        CommonDialogs.showErrorDialog(Mazer5D.ERROR_MESSAGE,
                Mazer5D.ERROR_TITLE);
        return Mazer5D.errorLogger;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            NativeIntegration ni = new NativeIntegration();
            ni.configureLookAndFeel();
            Mazer5D.application = new Mazer5D(ni);
            Mazer5D.application.playLogoSound();
            Mazer5D.application.getGUIManager().showGUI();
            // Register platform hooks
            ni.setAboutHandler(Mazer5D.application.getAboutDialog());
            ni.setOpenFileHandler(Mazer5D.application.getMazeManager());
            ni.setPreferencesHandler(new PrefsLauncher());
            ni.setQuitHandler(Mazer5D.application.getGUIManager());
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(Mazer5D.PROGRAM_NAME);
            CommonDialogs.setIcon(LogoManager.getMicroLogo());
        } catch (final Throwable t) {
            Mazer5D.getErrorLogger().logError(t);
        }
    }

    // Constructor
    public Mazer5D(final NativeIntegration inNi) {
        this.ni = inNi;
        this.objects = new MazeObjectList();
        this.vdat = new VersionData(Mazer5D.VERSION_MAJOR,
                Mazer5D.VERSION_MINOR, Mazer5D.VERSION_BUGFIX,
                Mazer5D.VERSION_CODE, Mazer5D.VERSION_PRERELEASE);
        this.mode = Mazer5D.STATUS_NULL;
        // Create Managers
        this.about = new AboutDialog(this.vdat.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.oHelpMgr = new ObjectHelpManager();
        // Cache Logo
        this.guiMgr.updateLogo();
        this.ni.setDefaultMenuBar(this.menuMgr.mainMenuBar);
    }

    public void setInGUI() {
        this.deactivateFormerMode();
        this.saveMode();
        this.mode = Mazer5D.STATUS_GUI;
        this.menuMgr.setMainMenus();
        this.menuMgr.checkFlags();
        this.activateCurrentMode();
    }

    public void setInPrefs() {
        this.deactivateFormerMode();
        this.saveMode();
        this.mode = Mazer5D.STATUS_PREFS;
        this.menuMgr.setPrefMenus();
        this.menuMgr.checkFlags();
        this.activateCurrentMode();
    }

    public void setInGame() {
        this.deactivateFormerMode();
        this.saveMode();
        this.mode = Mazer5D.STATUS_GAME;
        this.menuMgr.setGameMenus();
        this.menuMgr.checkFlags();
        this.activateCurrentMode();
    }

    public void setInEditor() {
        this.deactivateFormerMode();
        this.saveMode();
        this.mode = Mazer5D.STATUS_EDITOR;
        this.menuMgr.setEditorMenus();
        this.menuMgr.checkFlags();
        this.activateCurrentMode();
    }

    public boolean inGame() {
        return this.mode == Mazer5D.STATUS_GAME;
    }

    public boolean inEditor() {
        return this.mode == Mazer5D.STATUS_EDITOR;
    }

    public boolean inGUI() {
        return this.mode == Mazer5D.STATUS_GUI;
    }

    public void saveMode() {
        this.formerMode = this.mode;
    }

    public void restoreMode() {
        this.deactivateCurrentMode();
        this.mode = this.formerMode;
        this.activateFormerMode();
    }

    private void activateCurrentMode() {
        switch (this.mode) {
        case Mazer5D.STATUS_GAME:
            this.gameMgr.showOutput();
            break;
        case Mazer5D.STATUS_EDITOR:
            this.editor.showOutput();
            break;
        case Mazer5D.STATUS_GUI:
            this.guiMgr.showGUI();
            break;
        case Mazer5D.STATUS_PREFS:
            PreferencesManager.showPrefs();
            break;
        default:
            break;
        }
    }

    private void activateFormerMode() {
        switch (this.formerMode) {
        case Mazer5D.STATUS_GAME:
            this.gameMgr.showOutput();
            break;
        case Mazer5D.STATUS_EDITOR:
            this.editor.showOutput();
            break;
        case Mazer5D.STATUS_GUI:
            this.guiMgr.showGUI();
            break;
        case Mazer5D.STATUS_PREFS:
            PreferencesManager.showPrefs();
            break;
        default:
            break;
        }
    }

    private void deactivateCurrentMode() {
        switch (this.mode) {
        case Mazer5D.STATUS_GAME:
            this.gameMgr.hideOutput();
            break;
        case Mazer5D.STATUS_EDITOR:
            this.editor.hideOutput();
            break;
        case Mazer5D.STATUS_GUI:
            this.guiMgr.hideGUI();
            break;
        case Mazer5D.STATUS_PREFS:
            PreferencesManager.hidePrefs();
            break;
        default:
            break;
        }
    }

    private void deactivateFormerMode() {
        switch (this.formerMode) {
        case Mazer5D.STATUS_GAME:
            this.gameMgr.hideOutput();
            break;
        case Mazer5D.STATUS_EDITOR:
            this.editor.hideOutput();
            break;
        case Mazer5D.STATUS_GUI:
            this.guiMgr.hideGUI();
            break;
        case Mazer5D.STATUS_PREFS:
            PreferencesManager.hidePrefs();
            break;
        default:
            break;
        }
    }

    public void showMessage(final String msg) {
        if (this.mode == Mazer5D.STATUS_GAME) {
            this.getGameManager().setStatusMessage(msg);
        } else if (this.mode == Mazer5D.STATUS_EDITOR) {
            this.getEditor().setStatusMessage(msg);
        } else {
            CommonDialogs.showDialog(msg);
        }
    }

    public void checkFlags() {
        this.menuMgr.checkFlags();
    }

    public void setGameMenus() {
        this.menuMgr.setGameMenus();
    }

    public void setEditorMenus() {
        this.menuMgr.setEditorMenus();
    }

    public void setHelpMenus() {
        this.menuMgr.setHelpMenus();
    }

    public void setPrefMenus() {
        this.menuMgr.setPrefMenus();
    }

    public void setLockedFlag() {
        this.menuMgr.setLockedFlag();
    }

    public void clearLockedFlag() {
        this.menuMgr.clearLockedFlag();
    }

    public void enableUpOneFloor() {
        this.menuMgr.enableUpOneFloor();
    }

    public void disableUpOneFloor() {
        this.menuMgr.disableUpOneFloor();
    }

    public void enableDownOneFloor() {
        this.menuMgr.enableDownOneFloor();
    }

    public void disableDownOneFloor() {
        this.menuMgr.disableDownOneFloor();
    }

    public void enableUpOneLevel() {
        this.menuMgr.enableUpOneLevel();
    }

    public void disableUpOneLevel() {
        this.menuMgr.disableUpOneLevel();
    }

    public void enableDownOneLevel() {
        this.menuMgr.enableDownOneLevel();
    }

    public void disableDownOneLevel() {
        this.menuMgr.disableDownOneLevel();
    }

    public void enableAddLevel() {
        this.menuMgr.enableAddLevel();
    }

    public void disableAddLevel() {
        this.menuMgr.disableAddLevel();
    }

    public void enableRemoveLevel() {
        this.menuMgr.enableRemoveLevel();
    }

    public void disableRemoveLevel() {
        this.menuMgr.disableRemoveLevel();
    }

    public void enableUndo() {
        this.menuMgr.enableUndo();
    }

    public void disableUndo() {
        this.menuMgr.disableUndo();
    }

    public void enableRedo() {
        this.menuMgr.enableRedo();
    }

    public void disableRedo() {
        this.menuMgr.disableRedo();
    }

    public void enableClearHistory() {
        this.menuMgr.enableClearHistory();
    }

    public void disableClearHistory() {
        this.menuMgr.disableClearHistory();
    }

    public void enableCutLevel() {
        this.menuMgr.enableCutLevel();
    }

    public void disableCutLevel() {
        this.menuMgr.disableCutLevel();
    }

    public void enablePasteLevel() {
        this.menuMgr.enablePasteLevel();
    }

    public void disablePasteLevel() {
        this.menuMgr.disablePasteLevel();
    }

    public void enableInsertLevelFromClipboard() {
        this.menuMgr.enableInsertLevelFromClipboard();
    }

    public void disableInsertLevelFromClipboard() {
        this.menuMgr.disableInsertLevelFromClipboard();
    }

    public void enableSetStartPoint() {
        this.menuMgr.enableSetStartPoint();
    }

    public void disableSetStartPoint() {
        this.menuMgr.disableSetStartPoint();
    }

    public boolean useFillRuleSets() {
        return this.menuMgr.useFillRuleSets();
    }

    public GUIManager getGUIManager() {
        return this.guiMgr;
    }

    public GameManager getGameManager() {
        if (this.gameMgr == null) {
            this.gameMgr = new GameManager();
        }
        return this.gameMgr;
    }

    public MazeManager getMazeManager() {
        if (this.mazeMgr == null) {
            this.mazeMgr = new MazeManager();
        }
        return this.mazeMgr;
    }

    public ObjectHelpManager getObjectHelpManager() {
        return this.oHelpMgr;
    }

    public MazeEditor getEditor() {
        if (this.editor == null) {
            this.editor = new MazeEditor();
        }
        return this.editor;
    }

    public RuleSetPicker getRuleSetPicker() {
        if (this.rsPicker == null) {
            this.rsPicker = new RuleSetPicker();
        }
        return this.rsPicker;
    }

    public AboutDialog getAboutDialog() {
        return this.about;
    }

    public void playHighScoreSound() {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_USER_INTERFACE,
                SoundConstants.SOUND_HIGH_SCORE);
    }

    public void playLogoSound() {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_USER_INTERFACE,
                SoundConstants.SOUND_LOGO);
    }

    public MazeObjectList getObjects() {
        return this.objects;
    }

    public boolean isPrerelease() {
        return this.vdat.isPrerelease();
    }

    private static class MenuManager {
        // Fields
        private JMenuBar mainMenuBar;
        private JMenu fileMenu, editMenu, playMenu, gameMenu, editorMenu,
                helpMenu;
        private JMenu editorFillSubMenu;
        private JMenuItem fileNew, fileOpen, fileOpenLocked, fileClose,
                fileSave, fileSaveAs, fileSaveLocked, fileExit;
        private JMenuItem editUndo, editRedo, editCutLevel, editCopyLevel,
                editPasteLevel, editInsertLevelFromClipboard, editPreferences,
                editClearHistory;
        private JMenuItem playPlay, playEdit;
        private JMenuItem gameObjectInventory, gameUse, gameSwitchBow,
                gameReset, gameShowScore, gameShowTable;
        private JMenuItem editorGoToLocation, editorGoToDestination,
                editorUpOneFloor, editorDownOneFloor, editorUpOneLevel,
                editorDownOneLevel, editorAddLevel, editorRemoveLevel,
                editorResizeLevel, editorToggleLayer, editorLevelPreferences,
                editorMazePreferences, editorSetStartPoint,
                editorSetFirstMovingFinish;
        private JMenuItem editorFillFloor, editorFillLevel,
                editorFillFloorRandomly, editorFillLevelRandomly,
                editorFillRuleSets;
        private JCheckBoxMenuItem editorFillUseRuleSets;
        private JMenuItem helpAbout, helpObjectHelp;
        private KeyStroke fileNewAccel, fileOpenAccel, fileCloseAccel,
                fileSaveAccel, fileSaveAsAccel;
        private KeyStroke editUndoAccel, editRedoAccel, editCutLevelAccel,
                editCopyLevelAccel, editPasteLevelAccel,
                editInsertLevelFromClipboardAccel, editPreferencesAccel,
                editClearHistoryAccel;
        private KeyStroke playPlayMazeAccel, playEditMazeAccel;
        private KeyStroke gameObjectInventoryAccel, gameUseAccel,
                gameSwitchBowAccel, gameResetAccel, gameShowScoreAccel,
                gameShowTableAccel;
        private KeyStroke editorGoToLocationAccel, editorUpOneFloorAccel,
                editorDownOneFloorAccel, editorUpOneLevelAccel,
                editorDownOneLevelAccel, editorToggleLayerAccel;
        private final EventHandler handler;
        private boolean mazeLocked;

        // Constructors
        public MenuManager() {
            this.mainMenuBar = new JMenuBar();
            this.handler = new EventHandler();
            this.createAccelerators();
            this.createMenus();
            this.setInitialMenuState();
            this.mazeLocked = false;
        }

        // Methods
        public void setGameMenus() {
            this.fileNew.setEnabled(false);
            this.fileOpen.setEnabled(false);
            this.fileOpenLocked.setEnabled(false);
            this.fileExit.setEnabled(true);
            this.editUndo.setEnabled(false);
            this.editRedo.setEnabled(false);
            this.editCutLevel.setEnabled(false);
            this.editCopyLevel.setEnabled(false);
            this.editPasteLevel.setEnabled(false);
            this.editInsertLevelFromClipboard.setEnabled(false);
            this.editPreferences.setEnabled(true);
            this.editClearHistory.setEnabled(false);
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
            this.editorFillRuleSets.setEnabled(false);
            this.editorFillUseRuleSets.setEnabled(false);
            this.editorToggleLayer.setEnabled(false);
            this.editorLevelPreferences.setEnabled(false);
            this.editorMazePreferences.setEnabled(false);
            this.editorSetStartPoint.setEnabled(false);
            this.editorSetFirstMovingFinish.setEnabled(false);
            this.gameObjectInventory.setEnabled(true);
            this.gameUse.setEnabled(true);
            this.gameSwitchBow.setEnabled(true);
            this.gameReset.setEnabled(true);
            this.gameShowScore.setEnabled(true);
            this.gameShowTable.setEnabled(true);
            this.checkFlags();
            this.checkFlags();
        }

        public void setEditorMenus() {
            this.fileNew.setEnabled(false);
            this.fileOpen.setEnabled(false);
            this.fileOpenLocked.setEnabled(false);
            this.fileExit.setEnabled(true);
            this.editCutLevel.setEnabled(true);
            this.editCopyLevel.setEnabled(true);
            this.editPasteLevel.setEnabled(true);
            this.editInsertLevelFromClipboard.setEnabled(true);
            this.editPreferences.setEnabled(true);
            this.editorGoToLocation.setEnabled(true);
            this.editorGoToDestination.setEnabled(true);
            this.editorResizeLevel.setEnabled(true);
            this.editorFillFloor.setEnabled(true);
            this.editorFillLevel.setEnabled(true);
            this.editorFillFloorRandomly.setEnabled(true);
            this.editorFillLevelRandomly.setEnabled(true);
            this.editorFillRuleSets.setEnabled(true);
            this.editorFillUseRuleSets.setEnabled(true);
            this.editorToggleLayer.setEnabled(true);
            this.editorLevelPreferences.setEnabled(true);
            this.editorMazePreferences.setEnabled(true);
            this.editorSetStartPoint.setEnabled(true);
            this.editorSetFirstMovingFinish.setEnabled(true);
            this.gameObjectInventory.setEnabled(false);
            this.gameUse.setEnabled(false);
            this.gameSwitchBow.setEnabled(false);
            this.gameReset.setEnabled(false);
            this.gameShowScore.setEnabled(false);
            this.gameShowTable.setEnabled(false);
            this.checkFlags();
        }

        public void setPrefMenus() {
            this.fileNew.setEnabled(false);
            this.fileOpen.setEnabled(false);
            this.fileOpenLocked.setEnabled(false);
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
            this.editorFillRuleSets.setEnabled(false);
            this.editorFillUseRuleSets.setEnabled(false);
            this.editorToggleLayer.setEnabled(false);
            this.editorLevelPreferences.setEnabled(false);
            this.editorMazePreferences.setEnabled(false);
            this.editorSetStartPoint.setEnabled(false);
            this.editorSetFirstMovingFinish.setEnabled(false);
            this.gameObjectInventory.setEnabled(false);
            this.gameUse.setEnabled(false);
            this.gameSwitchBow.setEnabled(false);
            this.gameReset.setEnabled(false);
            this.gameShowScore.setEnabled(false);
            this.gameShowTable.setEnabled(false);
        }

        public void setHelpMenus() {
            this.fileNew.setEnabled(false);
            this.fileOpen.setEnabled(false);
            this.fileOpenLocked.setEnabled(false);
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
            this.editorFillRuleSets.setEnabled(false);
            this.editorFillUseRuleSets.setEnabled(false);
            this.editorToggleLayer.setEnabled(false);
            this.editorLevelPreferences.setEnabled(false);
            this.editorMazePreferences.setEnabled(false);
            this.editorSetStartPoint.setEnabled(false);
            this.gameObjectInventory.setEnabled(false);
            this.gameUse.setEnabled(false);
            this.gameSwitchBow.setEnabled(false);
            this.gameReset.setEnabled(false);
            this.gameShowScore.setEnabled(false);
            this.gameShowTable.setEnabled(false);
        }

        public void setMainMenus() {
            this.fileNew.setEnabled(true);
            this.fileOpen.setEnabled(true);
            this.fileOpenLocked.setEnabled(true);
            this.fileExit.setEnabled(true);
            this.editUndo.setEnabled(false);
            this.editRedo.setEnabled(false);
            this.editCutLevel.setEnabled(false);
            this.editCopyLevel.setEnabled(false);
            this.editPasteLevel.setEnabled(false);
            this.editInsertLevelFromClipboard.setEnabled(false);
            this.editPreferences.setEnabled(true);
            this.editClearHistory.setEnabled(false);
            this.editorGoToLocation.setEnabled(false);
            this.editorGoToDestination.setEnabled(false);
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
            this.editorLevelPreferences.setEnabled(false);
            this.editorMazePreferences.setEnabled(false);
            this.editorSetStartPoint.setEnabled(false);
            this.editorSetFirstMovingFinish.setEnabled(false);
            this.gameObjectInventory.setEnabled(false);
            this.gameUse.setEnabled(false);
            this.gameSwitchBow.setEnabled(false);
            this.gameReset.setEnabled(false);
            this.gameShowScore.setEnabled(false);
            this.gameShowTable.setEnabled(false);
            this.checkFlags();
        }

        public void setLockedFlag() {
            this.mazeLocked = true;
        }

        public void clearLockedFlag() {
            this.mazeLocked = false;
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
            final Mazer5D app = Mazer5D.getApplication();
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
            if (app.inEditor()) {
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
            final Mazer5D app = Mazer5D.getApplication();
            if (app.inGUI()) {
                this.fileClose.setEnabled(false);
                this.fileSaveAs.setEnabled(false);
                this.fileSaveLocked.setEnabled(false);
                if (app.getMazeManager().getMaze().doesPlayerExist()) {
                    this.playPlay.setEnabled(true);
                } else {
                    this.playPlay.setEnabled(false);
                }
                if (this.mazeLocked) {
                    this.playEdit.setEnabled(false);
                } else {
                    this.playEdit.setEnabled(true);
                }
            } else {
                this.fileClose.setEnabled(true);
                this.fileSaveAs.setEnabled(true);
                this.fileSaveLocked.setEnabled(true);
                this.playPlay.setEnabled(false);
                this.playEdit.setEnabled(false);
            }
        }

        private void setMenusLoadedOff() {
            this.fileClose.setEnabled(false);
            this.fileSaveAs.setEnabled(false);
            this.fileSaveLocked.setEnabled(false);
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
            this.editCutLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_X,
                    modKey);
            this.editCopyLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                    modKey);
            this.editPasteLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                    modKey);
            this.editInsertLevelFromClipboardAccel = KeyStroke
                    .getKeyStroke(KeyEvent.VK_F, modKey);
            this.editPreferencesAccel = KeyStroke
                    .getKeyStroke(KeyEvent.VK_COMMA, modKey);
            this.editClearHistoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                    modKey);
            this.editorGoToLocationAccel = KeyStroke.getKeyStroke(KeyEvent.VK_G,
                    modKey | InputEvent.SHIFT_DOWN_MASK);
            this.playPlayMazeAccel = KeyStroke.getKeyStroke(KeyEvent.VK_P,
                    modKey);
            this.playEditMazeAccel = KeyStroke.getKeyStroke(KeyEvent.VK_E,
                    modKey);
            this.gameObjectInventoryAccel = KeyStroke
                    .getKeyStroke(KeyEvent.VK_I, modKey);
            this.gameUseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_U, modKey);
            this.gameSwitchBowAccel = KeyStroke.getKeyStroke(KeyEvent.VK_B,
                    modKey);
            this.gameResetAccel = KeyStroke.getKeyStroke(KeyEvent.VK_R, modKey);
            this.gameShowScoreAccel = KeyStroke.getKeyStroke(KeyEvent.VK_G,
                    modKey);
            this.gameShowTableAccel = KeyStroke.getKeyStroke(KeyEvent.VK_T,
                    modKey);
            this.editorUpOneFloorAccel = KeyStroke.getKeyStroke(KeyEvent.VK_UP,
                    modKey);
            this.editorDownOneFloorAccel = KeyStroke
                    .getKeyStroke(KeyEvent.VK_DOWN, modKey);
            this.editorUpOneLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_UP,
                    modKey | InputEvent.SHIFT_DOWN_MASK);
            this.editorDownOneLevelAccel = KeyStroke.getKeyStroke(
                    KeyEvent.VK_DOWN, modKey | InputEvent.SHIFT_DOWN_MASK);
            this.editorToggleLayerAccel = KeyStroke.getKeyStroke(KeyEvent.VK_L,
                    modKey);
        }

        private void createMenus() {
            this.mainMenuBar = new JMenuBar();
            this.fileMenu = new JMenu("File");
            this.editMenu = new JMenu("Edit");
            this.playMenu = new JMenu("Play");
            this.gameMenu = new JMenu("Game");
            this.editorMenu = new JMenu("Editor");
            this.helpMenu = new JMenu("Help");
            this.editorFillSubMenu = new JMenu("Fill");
            this.fileNew = new JMenuItem("New...");
            this.fileNew.setAccelerator(this.fileNewAccel);
            this.fileOpen = new JMenuItem("Open...");
            this.fileOpen.setAccelerator(this.fileOpenAccel);
            this.fileOpenLocked = new JMenuItem("Open Locked...");
            this.fileClose = new JMenuItem("Close");
            this.fileClose.setAccelerator(this.fileCloseAccel);
            this.fileSave = new JMenuItem("Save");
            this.fileSave.setAccelerator(this.fileSaveAccel);
            this.fileSaveAs = new JMenuItem("Save As...");
            this.fileSaveAs.setAccelerator(this.fileSaveAsAccel);
            this.fileSaveLocked = new JMenuItem("Save Locked...");
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
            this.gameObjectInventory
                    .setAccelerator(this.gameObjectInventoryAccel);
            this.gameUse = new JMenuItem("Use an Item...");
            this.gameUse.setAccelerator(this.gameUseAccel);
            this.gameSwitchBow = new JMenuItem("Switch Bow...");
            this.gameSwitchBow.setAccelerator(this.gameSwitchBowAccel);
            this.gameReset = new JMenuItem("Reset Current Level");
            this.gameReset.setAccelerator(this.gameResetAccel);
            this.gameShowScore = new JMenuItem("Show Current Score");
            this.gameShowScore.setAccelerator(this.gameShowScoreAccel);
            this.gameShowTable = new JMenuItem("Show Score Table");
            this.gameShowTable.setAccelerator(this.gameShowTableAccel);
            this.editorGoToLocation = new JMenuItem("Go To Location...");
            this.editorGoToLocation
                    .setAccelerator(this.editorGoToLocationAccel);
            this.editorGoToDestination = new JMenuItem("Go To Destination...");
            this.editorUpOneFloor = new JMenuItem("Up One Floor");
            this.editorUpOneFloor.setAccelerator(this.editorUpOneFloorAccel);
            this.editorDownOneFloor = new JMenuItem("Down One Floor");
            this.editorDownOneFloor
                    .setAccelerator(this.editorDownOneFloorAccel);
            this.editorUpOneLevel = new JMenuItem("Up One Level");
            this.editorUpOneLevel.setAccelerator(this.editorUpOneLevelAccel);
            this.editorDownOneLevel = new JMenuItem("Down One Level");
            this.editorDownOneLevel
                    .setAccelerator(this.editorDownOneLevelAccel);
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
            this.editorToggleLayer.setAccelerator(this.editorToggleLayerAccel);
            this.editorLevelPreferences = new JMenuItem("Level Preferences...");
            this.editorMazePreferences = new JMenuItem("Maze Preferences...");
            this.editorSetStartPoint = new JMenuItem("Set Start Point...");
            this.editorSetFirstMovingFinish = new JMenuItem(
                    "Set First Moving Finish...");
            this.helpAbout = new JMenuItem("About Mazer5D...");
            this.helpObjectHelp = new JMenuItem("Mazer5D Object Help");
            this.fileNew.addActionListener(this.handler);
            this.fileOpen.addActionListener(this.handler);
            this.fileOpenLocked.addActionListener(this.handler);
            this.fileClose.addActionListener(this.handler);
            this.fileSave.addActionListener(this.handler);
            this.fileSaveAs.addActionListener(this.handler);
            this.fileSaveLocked.addActionListener(this.handler);
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
            this.gameUse.addActionListener(this.handler);
            this.gameSwitchBow.addActionListener(this.handler);
            this.gameReset.addActionListener(this.handler);
            this.gameShowScore.addActionListener(this.handler);
            this.gameShowTable.addActionListener(this.handler);
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
            this.editorFillRuleSets.addActionListener(this.handler);
            this.editorToggleLayer.addActionListener(this.handler);
            this.editorLevelPreferences.addActionListener(this.handler);
            this.editorMazePreferences.addActionListener(this.handler);
            this.editorSetStartPoint.addActionListener(this.handler);
            this.editorSetFirstMovingFinish.addActionListener(this.handler);
            this.helpAbout.addActionListener(this.handler);
            this.helpObjectHelp.addActionListener(this.handler);
            this.editorFillSubMenu.add(this.editorFillFloor);
            this.editorFillSubMenu.add(this.editorFillLevel);
            this.editorFillSubMenu.add(this.editorFillFloorRandomly);
            this.editorFillSubMenu.add(this.editorFillLevelRandomly);
            this.editorFillSubMenu.add(this.editorFillRuleSets);
            this.editorFillSubMenu.add(this.editorFillUseRuleSets);
            this.fileMenu.add(this.fileNew);
            this.fileMenu.add(this.fileOpen);
            this.fileMenu.add(this.fileOpenLocked);
            this.fileMenu.add(this.fileClose);
            this.fileMenu.add(this.fileSave);
            this.fileMenu.add(this.fileSaveAs);
            this.fileMenu.add(this.fileSaveLocked);
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
            this.gameMenu.add(this.gameObjectInventory);
            this.gameMenu.add(this.gameUse);
            this.gameMenu.add(this.gameSwitchBow);
            this.gameMenu.add(this.gameReset);
            this.gameMenu.add(this.gameShowScore);
            this.gameMenu.add(this.gameShowTable);
            this.editorMenu.add(this.editorGoToLocation);
            this.editorMenu.add(this.editorGoToDestination);
            this.editorMenu.add(this.editorUpOneFloor);
            this.editorMenu.add(this.editorDownOneFloor);
            this.editorMenu.add(this.editorUpOneLevel);
            this.editorMenu.add(this.editorDownOneLevel);
            this.editorMenu.add(this.editorAddLevel);
            this.editorMenu.add(this.editorRemoveLevel);
            this.editorMenu.add(this.editorResizeLevel);
            this.editorMenu.add(this.editorFillSubMenu);
            this.editorMenu.add(this.editorToggleLayer);
            this.editorMenu.add(this.editorLevelPreferences);
            this.editorMenu.add(this.editorMazePreferences);
            this.editorMenu.add(this.editorSetStartPoint);
            this.editorMenu.add(this.editorSetFirstMovingFinish);
            if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
                this.helpMenu.add(this.helpAbout);
            }
            this.helpMenu.add(this.helpObjectHelp);
            this.mainMenuBar.add(this.fileMenu);
            this.mainMenuBar.add(this.editMenu);
            this.mainMenuBar.add(this.playMenu);
            this.mainMenuBar.add(this.gameMenu);
            this.mainMenuBar.add(this.editorMenu);
            this.mainMenuBar.add(this.helpMenu);
        }

        private void setInitialMenuState() {
            this.fileNew.setEnabled(true);
            this.fileOpen.setEnabled(true);
            this.fileOpenLocked.setEnabled(true);
            this.fileClose.setEnabled(false);
            this.fileSave.setEnabled(false);
            this.fileSaveAs.setEnabled(false);
            this.fileSaveLocked.setEnabled(false);
            this.fileExit.setEnabled(true);
            this.editUndo.setEnabled(false);
            this.editRedo.setEnabled(false);
            this.editCutLevel.setEnabled(false);
            this.editCopyLevel.setEnabled(false);
            this.editPasteLevel.setEnabled(false);
            this.editInsertLevelFromClipboard.setEnabled(false);
            this.editPreferences.setEnabled(true);
            this.editClearHistory.setEnabled(false);
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
            this.editorFillRuleSets.setEnabled(false);
            this.editorFillUseRuleSets.setEnabled(false);
            this.editorToggleLayer.setEnabled(false);
            this.editorLevelPreferences.setEnabled(false);
            this.editorMazePreferences.setEnabled(false);
            this.editorSetStartPoint.setEnabled(false);
            this.editorSetFirstMovingFinish.setEnabled(false);
            this.playPlay.setEnabled(false);
            this.playEdit.setEnabled(false);
            this.gameObjectInventory.setEnabled(false);
            this.gameUse.setEnabled(false);
            this.gameSwitchBow.setEnabled(false);
            this.gameReset.setEnabled(false);
            this.gameShowScore.setEnabled(false);
            this.gameShowTable.setEnabled(false);
            this.helpAbout.setEnabled(true);
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
                    final Mazer5D app = Mazer5D.getApplication();
                    boolean loaded = false;
                    final String cmd = e.getActionCommand();
                    if (cmd.equals("New...")) {
                        loaded = app.getEditor().newMaze();
                        app.getMazeManager().setLoaded(loaded);
                        if (loaded) {
                            MenuManager.this.clearLockedFlag();
                        }
                    } else if (cmd.equals("Open...")) {
                        loaded = app.getMazeManager().loadMaze();
                        app.getMazeManager().setLoaded(loaded);
                    } else if (cmd.equals("Open Locked...")) {
                        loaded = app.getMazeManager().loadLockedMaze();
                        app.getMazeManager().setLoaded(loaded);
                    } else if (cmd.equals("Close")) {
                        // Close the window
                        if (app.inEditor()) {
                            app.getEditor().handleCloseWindow();
                        } else if (app.inGame()) {
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
                    } else if (cmd.equals("Save Locked...")) {
                        if (app.getMazeManager().getLoaded()) {
                            app.getMazeManager().saveLockedMaze();
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
                        app.getMazeManager().getMaze()
                                .insertLevelFromClipboard();
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
                                MazeEditor.TELEPORT_TYPE_FIRST_MOVING_FINISH);
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
                        if (!app.getGameManager().usingAnItem()) {
                            app.getGameManager().showInventoryDialog();
                        }
                    } else if (cmd.equals("Use an Item...")) {
                        if (!app.getGameManager().usingAnItem()) {
                            app.getGameManager().setUsingAnItem(true);
                            app.getGameManager().showUseDialog();
                        }
                    } else if (cmd.equals("Switch Bow...")) {
                        if (!app.getGameManager().usingAnItem()) {
                            app.getGameManager().showSwitchBowDialog();
                        }
                    } else if (cmd.equals("Reset Current Level")) {
                        if (!app.getGameManager().usingAnItem()) {
                            final int result = CommonDialogs.showConfirmDialog(
                                    "Are you sure you want to reset the current level?",
                                    "Mazer5D");
                            if (result == JOptionPane.YES_OPTION) {
                                app.getGameManager().resetCurrentLevel();
                            }
                        }
                    } else if (cmd.equals("Show Current Score")) {
                        app.getGameManager().showCurrentScore();
                    } else if (cmd.equals("Show Score Table")) {
                        app.getGameManager().showScoreTable();
                    } else if (cmd.equals("About Mazer5D...")) {
                        app.getAboutDialog().showAboutDialog();
                    } else if (cmd.equals("Mazer5D Object Help")) {
                        app.getObjectHelpManager().showHelp();
                    }
                    MenuManager.this.checkFlags();
                } catch (final Exception ex) {
                    Mazer5D.getErrorLogger().logError(ex);
                }
            }
        }
    }

    private static class VersionData {
        // Fields
        private int majorVersion;
        private int minorVersion;
        private int bugfixVersion;
        private int codeVersion;
        private int prereleaseVersion;
        public static final int CODE_PRE_ALPHA = 0;
        public static final int CODE_ALPHA = 1;
        public static final int CODE_BETA = 2;
        public static final int CODE_RELEASE_CANDIDATE = 3;
        public static final int CODE_STABLE = 4;

        // Constructors
        public VersionData(final int major, final int minor, final int bugfix,
                final int code, final int prerel) {
            this.majorVersion = major;
            this.minorVersion = minor;
            this.bugfixVersion = bugfix;
            this.codeVersion = code;
            this.prereleaseVersion = prerel;
        }

        // Methods
        public String getVersionString() {
            if (this.isPrerelease()) {
                String rt;
                if (this.codeVersion == VersionData.CODE_PRE_ALPHA) {
                    rt = "-dev";
                } else if (this.codeVersion == VersionData.CODE_ALPHA) {
                    rt = "-alpha";
                } else if (this.codeVersion == VersionData.CODE_BETA) {
                    rt = "-beta";
                } else if (this.codeVersion == VersionData.CODE_RELEASE_CANDIDATE) {
                    rt = "-RC";
                } else {
                    rt = "";
                }
                return "" + this.majorVersion + "." + this.minorVersion + "."
                        + this.bugfixVersion + rt + this.prereleaseVersion;
            } else {
                return "" + this.majorVersion + "." + this.minorVersion + "."
                        + this.bugfixVersion;
            }
        }

        public boolean isPrerelease() {
            return this.codeVersion != VersionData.CODE_STABLE;
        }
    }

    private static class PrefsLauncher implements PreferencesHandler {
        @Override
        public void handlePreferences(PreferencesEvent inE) {
            PreferencesManager.showPrefs();
        }
    }
}
