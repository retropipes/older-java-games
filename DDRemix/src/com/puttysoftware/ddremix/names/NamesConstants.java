/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.names;

import java.util.Arrays;

public class NamesConstants {
        // Section Names
        public static final String SECTION_STATS = "Statistics";
        public static final String SECTION_EQUIP_SLOT = "EquipmentSlots";
        public static final String SECTION_EQUIP_WEAPONS = "EquipmentWeapons";
        public static final String SECTION_FAITHS = "Faith";
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
        private static final String SLOT_MAIN_HAND = "MainHand";
        private static final String SLOT_BODY = "Body";
        public static final String[] SECTION_ARRAY_EQUIP_SLOTS = new String[] {
                        NamesConstants.SLOT_MAIN_HAND, NamesConstants.SLOT_BODY };
        // Equipment Weapons Section Keys
        private static final String WEAPON_SPEAR = "Spear";
        private static final String WEAPON_AXE = "Axe";
        private static final String WEAPON_BOW = "Bow";
        private static final String WEAPON_WHIP = "Whip";
        public static final String[] SECTION_ARRAY_WEAPONS = new String[] {
                        NamesConstants.WEAPON_SPEAR, NamesConstants.WEAPON_AXE,
                        NamesConstants.WEAPON_BOW, NamesConstants.WEAPON_WHIP };
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
        // Names Length
        public static final int NAMES_LENGTH = NamesConstants.SECTION_ARRAY_STATS.length
                        + NamesConstants.SECTION_ARRAY_EQUIP_SLOTS.length
                        + NamesConstants.SECTION_ARRAY_WEAPONS.length
                        + NamesConstants.SECTION_ARRAY_FAITHS.length;
        // Names Version
        public static final int NAMES_VERSION = 1;
        // Editor Section Names
        private static final String EDITOR_SECTION_STATS = "Statistic";
        private static final String EDITOR_SECTION_EQUIP_SLOT = "Equipment Slot";
        private static final String EDITOR_SECTION_WEAPONS = "Weapons";
        private static final String EDITOR_SECTION_FAITHS = "Faith";
        // Editor Section Array
        static final String[] EDITOR_SECTION_ARRAY = new String[NamesConstants.NAMES_LENGTH];
        private static String[] TEMP_SECTION_STATS = new String[NamesConstants.SECTION_ARRAY_STATS.length];
        private static String[] TEMP_SECTION_EQUIP_SLOTS = new String[NamesConstants.SECTION_ARRAY_EQUIP_SLOTS.length];
        private static String[] TEMP_SECTION_WEAPONS = new String[NamesConstants.SECTION_ARRAY_WEAPONS.length];
        private static String[] TEMP_SECTION_FAITHS = new String[NamesConstants.SECTION_ARRAY_FAITHS.length];
        static {
                Arrays.fill(NamesConstants.TEMP_SECTION_STATS,
                                NamesConstants.EDITOR_SECTION_STATS);
                Arrays.fill(NamesConstants.TEMP_SECTION_EQUIP_SLOTS,
                                NamesConstants.EDITOR_SECTION_EQUIP_SLOT);
                Arrays.fill(NamesConstants.TEMP_SECTION_WEAPONS,
                                NamesConstants.EDITOR_SECTION_WEAPONS);
                Arrays.fill(NamesConstants.TEMP_SECTION_FAITHS,
                                NamesConstants.EDITOR_SECTION_FAITHS);
                int counter = 0;
                System.arraycopy(NamesConstants.TEMP_SECTION_STATS, 0,
                                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                                NamesConstants.TEMP_SECTION_STATS.length);
                counter += NamesConstants.TEMP_SECTION_STATS.length;
                System.arraycopy(NamesConstants.TEMP_SECTION_EQUIP_SLOTS, 0,
                                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                                NamesConstants.TEMP_SECTION_EQUIP_SLOTS.length);
                counter += NamesConstants.TEMP_SECTION_EQUIP_SLOTS.length;
                System.arraycopy(NamesConstants.TEMP_SECTION_WEAPONS, 0,
                                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                                NamesConstants.TEMP_SECTION_WEAPONS.length);
                counter += NamesConstants.TEMP_SECTION_WEAPONS.length;
                System.arraycopy(NamesConstants.TEMP_SECTION_FAITHS, 0,
                                NamesConstants.EDITOR_SECTION_ARRAY, counter,
                                NamesConstants.TEMP_SECTION_FAITHS.length);
        }

        // Private constructor
        private NamesConstants() {
                // Do nothing
        }
}
