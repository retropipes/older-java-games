package com.puttysoftware.dungeondiver4.battle;

import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.creatures.PrestigeConstants;

public class BattlePrestige {
    private BattlePrestige() {
        // Do nothing
    }

    // Prestige Stuff
    public static void dealtDamage(AbstractCreature c, int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_GIVEN, value);
    }

    public static void tookDamage(AbstractCreature c, int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_TAKEN, value);
    }

    public static void hitEnemy(AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_GIVEN, 1);
    }

    public static void hitByEnemy(AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_TAKEN, 1);
    }

    public static void missedEnemy(AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MISSED_ATTACKS, 1);
    }

    public static void killedEnemy(AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MONSTERS_KILLED, 1);
    }

    public static void dodgedAttack(AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_ATTACKS_DODGED, 1);
    }

    public static void castSpell(AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_SPELLS_CAST, 1);
    }

    public static void killedInBattle(AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_KILLED, 1);
    }

    public static void ranAway(AbstractCreature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_RAN_AWAY, 1);
    }
}
