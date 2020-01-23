/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode;

import java.awt.Image;

import javax.swing.JFrame;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazemode.editor.MazeEditor;
import com.puttysoftware.mazemode.editor.rulesets.RuleSetPicker;
import com.puttysoftware.mazemode.game.GameManager;
import com.puttysoftware.mazemode.generic.MazeObjectList;
import com.puttysoftware.mazemode.maze.MazeManager;
import com.puttysoftware.mazemode.prefs.PreferencesManager;
import com.puttysoftware.mazemode.resourcemanagers.GraphicsManager;
import com.puttysoftware.mazemode.resourcemanagers.LogoCache;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class Application {
    // Fields
    private AboutDialog about;
    private GameManager gameMgr;
    private MazeManager mazeMgr;
    private MenuManager menuMgr;
    private HelpManager helpMgr;
    private MazeEditor editor;
    private RuleSetPicker rsPicker;
    private GUIManager guiMgr;
    private final MazeObjectList objects;
    private boolean IN_GUI, IN_PREFS, IN_GAME, IN_EDITOR;
    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_BETA = 1;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_EDITOR = 2;
    public static final int STATUS_PREFS = 3;
    public static final int STATUS_NULL = 4;

    // Constructors
    public Application() {
        this.objects = new MazeObjectList();
    }

    // Methods
    void postConstruct() {
        // Create Managers
        this.about = new AboutDialog(this.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.helpMgr = new HelpManager();
        // Cache Logo
        this.guiMgr.updateLogo();
    }

    public void invalidateImageCaches() {
        // Cache Logos
        LogoCache.flushCache();
        this.guiMgr.updateLogo();
        this.about.update();
        // Cache Stat Images
        this.getGameManager().updateStatImages();
        // Cache Object Images
        this.getEditor().updatePicker();
        // Cache Help Images
        this.helpMgr.updateHelp();
    }

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

    public HelpManager getHelpManager() {
        return this.helpMgr;
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

    public BufferedImageIcon getMicroLogo() {
        return GraphicsManager.getMicroLogo();
    }

    public Image getIconLogo() {
        return GraphicsManager.getIconLogo();
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
                    + Application.VERSION_BUGFIX + "-dev"
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
