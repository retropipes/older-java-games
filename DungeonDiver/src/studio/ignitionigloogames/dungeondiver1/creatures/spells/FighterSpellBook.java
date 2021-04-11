package studio.ignitionigloogames.dungeondiver1.creatures.spells;

import studio.ignitionigloogames.dungeondiver1.creatures.StatConstants;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.Buff;
import studio.ignitionigloogames.dungeondiver1.creatures.buffs.DamageBuff;

public class FighterSpellBook extends SpellBook {
    // Constructor
    public FighterSpellBook() {
        super(6);
    }

    @Override
    protected void defineSpells() {
        final DamageBuff spell0Effect = new DamageBuff("Poison", 1, 1, 1.0,
                StatConstants.STAT_LEVEL, 1.0, 1.0, StatConstants.STAT_LEVEL);
        spell0Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You spray poison on your weapon!");
        spell0Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy loses some health from being poisoned!");
        spell0Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The enemy is no longer poisoned!");
        final Spell spell0 = new Spell(spell0Effect, 1, 'E');
        this.spells[0] = spell0;
        final Buff spell1Effect = new Buff("Turtle Shell", 5);
        spell1Effect.setAffectedStat(StatConstants.STAT_DEFENSE);
        spell1Effect.setEffect(Buff.EFFECT_MULTIPLY, 1.5,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell1Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You conjure a turtle shell around yourself!");
        spell1Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "Your defense is increased!");
        spell1Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The shell has dissipated!");
        final Spell spell1 = new Spell(spell1Effect, 1, 'P');
        this.spells[1] = spell1;
        final Buff spell2Effect = new Buff("Charged Up", 5);
        spell2Effect.setAffectedStat(StatConstants.STAT_ATTACK);
        spell2Effect.setEffect(Buff.EFFECT_MULTIPLY, 3,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell2Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You charge your weapon!");
        spell2Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "Your attack is increased!");
        spell2Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The charge has dissipated!");
        final Spell spell2 = new Spell(spell2Effect, 2, 'P');
        this.spells[2] = spell2;
        final DamageBuff spell3Effect = new DamageBuff("Ghostly Axe", 20, 5,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE, 4.0);
        spell3Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You summon a ghostly axe!");
        spell3Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The axe attacks the enemy, hurting it somewhat!");
        spell3Effect.setMessage(Buff.MESSAGE_WEAR_OFF, "The axe disappears!");
        final Spell spell3 = new Spell(spell3Effect, 4, 'E');
        this.spells[3] = spell3;
        final Buff spell4Effect = new Buff("Armor Bind", 5);
        spell4Effect.setAffectedStat(StatConstants.STAT_DEFENSE);
        spell4Effect.setEffect(Buff.EFFECT_MULTIPLY, 0,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell4Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You bind the enemy's armor, rendering it useless!");
        spell4Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "The enemy is unable to defend!");
        spell4Effect.setMessage(Buff.MESSAGE_WEAR_OFF, "The binding breaks!");
        final Spell spell4 = new Spell(spell4Effect, 7, 'E');
        this.spells[4] = spell4;
        final Buff spell5Effect = new Buff("Supercharged", 10);
        spell5Effect.setAffectedStat(StatConstants.STAT_ATTACK);
        spell5Effect.setEffect(Buff.EFFECT_MULTIPLY, 10,
                Buff.DEFAULT_SCALE_FACTOR, StatConstants.STAT_NONE);
        spell5Effect.setMessage(Buff.MESSAGE_INITIAL,
                "You supercharge your weapon!");
        spell5Effect.setMessage(Buff.MESSAGE_SUBSEQUENT,
                "Your attack is GREATLY increased!");
        spell5Effect.setMessage(Buff.MESSAGE_WEAR_OFF,
                "The supercharge has dissipated!");
        final Spell spell5 = new Spell(spell5Effect, 10, 'P');
        this.spells[5] = spell5;
    }

    @Override
    public int getID() {
        return 1;
    }
}
