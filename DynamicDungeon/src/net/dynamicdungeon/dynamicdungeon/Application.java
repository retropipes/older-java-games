/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon;

import java.awt.Image;

import javax.swing.JFrame;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dynamicdungeon.battle.AbstractBattle;
import net.dynamicdungeon.dynamicdungeon.battle.window.time.WindowTimeBattleLogic;
import net.dynamicdungeon.dynamicdungeon.battle.window.turn.WindowTurnBattleLogic;
import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.DungeonObjectList;
import net.dynamicdungeon.dynamicdungeon.game.GameLogicManager;
import net.dynamicdungeon.dynamicdungeon.prefs.PreferencesManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.LogoManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;
import net.dynamicdungeon.dynamicdungeon.shops.Shop;
import net.dynamicdungeon.dynamicdungeon.shops.ShopTypes;
import net.dynamicdungeon.images.BufferedImageIcon;

public final class Application {
    // Fields
    private AboutDialog about;
    private GameLogicManager gameMgr;
    private DungeonManager mazeMgr;
    private MenuManager menuMgr;
    private GeneralHelpManager gHelpMgr;
    private ObjectHelpManager oHelpMgr;
    private GUIManager guiMgr;
    private final DungeonObjectList objects;
    private Shop weapons, armor, healer, bank, regenerator, spells, items;
    private WindowTurnBattleLogic windowTurnBattle;
    private WindowTimeBattleLogic windowTimeBattle;
    private int currentMode;
    private int formerMode;
    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_BATTLE = 2;
    public static final int STATUS_PREFS = 3;
    public static final int STATUS_NULL = 4;

    // Constructors
    public Application() {
        this.objects = new DungeonObjectList();
        this.currentMode = Application.STATUS_NULL;
        this.formerMode = Application.STATUS_NULL;
    }

    // Methods
    void postConstruct() {
        // Create Managers
        this.about = new AboutDialog(Application.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.gHelpMgr = new GeneralHelpManager();
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

    public DungeonManager getDungeonManager() {
        if (this.mazeMgr == null) {
            this.mazeMgr = new DungeonManager();
        }
        return this.mazeMgr;
    }

    public GeneralHelpManager getGeneralHelpManager() {
        return this.gHelpMgr;
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
        return Application.VERSION_MAJOR + "." + Application.VERSION_MINOR + "."
                + Application.VERSION_BUGFIX;
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

    public DungeonObjectList getObjects() {
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
