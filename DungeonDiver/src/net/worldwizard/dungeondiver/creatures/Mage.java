package net.worldwizard.dungeondiver.creatures;

import net.worldwizard.dungeondiver.creatures.spells.SpellBookManager;

public class Mage extends Player {
    // Fields
    private static final long serialVersionUID = 234923525250L;

    // Constructors
    public Mage() {
        super();
        this.hpPerLevel = 8;
        this.mpPerLevel = 6;
        this.permanentHPperPoint = 4;
        this.permanentMPperPoint = 3;
        this.classBonusAttack = 1;
        this.classBonusDefense = 1;
        this.updateMaxHPandMP();
        this.healFully();
        this.spellsKnown = SpellBookManager.getSpellBookByID(this
                .getPlayerClass());
    }

    public Mage(final int pAtk, final int pDef, final int pHP, final int pMP,
            final int k) {
        super(pAtk, pDef, pHP, pMP, k);
        this.hpPerLevel = 8;
        this.mpPerLevel = 6;
        this.permanentHPperPoint = 4;
        this.permanentMPperPoint = 3;
        this.classBonusAttack = 1;
        this.classBonusDefense = 1;
        this.updateMaxHPandMP();
        this.healFully();
        this.spellsKnown = SpellBookManager.getSpellBookByID(this
                .getPlayerClass());
    }

    // Methods
    @Override
    public int getPlayerClass() {
        return PlayerClasses.CLASS_MAGE;
    }
}
