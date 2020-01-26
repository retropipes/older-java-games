/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fantastlex.battle.AbstractBattle;
import com.puttysoftware.fantastlex.battle.map.MapBattleLogic;
import com.puttysoftware.fantastlex.battle.window.WindowBattleLogic;
import com.puttysoftware.fantastlex.editor.MazeEditorLogic;
import com.puttysoftware.fantastlex.editor.namer.abc.AbstractEditor;
import com.puttysoftware.fantastlex.editor.namer.editor.NameEditor;
import com.puttysoftware.fantastlex.editor.rulesets.RuleSetPicker;
import com.puttysoftware.fantastlex.game.GameLogicManager;
import com.puttysoftware.fantastlex.items.Shop;
import com.puttysoftware.fantastlex.items.ShopTypes;
import com.puttysoftware.fantastlex.maze.MazeManager;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.objects.GeneratedEdge;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectList;
import com.puttysoftware.fantastlex.prefs.PreferencesManager;
import com.puttysoftware.fantastlex.resourcemanagers.ImageTransformer;
import com.puttysoftware.fantastlex.resourcemanagers.LogoManager;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageManager;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;
import com.puttysoftware.fantastlex.scenario.ScenarioManager;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.images.edges.EdgeGenerator;
import com.puttysoftware.updater.ProductData;

public class Application {
    // Fields
    private AboutDialog about;
    private GameLogicManager gameMgr;
    private ScenarioManager scenarioMgr;
    private MazeManager mazeMgr;
    private MenuManager menuMgr;
    private ObjectHelpManager oHelpMgr;
    private MazeEditorLogic editor;
    private RuleSetPicker rsPicker;
    private GUIManager guiMgr;
    private final MazeObjectList objects;
    private Shop weapons, armor, healer, bank, regenerator, spells, items,
            socks, enhancements, faiths;
    private MapBattleLogic mapBattle;
    private WindowBattleLogic windowBattle;
    private NameEditor nameEditor;
    private ArrayList<AbstractEditor> allEditors;
    private int currentEditor;
    private int currentMode;
    private int formerMode;
    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    private static final int VERSION_CODE = ProductData.CODE_STABLE_RELEASE;
    private static final int VERSION_PRERELEASE = 0;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_BATTLE = 2;
    public static final int STATUS_EDITOR = 3;
    public static final int STATUS_PREFS = 4;
    public static final int STATUS_NULL = 5;
    public static final int EDITOR_NAME = 0;

    // Constructors
    public Application() {
        this.objects = new MazeObjectList();
        this.currentMode = Application.STATUS_NULL;
        this.formerMode = Application.STATUS_NULL;
    }

