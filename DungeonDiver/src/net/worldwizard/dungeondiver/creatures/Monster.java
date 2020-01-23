package net.worldwizard.dungeondiver.creatures;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.creatures.ai.AIRoutine;
import net.worldwizard.dungeondiver.creatures.ai.RandomAIRoutinePicker;
import net.worldwizard.dungeondiver.creatures.spells.SpellBookManager;
import net.worldwizard.dungeondiver.items.Shop;
import net.worldwizard.randomnumbers.RandomRange;

public class Monster extends Creature {
    // Fields
    private static final long serialVersionUID = 5892348230660L;
    private final int levelDifference;
    private final int attack;
    private final int defense;
    private final int perfectBonusGold;
    private String type;
    private Element element; // Private Constants
    private static final double MINIMUM_EXPERIENCE_RANDOM_VARIANCE = -5.0 / 2.0;
    private static final double MAXIMUM_EXPERIENCE_RANDOM_VARIANCE = 5.0 / 2.0;
    private static final int BASE_FIGHTS_PER_LEVEL = 11;
    private static final double FIGHTS_PER_LEVEL_INCREMENT = 1.25;
    private static final int HP_PER_LEVEL = 10;
    private static final int MP_PER_LEVEL = 4;

    // Constructors
    public Monster() {
        super();
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final int newLevel = player.getDungeonLevel();
        this.level = newLevel;
        this.levelDifference = newLevel - player.getLevel();
        this.maximumHP = this.getInitialMaximumHP();
        this.currentHP = this.maximumHP;
        this.maximumMP = this.getInitialMaximumMP();
        this.currentMP = this.maximumMP;
        this.attack = this.getInitialAttack();
        this.defense = this.getInitialDefense();
        this.gold = this.getInitialGold();
        this.experience = this.getInitialExperience();
        this.perfectBonusGold = this.getInitialPerfectBonusGold();
        this.image = this.getInitialImage();
        this.getInitialSpellBook();
        this.ai = Monster.getInitialAI();
    }

    // Accessors
    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public int getDefense() {
        return this.defense;
    }

    public int getPerfectBonusGold() {
        return this.perfectBonusGold;
    }

    @Override
    public String getName() {
        return this.element.getName() + " " + this.type;
    }

    // Helper Methods
    private static AIRoutine getInitialAI() {
        return RandomAIRoutinePicker.getNextRoutine();
    }

