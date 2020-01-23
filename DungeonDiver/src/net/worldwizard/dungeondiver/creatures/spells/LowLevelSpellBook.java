package net.worldwizard.dungeondiver.creatures.spells;

import net.worldwizard.dungeondiver.creatures.StatConstants;
import net.worldwizard.dungeondiver.creatures.buffs.Buff;
import net.worldwizard.dungeondiver.creatures.buffs.DamageBuff;
import net.worldwizard.dungeondiver.creatures.buffs.HealingBuff;

public class LowLevelSpellBook extends SpellBook {
    // Constructor
    public LowLevelSpellBook() {
        super(6);
    }

    @Override
    protected void defineSpells() {
        final DamageBuff spell0Effect = new DamageBuff("Slightly Poisoned", 1,
                3, Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE,
                Buff.DEFAULT_DECAY_RATE);
        spell0Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy breathes slightly poisonous breath at you!");
        spell0Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "You lose a little health from being poisoned!");
        spell0Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "You are no longer poisoned!");
        final Spell spell0 = new Spell(spell0Effect, 1, 'P');
        this.spells[0] = spell0;
        final HealingBuff spell1Effect = new HealingBuff("Minor Recover", 3, 1,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE,
                Buff.DEFAULT_DECAY_RATE);
        spell1Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy applies a small bandage to its wounds!");
        spell1Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy regains a little health!");
        final Spell spell1 = new Spell(spell1Effect, 1, 'E');
        this.spells[1] = spell1;
        final Buff spell2Effect = new Buff("Minor Weapon Drain", 3);
        spell2Effect.setAffectedStat(StatConstants.STAT_ATTACK);
        spell2Effect.setEffect(Buff.EFFECT_MULTIPLY, 0.9,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell2Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy drains your weapon of a little of its power!");
        spell2Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "Your attack is slightly decreased!");
        spell2Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "Your weapon's power has returned!");
        final Spell spell2 = new Spell(spell2Effect, 2, 'P');
        this.spells[2] = spell2;
        final Buff spell3Effect = new Buff("Minor Armor Drain", 3);
        spell3Effect.setAffectedStat(StatConstants.STAT_DEFENSE);
        spell3Effect.setEffect(Buff.EFFECT_MULTIPLY, 0.9,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell3Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy drains your armor of a little of its power!");
        spell3Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "Your defense is slightly decreased!");
        spell3Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "Your armor's power has returned!");
        final Spell spell3 = new Spell(spell3Effect, 2, 'P');
        this.spells[3] = spell3;
        final Buff spell4Effect = new Buff("Minor Weapon Charge", 3);
        spell4Effect.setAffectedStat(StatConstants.STAT_ATTACK);
        spell4Effect.setEffect(Buff.EFFECT_MULTIPLY, 1.1,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell4Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy charges its weapon with power!");
        spell4Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy's attack is slightly increased!");
        spell4Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The enemy's weapon returns to normal!");
        final Spell spell4 = new Spell(spell4Effect, 4, 'E');
        this.spells[4] = spell4;
        final Buff spell5Effect = new Buff("Minor Armor Charge", 3);
        spell5Effect.setAffectedStat(StatConstants.STAT_DEFENSE);
        spell5Effect.setEffect(Buff.EFFECT_MULTIPLY, 0.9,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell5Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy charges its armor with power!");
        spell5Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy's defense is slightly increased!");
        spell5Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The enemy's armor returns to normal!");
        final Spell spell5 = new Spell(spell5Effect, 4, 'E');
        this.spells[5] = spell5;
    }

    @Override
    public int getID() {
        return 1;
    }
}
