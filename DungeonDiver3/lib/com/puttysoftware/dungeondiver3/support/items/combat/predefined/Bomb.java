/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.items.combat.predefined;

import com.puttysoftware.dungeondiver3.support.creatures.BattleTarget;
import com.puttysoftware.dungeondiver3.support.creatures.StatConstants;
import com.puttysoftware.dungeondiver3.support.effects.Effect;
import com.puttysoftware.dungeondiver3.support.items.combat.CombatItem;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;

public class Bomb extends CombatItem {
    public Bomb() {
        super("Bomb", 30, BattleTarget.ONE_ENEMY);
    }

    @Override
    protected void defineFields() {
        this.sound = GameSoundConstants.SOUND_EXPLODE;
        this.e = new Effect("Bomb", 1);
        this.e.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP, -5);
        this.e.setMessage(Effect.MESSAGE_INITIAL,
                "You throw a bomb at the enemy!");
        this.e.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The bomb goes BOOM, inflicting a little damage!");
    }
}
