/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class SuperHurtPotion extends GenericPotion {
    // Constructors
    public SuperHurtPotion() {
	super(StatConstants.STAT_CURRENT_HP, false);
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
    public byte getObjectID() {
	return (byte) 6;
    }

    @Override
    public int getEffectValue() {
	return -(PCManager.getPlayer().getCurrentHP() - 1);
    }

    @Override
    public String getDescription() {
	return "Super Hurt Potions bring you to the brink of death when picked up.";
    }
}