/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.items.combat.predefined;

import com.puttysoftware.fantastlex.battle.BattleTarget;
import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.effects.Effect;
import com.puttysoftware.fantastlex.items.combat.CombatItem;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;

public class Bomb extends CombatItem {
    public Bomb() {
        super("Bomb", 30, BattleTarget.ONE_ENEMY);
    }

    @Override
    protected void defineFields() {
        this.sound = SoundConstants.SOUND_EXPLODE;
        this.e = new Effect("Bomb", 1);
        this.e.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP, -5);
        this.e.setMessage(Effect.MESSAGE_INITIAL,
                "You throw a bomb at the enemy!");
        this.e.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The bomb goes BOOM, inflicting a little damage!");
    }
}
