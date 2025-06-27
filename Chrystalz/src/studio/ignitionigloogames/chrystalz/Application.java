/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz;

import java.awt.Image;

import javax.swing.JFrame;

import studio.ignitionigloogames.chrystalz.battle.AbstractBattle;
import studio.ignitionigloogames.chrystalz.battle.MapBattleLogic;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.GameObjectList;
import studio.ignitionigloogames.chrystalz.game.GameLogic;
import studio.ignitionigloogames.chrystalz.manager.asset.LogoManager;
import studio.ignitionigloogames.chrystalz.manager.dungeon.DungeonManager;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.chrystalz.shops.Shop;
import studio.ignitionigloogames.chrystalz.shops.ShopType;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

public final class Application {
    // Fields
    private AboutDialog about;
    private GameLogic gameMgr;
    private DungeonManager mazeMgr;
    private MenuManager menuMgr;
    private GUIManager guiMgr;
    private final GameObjectList objects;
    private Shop weapons, armor, healer, regenerator, spells;
    private MapBattleLogic mapTurnBattle;
    private int currentMode;
    private int formerMode;
    private static final int VERSION_MAJOR = 0;
    private static final int VERSION_MINOR = 3;
    private static final int VERSION_BUGFIX = 0;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_BATTLE = 2;
    public static final int STATUS_PREFS = 3;
    public static final int STATUS_NULL = 4;

    // Constructors
    public Application() {
        this.objects = new GameObjectList();
        this.currentMode = Application.STATUS_NULL;
        this.formerMode = Application.STATUS_NULL;
    }

    // Methods
    void postConstruct() {
        // Create Managers
        this.about = new AboutDialog(Application.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.mapTurnBattle = new MapBattleLogic();
        this.weapons = new Shop(ShopType.WEAPONS);
        this.armor = new Shop(ShopType.ARMOR);
        this.healer = new Shop(ShopType.HEALER);
        this.regenerator = new Shop(ShopType.REGENERATOR);
        this.spells = new Shop(ShopType.SPELLS);
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
            this.getGame().setStatusMessage(msg);
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

    public GameLogic getGame() {
        if (this.gameMgr == null) {
            this.gameMgr = new GameLogic();
        }
        return this.gameMgr;
    }

    public DungeonManager getDungeonManager() {
        if (this.mazeMgr == null) {
            this.mazeMgr = new DungeonManager();
        }
        return this.mazeMgr;
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

    private static String getVersionString() {
        return Application.VERSION_MAJOR + "." + Application.VERSION_MINOR + "."
                + Application.VERSION_BUGFIX;
    }

    public JFrame getOutputFrame() {
        if (this.getMode() == Application.STATUS_PREFS) {
            return PreferencesManager.getPrefFrame();
        } else if (this.getMode() == Application.STATUS_GUI) {
            return this.getGUIManager().getGUIFrame();
        } else if (this.getMode() == Application.STATUS_GAME) {
            return this.getGame().getOutputFrame();
        } else if (this.getMode() == Application.STATUS_BATTLE) {
            return this.getBattle().getOutputFrame();
        } else {
            return null;
        }
    }

    public GameObjectList getObjects() {
        return this.objects;
    }

    public Shop getGenericShop(final ShopType shopType) {
        this.getGame().stopMovement();
        switch (shopType) {
            case ARMOR:
                return this.armor;
            case HEALER:
                return this.healer;
            case REGENERATOR:
                return this.regenerator;
            case SPELLS:
                return this.spells;
            case WEAPONS:
                return this.weapons;
            default:
                // Invalid shop type
                return null;
        }
    }

    public AbstractBattle getBattle() {
        return this.mapTurnBattle;
    }

    public void resetBattleGUI() {
        this.mapTurnBattle.resetGUI();
    }
}
