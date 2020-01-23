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

public class Bomb extends CombatItem {
    public Bomb() {
        super("Bomb", 30, BattleTarget.ONE_ENEMY);
    }

    @Override
    protected void defineFields() {
        this.sound = GameSoundConstants.SOUND_EXPLODE;
        this.e = new Effect("Bomb", 1);
        this.e.setAffectedStat(StatConstants.STAT_CURRENT_HP);
        this.e.setEffect(-5, 1);
        this.e.setMessage(Effect.MESSAGE_INITIAL,
                "You throw a bomb at the enemy!");
        this.e.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The bomb goes BOOM, inflicting a little damage!");
    }
}
