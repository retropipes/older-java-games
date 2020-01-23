package net.worldwizard.dungeondiver.creatures.spells;

import net.worldwizard.dungeondiver.creatures.StatConstants;
import net.worldwizard.dungeondiver.creatures.buffs.Buff;
import net.worldwizard.dungeondiver.creatures.buffs.DamageBuff;
import net.worldwizard.dungeondiver.creatures.buffs.DrainBuff;

public class ThiefSpellBook extends SpellBook {
    // Constructor
    public ThiefSpellBook() {
        super(6);
    }

    @Override
    protected void defineSpells() {
        final DrainBuff spell0Effect = new DrainBuff("Mana Drain", 4, 1, 0.5,
                StatConstants.STAT_LEVEL, Buff.DEFAULT_DECAY_RATE);
        spell0Effect
                .setMessage(Buff.MESSAGE_INITIAL,
                        "You spin your weapon, forming a vortex that engulfs the enemy!");
        spell0Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy loses some mana!");
        final Spell spell0 = new Spell(spell0Effect, 1, 'E');
        this.spells[0] = spell0;
        final Buff spell1Effect = new Buff("Sneak Attack", 5);
        spell1Effect.setAffectedStat(StatConstants.STAT_DEFENSE);
        spell1Effect.setEffect(Buff.EFFECT_MULTIPLY, 0.5,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell1Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You sneak up behind the enemy!");
        spell1Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy's defenses are weaker here!");
        spell1Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The enemy spots you, and turns around!");
        final Spell spell1 = new Spell(spell1Effect, 2, 'E');
        this.spells[1] = spell1;
        final Buff spell2Effect = new Buff("Weapon Steal", 5);
        spell2Effect.setAffectedStat(StatConstants.STAT_ATTACK);
        spell2Effect.setEffect(Buff.EFFECT_MULTIPLY, 0.5,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell2Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You steal the enemy's weapon!");
        spell2Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy cannot attack as well!");
        spell2Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The enemy finds their weapon, and reclaims it!");
        final Spell spell2 = new Spell(spell2Effect, 4, 'E');
        this.spells[2] = spell2;
        final Buff spell3Effect = new Buff("Glowing Armor", 5);
        spell3Effect.setAffectedStat(StatConstants.STAT_DEFENSE);
        spell3Effect.setEffect(Buff.EFFECT_MULTIPLY, 3,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell3Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You inject mystical energy into your armor, making it glow!");
        spell3Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "Your defense is increased!");
        spell3Effect.setMessage(Buff.MESSAGE_WEAR_OFF, "The glow fades!");
        final Spell spell3 = new Spell(spell3Effect, 7, 'P');
        this.spells[3] = spell3;
        final Buff spell4Effect = new Buff("Hardened Armor", 5);
        spell4Effect.setAffectedStat(StatConstants.STAT_ATTACK);
        spell4Effect.setEffect(Buff.EFFECT_MULTIPLY, 10,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell4Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You harden your armor, using your magic!");
        spell4Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "Your defense is GREATLY increased!");
        spell4Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "Your armor returns to normal!");
        final Spell spell4 = new Spell(spell4Effect, 10, 'P');
        this.spells[4] = spell4;
        final DamageBuff spell5Effect = new DamageBuff("Weakness Strike", 1, 1,
                0.8, StatConstants.STAT_CURRENT_HP, Buff.DEFAULT_DECAY_RATE);
        spell5Effect
                .setMessage(Buff.MESSAGE_INITIAL,
                        "You wait for the right moment, then suddenly attack the enemy's weak point!");
        spell5Effect
                .setMessage(Buff.MESSAGE_SUBSEQUENT,
                        "The enemy, caught off-guard by the attack, loses a LOT of health!");
        final Spell spell5 = new Spell(spell5Effect, 20, 'E');
        this.spells[5] = spell5;
    }

    @Override
    public int getID() {
        return 3;
    }
}
