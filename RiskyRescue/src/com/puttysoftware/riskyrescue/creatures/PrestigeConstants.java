/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.creatures;

public class PrestigeConstants {
    // Prestige
    public static final int PRESTIGE_DAMAGE_GIVEN = 0;
    public static final int PRESTIGE_DAMAGE_TAKEN = 1;
    public static final int PRESTIGE_HITS_GIVEN = 2;
    public static final int PRESTIGE_HITS_TAKEN = 3;
    public static final int PRESTIGE_ATTACKS_DODGED = 4;
    public static final int PRESTIGE_MISSED_ATTACKS = 5;
    public static final int PRESTIGE_MONSTERS_KILLED = 6;
    public static final int PRESTIGE_SPELLS_CAST = 7;
    public static final int PRESTIGE_TIMES_KILLED = 8;
    public static final int PRESTIGE_TIMES_RAN_AWAY = 9;
    public static final int MAX_PRESTIGE = 10;
    public static final String[] PRESTIGE_NAMES = new String[] { "Damage Given",
            "Damage Taken", "Hits Given", "Hits Taken", "Attacks Dodged",
            "Missed Attacks", "Monsters Killed", "Spells Cast", "Times Killed",
            "Times Ran Away" };
}
