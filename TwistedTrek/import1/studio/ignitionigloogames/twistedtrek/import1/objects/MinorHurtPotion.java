/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MinorHurtPotion extends GenericPotion {
    // Fields
    private static final int MIN_HURT = -1;
    private static final int MAX_HURT = -5;

    // Constructors
    public MinorHurtPotion() {
	super(StatConstants.STAT_CURRENT_HP, true, MinorHurtPotion.MAX_HURT, MinorHurtPotion.MIN_HURT);
    }

    @Override
    public String getName() {
	return "Minor Hurt Potion";
    }

    @Override
    public String getPluralName() {
	return "Minor Hurt Potions";
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Minor Hurt Potions hurt you slightly when picked up.";
    }
}