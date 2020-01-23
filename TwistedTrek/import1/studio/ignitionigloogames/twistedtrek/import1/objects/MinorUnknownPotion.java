/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPotion;

public class MinorUnknownPotion extends GenericPotion {
    // Fields
    private static final int MIN_EFFECT = -3;
    private static final int MAX_EFFECT = 3;

    // Constructors
    public MinorUnknownPotion() {
	super(StatConstants.STAT_CURRENT_HP, true, MinorUnknownPotion.MIN_EFFECT, MinorUnknownPotion.MAX_EFFECT);
    }

    @Override
    public String getName() {
	return "Minor Unknown Potion";
    }

    @Override
    public String getPluralName() {
	return "Minor Unknown Potions";
    }

    @Override
    public byte getObjectID() {
	return (byte) 7;
    }

    @Override
    public String getDescription() {
	return "Minor Unknown Potions might heal you or hurt you slightly when picked up.";
    }
}