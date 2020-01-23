package net.worldwizard.dungeondiver.creatures;

import net.worldwizard.dungeondiver.creatures.spells.SpellBookManager;

public class Fighter extends Player {
    // Fields
    private static final long serialVersionUID = 234923525250L;

    // Constructors
    public Fighter() {
        super();
        this.hpPerLevel = 12;
        this.mpPerLevel = 2;
        this.permanentHPperPoint = 6;
        this.permanentMPperPoint = 1;
        this.classBonusAttack = 2;
        this.classBonusDefense = 0;
        this.updateMaxHPandMP();
        this.healFully();
        this.spellsKnown = SpellBookManager.getSpellBookByID(this
                .getPlayerClass());
    }

    public Fighter(final int pAtk, final int pDef, final int pHP,
            final int pMP, final int k) {
        super(pAtk, pDef, pHP, pMP, k);
        this.hpPerLevel = 12;
        this.mpPerLevel = 2;
        this.permanentHPperPoint = 6;
        this.permanentMPperPoint = 1;
        this.classBonusAttack = 2;
        this.classBonusDefense = 0;
        this.updateMaxHPandMP();
        this.healFully();
        this.spellsKnown = SpellBookManager.getSpellBookByID(this
                .getPlayerClass());
    }

    // Methods
    @Override
    public int getPlayerClass() {
        return PlayerClasses.CLASS_FIGHTER;
    }
}
