/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.creatures.castes.predefined;

import com.puttysoftware.dungeondiver4.battle.BattleTarget;
import com.puttysoftware.dungeondiver4.creatures.StatConstants;
import com.puttysoftware.dungeondiver4.creatures.castes.CasteConstants;
import com.puttysoftware.dungeondiver4.effects.Effect;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.spells.Spell;
import com.puttysoftware.dungeondiver4.spells.SpellBook;

public class CurerSpellBook extends SpellBook {
    // Constructor
    public CurerSpellBook() {
        super(8, false);
        this.setName(CasteConstants.CASTE_NAMES[this.getLegacyID()]);
    }

    @Override
    protected void defineSpells() {
        final Effect spell0Effect = new Effect("Bandage", 1);
        spell0Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                3);
        spell0Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell0Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You bandage up an ally's wounds!");
        spell0Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The ally feels a little better!");
        final Spell spell0 = new Spell(spell0Effect, 1, BattleTarget.ONE_ALLY,
                SoundConstants.SOUND_HEAL);
        this.spells[0] = spell0;
        final Effect spell1Effect = new Effect("Bandage All", 1);
        spell1Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                3);
        spell1Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell1Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You bandage the wounds of all allies!");
        spell1Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "All the allies feel a little better!");
        final Spell spell1 = new Spell(spell1Effect, 2, BattleTarget.ALL_ALLIES,
                SoundConstants.SOUND_HEAL);
        this.spells[1] = spell1;
        final Effect spell2Effect = new Effect("Recover", 1);
        spell2Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                5);
        spell2Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell2Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You magically recover an ally's stamina!");
        spell2Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The ally feels better!");
        final Spell spell2 = new Spell(spell2Effect, 3, BattleTarget.ONE_ALLY,
                SoundConstants.SOUND_HEAL);
        this.spells[2] = spell2;
        final Effect spell3Effect = new Effect("Recover All", 1);
        spell3Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                5);
        spell3Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell3Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You magically recover the stamina of all allies!");
        spell3Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "All the allies feel better!");
        final Spell spell3 = new Spell(spell3Effect, 5, BattleTarget.ALL_ALLIES,
                SoundConstants.SOUND_HEAL);
        this.spells[3] = spell3;
        final Effect spell4Effect = new Effect("Heal", 1);
        spell4Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                8);
        spell4Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell4Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You heal an ally's wounds!");
        spell4Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The ally feels much better!");
        final Spell spell4 = new Spell(spell4Effect, 7, BattleTarget.ONE_ALLY,
                SoundConstants.SOUND_HEAL);
        this.spells[4] = spell4;
        final Effect spell5Effect = new Effect("Heal All", 1);
        spell5Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                8);
        spell5Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell5Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You heal the wounds of all allies!");
        spell5Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "All the allies feel much better!");
        final Spell spell5 = new Spell(spell5Effect, 11,
                BattleTarget.ALL_ALLIES, SoundConstants.SOUND_HEAL);
        this.spells[5] = spell5;
        final Effect spell6Effect = new Effect("Full Heal All", 1);
        spell6Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                1);
        spell6Effect.setScaleFactor(1);
        spell6Effect.setScaleStat(StatConstants.STAT_MAXIMUM_HP);
        spell6Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You fully heal the wounds of all allies!");
        spell6Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "All the allies feel completely refreshed!");
        final Spell spell6 = new Spell(spell6Effect, 13,
                BattleTarget.ALL_ALLIES, SoundConstants.SOUND_HEAL);
        this.spells[6] = spell6;
        final Effect spell7Effect = new Effect("Power Surge", 1);
        spell7Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_MP,
                1);
        spell7Effect.setScaleFactor(0.4);
        spell7Effect.setScaleStat(StatConstants.STAT_MAXIMUM_MP);
        spell7Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You zap an ally with a bolt of energy!");
        spell7Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The ally gains MP!");
        final Spell spell7 = new Spell(spell7Effect, 17, BattleTarget.ONE_ALLY,
                SoundConstants.SOUND_FOCUS);
        this.spells[7] = spell7;
    }

    @Override
    public int getLegacyID() {
        return CasteConstants.CASTE_CURER;
    }
}
