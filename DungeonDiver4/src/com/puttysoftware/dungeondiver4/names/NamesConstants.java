/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.names;

import java.util.Arrays;

public class NamesConstants {
    // Section Names
    public static final String SECTION_STATS = "Statistics";
    public static final String SECTION_EQUIP_SLOT = "EquipmentSlots";
    public static final String SECTION_EQUIP_ARMOR = "EquipmentArmor";
    public static final String SECTION_EQUIP_WEAPONS_1H = "EquipmentWeapons1H";
    public static final String SECTION_EQUIP_WEAPONS_2H = "EquipmentWeapons2H";
    public static final String SECTION_FAITHS = "Faith";
    public static final String SECTION_FAITH_POWERS_PREFIX = "FaithPower";
    // Statistics Section Keys
    private static final String STAT_STRENGTH = "Strength";
    private static final String STAT_BLOCK = "Block";
    private static final String STAT_AGILITY = "Agility";
    private static final String STAT_VITALITY = "Vitality";
    private static final String STAT_INTELLIGENCE = "Intelligence";
    private static final String STAT_LUCK = "Luck";
    private static final String STAT_CURRENT_HP = "CurrentHP";
    private static final String STAT_CURRENT_MP = "CurrentMP";
    private static final String STAT_GOLD = "Gold";
    private static final String STAT_LEVEL = "Level";
    private static final String STAT_ATTACKS_PER_ROUND = "AttacksPerRound";
    private static final String STAT_SPELLS_PER_ROUND = "SpellsPerRound";
    private static final String STAT_LOAD = "Load";
    private static final String STAT_MAXIMUM_HP = "MaximumHP";
    private static final String STAT_MAXIMUM_MP = "MaximumMP";
    private static final String STAT_ATTACK = "Attack";
    private static final String STAT_DEFENSE = "Defense";
    private static final String STAT_SPEED = "Speed";
    private static final String STAT_HIT = "Hit";
    private static final String STAT_EVADE = "Evade";
    private static final String STAT_CAPACITY = "Capacity";
    public static final String[] SECTION_ARRAY_STATS = new String[] {
            NamesConstants.STAT_STRENGTH, NamesConstants.STAT_BLOCK,
            NamesConstants.STAT_AGILITY, NamesConstants.STAT_VITALITY,
            NamesConstants.STAT_INTELLIGENCE, NamesConstants.STAT_LUCK,
            NamesConstants.STAT_CURRENT_HP, NamesConstants.STAT_CURRENT_MP,
            NamesConstants.STAT_GOLD, NamesConstants.STAT_LEVEL,
            NamesConstants.STAT_ATTACKS_PER_ROUND,
            NamesConstants.STAT_SPELLS_PER_ROUND, NamesConstants.STAT_LOAD,
            NamesConstants.STAT_MAXIMUM_HP, NamesConstants.STAT_MAXIMUM_MP,
            NamesConstants.STAT_ATTACK, NamesConstants.STAT_DEFENSE,
            NamesConstants.STAT_SPEED, NamesConstants.STAT_HIT,
            NamesConstants.STAT_EVADE, NamesConstants.STAT_CAPACITY };
    // Equipment Slots Section Keys
    private static final String SLOT_HELMET = "Helmet";
    private static final String SLOT_NECKLACE = "Necklace";
    private static final String SLOT_MAIN_HAND = "MainHand";
    private static final String SLOT_OFF_HAND = "OffHand";
    private static final String SLOT_ROBE = "Robe";
    private static final String SLOT_CAPE = "Cape";
    private static final String SLOT_SHIRT = "Shirt";
    private static final String SLOT_BRACERS = "Bracers";
    private static final String SLOT_GLOVES = "Gloves";
    private static final String SLOT_RING = "Ring";
    private static final String SLOT_BELT = "Belt";
    private static final String SLOT_PANTS = "Pants";
    private static final String SLOT_BOOTS = "Boots";
    public static final String[] SECTION_ARRAY_EQUIP_SLOTS = new String[] {
            NamesConstants.SLOT_HELMET, NamesConstants.SLOT_NECKLACE,
            NamesConstants.SLOT_MAIN_HAND, NamesConstants.SLOT_OFF_HAND,
            NamesConstants.SLOT_ROBE, NamesConstants.SLOT_CAPE,
            NamesConstants.SLOT_SHIRT, NamesConstants.SLOT_BRACERS,
            NamesConstants.SLOT_GLOVES, NamesConstants.SLOT_RING,
            NamesConstants.SLOT_BELT, NamesConstants.SLOT_PANTS,
            NamesConstants.SLOT_BOOTS };
    // Equipment Armor Section Keys
    public static final String ARMOR_SHIELD = "Shield";
    private static final String[] SECTION_ARRAY_EQUIP_ARMOR = new String[] {
            NamesConstants.ARMOR_SHIELD };
    // Equipment Weapons 1H Section Keys
    private static final String WEAPON_1H_GLOVES = "Gloves";
    private static final String WEAPON_1H_SWORD = "Sword";
    private static final String WEAPON_1H_BOOMERANG = "Boomerang";
    private static final String WEAPON_1H_JAVELIN = "Javelin";
    public static final String[] SECTION_ARRAY_WEAPONS_1H = new String[] {
            NamesConstants.WEAPON_1H_GLOVES, NamesConstants.WEAPON_1H_SWORD,
            NamesConstants.WEAPON_1H_BOOMERANG,
            NamesConstants.WEAPON_1H_JAVELIN };
    // Equipment Weapons 2H Section Keys
    private static final String WEAPON_2H_SPEAR = "Spear";
    private static final String WEAPON_2H_AXE = "Axe";
    private static final String WEAPON_2H_BOW = "Bow";
    private static final String WEAPON_2H_WHIP = "Whip";
    public static final String[] SECTION_ARRAY_WEAPONS_2H = new String[] {
            NamesConstants.WEAPON_2H_SPEAR, NamesConstants.WEAPON_2H_AXE,
            NamesConstants.WEAPON_2H_BOW, NamesConstants.WEAPON_2H_WHIP };
    // Faith Section Keys
    private static final String FAITH_NONE = "None";
    private static final String FAITH_HEAT = "Heat";
    private static final String FAITH_COLD = "Cold";
    private static final String FAITH_ROCK = "Rock";
    private static final String FAITH_GUST = "Gust";
    private static final String FAITH_BEAM = "Beam";
    private static final String FAITH_DEAD = "Dead";
    private static final String FAITH_BOLT = "Bolt";
    private static final String FAITH_BOOM = "Boom";
    public static final String[] SECTION_ARRAY_FAITHS = new String[] {
            NamesConstants.FAITH_NONE, NamesConstants.FAITH_HEAT,
            NamesConstants.FAITH_COLD, NamesConstants.FAITH_ROCK,
            NamesConstants.FAITH_GUST, NamesConstants.FAITH_BEAM,
            NamesConstants.FAITH_DEAD, NamesConstants.FAITH_BOLT,
            NamesConstants.FAITH_BOOM };
    // Faith Power Section Keys
    private static final String FAITH_POWER_1 = "1";
    private static final String FAITH_POWER_2 = "2";
    private static final String FAITH_POWER_3 = "3";
    private static final String FAITH_POWER_4 = "4";
    private static final String FAITH_POWER_5 = "5";
    private static final String FAITH_POWER_6 = "6";
    private static final String FAITH_POWER_7 = "7";
    private static final String FAITH_POWER_8 = "8";
    private static final String FAITH_POWER_9 = "9";
    public static final String[] SECTION_ARRAY_FAITH_POWERS = new String[] {
            NamesConstants.FAITH_POWER_1, NamesConstants.FAITH_POWER_2,
            NamesConstants.FAITH_POWER_3, NamesConstants.FAITH_POWER_4,
            NamesConstants.FAITH_POWER_5, NamesConstants.FAITH_POWER_6,
            NamesConstants.FAITH_POWER_7, NamesConstants.FAITH_POWER_8,
            NamesConstants.FAITH_POWER_9 };
    // Names Length
    public static final int NAMES_LENGTH = NamesConstants.SECTION_ARRAY_STATS.length
            + NamesConstants.SECTION_ARRAY_EQUIP_SLOTS.length
            + NamesConstants.SECTION_ARRAY_EQUIP_ARMOR.length
            + NamesConstants.SECTION_ARRAY_WEAPONS_1H.length
            + NamesConstants.SECTION_ARRAY_WEAPONS_2H.length
            + NamesConstants.SECTION_ARRAY_FAITHS.length
            + NamesConstants.SECTION_ARRAY_FAITH_POWERS.length * 9;
    // Names Version
    public static final int NAMES_VERSION = 2;
    // Editor Section Names
    private static final String EDITOR_SECTION_STATS = "Statistic";
    private static final String EDITOR_SECTION_EQUIP_SLOT = "Equipment Slot";
    private static final String EDITOR_SECTION_EQUIP_ARMOR = "Armor";
    private static final String EDITOR_SECTION_WEAPONS_1H = "1-Handed Weapon";
    private static final String EDITOR_SECTION_WEAPONS_2H = "2-Handed Weapon";
    private static final String EDITOR_SECTION_FAITHS = "Faith";
    private static final String EDITOR_SECTION_FAITH_POWERS_NONE = "Faith Power of None";
    private static final String EDITOR_SECTION_FAITH_POWERS_HEAT = "Faith Power of Heat";
    private static final String EDITOR_SECTION_FAITH_POWERS_COLD = "Faith Power of Cold";
    private static final String EDITOR_SECTION_FAITH_POWERS_ROCK = "Faith Power of Rock";
    private static final String EDITOR_SECTION_FAITH_POWERS_GUST = "Faith Power of Gust";
    private static final String EDITOR_SECTION_FAITH_POWERS_BEAM = "Faith Power of Beam";
    private static final String EDITOR_SECTION_FAITH_POWERS_DEAD = "Faith Power of Dead";
    private static final String EDITOR_SECTION_FAITH_POWERS_BOLT = "Faith Power of Bolt";
    private static final String EDITOR_SECTION_FAITH_POWERS_BOOM = "Faith Power of Boom";
    // Editor Section Array
    public static String[] EDITOR_SECTION_ARRAY = new String[NamesConstants.NAMES_LENGTH];
    private static String[] TEMP_SECTION_STATS = new String[NamesConstants.SECTION_ARRAY_STATS.length];
    private static String[] TEMP_SECTION_EQUIP_SLOTS = new String[NamesConstants.SECTION_ARRAY_EQUIP_SLOTS.length];
    private static String[] TEMP_SECTION_EQUIP_ARMOR = new String[NamesConstants.SECTION_ARRAY_EQUIP_ARMOR.length];
    private static String[] TEMP_SECTION_WEAPONS_1H = new String[NamesConstants.SECTION_ARRAY_WEAPONS_1H.length];
    private static String[] TEMP_SECTION_WEAPONS_2H = new String[NamesConstants.SECTION_ARRAY_WEAPONS_2H.length];
    private static String[] TEMP_SECTION_FAITHS = new String[NamesConstants.SECTION_ARRAY_FAITHS.length];
    private static String[] TEMP_SECTION_FAITH_POWERS_NONE = new String[NamesConstants.SECTION_ARRAY_FAITH_POWERS.length];
    private static String[] TEMP_SECTION_FAITH_POWERS_HEAT = new String[NamesConstants.SECTION_ARRAY_FAITH_POWERS.length];
    private static String[] TEMP_SECTION_FAITH_POWERS_COLD = new String[NamesConstants.SECTION_ARRAY_FAITH_POWERS.length];
    private static String[] TEMP_SECTION_FAITH_POWERS_ROCK = new String[NamesConstants.SECTION_ARRAY_FAITH_POWERS.length];
    private static String[] TEMP_SECTION_FAITH_POWERS_GUST = new String[NamesConstants.SECTION_ARRAY_FAITH_POWERS.length];
    private static String[] TEMP_SECTION_FAITH_POWERS_BEAM = new String[NamesConstants.SECTION_ARRAY_FAITH_POWERS.length];
    private static String[] TEMP_SECTION_FAITH_POWERS_DEAD = new String[NamesConstants.SECTION_ARRAY_FAITH_POWERS.length];
    private static String[] TEMP_SECTION_FAITH_POWERS_BOLT = new String[NamesConstants.SECTION_ARRAY_FAITH_POWERS.length];
    private static String[] TEMP_SECTION_FAITH_POWERS_BOOM = new String[NamesConstants.SECTION_ARRAY_FAITH_POWERS.length];
    static {
        Arrays.fill(NamesConstants.TEMP_SECTION_STATS,
                NamesConstants.EDITOR_SECTION_STATS);
        Arrays.fill(NamesConstants.TEMP_SECTION_EQUIP_SLOTS,
                NamesConstants.EDITOR_SECTION_EQUIP_SLOT);
        Arrays.fill(NamesConstants.TEMP_SECTION_EQUIP_ARMOR,
                NamesConstants.EDITOR_SECTION_EQUIP_ARMOR);
        Arrays.fill(NamesConstants.TEMP_SECTION_WEAPONS_1H,
                NamesConstants.EDITOR_SECTION_WEAPONS_1H);
        Arrays.fill(NamesConstants.TEMP_SECTION_WEAPONS_2H,
                NamesConstants.EDITOR_SECTION_WEAPONS_2H);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITHS,
                NamesConstants.EDITOR_SECTION_FAITHS);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITH_POWERS_NONE,
                NamesConstants.EDITOR_SECTION_FAITH_POWERS_NONE);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITH_POWERS_HEAT,
                NamesConstants.EDITOR_SECTION_FAITH_POWERS_HEAT);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITH_POWERS_COLD,
                NamesConstants.EDITOR_SECTION_FAITH_POWERS_COLD);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITH_POWERS_ROCK,
                NamesConstants.EDITOR_SECTION_FAITH_POWERS_ROCK);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITH_POWERS_GUST,
                NamesConstants.EDITOR_SECTION_FAITH_POWERS_GUST);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITH_POWERS_BEAM,
                NamesConstants.EDITOR_SECTION_FAITH_POWERS_BEAM);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITH_POWERS_DEAD,
                NamesConstants.EDITOR_SECTION_FAITH_POWERS_DEAD);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITH_POWERS_BOLT,
                NamesConstants.EDITOR_SECTION_FAITH_POWERS_BOLT);
        Arrays.fill(NamesConstants.TEMP_SECTION_FAITH_POWERS_BOOM,
                NamesConstants.EDITOR_SECTION_FAITH_POWERS_BOOM);
        int counter = 0;
        System.arraycopy(NamesConstants.TEMP_SECTION_STATS, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_STATS.length);
        counter += NamesConstants.TEMP_SECTION_STATS.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_EQUIP_SLOTS, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_EQUIP_SLOTS.length);
        counter += NamesConstants.TEMP_SECTION_EQUIP_SLOTS.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_EQUIP_ARMOR, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_EQUIP_ARMOR.length);
        counter += NamesConstants.TEMP_SECTION_EQUIP_ARMOR.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_WEAPONS_1H, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_WEAPONS_1H.length);
        counter += NamesConstants.TEMP_SECTION_WEAPONS_1H.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_WEAPONS_2H, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_WEAPONS_2H.length);
        counter += NamesConstants.TEMP_SECTION_WEAPONS_2H.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITHS, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITHS.length);
        counter += NamesConstants.TEMP_SECTION_FAITHS.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITH_POWERS_NONE, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITH_POWERS_NONE.length);
        counter += NamesConstants.TEMP_SECTION_FAITH_POWERS_NONE.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITH_POWERS_HEAT, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITH_POWERS_HEAT.length);
        counter += NamesConstants.TEMP_SECTION_FAITH_POWERS_HEAT.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITH_POWERS_COLD, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITH_POWERS_COLD.length);
        counter += NamesConstants.TEMP_SECTION_FAITH_POWERS_COLD.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITH_POWERS_ROCK, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITH_POWERS_ROCK.length);
        counter += NamesConstants.TEMP_SECTION_FAITH_POWERS_ROCK.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITH_POWERS_GUST, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITH_POWERS_GUST.length);
        counter += NamesConstants.TEMP_SECTION_FAITH_POWERS_GUST.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITH_POWERS_BEAM, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITH_POWERS_BEAM.length);
        counter += NamesConstants.TEMP_SECTION_FAITH_POWERS_BEAM.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITH_POWERS_DEAD, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITH_POWERS_DEAD.length);
        counter += NamesConstants.TEMP_SECTION_FAITH_POWERS_DEAD.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITH_POWERS_BOLT, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITH_POWERS_BOLT.length);
        counter += NamesConstants.TEMP_SECTION_FAITH_POWERS_BOLT.length;
        System.arraycopy(NamesConstants.TEMP_SECTION_FAITH_POWERS_BOOM, 0,
                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                NamesConstants.TEMP_SECTION_FAITH_POWERS_BOOM.length);
        counter += NamesConstants.TEMP_SECTION_FAITH_POWERS_BOOM.length;
    }

    // Private constructor
    private NamesConstants() {
        // Do nothing
    }
}
