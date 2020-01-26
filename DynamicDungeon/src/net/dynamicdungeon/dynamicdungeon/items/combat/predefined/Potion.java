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

public class Potion extends CombatItem {
    public Potion() {
        super("Potion", 250, BattleTarget.SELF);
    }

    @Override
    protected void defineFields() {
        this.sound = SoundConstants.SOUND_HEAL;
        this.e = new Effect("Potion", 1);
        this.e.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP, 5);
        this.e.setScaleStat(StatConstants.STAT_LEVEL);
        this.e.setScaleFactor(1.25);
        this.e.setMessage(Effect.MESSAGE_INITIAL,
                "You drink a healing potion!");
        this.e.setMessage(Effect.MESSAGE_SUBSEQUENT, "You feel better!");
    }
}
