/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.updater.ProductData;
import com.puttysoftware.weaselweb.editor.MazeEditor;
import com.puttysoftware.weaselweb.editor.rulesets.RuleSetPicker;
import com.puttysoftware.weaselweb.game.GameManager;
import com.puttysoftware.weaselweb.maze.MazeManager;
import com.puttysoftware.weaselweb.maze.generic.MazeObjectList;
import com.puttysoftware.weaselweb.prefs.PreferencesManager;
import com.puttysoftware.weaselweb.resourcemanagers.LogoCache;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class Application {
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
    private int mode, formerMode;
    private static final String UPDATE_SITE = "http://update.puttysoftware.com/weaselweb/version.txt";
    private static final String PRERELEASE_UPDATE_SITE = "http://update.puttysoftware.com/weaselweb/prerelease_version.txt";
    private static final String BLURB_SITE = "http://update.puttysoftware.com/weaselweb/";
    private static final String NEW_VERSION_SITE = "http://www.puttysoftware.com/weaselweb/";
    private static final String PRODUCT_NAME = "WeaselWeb";
    private static final String COMPANY_NAME = "Putty Software";
    private static final String RDNS_COMPANY_NAME = "com.puttysoftware.weaselweb";
    private static final String NEW_PRERELEASE_VERSION_SITE = "http://www.puttysoftware.com/weaselweb/prerelease/";
    private static final int VERSION_MAJOR = 0;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_CODE = ProductData.CODE_PRE_ALPHA;
    private static final int VERSION_BETA = 1;
    private static ProductData pd = new ProductData(Application.UPDATE_SITE,
            Application.BLURB_SITE, Application.NEW_VERSION_SITE,
            Application.RDNS_COMPANY_NAME, Application.COMPANY_NAME,
            Application.PRODUCT_NAME, Application.VERSION_MAJOR,
            Application.VERSION_MINOR, Application.VERSION_BUGFIX,
            Application.VERSION_CODE, Application.VERSION_BETA);
    private static ProductData ppd = new ProductData(
            Application.PRERELEASE_UPDATE_SITE, Application.BLURB_SITE,
            Application.NEW_PRERELEASE_VERSION_SITE,
            Application.RDNS_COMPANY_NAME, Application.COMPANY_NAME,
            Application.PRODUCT_NAME, Application.VERSION_MAJOR,
            Application.VERSION_MINOR, Application.VERSION_BUGFIX,
            Application.VERSION_CODE, Application.VERSION_BETA);
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_EDITOR = 2;
    public static final int STATUS_PREFS = 3;
    public static final int STATUS_NULL = 4;

    // Constructors
    public Application() {
        this.objects = new MazeObjectList();
        this.mode = Application.STATUS_NULL;
        this.formerMode = Application.STATUS_NULL;
    }

    // Methods
    void postConstruct() {
        // Create Managers
        this.about = new AboutDialog(this.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.oHelpMgr = new ObjectHelpManager();
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
        this.oHelpMgr.updateHelp();
    }

    public void setInGUI() {
        this.mode = Application.STATUS_GUI;
    }

    public void setInPrefs() {
        this.formerMode = this.mode;
        this.mode = Application.STATUS_PREFS;
    }

    public void setInGame() {
        this.mode = Application.STATUS_GAME;
    }

    public void setInEditor() {
        this.mode = Application.STATUS_EDITOR;
    }

    public int getMode() {
        return this.mode;
    }

    public int getFormerMode() {
        return this.formerMode;
    }

    public void showMessage(final String msg) {
        if (this.mode == Application.STATUS_GAME) {
            this.getGameManager().setStatusMessage(msg);
        } else if (this.mode == Application.STATUS_EDITOR) {
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
            return Application.ppd.getVersionString();
        } else {
            return Application.pd.getVersionString();
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
        return Application.VERSION_CODE != ProductData.CODE_STABLE_RELEASE;
    }
}
