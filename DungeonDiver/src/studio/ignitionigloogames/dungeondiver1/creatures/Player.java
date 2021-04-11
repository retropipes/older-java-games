package studio.ignitionigloogames.dungeondiver1.creatures;

import studio.ignitionigloogames.dungeondiver1.creatures.spells.SpellBookManager;
import studio.ignitionigloogames.dungeondiver1.utilities.BufferedImageIcon;

public abstract class Player extends Creature {
    // Fields
    private static final long serialVersionUID = 234923525250L;
    protected int hpPerLevel;
    protected int mpPerLevel;
    protected int permanentHPperPoint;
    protected int permanentMPperPoint;
    protected int classBonusAttack;
    protected int classBonusDefense;
    private int permanentAttack;
    private int permanentDefense;
    private int permanentHP;
    private int permanentMP;
    private int kills;
    private int power;
    private int block;
    private int weapon;
    private int armor;
    private int bank;
    private long toNextLevel;
    private int dungeonLevel;
    private static final int START_GOLD = 0;
    private static final int START_WEAPON = 1;
    private static final int START_ARMOR = 1;
    private static final double DEFEAT_PENALTY = 0.9;
    private static final double ANNIHILATION_PENALTY = 0.8;
    private static final int MIN_DUNGEON_LEVEL = 1;

    // Constructors
    public Player() {
        super();
        this.level = 1;
        this.permanentAttack = 0;
        this.permanentDefense = 0;
        this.permanentHP = 0;
        this.permanentMP = 0;
        this.classBonusAttack = 0;
        this.classBonusDefense = 0;
        this.kills = 0;
        this.hpPerLevel = 10;
        this.mpPerLevel = 4;
        this.permanentHPperPoint = 5;
        this.permanentMPperPoint = 2;
        this.updateMaxHPandMP();
        this.healFully();
        this.power = this.level;
        this.block = this.level;
        this.weapon = Player.START_WEAPON;
        this.armor = Player.START_ARMOR;
        this.gold = Player.START_GOLD;
        this.bank = 0;
        this.experience = 0L;
        this.toNextLevel = this.getExpToNextLevel(this.level + 1, this.kills);
        this.dungeonLevel = this.level;
    }

    public Player(final int pAtk, final int pDef, final int pHP, final int pMP,
            final int k) {
        super();
        this.level = 1;
        this.permanentAttack = pAtk;
        this.permanentDefense = pDef;
        this.permanentHP = pHP;
        this.permanentMP = pMP;
        this.classBonusAttack = 0;
        this.classBonusDefense = 0;
        this.kills = k;
        this.hpPerLevel = 10;
        this.mpPerLevel = 4;
        this.permanentHPperPoint = 5;
        this.permanentMPperPoint = 2;
        this.updateMaxHPandMP();
        this.healFully();
        this.power = this.level;
        this.block = this.level;
        this.weapon = Player.START_WEAPON;
        this.armor = Player.START_ARMOR;
        this.gold = Player.START_GOLD;
        this.bank = 0;
        this.experience = 0L;
        this.toNextLevel = this.getExpToNextLevel(this.level + 1, this.kills);
        this.dungeonLevel = this.level;
    }

    // Methods
    public void addExperience(final long newExperience) {
        this.experience += newExperience;
    }

    public void addGold(final int newGold) {
        this.gold += newGold;
    }

    public void addGoldToBank(final int newGold) {
        this.bank += newGold;
    }

    public void annihilated() {
        this.experience *= Player.ANNIHILATION_PENALTY;
        this.gold = 0;
        this.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
        this.regeneratePercentage(Creature.FULL_HEAL_PERCENTAGE);
    }

    public boolean checkLevelUp() {
        return this.experience >= this.toNextLevel;
    }

    public void defeat() {
        this.experience *= Player.DEFEAT_PENALTY;
        this.gold = 0;
        this.healPercentage(Creature.FULL_HEAL_PERCENTAGE);
        this.regeneratePercentage(Creature.FULL_HEAL_PERCENTAGE);
    }

    public int getArmorBlock() {
        return this.armor;
    }

    @Override
    public int getAttack() {
        return this.power + this.weapon + this.permanentAttack
                + this.classBonusAttack;
    }

    public int getBlock() {
        return this.block;
    }

    @Override
    public int getDefense() {
        return this.block + this.armor + this.permanentDefense
                + this.classBonusDefense;
    }

    public long getExpToNextLevel(final int x, final int k) {
        if (x == 1) {
            return 0L;
        } else {
            return 10 * x * x * x + 10 * x * x + 10 * x + 10 + 3 * x * k;
        }
    }

    @Override
    public BufferedImageIcon getInitialImage() {
        return null;
    }