    private int getInitialAttack() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final RandomRange r1 = new RandomRange(0, player.getPermanentAttack());
        final RandomRange r2 = new RandomRange(0, this.level
                + player.getPower());
        return (int) r1.generate() + (int) r2.generate();
    }

    private int getInitialDefense() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final RandomRange r1 = new RandomRange(0, player.getPermanentDefense());
        final RandomRange r2 = new RandomRange(0, this.level
                + player.getBlock());
        return (int) r1.generate() + (int) r2.generate();
    }

    private long getInitialExperience() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        int min, max;
        min = (int) (player.getLevel() * Monster.MINIMUM_EXPERIENCE_RANDOM_VARIANCE);
        max = (int) (player.getLevel() * Monster.MAXIMUM_EXPERIENCE_RANDOM_VARIANCE);
        final RandomRange r = new RandomRange(min, max);
        final long toNext = player.getExpToNextLevel(player.getLevel() + 1,
                player.getKills());
        final long toCurrent = player.getExpToNextLevel(player.getLevel(),
                player.getKills());
        final long needed = toNext - toCurrent;
        final long factor = Monster.getFightsPerLevel();
        final long exp = (long) ((needed / factor + r.generate()) * this
                .adjustForLevelDifference());
        if (exp < 0L) {
            return 0L;
        } else {
            return exp;
        }
    }

    private int getInitialGold() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final int needed = Shop.getEquipmentCost(player.getLevel() + 1) * 2;
        final int factor = Monster.getFightsPerLevel();
        final int averageHealingCost = Shop.getHealingCost(player.getLevel(),
                player.getMaximumHP() * 2 / 3, player.getMaximumHP(),
                player.getKills());
        final int averageRegenerationCost = Shop.getRegenerationCost(
                player.getLevel(), player.getMaximumMP() / 2,
                player.getMaximumMP(), player.getKills());
        final int max = needed / factor * 2 + averageHealingCost
                + averageRegenerationCost;
        final RandomRange r = new RandomRange(0, max);
        return (int) ((int) r.generate() * this.adjustForLevelDifference());
    }

    private int getInitialPerfectBonusGold() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final int needed = Shop.getEquipmentCost(player.getLevel() + 1) * 2;
        final int factor = Monster.getFightsPerLevel();
        final int min = needed / factor / 4;
        final int max = needed / factor / 2;
        final RandomRange r = new RandomRange(min, max);
        return (int) ((int) r.generate() * this.adjustForLevelDifference());
    }

    private int getInitialMaximumHP() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final RandomRange r1 = new RandomRange(0, player.getPermanentHP());
        final RandomRange r2 = new RandomRange(0, this.level - 1);
        return Monster.HP_PER_LEVEL * this.level + (int) r1.generate()
                + (int) r2.generate();
    }

    private int getInitialMaximumMP() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        final RandomRange r1 = new RandomRange(0, player.getPermanentMP());
        final RandomRange r2 = new RandomRange(0, this.level
                * Monster.MP_PER_LEVEL);
        return (int) r1.generate() + (int) r2.generate();
    }

    private void getInitialSpellBook() {
        int bookID = (int) Math.ceil(this.level / 4.0);
        if (bookID > 4) {
            bookID = 4;
        }
        this.setSpellBook(SpellBookManager.getEnemySpellBookByID(bookID));
    }

    private double adjustForLevelDifference() {
        return this.levelDifference / 16.0 + 1.0;
    }

    private static int getFightsPerLevel() {
        final Player player = DungeonDiver.getHoldingBag().getPlayer();
        return (int) (Monster.BASE_FIGHTS_PER_LEVEL + (player.getLevel() - 1)
                * Monster.FIGHTS_PER_LEVEL_INCREMENT);
    }

    @Override
    protected ImageIcon getInitialImage() {
        if (this.level == 0) {
            return null;
        } else {
            final String[] types = Monster.getTypesForLevel(this.level);
            final RandomRange r = new RandomRange(0, types.length - 1);
            this.type = types[(int) r.generate()];
            this.element = ElementList.getRandomElement();
            return GraphicsManager.getMonsterImage(this.type, this.element);
        }
    }

    private static String[] getTypesForLevel(final int level) {
        String[] types = null;
        switch (level) {
        case 1:
            types = new String[6];
            types[0] = "Amoeba";
            types[1] = "Drop";
            types[2] = "Jelly";
            types[3] = "Bunny";
            types[4] = "Bat";
            types[5] = "Turtle";
            break;
        case 2:
            types = new String[6];
            types[0] = "Anglebot";
            types[1] = "Boxbot";
            types[2] = "Curvebot";
            types[3] = "Diamondbot";
            types[4] = "Ellipsebot";
            types[5] = "Jetbot";
            break;
        case 3:
            types = new String[6];
            types[0] = "Flask";
            types[1] = "Tube";
            types[2] = "Vial";
            types[3] = "Beaker";
            types[4] = "Bowl";
            types[5] = "Can";
            break;
        case 4:
            types = new String[6];
            types[0] = "Grabber";
            types[1] = "Lander";
            types[2] = "Medic";
            types[3] = "Alarm";
            types[4] = "Scout";
            types[5] = "Warship";
            break;
        case 5:
            types = new String[6];
            types[0] = "CPU";
            types[1] = "Motherboard";
            types[2] = "RAM";
            types[3] = "GPU";
            types[4] = "Network";
            types[5] = "Soundcard";
            break;
        case 6:
            types = new String[6];
            types[0] = "Banana";
            types[1] = "Bush";
            types[2] = "Pear";
            types[3] = "Mushroom";
            types[4] = "Tree";
            types[5] = "Vine";
            break;
        case 7:
            types = new String[6];
            types[0] = "Fightbot";
            types[1] = "Sonicbot";
            types[2] = "Tacklebot";
            types[3] = "Bomber";
            types[4] = "Dropper";
            types[5] = "Fighter";
            break;
        case 8:
            types = new String[6];
            types[0] = "Barrel";
            types[1] = "Bottle";
            types[2] = "Jar";
            types[3] = "Pipe";
            types[4] = "Recycler";
            types[5] = "Trash Can";
            break;
        case 9:
            types = new String[6];
            types[0] = "Healer";
            types[1] = "Power Ten";
            types[2] = "Solar";
            types[3] = "Twin Claw";
            types[4] = "Super War";
            types[5] = "Driller";
            break;
        case 10:
            types = new String[6];
            types[0] = "Burger";
            types[1] = "Mega War";
            types[2] = "Radio";
            types[3] = "Rover";
            types[4] = "Spike";
            types[5] = "Top";
            break;
        case 11:
            types = new String[5];
            types[0] = "Banana Peel";
            types[1] = "Discarded Note";
            types[2] = "Garbage";
            types[3] = "Paper Wad";
            types[4] = "Gunk Ball";
            break;
        case 12:
            types = new String[4];
            types[0] = "Artillery";
            types[1] = "Bomb";
            types[2] = "Missile";
            types[3] = "Tank";
            break;
        default:
            types = new String[13];
            types[0] = "ROM";
            types[1] = "Blaster Tree";
            types[2] = "Canned Can";
            types[3] = "Cross Vine";
            types[4] = "Double Banana";
            types[5] = "Giant Amoeba";
            types[6] = "Giant Drop";
            types[7] = "Goat";
            types[8] = "Piped Tube";
            types[9] = "Spiked Bush";
            types[10] = "Super Spike";
            types[11] = "Triple Tube";
            types[12] = "Twin RAM";
            break;
        }
        return types;
    }
}
