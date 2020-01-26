package com.puttysoftware.dungeondiver4.battle;

import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.creatures.PrestigeConstants;

public class BattlePrestige {
    private BattlePrestige() {
        // Do nothing
    }

    // Prestige Stuff
    public static void dealtDamage(final AbstractCreature c, final int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_GIVEN, value);
    }

    public static void tookDamage(final AbstractCreature c, final int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_TAKEN, value);
    }

    public static void hitEnemy(final AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_GIVEN, 1);
    }

    public static void hitByEnemy(final AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_TAKEN, 1);
    }

    public static void missedEnemy(final AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MISSED_ATTACKS, 1);
    }

    public static void killedEnemy(final AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MONSTERS_KILLED, 1);
    }

    public static void dodgedAttack(final AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_ATTACKS_DODGED, 1);
    }

    public static void castSpell(final AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_SPELLS_CAST, 1);
    }

    public static void killedInBattle(final AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_KILLED, 1);
    }

    public static void ranAway(final AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_RAN_AWAY, 1);
    }
}
