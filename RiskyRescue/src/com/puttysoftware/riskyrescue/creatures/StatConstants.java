/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.creatures;

public class StatConstants {
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
    public static final int STAT_ITEMS_PER_ROUND = 12;
    public static final int STAT_STEALS_PER_ROUND = 13;
    public static final int STAT_LOAD = 14;
    public static final int STAT_MAXIMUM_HP = 15;
    public static final int STAT_MAXIMUM_MP = 16;
    public static final int STAT_ATTACK = 17;
    public static final int STAT_DEFENSE = 18;
    public static final int STAT_SPEED = 19;
    public static final int STAT_HIT = 20;
    public static final int STAT_EVADE = 21;
    public static final int STAT_CAPACITY = 22;
    public static final int STAT_MAGIC_POWER = 23;
    public static final int STAT_MAGIC_DEFENSE = 24;
    public static final int STAT_MAX_LEVEL = 25;
    public static final int MAX_STORED_STATS = 15;
    // Factors
    public static final double FACTOR_STRENGTH_ATTACK = 1.0;
    public static final double FACTOR_POWER_ATTACK = 1.0;
    public static final double FACTOR_BLOCK_DEFENSE = 1.0;
    public static final double FACTOR_BLOCK_MAGIC_DEFENSE = 1.0;
    public static final double FACTOR_ABSORB_DEFENSE = 1.0;
    public static final double FACTOR_AGILITY_ACTIONS_PER_ROUND = 1.0;
    public static final double FACTOR_AGILITY_SPEED = 1.0;
    public static final double FACTOR_LOAD_SPEED = 1.0;
    public static final double FACTOR_VITALITY_HEALTH = 2.0;
    public static final double FACTOR_INTELLIGENCE_MAGIC = 2.0;
    public static final double FACTOR_INTELLIGENCE_MAGIC_POWER = 1.5;
    public static final double FACTOR_TWO_HANDED_BONUS = 0.75;
    public static final double FACTOR_STRENGTH_HIT = 0.75;
    public static final double FACTOR_LUCK_HIT = 0.25;
    public static final double FACTOR_AGILITY_EVADE = 0.75;
    public static final double FACTOR_LUCK_EVADE = 0.25;
    public static final double FACTOR_STRENGTH_CAPACITY = 8.0;
    public static final double FACTOR_AGILITY_CAPACITY = 2.0;
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
    // Other Bases
    public static final int MIN_CAPACITY = 500;
    public static final int HIT_BASE = 8000;
    public static final int EVADE_BASE = 0;
    // Caps
    public static final int HIT_MAX = 10000;
    public static final int EVADE_MAX = 2000;
    public static final int LEVEL_MAX = 25;
}
