/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.items.combat.predefined;

import com.puttysoftware.mastermaze.battle.BattleTarget;
import com.puttysoftware.mastermaze.creatures.StatConstants;
import com.puttysoftware.mastermaze.effects.Effect;
import com.puttysoftware.mastermaze.items.combat.CombatItem;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;

public class Rope extends CombatItem {
    public Rope() {
        super("Rope", 50, BattleTarget.ONE_ENEMY);
    }

    @Override
    protected void defineFields() {
        this.sound = SoundConstants.SOUND_BIND;
        this.e = new Effect("Roped", 2);
        this.e.setEffect(Effect.EFFECT_MULTIPLY, StatConstants.STAT_AGILITY, 0,
                Effect.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        this.e.setMessage(Effect.MESSAGE_INITIAL,
                "You wind a rope around the enemy!");
        this.e.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The enemy is tied up, and unable to act!");
        this.e.setMessage(Effect.MESSAGE_WEAR_OFF, "The rope falls off!");
    }
}
