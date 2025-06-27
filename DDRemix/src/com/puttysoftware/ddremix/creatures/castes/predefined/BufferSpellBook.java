/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.creatures.castes.predefined;

import com.puttysoftware.ddremix.battle.BattleTarget;
import com.puttysoftware.ddremix.creatures.StatConstants;
import com.puttysoftware.ddremix.creatures.castes.CasteConstants;
import com.puttysoftware.ddremix.effects.Effect;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.spells.Spell;
import com.puttysoftware.ddremix.spells.SpellBook;

public class BufferSpellBook extends SpellBook {
        // Constructor
        public BufferSpellBook() {
                super(8, false);
                this.setName(CasteConstants.CASTE_NAMES[this.getLegacyID()]);
        }

        @Override
        protected void defineSpells() {
                final Effect spell0Effect = new Effect("Brute Force", 5);
                spell0Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_STRENGTH, 2, Effect.DEFAULT_SCALE_FACTOR,
                                StatConstants.STAT_NONE);
                spell0Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure up a potion of strength, and drink it!");
                spell0Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "Your strength is increased!");
                spell0Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The potion wears off!");
                final Spell spell0 = new Spell(spell0Effect, 1, BattleTarget.SELF,
                                SoundConstants.SOUND_CHANGE);
                this.spells[0] = spell0;
                final Effect spell1Effect = new Effect("Hide of the Rhino", 5);
                spell1Effect.setEffect(Effect.EFFECT_MULTIPLY, StatConstants.STAT_BLOCK,
                                2, Effect.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
                spell1Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure up a potion of shielding, and drink it!");
                spell1Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "Your block is increased!");
                spell1Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The potion wears off!");
                final Spell spell1 = new Spell(spell1Effect, 2, BattleTarget.SELF,
                                SoundConstants.SOUND_CHANGE);
                this.spells[1] = spell1;
                final Effect spell2Effect = new Effect("Stamina of the Elephant", 5);
                spell2Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_VITALITY, 2, Effect.DEFAULT_SCALE_FACTOR,
                                StatConstants.STAT_NONE);
                spell2Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure up a potion of toughness, and drink it!");
                spell2Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "Your vitality is increased!");
                spell2Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The potion wears off!");
                final Spell spell2 = new Spell(spell2Effect, 3, BattleTarget.SELF,
                                SoundConstants.SOUND_CHANGE);
                this.spells[2] = spell2;
                final Effect spell3Effect = new Effect("Wisdom of the Tortoise", 5);
                spell3Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_INTELLIGENCE, 2, Effect.DEFAULT_SCALE_FACTOR,
                                StatConstants.STAT_NONE);
                spell3Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure up a potion of smarts, and drink it!");
                spell3Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "Your intelligence is increased!");
                spell3Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The potion wears off!");
                final Spell spell3 = new Spell(spell3Effect, 5, BattleTarget.SELF,
                                SoundConstants.SOUND_CHANGE);
                this.spells[3] = spell3;
                final Effect spell4Effect = new Effect("Luck of the Leprechaun", 5);
                spell4Effect.setEffect(Effect.EFFECT_MULTIPLY, StatConstants.STAT_LUCK,
                                2, Effect.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
                spell4Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure up a potion of luck, and drink it!");
                spell4Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "Your luck is increased!");
                spell4Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The potion wears off!");
                final Spell spell4 = new Spell(spell4Effect, 7, BattleTarget.SELF,
                                SoundConstants.SOUND_CHANGE);
                this.spells[4] = spell4;
                final Effect spell5Effect = new Effect("Twin Mystics", 5);
                spell5Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_SPELLS_PER_ROUND, 2,
                                Effect.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
                spell5Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure up a potion of mysticality, and drink it!");
                spell5Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "Your spells per round are increased!");
                spell5Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The potion wears off!");
                final Spell spell5 = new Spell(spell5Effect, 11, BattleTarget.SELF,
                                SoundConstants.SOUND_CHANGE);
                this.spells[5] = spell5;
                final Effect spell6Effect = new Effect("Twin Hits", 5);
                spell6Effect.setEffect(Effect.EFFECT_MULTIPLY,
                                StatConstants.STAT_ATTACKS_PER_ROUND, 2,
                                Effect.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
                spell6Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure up a potion of smackdown, and drink it!");
                spell6Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "Your attacks per round are increased!");
                spell6Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The potion wears off!");
                final Spell spell6 = new Spell(spell6Effect, 13, BattleTarget.SELF,
                                SoundConstants.SOUND_CHANGE);
                this.spells[6] = spell6;
                final Effect spell7Effect = new Effect("Accuracy of the Eagle", 5);
                spell7Effect.setEffect(Effect.EFFECT_MULTIPLY, StatConstants.STAT_HIT,
                                2, Effect.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
                spell7Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure up a potion of accuracy, and drink it!");
                spell7Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "Your accuracy is increased!");
                spell7Effect.setMessage(Effect.MESSAGE_WEAR_OFF,
                                "The potion wears off!");
                final Spell spell7 = new Spell(spell7Effect, 17, BattleTarget.SELF,
                                SoundConstants.SOUND_CHANGE);
                this.spells[7] = spell7;
        }

        @Override
        public int getLegacyID() {
                return CasteConstants.CASTE_BUFFER;
        }
}
