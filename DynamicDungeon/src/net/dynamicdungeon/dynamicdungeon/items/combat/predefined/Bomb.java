/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.items.combat.predefined;

import net.dynamicdungeon.dynamicdungeon.battle.BattleTarget;
import net.dynamicdungeon.dynamicdungeon.creatures.StatConstants;
import net.dynamicdungeon.dynamicdungeon.effects.Effect;
import net.dynamicdungeon.dynamicdungeon.items.combat.CombatItem;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;

public class Bomb extends CombatItem {
    public Bomb() {
        super("Bomb", 30, BattleTarget.ENEMY);
    }

    @Override
    protected void defineFields() {
        this.sound = SoundConstants.SOUND_EXPLODE;
        this.e = new Effect("Bomb", 1);
        this.e.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP, -5);
        this.e.setScaleStat(StatConstants.STAT_LEVEL);
        this.e.setScaleFactor(0.75);
        this.e.setMessage(Effect.MESSAGE_INITIAL,
                "You throw a bomb at the enemy!");
        this.e.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The bomb goes BOOM, inflicting a little damage!");
    }
}
