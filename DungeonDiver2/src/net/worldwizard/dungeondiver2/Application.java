/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2;

import java.awt.Image;

import javax.swing.JFrame;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.dungeondiver2.battle.BattleGUI;
import net.worldwizard.dungeondiver2.game.GameManager;
import net.worldwizard.dungeondiver2.prefs.PreferencesManager;
import net.worldwizard.dungeondiver2.resourcemanagers.LogoManager;
import net.worldwizard.dungeondiver2.variables.VariablesManager;
import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.Support;
import net.worldwizard.support.items.Shop;
import net.worldwizard.support.items.ShopTypes;
import net.worldwizard.support.map.generic.GameSounds;
import net.worldwizard.support.map.generic.MapObjectList;
import net.worldwizard.support.resourcemanagers.SoundManager;

public class Application {
    // Fields
    private AboutDialog about;
    private GameManager gameMgr;
    private VariablesManager variablesMgr;
    private MenuManager menuMgr;
    private HelpManager helpMgr;
    private GUIManager guiMgr;
    private final MapObjectList objects;
    private Shop weapons, armor, healer, bank, regenerator, spells, items,
            socks;
    private BattleGUI battle;
    private boolean IN_GUI, IN_PREFS, IN_GAME;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_PREFS = 2;
    public static final int STATUS_NULL = 3;

    // Constructors
    public Application() {
        this.objects = new MapObjectList();
    }

    // Methods
    void postConstruct() {
        // Create Managers
        this.about = new AboutDialog(Support.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.helpMgr = new HelpManager();
        this.battle = new BattleGUI(false);
        this.weapons = new Shop(ShopTypes.SHOP_TYPE_WEAPONS);
        this.armor = new Shop(ShopTypes.SHOP_TYPE_ARMOR);
        this.healer = new Shop(ShopTypes.SHOP_TYPE_HEALER);
        this.bank = new Shop(ShopTypes.SHOP_TYPE_BANK);
        this.regenerator = new Shop(ShopTypes.SHOP_TYPE_REGENERATOR);
        this.spells = new Shop(ShopTypes.SHOP_TYPE_SPELLS);
        this.items = new Shop(ShopTypes.SHOP_TYPE_ITEMS);
        this.socks = new Shop(ShopTypes.SHOP_TYPE_SOCKS);
        // Cache Logo
        this.guiMgr.updateLogo();
        // Set Up Common Dialogs
        CommonDialogs.setIcon(this.getMicroLogo());
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

    public int getMode() {
        if (this.IN_PREFS) {
            return Application.STATUS_PREFS;
        } else if (this.IN_GUI) {
            return Application.STATUS_GUI;
        } else if (this.IN_GAME) {
            return Application.STATUS_GAME;
        } else {
            return Application.STATUS_NULL;
        }
    }

    public int getFormerMode() {
        if (this.IN_GUI) {
            return Application.STATUS_GUI;
        } else if (this.IN_GAME) {
            return Application.STATUS_GAME;
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

    public VariablesManager getVariablesManager() {
        if (this.variablesMgr == null) {
            this.variablesMgr = new VariablesManager();
        }
        return this.variablesMgr;
    }

    public HelpManager getHelpManager() {
        return this.helpMgr;
    }

    public AboutDialog getAboutDialog() {
        return this.about;
    }

    public BufferedImageIcon getMicroLogo() {
        return LogoManager.getMicroLogo();
    }

    public Image getIconLogo() {
        return LogoManager.getIconLogo();
    }

    public void playLogoSound() {
        SoundManager.playSound(GameSounds.LOGO);
    }

    public JFrame getOutputFrame() {
        try {
            if (this.getMode() == Application.STATUS_PREFS) {
                return PreferencesManager.getPrefFrame();
            } else if (this.getMode() == Application.STATUS_GUI) {
                return this.getGUIManager().getGUIFrame();
            } else if (this.getMode() == Application.STATUS_GAME) {
                return this.getGameManager().getOutputFrame();
            } else {
                return null;
            }
        } catch (final NullPointerException npe) {
            return null;
        }
    }

    public MapObjectList getObjects() {
        return this.objects;
    }

    public Shop getGenericShop(final int shopType) {
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
        case ShopTypes.SHOP_TYPE_SOCKS:
            return this.socks;
        case ShopTypes.SHOP_TYPE_SPELLS:
            return this.spells;
        case ShopTypes.SHOP_TYPE_WEAPONS:
            return this.weapons;
        default:
            // Invalid shop type
            return null;
        }
    }

    public BattleGUI getBattle() {
        return this.battle;
    }
}
