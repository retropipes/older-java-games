/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.generic.GenericBoots;

public class HealBoots extends GenericBoots {
    // Constants
    private static final int HEAL_AMOUNT = 1;

    // Constructors
    public HealBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Heal Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Heal Boots";
    }

    @Override
    public String getDescription() {
        return "Heal Boots restore your health as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public void stepAction() {
        PartyManager.getParty().healParty(HealBoots.HEAL_AMOUNT);
    }
}
