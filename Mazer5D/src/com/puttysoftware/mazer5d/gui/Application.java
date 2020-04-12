/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.gui;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.integration.NativeIntegration;
import com.puttysoftware.mazer5d.editor.MazeEditor;
import com.puttysoftware.mazer5d.editor.rulesets.RuleSetPicker;
import com.puttysoftware.mazer5d.game.GameManager;
import com.puttysoftware.mazer5d.generic.MazeObjectList;
import com.puttysoftware.mazer5d.loaders.SoundConstants;
import com.puttysoftware.mazer5d.loaders.SoundManager;
import com.puttysoftware.mazer5d.maze.MazeManager;
import com.puttysoftware.mazer5d.prefs.PreferencesManager;

public class Application {
    // Fields
    private final AboutDialog about;
    private GameManager gameMgr;
    private MazeManager mazeMgr;
    private final MenuManager menuMgr;
    private final ObjectHelpManager oHelpMgr;
    private MazeEditor editor;
    private RuleSetPicker rsPicker;
    private final GUIManager guiMgr;
    private final MazeObjectList objects;
    private boolean IN_GUI, IN_PREFS, IN_GAME, IN_EDITOR;
    private static final int VERSION_MAJOR = 9;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_BETA = 2;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_EDITOR = 2;
    public static final int STATUS_PREFS = 3;
    public static final int STATUS_NULL = 4;

    // Constructors
    public Application(final NativeIntegration ni) {
        this.objects = new MazeObjectList();
        this.about = new AboutDialog(this.getVersionString());
        this.guiMgr = new GUIManager();
        this.oHelpMgr = new ObjectHelpManager();
        this.menuMgr = new MenuManager();
        this.guiMgr.updateLogo();
        this.menuMgr.configureMenus(ni);
    }

    // Methods
    public void setInGUI(final boolean value) {
        this.IN_GUI = value;
    }

    public void setInPrefs(final boolean value) {
        this.IN_PREFS = value;
    }

    public void setInGame(final boolean value) {
        this.IN_GAME = value;
    }

    public void setInEditor(final boolean value) {
        this.IN_EDITOR = value;
    }

    public int getMode() {
        if (this.IN_PREFS) {
            return Application.STATUS_PREFS;
        } else if (this.IN_GUI) {
            return Application.STATUS_GUI;
        } else if (this.IN_GAME) {
            return Application.STATUS_GAME;
        } else if (this.IN_EDITOR) {
            return Application.STATUS_EDITOR;
        } else {
            return Application.STATUS_NULL;
        }
    }

    public int getFormerMode() {
        if (this.IN_GUI) {
            return Application.STATUS_GUI;
        } else if (this.IN_GAME) {
            return Application.STATUS_GAME;
        } else if (this.IN_EDITOR) {
            return Application.STATUS_EDITOR;
        } else {
            return Application.STATUS_NULL;
        }
    }

    public void showMessage(final String msg) {
        if (this.IN_PREFS) {
            CommonDialogs.showDialog(msg);
        } else if (this.IN_GUI) {
            CommonDialogs.showDialog(msg);
        } else if (this.IN_GAME) {
            this.getGameManager().setStatusMessage(msg);
        } else if (this.IN_EDITOR) {
            this.getEditor().setStatusMessage(msg);
        } else {
            CommonDialogs.showDialog(msg);
        }
    }

    public MenuManager getMenuManager() {
        return this.menuMgr;
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

    private String getVersionString() {
        if (this.isBetaModeEnabled()) {
            return "" + Application.VERSION_MAJOR + "."
                    + Application.VERSION_MINOR + "."
                    + Application.VERSION_BUGFIX + "b"
                    + Application.VERSION_BETA;
        } else {
            return "" + Application.VERSION_MAJOR + "."
                    + Application.VERSION_MINOR + "."
                    + Application.VERSION_BUGFIX;
        }
    }

    public JFrame getOutputFrame() {
        try {
            if (this.getMode() == Application.STATUS_PREFS) {
                return PreferencesManager.getPrefFrame();
            } else if (this.getMode() == Application.STATUS_GUI) {
                return this.getGUIManager().getGUIFrame();
            } else if (this.getMode() == Application.STATUS_GAME) {
                return this.getGameManager().getOutputFrame();
            } else if (this.getMode() == Application.STATUS_EDITOR) {
                return this.getEditor().getOutputFrame();
            } else {
                return null;
            }
        } catch (final NullPointerException npe) {
            return null;
        }
    }

    public MazeObjectList getObjects() {
        return this.objects;
    }

    public boolean isBetaModeEnabled() {
        return Application.VERSION_BETA > 0;
    }
}