    // Methods
    void postConstruct() {
        // Create Edge Images and Objects
        final AbstractMazeObject[] groundTypes = this.objects
                .getAllGenerationEligibleTypedObjects();
        final String[] edgeFriendlyNameSuffixes = EdgeGenerator
                .generateAllEdgedImageFriendlyNameSuffixes();
        for (int x = 0; x < groundTypes.length - 1; x++) {
            for (int y = x + 1; y < groundTypes.length; y++) {
                if (groundTypes[x].equals(groundTypes[y])) {
                    continue;
                }
                final BufferedImageIcon[] edges = EdgeGenerator
                        .generateAllEdgedImages(
                                ObjectImageManager.getImage(
                                        groundTypes[x].getName(),
                                        groundTypes[x].getBaseID(),
                                        groundTypes[x].getTemplateColor(),
                                        groundTypes[x].getAttributeID(),
                                        groundTypes[x]
                                                .getAttributeTemplateColor()),
                                ObjectImageManager.getImage(
                                        groundTypes[y].getName(),
                                        groundTypes[y].getBaseID(),
                                        groundTypes[y].getTemplateColor(),
                                        groundTypes[y].getAttributeID(),
                                        groundTypes[y]
                                                .getAttributeTemplateColor()),
                                ImageTransformer.generateEdgeColor(
                                        groundTypes[x].getTemplateColor(),
                                        groundTypes[y].getTemplateColor()));
                for (int z = 0; z < edges.length; z++) {
                    ObjectImageManager.addImageToCache(groundTypes[x].getName()
                            + "/" + groundTypes[y].getName() + " Transition "
                            + edgeFriendlyNameSuffixes[z], edges[z]);
                    final GeneratedEdge ge = new GeneratedEdge(
                            groundTypes[x].getName() + "/"
                                    + groundTypes[y].getName() + " Transition "
                                    + edgeFriendlyNameSuffixes[z],
                            ObjectImageConstants.OBJECT_IMAGE_NONE,
                            groundTypes[x].getName() + "/"
                                    + groundTypes[y].getName() + " Transitions "
                                    + edgeFriendlyNameSuffixes[z],
                            groundTypes[x].getName(), groundTypes[y].getName(),
                            edgeFriendlyNameSuffixes[z]);
                    this.objects.addObject(ge);
                }
            }
        }
        // Create Managers
        this.nameEditor = new NameEditor();
        this.allEditors = new ArrayList<>();
        this.allEditors.add(this.nameEditor);
        this.about = new AboutDialog(
                Application.getVersionString(Application.VERSION_CODE));
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.oHelpMgr = new ObjectHelpManager();
        this.mapBattle = new MapBattleLogic();
        this.windowBattle = new WindowBattleLogic();
        this.weapons = new Shop(ShopTypes.SHOP_TYPE_WEAPONS);
        this.armor = new Shop(ShopTypes.SHOP_TYPE_ARMOR);
        this.healer = new Shop(ShopTypes.SHOP_TYPE_HEALER);
        this.bank = new Shop(ShopTypes.SHOP_TYPE_BANK);
        this.regenerator = new Shop(ShopTypes.SHOP_TYPE_REGENERATOR);
        this.spells = new Shop(ShopTypes.SHOP_TYPE_SPELLS);
        this.items = new Shop(ShopTypes.SHOP_TYPE_ITEMS);
        this.socks = new Shop(ShopTypes.SHOP_TYPE_SOCKS);
        this.enhancements = new Shop(ShopTypes.SHOP_TYPE_ENHANCEMENTS);
        this.faiths = new Shop(ShopTypes.SHOP_TYPE_FAITH_POWERS);
        // Cache Logo
        this.guiMgr.updateLogo();
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

    public int getMode() {
        return this.currentMode;
    }

    public int getFormerMode() {
        return this.formerMode;
    }

    public void showMessage(final String msg) {
        if (this.currentMode == Application.STATUS_GAME) {
            this.getGameManager().setStatusMessage(msg);
        } else if (this.currentMode == Application.STATUS_BATTLE) {
            this.getBattle().setStatusMessage(msg);
        } else if (this.currentMode == Application.STATUS_EDITOR) {
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

    public GameLogicManager getGameManager() {
        if (this.gameMgr == null) {
            this.gameMgr = new GameLogicManager();
        }
        return this.gameMgr;
    }

    public ScenarioManager getScenarioManager() {
        if (this.scenarioMgr == null) {
            this.scenarioMgr = new ScenarioManager();
        }
        return this.scenarioMgr;
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

    public MazeEditorLogic getEditor() {
        if (this.editor == null) {
            this.editor = new MazeEditorLogic();
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

    public void notifyAllNonCurrentEditorsDisableCommands() {
        for (int x = 0; x < this.allEditors.size(); x++) {
            if (x != this.currentEditor) {
                final AbstractEditor ge = this.allEditors.get(x);
                ge.disableEditorCommands();
            }
        }
    }

    public void notifyAllNonCurrentEditorsEnableCommands() {
        for (int x = 0; x < this.allEditors.size(); x++) {
            if (x != this.currentEditor) {
                final AbstractEditor ge = this.allEditors.get(x);
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

    public ArrayList<AbstractEditor> getAllEditors() {
        return this.allEditors;
    }

    public BufferedImageIcon getMicroLogo() {
        return LogoManager.getMicroLogo();
    }

    public Image getIconLogo() {
        return LogoManager.getIconLogo();
    }

    public void playLogoSound() {
        SoundManager.playSound(SoundConstants.SOUND_LOGO);
    }

    private static String getVersionString(final int code) {
        String rt;
        if (code == ProductData.CODE_BETA) {
            rt = "-beta";
        } else {
            rt = "";
        }
        if (code < ProductData.CODE_STABLE_RELEASE) {
            return "" + Application.VERSION_MAJOR + "."
                    + Application.VERSION_MINOR + "."
                    + Application.VERSION_BUGFIX + rt
                    + Application.VERSION_PRERELEASE;
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
            } else if (this.getMode() == Application.STATUS_BATTLE) {
                return this.getBattle().getOutputFrame();
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

    public Shop getGenericShop(final int shopType) {
        switch (shopType) {
        case ShopTypes.SHOP_TYPE_ARMOR:
            return this.armor;
        case ShopTypes.SHOP_TYPE_BANK:
            return this.bank;
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

    public AbstractBattle getBattle() {
        if (PreferencesManager.getBattleStyle()) {
            return this.mapBattle;
        } else {
            return this.windowBattle;
        }
    }
}
