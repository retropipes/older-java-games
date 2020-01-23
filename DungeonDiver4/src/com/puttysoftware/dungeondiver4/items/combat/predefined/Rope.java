/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.items.combat.predefined;

import com.puttysoftware.dungeondiver4.battle.BattleTarget;
import com.puttysoftware.dungeondiver4.creatures.StatConstants;
import com.puttysoftware.dungeondiver4.effects.Effect;
import com.puttysoftware.dungeondiver4.items.combat.CombatItem;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;

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
