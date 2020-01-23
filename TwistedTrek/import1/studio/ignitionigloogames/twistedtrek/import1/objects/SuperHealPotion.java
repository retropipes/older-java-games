/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class SuperHealPotion extends GenericPotion {
    // Constructors
    public SuperHealPotion() {
	super(StatConstants.STAT_CURRENT_HP, false);
    }

    @Override
    public String getName() {
	return "Super Heal Potion";
    }

    @Override
    public String getPluralName() {
	return "Super Heal Potions";
    }

    @Override
    public byte getObjectID() {
	return (byte) 5;
    }

    @Override
    public int getEffectValue() {
	return PCManager.getPlayer().getMaximumHP();
    }

    @Override
    public String getDescription() {
	return "Super Heal Potions heal you completely when picked up.";
    }
}