/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.battle.BattleLogic;
import com.puttysoftware.gemma.game.GameLogic;
import com.puttysoftware.gemma.namer.editor.NameEditor;
import com.puttysoftware.gemma.namer.generic.GenericEditor;
import com.puttysoftware.gemma.prefs.PreferencesManager;
import com.puttysoftware.gemma.resourcemanagers.LogoManager;
import com.puttysoftware.gemma.scenario.ScenarioManager;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.items.Shop;
import com.puttysoftware.gemma.support.items.ShopTypes;
import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.generic.MapObjectList;
import com.puttysoftware.gemma.support.map.objects.Empty;
import com.puttysoftware.gemma.support.map.objects.Tile;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.resourcemanagers.SoundManager;
import com.puttysoftware.images.BufferedImageIcon;

public class Application {
    // Fields
    private AboutDialog about;
    private GameLogic gameMgr;
    private ScenarioManager scenarioMgr;
    private MenuManager menuMgr;
    private ObjectHelpManager helpMgr;
    private GUIManager guiMgr;
    private final MapObjectList objects;
    private Shop weapons, armor, healer, regenerator, items, enhancements,
            faiths;
    private BattleLogic battle;
    private NameEditor nameEditor;
    private int currentMode;
    private int formerMode;
    private ArrayList<GenericEditor> allEditors;
    private int currentEditor;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    private static final int STATUS_PREFS = 2;
    public static final int STATUS_BATTLE = 3;
    static final int STATUS_EDITOR = 4;
    public static final int STATUS_SHOP = 5;
    private static final int STATUS_NULL = 6;
    public static final int EDITOR_NAME = 0;

    // Constructors
    public Application() {
        this.objects = new MapObjectList();
        this.currentMode = Application.STATUS_NULL;
        this.formerMode = Application.STATUS_NULL;
    }

    // Methods
    void postConstruct() {
        // Create Managers
        this.nameEditor = new NameEditor();
        this.allEditors = new ArrayList<>();
        this.allEditors.add(this.nameEditor);
        this.about = new AboutDialog(Support.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.helpMgr = new ObjectHelpManager();
        this.battle = new BattleLogic();
        this.weapons = new Shop(ShopTypes.SHOP_TYPE_WEAPONS);
        this.armor = new Shop(ShopTypes.SHOP_TYPE_ARMOR);
        this.healer = new Shop(ShopTypes.SHOP_TYPE_HEALER);
        this.regenerator = new Shop(ShopTypes.SHOP_TYPE_REGENERATOR);
        this.items = new Shop(ShopTypes.SHOP_TYPE_ITEMS);
        this.enhancements = new Shop(ShopTypes.SHOP_TYPE_ENHANCEMENTS);
        this.faiths = new Shop(ShopTypes.SHOP_TYPE_FAITH_POWERS);
        // Cache Logo
        this.guiMgr.updateLogo();
        // Set Up Common Dialogs
        CommonDialogs.setIcon(Application.getMicroLogo());
        Support.createScenario();
        this.getScenarioManager().setMap(new Map());
        this.getScenarioManager().getMap().addLevel(Support.getGameMapSize(),
                Support.getGameMapSize(), Support.getGameMapFloorSize());
        this.getScenarioManager().getMap().fillLevelRandomly(new Tile(),
                new Empty());
        this.getScenarioManager().getMap().setPlayerLocationW(0);
        this.getScenarioManager().getMap().findStart();
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

    public void setInEditor() {
        this.currentMode = Application.STATUS_EDITOR;
    }

    public void setInShop() {
        this.currentMode = Application.STATUS_SHOP;
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

    ObjectHelpManager getHelpManager() {
        return this.helpMgr;
    }

    AboutDialog getAboutDialog() {
        return this.about;
    }

    public GenericEditor getCurrentEditor() {
        return this.allEditors.get(this.currentEditor);
    }

    public void notifyAllNonCurrentEditorsDisableCommands() {
        for (int x = 0; x < this.allEditors.size(); x++) {
            if (x != this.currentEditor) {
                final GenericEditor ge = this.allEditors.get(x);
                ge.disableEditorCommands();
            }
        }
    }

    public void notifyAllNonCurrentEditorsEnableCommands() {
        for (int x = 0; x < this.allEditors.size(); x++) {
            if (x != this.currentEditor) {
                final GenericEditor ge = this.allEditors.get(x);
                ge.enableEditorCommands();
            }
        }
    }

    public void setCurrentEditor(final int ce) {
        this.currentEditor = ce;
    }

    public NameEditor getNamesEditor() {
        return this.nameEditor;
    }

    public ArrayList<GenericEditor> getAllEditors() {
        return this.allEditors;
    }

    private static BufferedImageIcon getMicroLogo() {
        return LogoManager.getMicroLogo();
    }

    public static Image getIconLogo() {
        return LogoManager.getIconLogo();
    }

    static void playLogoSound() {
        SoundManager.playSound(GameSoundConstants.SOUND_LOGO);
    }

    public JFrame getOutputFrame() {
        try {
            if (this.getMode() == Application.STATUS_PREFS) {
                return PreferencesManager.getPrefFrame();
            } else if (this.getMode() == Application.STATUS_GUI) {
                return this.getGUIManager().getGUIFrame();
            } else if (this.getMode() == Application.STATUS_BATTLE) {
                return this.getBattle().getOutputFrame();
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
        case ShopTypes.SHOP_TYPE_ENHANCEMENTS:
            return this.enhancements;
        case ShopTypes.SHOP_TYPE_FAITH_POWERS:
            return this.faiths;
        case ShopTypes.SHOP_TYPE_HEALER:
            return this.healer;
        case ShopTypes.SHOP_TYPE_ITEMS:
            return this.items;
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
