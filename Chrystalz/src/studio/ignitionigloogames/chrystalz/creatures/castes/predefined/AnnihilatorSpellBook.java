/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.creatures.castes.predefined;

import studio.ignitionigloogames.chrystalz.battle.BattleTarget;
import studio.ignitionigloogames.chrystalz.creatures.StatConstants;
import studio.ignitionigloogames.chrystalz.creatures.castes.CasteConstants;
import studio.ignitionigloogames.chrystalz.effects.Effect;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.spells.Spell;
import studio.ignitionigloogames.chrystalz.spells.SpellBook;

public class AnnihilatorSpellBook extends SpellBook {
        // Constructor
        public AnnihilatorSpellBook() {
                super(8, false);
                this.setName(CasteConstants.CASTE_NAMES[this.getLegacyID()]);
        }

        @Override
        protected void defineSpells() {
                final Effect spell0Effect = new Effect("Icicle", 1);
                spell0Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                                -1);
                spell0Effect.setScaleStat(StatConstants.STAT_LEVEL);
                spell0Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure an icicle, and throw it at an enemy!");
                spell0Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy recoils, and is hurt!");
                final Spell spell0 = new Spell(spell0Effect, 1, BattleTarget.ENEMY,
                                SoundConstants.COLD_SPELL);
                this.spells[0] = spell0;
                final Effect spell1Effect = new Effect("Freeze", 1);
                spell1Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                                -1.25);
                spell1Effect.setScaleStat(StatConstants.STAT_LEVEL);
                spell1Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure a sheet of ice, and throw it at the enemy!");
                spell1Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy recoils, and is hurt!");
                final Spell spell1 = new Spell(spell1Effect, 2, BattleTarget.ENEMY,
                                SoundConstants.FREEZE_SPELL);
                this.spells[1] = spell1;
                final Effect spell2Effect = new Effect("Scalding Steam", 1);
                spell2Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                                -1.5);
                spell2Effect.setScaleStat(StatConstants.STAT_LEVEL);
                spell2Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure a cloud of scalding steam!");
                spell2Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy feels the heat!");
                final Spell spell2 = new Spell(spell2Effect, 3, BattleTarget.ENEMY,
                                SoundConstants.STEAM_SPELL);
                this.spells[2] = spell2;
                final Effect spell3Effect = new Effect("Liquid Lava", 1);
                spell3Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                                -1.75);
                spell3Effect.setScaleStat(StatConstants.STAT_LEVEL);
                spell3Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure a river of lava!");
                spell3Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy melts a little!");
                final Spell spell3 = new Spell(spell3Effect, 5, BattleTarget.ENEMY,
                                SoundConstants.MELT_SPELL);
                this.spells[3] = spell3;
                final Effect spell4Effect = new Effect("Ignite", 1);
                spell4Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                                -2);
                spell4Effect.setScaleStat(StatConstants.STAT_LEVEL);
                spell4Effect.setMessage(Effect.MESSAGE_INITIAL, "You ignite the air!");
                spell4Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy is engulfed!");
                final Spell spell4 = new Spell(spell4Effect, 7, BattleTarget.ENEMY,
                                SoundConstants.FIREBALL_SPELL);
                this.spells[4] = spell4;
                final Effect spell5Effect = new Effect("Blast Wave", 1);
                spell5Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                                -2.5);
                spell5Effect.setScaleStat(StatConstants.STAT_LEVEL);
                spell5Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure an unstable glowing orb, which suddenly explodes!");
                spell5Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy recoils from the shockwave!");
                final Spell spell5 = new Spell(spell5Effect, 11, BattleTarget.ENEMY,
                                SoundConstants.EXPLODE_SPELL);
                this.spells[5] = spell5;
                final Effect spell6Effect = new Effect("Air Tear", 1);
                spell6Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                                -3);
                spell6Effect.setScaleStat(StatConstants.STAT_LEVEL);
                spell6Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You focus all your might into a blast powerful enough to rip the air apart!");
                spell6Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy is devastated!");
                final Spell spell6 = new Spell(spell6Effect, 13, BattleTarget.ENEMY,
                                SoundConstants.WEAKNESS_SPELL);
                this.spells[6] = spell6;
                final Effect spell7Effect = new Effect("Power Drain", 1);
                spell7Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_MP,
                                -1);
                spell7Effect.setScaleFactor(0.4);
                spell7Effect.setScaleStat(StatConstants.STAT_MAXIMUM_MP);
                spell7Effect.setMessage(Effect.MESSAGE_INITIAL,
                                "You conjure a draining vortex around the enemy!");
                spell7Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                                "The enemy loses some magic!");
                final Spell spell7 = new Spell(spell7Effect, 17, BattleTarget.ENEMY,
                                SoundConstants.ZAP_SPELL);
                this.spells[7] = spell7;
        }

        @Override
        public int getLegacyID() {
                return CasteConstants.CASTE_ANNIHILATOR;
        }
}
