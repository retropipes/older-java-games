package studio.ignitionigloogames.dungeondiver1;

import studio.ignitionigloogames.dungeondiver1.creatures.Battle;
import studio.ignitionigloogames.dungeondiver1.creatures.Boss;
import studio.ignitionigloogames.dungeondiver1.creatures.BossBattle;
import studio.ignitionigloogames.dungeondiver1.creatures.Player;
import studio.ignitionigloogames.dungeondiver1.dungeon.DungeonGUI;
import studio.ignitionigloogames.dungeondiver1.items.Shop;
import studio.ignitionigloogames.dungeondiver1.items.ShopTypes;

public class HoldingBag implements ShopTypes {
    // Fields
    private Player player;
    private final Battle battle;
    private final BossBattle bossBattle;
    private final Shop weapons, armor, healer, bank, regenerator;
    private final SavedState state;
    private final Help help;
    private final DungeonGUI dungeonGUI;
    private final GUIManager gui;
    private final GameManager game;
    private final Preferences prefs;
    private boolean bossFlag;

    // Constructors
    public HoldingBag() {
        this.battle = new Battle();
        this.bossBattle = new BossBattle();
        this.weapons = new Shop(ShopTypes.WEAPONS);
        this.armor = new Shop(ShopTypes.ARMOR);
        this.healer = new Shop(ShopTypes.HEALER);
        this.bank = new Shop(ShopTypes.BANK);
        this.regenerator = new Shop(ShopTypes.REGENERATOR);
        this.state = new SavedState();
        this.dungeonGUI = new DungeonGUI();
        this.help = new Help();
        this.gui = new GUIManager();
        this.game = new GameManager();
        this.prefs = new Preferences();
        this.bossFlag = false;
    }

    // Methods
    public void showGUI() {
        this.dungeonGUI.hideDungeon();
        this.gui.showGUI();
    }

    public GUIManager getGUIManager() {
        return this.gui;
    }

    public GameManager getGameManager() {
        return this.game;
    }

    public DungeonGUI getDungeonGUI() {
        return this.dungeonGUI;
    }

    public Battle getBattle() {
        if (this.player.getLevel() == Boss.FIGHT_LEVEL) {
            if (!this.bossFlag) {
                this.battle.battleDone();
                this.bossFlag = true;
            }
            return this.bossBattle;
        } else {
            this.bossFlag = false;
            return this.battle;
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(final Player newPlayer) {
        this.player = newPlayer;
    }

    public Shop getArmor() {
        return this.armor;
    }

    public Shop getBank() {
        return this.bank;
    }

    public Shop getHealer() {
        return this.healer;
    }

    public Shop getRegenerator() {
        return this.regenerator;
    }

    public Shop getWeapons() {
        return this.weapons;
    }

    public Preferences getPrefs() {
        return this.prefs;
    }

    public boolean loadState() {
        this.hideGUI();
        final boolean result = this.state.load();
        this.showGUI();
        return result;
    }

    public void saveState() {
        this.hideGUI();
        this.state.save();
        this.showGUI();
    }

    public void newDungeon() {
        this.hideGUI();
        this.dungeonGUI.newDungeon();
    }

    public void showHelp() {
        this.help.showHelp();
    }

    public void quit() {
        this.hideGUI();
        System.exit(0);
    }

    public void exploreDungeon() {
        this.hideGUI();
        if (!this.dungeonGUI.doesDungeonExist()) {
            this.dungeonGUI.newDungeon();
        }
        this.dungeonGUI.exploreDungeon();
    }

    private void hideGUI() {
        this.gui.hideGUI();
    }
}
