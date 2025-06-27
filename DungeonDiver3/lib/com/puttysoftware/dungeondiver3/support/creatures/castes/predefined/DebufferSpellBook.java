/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.creatures.castes.predefined;

import com.puttysoftware.dungeondiver3.support.creatures.BattleTarget;
import com.puttysoftware.dungeondiver3.support.creatures.StatConstants;
import com.puttysoftware.dungeondiver3.support.creatures.castes.CasteConstants;
import com.puttysoftware.dungeondiver3.support.effects.Effect;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.dungeondiver3.support.spells.Spell;
import com.puttysoftware.dungeondiver3.support.spells.SpellBook;

public class DebufferSpellBook extends SpellBook {
        // Constructor
        public DebufferSpellBook() {
                super(8, false);
                this.setName(CasteConstants.CASTE_NAMES[this.getLegacyID()]);
        }

        @Override
        protected void defineSpells() {
                final Effect spell0Effect = new Effect("Damage Lock", 5);
                spell0Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                                -2);
                spell0Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You perpetrate some locksmithery on your enemy!");
                spell0Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy recoils, taking a little damage!");
                spell0Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The enemy recovers!");
                final Spell spell0 = new Spell(spell0Effect, 1, BattleTarget.ONE_ENEMY,
                                GameSoundConstants.SOUND_UNLOCK);
                this.spells[0] = spell0;
                final Effect spell1Effect = new Effect("Speed Down", 5);
                spell1Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_AGILITY, 0.5, Effect.DEFAULT_SCALE_FACTOR,
                                StatConstants.STAT_NONE);
                spell1Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You take out a whip, and tangle the enemy with it!");
                spell1Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy's speed is reduced!");
                spell1Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The enemy breaks free of the tangle!");
                final Spell spell1 = new Spell(spell1Effect, 2, BattleTarget.ONE_ENEMY,
                                GameSoundConstants.SOUND_PARALYZE);
                this.spells[1] = spell1;
                final Effect spell2Effect = new Effect("Power Lock", 5);
                spell2Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                                -10);
                spell2Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You lock your enemy into a damage trap!");
                spell2Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy recoils, taking damage!");
                spell2Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The trap vanishes!");
                final Spell spell2 = new Spell(spell2Effect, 3, BattleTarget.ONE_ENEMY,
                                GameSoundConstants.SOUND_UNLOCK);
                this.spells[2] = spell2;
                final Effect spell3Effect = new Effect("Attack Lock", 10);
                spell3Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_ATTACK, 0, Effect.DEFAULT_SCALE_FACTOR,
                                Effect.DEFAULT_SCALE_STAT);
                spell3Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You lock your enemy's weapon!");
                spell3Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy cannot attack!");
                spell3Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The lock breaks!");
                final Spell spell3 = new Spell(spell3Effect, 5, BattleTarget.ONE_ENEMY,
                                GameSoundConstants.SOUND_UNLOCK);
                this.spells[3] = spell3;
                final Effect spell4Effect = new Effect("Weapon Steal", 5);
                spell4Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_ATTACK, 0.5, Effect.DEFAULT_SCALE_FACTOR,
                                StatConstants.STAT_NONE);
                spell4Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You steal the enemy's weapon!");
                spell4Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy's attack is significantly reduced!");
                spell4Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The enemy recovers their weapon!");
                final Spell spell4 = new Spell(spell4Effect, 7, BattleTarget.ONE_ENEMY,
                                GameSoundConstants.SOUND_PARALYZE);
                this.spells[4] = spell4;
                final Effect spell5Effect = new Effect("Armor Bind", 5);
                spell5Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_DEFENSE, 0, Effect.DEFAULT_SCALE_FACTOR,
                                StatConstants.STAT_NONE);
                spell5Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You bind the enemy's armor, rendering it useless!");
                spell5Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy is unable to defend!");
                spell5Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The binding breaks!");
                final Spell spell5 = new Spell(spell5Effect, 11, BattleTarget.ONE_ENEMY,
                                GameSoundConstants.SOUND_PARALYZE);
                this.spells[5] = spell5;
                final Effect spell6Effect = new Effect("Attack Lock All", 10);
                spell6Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_ATTACK, 0, Effect.DEFAULT_SCALE_FACTOR,
                                Effect.DEFAULT_SCALE_STAT);
                spell6Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You lock all the enemies' weapons!");
                spell6Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemies cannot attack!");
                spell6Effect.setMessage(Effect.MESSAGE_WEAR_OFF, "The locks break!");
                final Spell spell6 = new Spell(spell6Effect, 13,
                                BattleTarget.ALL_ENEMIES, GameSoundConstants.SOUND_UNLOCK);
                this.spells[6] = spell6;
                final Effect spell7Effect = new Effect("Blindness", 10);
                spell7Effect.setEffect(Effect.EFFECT_MULTIPLY, StatConstants.STAT_EVADE,
                                0, Effect.DEFAULT_SCALE_FACTOR, Effect.DEFAULT_SCALE_STAT);
                spell7Effect.setMessage(Effect.MESSAGE_INITIAL, "You blind an enemy!");
                spell7Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy cannot dodge attacks!");
                spell7Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The enemy's vision returns to normal!");
                final Spell spell7 = new Spell(spell7Effect, 17, BattleTarget.ONE_ENEMY,
                                GameSoundConstants.SOUND_UNLOCK);
                this.spells[7] = spell7;
        }

        @Override
        public int getLegacyID() {
                return CasteConstants.CASTE_DEBUFFER;
        }
}
