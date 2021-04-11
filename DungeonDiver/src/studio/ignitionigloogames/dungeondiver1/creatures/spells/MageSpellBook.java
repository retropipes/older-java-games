package studio.ignitionigloogames.dungeondiver1.creatures.spells;

import studio.ignitionigloogames.dungeondiver1.creatures.StatConstants;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.Buff;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.DamageBuff;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.HealingBuff;

public class MageSpellBook extends SpellBook {
    // Constructor
    public MageSpellBook() {
        super(6);
    }

    @Override
    protected void defineSpells() {
        final DamageBuff spell0Effect = new DamageBuff("Fireball", 10, 1, 0.25,
                StatConstants.STAT_LEVEL, Buff.DEFAULT_DECAY_RATE);
        spell0Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You conjure a fireball, then throw it at the enemy!");
        spell0Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy loses a little health from being burned!");
        final Spell spell0 = new Spell(spell0Effect, 1, 'E');
        this.spells[0] = spell0;
        final HealingBuff spell1Effect = new HealingBuff("Minor Heal", 15, 1,
                0.25, StatConstants.STAT_LEVEL, Buff.DEFAULT_DECAY_RATE);
        spell1Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You conjure a small bandage, and apply it to your wounds!");
        spell1Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "You gain some health!");
        final Spell spell1 = new Spell(spell1Effect, 2, 'P');
        this.spells[1] = spell1;
        final DamageBuff spell2Effect = new DamageBuff("Ice Shard", 10, 1, 0.4,
                StatConstants.STAT_LEVEL, Buff.DEFAULT_DECAY_RATE);
        spell2Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You conjure a shard of ice, then throw it at the enemy!");
        spell2Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy loses some health from being frozen!");
        final Spell spell2 = new Spell(spell2Effect, 4, 'E');
        this.spells[2] = spell2;
        final Buff spell3Effect = new Buff("Weapon Bind", 5);
        spell3Effect.setAffectedStat(StatConstants.STAT_ATTACK);
        spell3Effect.setEffect(Buff.EFFECT_MULTIPLY, 0,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell3Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You bind the enemy's weapon, rendering it useless!");
        spell3Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy is unable to attack!");
        spell3Effect.setMessage(Buff.MESSAGE_WEAR_OFF, "The binding breaks!");
        final Spell spell3 = new Spell(spell3Effect, 7, 'E');
        this.spells[3] = spell3;
        final HealingBuff spell4Effect = new HealingBuff("Major Heal", 10, 1,
                0.75, StatConstants.STAT_LEVEL, Buff.DEFAULT_DECAY_RATE);
        spell4Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You summon a large bandage, and apply it to your wounds!");
        spell4Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "You gain a LOT of health!");
        final Spell spell4 = new Spell(spell4Effect, 15, 'P');
        this.spells[4] = spell4;
        final DamageBuff spell5Effect = new DamageBuff("Lightning Bolt", 1, 1,
                0.8, StatConstants.STAT_MAXIMUM_HP, Buff.DEFAULT_DECAY_RATE);
        spell5Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You summon a bolt of lightning, then throw it at the enemy!");
        spell5Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy loses a LOT of health from being shocked!");
        final Spell spell5 = new Spell(spell5Effect, 30, 'E');
        this.spells[5] = spell5;
    }

    @Override
    public int getID() {
        return 2;
    }
}
