/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.creatures.PartyManager;
import net.worldwizard.worldz.generic.GenericPotion;

public class SuperHurtPotion extends GenericPotion {
    // Constructors
    public SuperHurtPotion() {
        super(false);
    }

    @Override
    public String getName() {
        return "Super Hurt Potion";
    }

    @Override
    public String getPluralName() {
        return "Super Hurt Potions";
    }

    @Override
    public int getEffectValue() {
        return -(PartyManager.getParty().getLeader().getCurrentHP() - 1);
    }

    @Override
    public String getDescription() {
        return "Super Hurt Potions bring you to the brink of death when picked up.";
    }
}