    public int getGoldInBank() {
        return this.bank;
    }

    public int getKills() {
        return this.kills;
    }

    public int getPermanentAttack() {
        return this.permanentAttack;
    }

    public int getPermanentAttackPoints() {
        return this.permanentAttack;
    }

    public int getPermanentDefense() {
        return this.permanentDefense;
    }

    public int getPermanentDefensePoints() {
        return this.permanentDefense;
    }

    public int getPermanentHP() {
        return this.permanentHP * this.permanentHPperPoint;
    }

    public int getPermanentHPPoints() {
        return this.permanentHP;
    }

    public int getPermanentMP() {
        return this.permanentMP * this.permanentMPperPoint;
    }

    public int getPermanentMPPoints() {
        return this.permanentMP;
    }

    public abstract int getPlayerClass();

    public int getPower() {
        return this.power;
    }

    public long getToNextLevel() {
        return this.toNextLevel;
    }

    public int getWeaponPower() {
        return this.weapon;
    }

    public int getDungeonLevel() {
        return this.dungeonLevel;
    }

    // Transformers
    public void levelUp() {
        this.level++;
        this.updateMaxHPandMP();
        this.healFully();
        this.power = this.level;
        this.block = this.level;
        this.toNextLevel = this.getExpToNextLevel(this.level + 1, this.kills);
    }

    public void loadPlayer(final int pAttack, final int pDefense, final int pHP,
            final int pMP, final int newKills, final int newLevel,
            final int chp, final int cmp, final int newWeapon,
            final int newArmor, final int newGold, final int newBank,
            final long newExperience, final int dl, final int bookID) {
        this.permanentAttack = pAttack;
        this.permanentDefense = pDefense;
        this.permanentHP = pHP;
        this.permanentMP = pMP;
        this.kills = newKills;
        this.level = newLevel;
        this.updateMaxHPandMP();
        this.currentHP = chp;
        this.currentMP = cmp;
        this.power = this.level;
        this.block = this.level;
        this.weapon = newWeapon;
        this.armor = newArmor;
        this.gold = newGold;
        this.bank = newBank;
        this.experience = newExperience;
        this.toNextLevel = this.getExpToNextLevel(this.level + 1, this.kills);
        this.dungeonLevel = dl;
        this.spellsKnown = SpellBookManager.getSpellBookByID(bookID);
    }

    private void postPointSpend() {
        this.level = 1;
        this.updateMaxHPandMP();
        this.healFully();
        this.power = this.level;
        this.block = this.level;
        this.weapon = Player.START_WEAPON;
        this.armor = Player.START_ARMOR;
        this.gold = Player.START_GOLD;
        this.bank = 0;
        this.experience = 0L;
        this.toNextLevel = this.getExpToNextLevel(this.level + 1, this.kills);
        this.dungeonLevel = this.level;
    }

    public void removeGold(final int cost) {
        this.gold -= cost;
        if (this.gold < 0) {
            this.gold = 0;
        }
    }

    public void removeGoldFromBank(final int cost) {
        this.bank -= cost;
        if (this.bank < 0) {
            this.bank = 0;
        }
    }

    public void setArmorBlock(final int newArmorBlock) {
        this.armor = newArmorBlock;
    }

    public void setWeaponPower(final int newWeaponPower) {
        this.weapon = newWeaponPower;
    }

    public void setDungeonLevel(final int newDungeonLevel) {
        if (newDungeonLevel >= Player.MIN_DUNGEON_LEVEL) {
            this.dungeonLevel = newDungeonLevel;
        }
    }

    public void incrementDungeonLevel() {
        this.dungeonLevel++;
    }

    public void decrementDungeonLevel() {
        if (this.dungeonLevel > Player.MIN_DUNGEON_LEVEL) {
            this.dungeonLevel--;
        }
    }

    public void spendPointOnAttack() {
        this.kills++;
        this.permanentAttack++;
        this.postPointSpend();
    }

    public void spendPointOnDefense() {
        this.kills++;
        this.permanentDefense++;
        this.postPointSpend();
    }

    public void spendPointOnHP() {
        this.kills++;
        this.permanentHP++;
        this.postPointSpend();
    }

    public void spendPointOnMP() {
        this.kills++;
        this.permanentMP++;
        this.postPointSpend();
    }

    public void updateMaxHPandMP() {
        this.maximumHP = this.level * this.hpPerLevel
                + this.permanentHP * this.permanentHPperPoint;
        this.maximumMP = this.level * this.mpPerLevel
                + this.permanentMP * this.permanentMPperPoint;
    }

    public void healFully() {
        this.currentHP = this.maximumHP;
        this.currentMP = this.maximumMP;
    }

    @Override
    public String getName() {
        return null;
    }
}
