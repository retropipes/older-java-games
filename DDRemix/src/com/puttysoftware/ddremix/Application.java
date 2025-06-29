/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix;

import java.awt.Image;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.ddremix.battle.AbstractBattle;
import com.puttysoftware.ddremix.battle.window.time.WindowTimeBattleLogic;
import com.puttysoftware.ddremix.battle.window.turn.WindowTurnBattleLogic;
import com.puttysoftware.ddremix.game.GameLogicManager;
import com.puttysoftware.ddremix.maze.MazeManager;
import com.puttysoftware.ddremix.maze.utilities.MazeObjectList;
import com.puttysoftware.ddremix.prefs.PreferencesManager;
import com.puttysoftware.ddremix.resourcemanagers.LogoManager;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;
import com.puttysoftware.ddremix.shops.Shop;
import com.puttysoftware.ddremix.shops.ShopTypes;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.updater.ProductData;

public final class Application {
    // Fields
    private AboutDialog about;
    private GameLogicManager gameMgr;
    private MazeManager mazeMgr;
    private MenuManager menuMgr;
    private ObjectHelpManager oHelpMgr;
    private GUIManager guiMgr;
    private final MazeObjectList objects;
    private Shop weapons, armor, healer, bank, regenerator, spells, items;
    private WindowTurnBattleLogic windowTurnBattle;
    private WindowTimeBattleLogic windowTimeBattle;
    private int currentMode;
    private int formerMode;
    private static final String UPDATE_SITE = "http://update.puttysoftware.com/ddremix/";
    private static final String NEW_VERSION_SITE = "http://www.puttysoftware.com/ddremix/";
    private static final String PRODUCT_NAME = "DDRemix";
    private static final String COMPANY_NAME = "Putty Software";
    private static final String RDNS_COMPANY_NAME = "com.puttysoftware.ddremix";
    private static final ProductData pd = new ProductData(
            Application.UPDATE_SITE, Application.UPDATE_SITE,
            Application.NEW_VERSION_SITE, Application.RDNS_COMPANY_NAME,
            Application.COMPANY_NAME, Application.PRODUCT_NAME,
            Application.VERSION_MAJOR, Application.VERSION_MINOR,
            Application.VERSION_BUGFIX, Application.VERSION_CODE,
            Application.VERSION_PRERELEASE);
    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_CODE = ProductData.CODE_STABLE;
    private static final int VERSION_PRERELEASE = 0;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_BATTLE = 2;
    public static final int STATUS_PREFS = 3;
    public static final int STATUS_NULL = 4;

    // Constructors
    public Application() {
        this.objects = new MazeObjectList();
        this.currentMode = Application.STATUS_NULL;
        this.formerMode = Application.STATUS_NULL;
    }

    // Methods
    void postConstruct() {
        // Create Managers
        this.about = new AboutDialog(Application.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.oHelpMgr = new ObjectHelpManager();
        this.windowTurnBattle = new WindowTurnBattleLogic();
        this.windowTimeBattle = new WindowTimeBattleLogic();
        this.weapons = new Shop(ShopTypes.SHOP_TYPE_WEAPONS);
        this.armor = new Shop(ShopTypes.SHOP_TYPE_ARMOR);
        this.healer = new Shop(ShopTypes.SHOP_TYPE_HEALER);
        this.bank = new Shop(ShopTypes.SHOP_TYPE_BANK);
        this.regenerator = new Shop(ShopTypes.SHOP_TYPE_REGENERATOR);
        this.spells = new Shop(ShopTypes.SHOP_TYPE_SPELLS);
        this.items = new Shop(ShopTypes.SHOP_TYPE_ITEMS);
        // Cache Logo
        this.guiMgr.updateLogo();
    }

    public void setMode(final int newMode) {
        this.formerMode = this.currentMode;
        this.currentMode = newMode;
    }

    public int getMode() {
        return this.currentMode;
    }

    public int getFormerMode() {
        return this.formerMode;
    }

    public boolean modeChanged() {
        return this.formerMode != this.currentMode;
    }

    public void saveFormerMode() {
        this.formerMode = this.currentMode;
    }

    public void restoreFormerMode() {
        this.currentMode = this.formerMode;
    }

    public void showMessage(final String msg) {
        if (this.currentMode == Application.STATUS_GAME) {
            this.getGameManager().setStatusMessage(msg);
        } else if (this.currentMode == Application.STATUS_BATTLE) {
            this.getBattle().setStatusMessage(msg);
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

    public GameLogicManager getGameManager() {
        if (this.gameMgr == null) {
            this.gameMgr = new GameLogicManager();
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

    public AboutDialog getAboutDialog() {
        return this.about;
    }

    public static BufferedImageIcon getMicroLogo() {
        return LogoManager.getMicroLogo();
    }

    public static Image getIconLogo() {
        return LogoManager.getIconLogo();
    }

    public static void playLogoSound() {
        SoundManager.playSound(SoundConstants.SOUND_LOGO);
    }

    private static String getVersionString() {
        final int code = Application.pd.getCodeVersion();
        String rt;
        if (code < ProductData.CODE_STABLE) {
            rt = "-beta" + Application.VERSION_PRERELEASE;
        } else {
            rt = "";
        }
        return Application.VERSION_MAJOR + "." + Application.VERSION_MINOR + "."
                + Application.VERSION_BUGFIX + rt;
    }

    public JFrame getOutputFrame() {
        try {
            if (this.getMode() == Application.STATUS_PREFS) {
                return PreferencesManager.getPrefFrame();
            } else if (this.getMode() == Application.STATUS_GUI) {
                return this.getGUIManager().getGUIFrame();
            } else if (this.getMode() == Application.STATUS_GAME) {
                return this.getGameManager().getOutputFrame();
            } else if (this.getMode() == Application.STATUS_BATTLE) {
                return this.getBattle().getOutputFrame();
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

    public Shop getGenericShop(final int shopType) {
        this.getGameManager().stopMovement();
        switch (shopType) {
            case ShopTypes.SHOP_TYPE_ARMOR:
                return this.armor;
            case ShopTypes.SHOP_TYPE_BANK:
                return this.bank;
            case ShopTypes.SHOP_TYPE_HEALER:
                return this.healer;
            case ShopTypes.SHOP_TYPE_ITEMS:
                return this.items;
            case ShopTypes.SHOP_TYPE_REGENERATOR:
                return this.regenerator;
            case ShopTypes.SHOP_TYPE_SPELLS:
                return this.spells;
            case ShopTypes.SHOP_TYPE_WEAPONS:
                return this.weapons;
            default:
                // Invalid shop type
                return null;
        }
    }

    public AbstractBattle getBattle() {
        if (PreferencesManager.useTimeBattleEngine()) {
            return this.windowTimeBattle;
        } else {
            return this.windowTurnBattle;
        }
    }

    public void resetBattleGUI() {
        this.windowTimeBattle.resetGUI();
        this.windowTurnBattle.resetGUI();
    }
}
