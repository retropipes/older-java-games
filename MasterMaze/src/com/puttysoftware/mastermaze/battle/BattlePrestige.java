package com.puttysoftware.mastermaze.battle;

import com.puttysoftware.mastermaze.creatures.Creature;
import com.puttysoftware.mastermaze.creatures.PrestigeConstants;

public class BattlePrestige {
    private BattlePrestige() {
        // Do nothing
    }

    // Prestige Stuff
    public static void dealtDamage(final Creature c, final int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_GIVEN, value);
    }

    public static void tookDamage(final Creature c, final int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_TAKEN, value);
    }

    public static void hitEnemy(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_GIVEN, 1);
    }

    public static void hitByEnemy(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_TAKEN, 1);
    }

    public static void missedEnemy(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MISSED_ATTACKS, 1);
    }

    public static void killedEnemy(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MONSTERS_KILLED, 1);
    }

    public static void dodgedAttack(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_ATTACKS_DODGED, 1);
    }

    public static void castSpell(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_SPELLS_CAST, 1);
    }

    public static void killedInBattle(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_KILLED, 1);
    }

    public static void ranAway(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_RAN_AWAY, 1);
    }
}
