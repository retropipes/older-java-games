/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.creatures.castes.predefined;

import com.puttysoftware.mazerunner2.battle.BattleTarget;
import com.puttysoftware.mazerunner2.creatures.StatConstants;
import com.puttysoftware.mazerunner2.creatures.castes.CasteConstants;
import com.puttysoftware.mazerunner2.effects.Effect;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.spells.Spell;
import com.puttysoftware.mazerunner2.spells.SpellBook;

public class AnnihilatorSpellBook extends SpellBook {
    // Constructor
    public AnnihilatorSpellBook() {
        super(8, false);
        this.setName(CasteConstants.CASTE_NAMES[this.getLegacyID()]);
    }

    @Override
    protected void defineSpells() {
        final Effect spell0Effect = new Effect("Force Ball", 1);
        spell0Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                -2);
        spell0Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell0Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You conjure a force ball, and throw it at an enemy!");
        spell0Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The enemy recoils, and is hurt!");
        final Spell spell0 = new Spell(spell0Effect, 1, BattleTarget.ONE_ENEMY,
                SoundConstants.SOUND_COLD);
        this.spells[0] = spell0;
        final Effect spell1Effect = new Effect("Force Ball All", 1);
        spell1Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                -1);
        spell1Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell1Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You conjure a cloud of force balls, and throw them at all the enemies!");
        spell1Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "All the enemies recoil, and are hurt!");
        final Spell spell1 = new Spell(spell1Effect, 2,
                BattleTarget.ALL_ENEMIES, SoundConstants.SOUND_COLD);
        this.spells[1] = spell1;
        final Effect spell2Effect = new Effect("Cutter Cloud", 1);
        spell2Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                -4);
        spell2Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell2Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You conjure a cloud of cutters!");
        spell2Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The enemy gets cut!");
        final Spell spell2 = new Spell(spell2Effect, 3, BattleTarget.ONE_ENEMY,
                SoundConstants.SOUND_MISSED);
        this.spells[2] = spell2;
        final Effect spell3Effect = new Effect("Cutter Cloud All", 1);
        spell3Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                -2);
        spell3Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell3Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You conjure a group of cutter clouds!");
        spell3Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "All the enemies get cut!");
        final Spell spell3 = new Spell(spell3Effect, 5,
                BattleTarget.ALL_ENEMIES, SoundConstants.SOUND_MISSED);
        this.spells[3] = spell3;
        final Effect spell4Effect = new Effect("Vortex", 1);
        spell4Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                -8);
        spell4Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell4Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You conjure a vortex!");
        spell4Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The enemy is engulfed!");
        final Spell spell4 = new Spell(spell4Effect, 7, BattleTarget.ONE_ENEMY,
                SoundConstants.SOUND_FIREBALL);
        this.spells[4] = spell4;
        final Effect spell5Effect = new Effect("Vortex All", 1);
        spell5Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                -4);
        spell5Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell5Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You conjure a cloud of vortices!");
        spell5Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "All the enemies are engulfed!");
        final Spell spell5 = new Spell(spell5Effect, 11,
                BattleTarget.ALL_ENEMIES, SoundConstants.SOUND_FIREBALL);
        this.spells[5] = spell5;
        final Effect spell6Effect = new Effect("Air Tear", 1);
        spell6Effect.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP,
                -8);
        spell6Effect.setScaleStat(StatConstants.STAT_LEVEL);
        spell6Effect.setMessage(Effect.MESSAGE_INITIAL,
                "You focus all your might into a blast powerful enough to rip the air apart!");
        spell6Effect.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "All the enemies are devastated!");
        final Spell spell6 = new Spell(spell6Effect, 13,
                BattleTarget.ALL_ENEMIES, SoundConstants.SOUND_WEAKNESS);
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
        final Spell spell7 = new Spell(spell7Effect, 17, BattleTarget.ONE_ENEMY,
                SoundConstants.SOUND_DRAIN);
        this.spells[7] = spell7;
    }

    @Override
    public int getLegacyID() {
        return CasteConstants.CASTE_ANNIHILATOR;
    }
}
