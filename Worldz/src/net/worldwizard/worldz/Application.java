/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

import java.awt.Image;

import javax.swing.JFrame;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.worldz.battle.Battle;
import net.worldwizard.worldz.editor.WorldEditor;
import net.worldwizard.worldz.editor.rulesets.RuleSetPicker;
import net.worldwizard.worldz.game.GameManager;
import net.worldwizard.worldz.generic.WorldObjectList;
import net.worldwizard.worldz.items.Shop;
import net.worldwizard.worldz.items.ShopTypes;
import net.worldwizard.worldz.items.combat.CombatItemList;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;
import net.worldwizard.worldz.resourcemanagers.SoundManager;
import net.worldwizard.worldz.world.WorldManager;

public class Application {
    // Fields
    private AboutDialog about;
    private GameManager gameMgr;
    private WorldManager worldMgr;
    private MenuManager menuMgr;
    private PreferencesManager prefsMgr;
    private GeneralHelpManager gHelpMgr;
    private ObjectHelpManager oHelpMgr;
    private WorldEditor editor;
    private RuleSetPicker rsPicker;
    private GUIManager guiMgr;
    private final WorldObjectList objects;
    private final CombatItemList combatItems;
    private Shop weapons, armor, healer, bank, regenerator, spells, items,
            socks;
    private Battle battle;
    private BufferedImageIcon microLogo;
    private Image iconlogo;
    private boolean IN_GUI, IN_PREFS, IN_GAME, IN_EDITOR;
    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_BETA = 2;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_EDITOR = 2;
    public static final int STATUS_PREFS = 3;
    public static final int STATUS_BATTLE = 4;
    public static final int STATUS_NULL = 5;

    // Constructors
    public Application() {
        this.objects = new WorldObjectList();
        this.combatItems = new CombatItemList();
    }

    // Methods
    void postConstruct() {
        // Cache Icon Logo
        this.iconlogo = GraphicsManager.getIconLogo();
        // Create Managers
        this.prefsMgr = new PreferencesManager();
        this.about = new AboutDialog(this.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.gHelpMgr = new GeneralHelpManager();
        this.oHelpMgr = new ObjectHelpManager();
        this.weapons = new Shop(ShopTypes.SHOP_TYPE_WEAPONS);
        this.armor = new Shop(ShopTypes.SHOP_TYPE_ARMOR);
        this.healer = new Shop(ShopTypes.SHOP_TYPE_HEALER);
        this.bank = new Shop(ShopTypes.SHOP_TYPE_BANK);
        this.regenerator = new Shop(ShopTypes.SHOP_TYPE_REGENERATOR);
        this.spells = new Shop(ShopTypes.SHOP_TYPE_SPELLS);
        this.items = new Shop(ShopTypes.SHOP_TYPE_ITEMS);
        this.socks = new Shop(ShopTypes.SHOP_TYPE_SOCKS);
        this.battle = new Battle();
        // Cache Micro Logo
        this.microLogo = GraphicsManager.getMicroLogo();
        // Cache Logo
        this.guiMgr.updateLogo();
    }

    public void invalidateImageCaches() {
        // Cache Icon Logo
        this.iconlogo = GraphicsManager.getIconLogo();
        // Cache Micro Logo
        this.microLogo = GraphicsManager.getMicroLogo();
        // Cache Logo
        this.guiMgr.updateLogo();
        // Cache Mini Logo
        this.about.update();
        // Cache Stat Images
        this.gameMgr.invalidateStatImageCaches();
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
        } else if (Battle.isInBattle()) {
            return Application.STATUS_BATTLE;
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
        if (Battle.isInBattle()) {
            return Application.STATUS_BATTLE;
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

    public MenuManager getMenuManager() {
        return this.menuMgr;
    }

    public GUIManager getGUIManager() {
        return this.guiMgr;
    }

    public PreferencesManager getPrefsManager() {
        return this.prefsMgr;
    }

    public GameManager getGameManager() {
        if (this.gameMgr == null) {
            this.gameMgr = new GameManager();
        }
        return this.gameMgr;
    }

    public WorldManager getWorldManager() {
        if (this.worldMgr == null) {
            this.worldMgr = new WorldManager();
        }
        return this.worldMgr;
    }

    public GeneralHelpManager getGeneralHelpManager() {
        return this.gHelpMgr;
    }

    public ObjectHelpManager getObjectHelpManager() {
        return this.oHelpMgr;
    }

    public WorldEditor getEditor() {
        if (this.editor == null) {
            this.editor = new WorldEditor();
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
        return this.microLogo;
    }

    public Image getIconLogo() {
        return this.iconlogo;
    }

    public void playLogoSound() {
        if (this.prefsMgr.getSoundEnabled(PreferencesManager.SOUNDS_UI)) {
            SoundManager.playSound("logo");
        }
    }

    public CombatItemList getCombatItems() {
        return this.combatItems;
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

    public Battle getBattle() {
        return this.battle;
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
                return this.getPrefsManager().getPrefFrame();
            } else if (Battle.isInBattle()) {
                return this.battle.getBattleFrame();
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

    public WorldObjectList getObjects() {
        return this.objects;
    }

    public boolean isBetaModeEnabled() {
        return Application.VERSION_BETA > 0;
    }
}
