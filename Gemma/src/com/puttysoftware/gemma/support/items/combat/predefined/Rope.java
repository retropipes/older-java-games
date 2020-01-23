/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.items.combat.predefined;

import com.puttysoftware.gemma.support.creatures.BattleTarget;
import com.puttysoftware.gemma.support.creatures.StatConstants;
import com.puttysoftware.gemma.support.effects.Effect;
import com.puttysoftware.gemma.support.items.combat.CombatItem;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;

public class Rope extends CombatItem {
    public Rope() {
        super("Rope", 50, BattleTarget.ONE_ENEMY);
    }

    @Override
    protected void defineFields() {
        this.sound = GameSoundConstants.SOUND_SCAN;
        this.e = new Effect("Roped", 2);
        this.e.setAffectedStat(StatConstants.STAT_AGILITY);
        this.e.setEffect(-1, 1);
        this.e.setScaleStat(StatConstants.STAT_AGILITY);
        this.e.setScaleFactor(1, 1);
        this.e.setMessage(Effect.MESSAGE_INITIAL,
                "You wind a rope around the enemy!");
        this.e.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The enemy is tied up, and unable to act!");
        this.e.setMessage(Effect.MESSAGE_WEAR_OFF, "The rope falls off!");
    }
}
