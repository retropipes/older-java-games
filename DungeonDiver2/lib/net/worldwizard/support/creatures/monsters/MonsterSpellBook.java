package net.worldwizard.support.creatures.monsters;

import net.worldwizard.support.creatures.BattleTarget;
import net.worldwizard.support.creatures.StatConstants;
import net.worldwizard.support.effects.DamageEffect;
import net.worldwizard.support.effects.Effect;
import net.worldwizard.support.effects.HealingEffect;
import net.worldwizard.support.map.generic.GameSoundConstants;
import net.worldwizard.support.spells.Spell;
import net.worldwizard.support.spells.SpellBook;

public class MonsterSpellBook extends SpellBook {
    // Constructor
    public MonsterSpellBook() {
        super(7, false, true);
        this.setName("Monster Spell Book");
    }

    @Override
    protected void defineSpells() {
        final DamageEffect spell0Effect = new DamageEffect("Poisoned", 1, 3,
                Effect.DEFAULT_SCALE_FACTOR, Effect.DEFAULT_SCALE_STAT,
                Effect.DEFAULT_DECAY_RATE);
        spell0Effect.setMessage(Effect.MESSAGE_INITIAL,
                "The enemy breathes poison at you!");
        spell0Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "Your health decreases from the poison!");
        spell0Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The poison fades!");
        final Spell spell0 = new Spell(spell0Effect, 1, BattleTarget.ONE_ENEMY,
                GameSoundConstants.SOUND_BREATHE);
        this.spells[0] = spell0;
        final HealingEffect spell1Effect = new HealingEffect("Recover", 1, 1,
                0.25, StatConstants.STAT_MAXIMUM_HP, Effect.DEFAULT_DECAY_RATE);
        spell1Effect.setMessage(Effect.MESSAGE_INITIAL,
                "The enemy bandages its wounds!");
        spell1Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The enemy recovers some HP!");
        final Spell spell1 = new Spell(spell1Effect, 2, BattleTarget.SELF,
                GameSoundConstants.SOUND_HEAL);
        this.spells[1] = spell1;
        final Effect spell2Effect = new Effect("Power Sap", 3);
        spell2Effect.setEffect(Effect.EFFECT_MULTIPLY,
                StatConstants.STAT_ATTACK, 0.75);
        spell2Effect.setMessage(Effect.MESSAGE_INITIAL,
                "The enemy casts a curse upon your weapon!");
        spell2Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "Your attack is reduced!");
        spell2Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The curse fades!");
        final Spell spell2 = new Spell(spell2Effect, 4, BattleTarget.ONE_ENEMY,
                GameSoundConstants.SOUND_CHACLUNK);
        this.spells[2] = spell2;
        final Effect spell3Effect = new Effect("Armor Zap", 3);
        spell3Effect.setEffect(Effect.EFFECT_MULTIPLY,
                StatConstants.STAT_DEFENSE, 0.75);
        spell3Effect.setMessage(Effect.MESSAGE_INITIAL,
                "The enemy casts a curse upon your armor!");
        spell3Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "Your defense is reduced!");
        spell3Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The curse fades!");
        final Spell spell3 = new Spell(spell3Effect, 8, BattleTarget.ONE_ENEMY,
                GameSoundConstants.SOUND_ZAP);
        this.spells[3] = spell3;
        final Effect spell4Effect = new Effect("Power Surge", 3);
        spell4Effect.setEffect(Effect.EFFECT_MULTIPLY,
                StatConstants.STAT_ATTACK, 1.25);
        spell4Effect.setMessage(Effect.MESSAGE_INITIAL,
                "The enemy casts a blessing upon its weapon!");
        spell4Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The enemy's attack is increased!");
        spell4Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The blessing fades!");
        final Spell spell4 = new Spell(spell4Effect, 16, BattleTarget.SELF,
                GameSoundConstants.SOUND_CLUB);
        this.spells[4] = spell4;
        final Effect spell5Effect = new Effect("Armor Tap", 3);
        spell5Effect.setEffect(Effect.EFFECT_MULTIPLY,
                StatConstants.STAT_DEFENSE, 1.25);
        spell5Effect.setMessage(Effect.MESSAGE_INITIAL,
                "The enemy casts a blessing upon its armor!");
        spell5Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The enemy's defense is increased!");
        spell5Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The blessing fades!");
        final Spell spell5 = new Spell(spell5Effect, 32, BattleTarget.SELF,
                GameSoundConstants.SOUND_BOLT);
        this.spells[5] = spell5;
        final Effect spell6Effect = new Effect("Attack Lock", 3);
        spell6Effect.setEffect(Effect.EFFECT_MULTIPLY,
                StatConstants.STAT_ATTACKS_PER_ROUND, 0);
        spell6Effect.setMessage(Effect.MESSAGE_INITIAL,
                "The enemy locks your weapon!");
        spell6Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "You are unable to attack!");
        spell6Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The lock breaks!");
        final Spell spell6 = new Spell(spell6Effect, 64, BattleTarget.SELF,
                GameSoundConstants.SOUND_DOOR_SLAM);
        this.spells[6] = spell6;
        // Set all spells to known
        this.learnAllSpells();
    }
}
