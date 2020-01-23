package net.worldwizard.support.creatures;

public interface StatConstants {
    // Statistics
    public static final int STAT_NONE = -1;
    public static final int STAT_STRENGTH = 0;
    public static final int STAT_BLOCK = 1;
    public static final int STAT_AGILITY = 2;
    public static final int STAT_VITALITY = 3;
    public static final int STAT_INTELLIGENCE = 4;
    public static final int STAT_LUCK = 5;
    public static final int STAT_CURRENT_HP = 6;
    public static final int STAT_CURRENT_MP = 7;
    public static final int STAT_GOLD = 8;
    public static final int STAT_LEVEL = 9;
    public static final int STAT_ATTACKS_PER_ROUND = 10;
    public static final int STAT_SPELLS_PER_ROUND = 11;
    public static final int STAT_MAXIMUM_HP = 12;
    public static final int STAT_MAXIMUM_MP = 13;
    public static final int STAT_ATTACK = 14;
    public static final int STAT_DEFENSE = 15;
    public static final int STAT_FUMBLE_CHANCE = 16;
    public static final int STAT_SPEED = 17;
    public static final int STAT_HIT = 18;
    public static final int STAT_EVADE = 19;
    public static final int MAX_STORED_STATS = 12;
    public static final int MAX_STATS = 20;
    // Factors
    public static final double FACTOR_STRENGTH_ATTACK = 10.0;
    public static final double FACTOR_POWER_ATTACK = 10.0;
    public static final double FACTOR_BLOCK_DEFENSE = 10.0;
    public static final double FACTOR_ABSORB_DEFENSE = 1.0;
    public static final double FACTOR_AGILITY_ACTIONS_PER_ROUND = 1.0;
    public static final double FACTOR_AGILITY_SPEED = 1.0;
    public static final double FACTOR_WEIGHT_SPEED = 1.0;
    public static final double FACTOR_VITALITY_HEALTH = 2.0;
    public static final double FACTOR_INTELLIGENCE_MAGIC = 2.0;
    public static final double FACTOR_LUCK_FUMBLE = 0.5;
    public static final double FACTOR_AGILITY_FUMBLE = 0.5;
    public static final double FACTOR_TWO_HANDED_BONUS = 3.0;
    public static final double FACTOR_STRENGTH_HIT = 0.75;
    public static final double FACTOR_LUCK_HIT = 0.25;
    public static final double FACTOR_AGILITY_EVADE = 0.75;
    public static final double FACTOR_LUCK_EVADE = 0.25;
    // Base Gains Per Level
    public static final int GAIN_STRENGTH = 5;
    public static final int GAIN_BLOCK = 5;
    public static final int GAIN_AGILITY = 5;
    public static final int GAIN_VITALITY = 5;
    public static final int GAIN_INTELLIGENCE = 5;
    public static final int GAIN_LUCK = 5;
    // Base Chances
    public static final int CHANCE_STEAL = 50;
    public static final int CHANCE_DRAIN = 50;
}
