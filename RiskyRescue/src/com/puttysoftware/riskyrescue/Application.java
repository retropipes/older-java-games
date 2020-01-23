/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.integration.NativeIntegration;
import com.puttysoftware.riskyrescue.assets.SoundConstants;
import com.puttysoftware.riskyrescue.assets.SoundManager;
import com.puttysoftware.riskyrescue.battle.BattleLogic;
import com.puttysoftware.riskyrescue.game.GameLogic;
import com.puttysoftware.riskyrescue.items.Shop;
import com.puttysoftware.riskyrescue.items.ShopTypes;
import com.puttysoftware.riskyrescue.map.objects.MapObjectList;
import com.puttysoftware.riskyrescue.prefs.PreferencesManager;
import com.puttysoftware.riskyrescue.scenario.ScenarioManager;

public class Application {
    // Fields
    private AboutDialog about;
    private GameLogic gameMgr;
    private ScenarioManager scenarioMgr;
    private MenuManager menuMgr;
    private GUIManager guiMgr;
    private final MapObjectList objects;
    private Shop weapons, armor, healer, regenerator;
    private BattleLogic battle;
    private int currentMode;
    private int formerMode;
    private NativeIntegration integration;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    private static final int STATUS_PREFS = 2;
    public static final int STATUS_BATTLE = 3;
    private static final int STATUS_NULL = 4;

    // Constructors
    public Application() {
        this.objects = new MapObjectList();
        this.currentMode = Application.STATUS_NULL;
        this.formerMode = Application.STATUS_NULL;
    }

    // Methods
    void postConstruct(final NativeIntegration ni) {
        // Create Managers
        this.about = new AboutDialog(Support.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.battle = new BattleLogic();
        this.weapons = new Shop(ShopTypes.SHOP_TYPE_WEAPONS);
        this.armor = new Shop(ShopTypes.SHOP_TYPE_ARMOR);
        this.healer = new Shop(ShopTypes.SHOP_TYPE_HEALER);
        this.regenerator = new Shop(ShopTypes.SHOP_TYPE_REGENERATOR);
        // Cache Logo
        this.guiMgr.updateLogo();
        // Late native hooks
        this.integration = ni;
        ni.setDefaultMenuBar(this.menuMgr.getMainMenuBar());
        // Set Up
        Support.createScenario();
    }

    void setInGUI() {
        this.currentMode = Application.STATUS_GUI;
    }

    public void setInPrefs() {
        this.formerMode = this.currentMode;
        this.currentMode = Application.STATUS_PREFS;
    }

    public void setInGame() {
        this.currentMode = Application.STATUS_GAME;
    }

    public void setInBattle() {
        this.currentMode = Application.STATUS_BATTLE;
    }

    public int getMode() {
        return this.currentMode;
    }

    public void restoreFormerMode() {
        this.currentMode = this.formerMode;
    }

    public int getFormerMode() {
        return this.formerMode;
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

    public GameLogic getGameManager() {
        if (this.gameMgr == null) {
            this.gameMgr = new GameLogic();
        }
        return this.gameMgr;
    }

    public ScenarioManager getScenarioManager() {
        if (this.scenarioMgr == null) {
            this.scenarioMgr = new ScenarioManager();
        }
        return this.scenarioMgr;
    }

    AboutDialog getAboutDialog() {
        return this.about;
    }

    static void playLogoSound() {
        SoundManager.playSound(SoundConstants.LOGO);
    }

    public void attachMenus(final JFrame frame) {
        if (!this.integration.isMacOS()) {
            frame.setJMenuBar(this.menuMgr.getMainMenuBar());
        }
    }

    public JFrame getOutputFrame() {
        if (this.getMode() == Application.STATUS_PREFS) {
            return PreferencesManager.getPrefFrame();
        } else if (this.getMode() == Application.STATUS_BATTLE) {
            return this.getBattle().getOutputFrame();
        } else if (this.getMode() == Application.STATUS_GAME) {
            return this.getGameManager().getOutputFrame();
        } else {
            return this.getGUIManager().getGUIFrame();
        }
    }

    public MapObjectList getObjects() {
        return this.objects;
    }

    public Shop getGenericShop(final int shopType) {
        switch (shopType) {
        case ShopTypes.SHOP_TYPE_ARMOR:
            return this.armor;
        case ShopTypes.SHOP_TYPE_HEALER:
            return this.healer;
        case ShopTypes.SHOP_TYPE_REGENERATOR:
            return this.regenerator;
        case ShopTypes.SHOP_TYPE_WEAPONS:
            return this.weapons;
        default:
            // Invalid shop type
            return null;
        }
    }

    public BattleLogic getBattle() {
        return this.battle;
    }
}
