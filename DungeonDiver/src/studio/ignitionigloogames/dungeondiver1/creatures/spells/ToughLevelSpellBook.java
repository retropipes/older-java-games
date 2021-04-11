package studio.ignitionigloogames.dungeondiver1.creatures.spells;

import studio.ignitionigloogames.dungeondiver1.creatures.StatConstants;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.Buff;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.DamageBuff;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.HealingBuff;

public class ToughLevelSpellBook extends SpellBook {
    // Constructor
    public ToughLevelSpellBook() {
        super(6);
    }

    @Override
    protected void defineSpells() {
        final DamageBuff spell0Effect = new DamageBuff("Lethally Poisoned", 10,
                10, Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE, 1);
        spell0Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy breathes extremely poisonous breath at you!");
        spell0Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "You lose a LOT of health from being poisoned!");
        spell0Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "You are no longer poisoned!");
        final Spell spell0 = new Spell(spell0Effect, 4, 'P');
        this.spells[0] = spell0;
        final HealingBuff spell1Effect = new HealingBuff("Ultra Recover", 100,
                1, Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE,
                Buff.DEFAULT_DECAY_RATE);
        spell1Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy applies a very large bandage to its wounds!");
        spell1Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy regains a LOT of health!");
        final Spell spell1 = new Spell(spell1Effect, 4, 'E');
        this.spells[1] = spell1;
        final Buff spell2Effect = new Buff("Ultra Weapon Drain", 10);
        spell2Effect.setAffectedStat(StatConstants.STAT_ATTACK);
        spell2Effect.setEffect(Buff.EFFECT_MULTIPLY, 0.5,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell2Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy drains your weapon of half of its power!");
        spell2Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "Your attack is significantly decreased!");
        spell2Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "Your weapon's power has returned!");
        final Spell spell2 = new Spell(spell2Effect, 8, 'P');
        this.spells[2] = spell2;
        final Buff spell3Effect = new Buff("Ultra Armor Drain", 10);
        spell3Effect.setAffectedStat(StatConstants.STAT_DEFENSE);
        spell3Effect.setEffect(Buff.EFFECT_MULTIPLY, 0.5,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell3Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy drains your armor of half of its power!");
        spell3Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "Your defense is significantly decreased!");
        spell3Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "Your armor's power has returned!");
        final Spell spell3 = new Spell(spell3Effect, 8, 'P');
        this.spells[3] = spell3;
        final Buff spell4Effect = new Buff("Ultra Weapon Charge", 10);
        spell4Effect.setAffectedStat(StatConstants.STAT_ATTACK);
        spell4Effect.setEffect(Buff.EFFECT_MULTIPLY, 2,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell4Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy charges its weapon with godlike power!");
        spell4Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy's attack is drastically increased!");
        spell4Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The enemy's weapon returns to normal!");
        final Spell spell4 = new Spell(spell4Effect, 16, 'E');
        this.spells[4] = spell4;
        final Buff spell5Effect = new Buff("Ultra Armor Charge", 10);
        spell5Effect.setAffectedStat(StatConstants.STAT_DEFENSE);
        spell5Effect.setEffect(Buff.EFFECT_MULTIPLY, 2,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell5Effect.setMessage(Buff.MESSAGE_INITIAL,
                "The enemy charges its armor with godlike power!");
        spell5Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy's defense is drastically increased!");
        spell5Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The enemy's armor returns to normal!");
        final Spell spell5 = new Spell(spell5Effect, 16, 'E');
        this.spells[5] = spell5;
    }

    @Override
    public int getID() {
        return 4;
    }
}
