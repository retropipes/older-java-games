package net.worldwizard.dungeondiver.creatures;

import net.worldwizard.dungeondiver.creatures.spells.SpellBookManager;

public class Thief extends Player {
    // Fields
    private static final long serialVersionUID = 234923525250L;

    // Constructors
    public Thief() {
        super();
        this.hpPerLevel = 10;
        this.mpPerLevel = 4;
        this.permanentHPperPoint = 5;
        this.permanentMPperPoint = 2;
        this.classBonusAttack = 0;
        this.classBonusDefense = 2;
        this.updateMaxHPandMP();
        this.healFully();
        this.spellsKnown = SpellBookManager.getSpellBookByID(this
                .getPlayerClass());
    }

    public Thief(final int pAtk, final int pDef, final int pHP, final int pMP,
            final int k) {
        super(pAtk, pDef, pHP, pMP, k);
        this.hpPerLevel = 10;
        this.mpPerLevel = 4;
        this.permanentHPperPoint = 5;
        this.permanentMPperPoint = 2;
        this.classBonusAttack = 0;
        this.classBonusDefense = 2;
        this.updateMaxHPandMP();
        this.healFully();
        this.spellsKnown = SpellBookManager.getSpellBookByID(this
                .getPlayerClass());
    }

    // Methods
    @Override
    public int getPlayerClass() {
        return PlayerClasses.CLASS_THIEF;
    }
}
