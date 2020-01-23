/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.items.combat.predefined;

import com.puttysoftware.tallertower.battle.BattleTarget;
import com.puttysoftware.tallertower.creatures.StatConstants;
import com.puttysoftware.tallertower.effects.Effect;
import com.puttysoftware.tallertower.items.combat.CombatItem;
import com.puttysoftware.tallertower.resourcemanagers.SoundConstants;

public class Rope extends CombatItem {
    public Rope() {
        super("Rope", 50, BattleTarget.ENEMY);
    }

    @Override
    protected void defineFields() {
        this.sound = SoundConstants.SOUND_BIND;
        this.e = new Effect("Roped", 4);
        this.e.setEffect(Effect.EFFECT_MULTIPLY, StatConstants.STAT_AGILITY, 0,
                Effect.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        this.e.setMessage(Effect.MESSAGE_INITIAL,
                "You wind a rope around the enemy!");
        this.e.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The enemy is tied up, and unable to act!");
        this.e.setMessage(Effect.MESSAGE_WEAR_OFF, "The rope falls off!");
    }
}
